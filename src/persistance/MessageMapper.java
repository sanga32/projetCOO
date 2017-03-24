package persistance;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Interface.MessageInterface;
import Interface.PersonneInterface;
import Interface.SalonInterface;

import java.sql.Date;

import domaine.Administrateur;
import domaine.Personne;
import domaine.Salon;
import domaine.Utilisateur;
import message.*;
import settings.ConnectionInfo;


/**
 * MessageMapper est la classe permettant de faire le lien avec la BDD Elle
 * permet d'insérer, modifier, supprimer ou chercher un ou plusieurs messages
 * 
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
public class MessageMapper {
	private Connection conn;
	static MessageMapper inst;

	/**
	 * Permet d'initialiser le MessageMapper
	 */
	public MessageMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	/**
	 * Retourne l'instance de MessageMapper
	 */

	public static MessageMapper getInstance() {
		if (inst == null)
			inst = new MessageMapper();
		return inst;
	}

	/**
	 * Supprime le contenue de la table Projet_MessagePrive
	 */

	public void clear() {
		try {
			String req = "delete from Projet_MessagePrive";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert en BDD un message
	 * 
	 * @param toSend
	 *            message à insérer en BDD
	 */
	public void insert(MessageInterface toSend) {
		try {
			String req = "";
			req = "insert into Projet_MessagePrive( message, expediteur, destinataire, "
					+ "dateHeure, isReception, isExpiration, isChiffre, isPrioritaire) values(?,?,?,?,?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(req);

			String contenu = "";
			if (toSend.isChiffre()) {
				contenu = Cryptage.chiffrage(toSend.getContenu());
			} else {
				contenu = toSend.getContenu();
			}

			ps.setString(1, contenu);
			ps.setInt(2, (toSend).getExpediteur().getId());
			ps.setInt(3, (toSend).getDestinataire().getId());
			ps.setInt(5, (toSend.isReception()) ? 1 : 0);
			ps.setInt(6, (toSend.isExpiration()) ? 1 : 0);
			ps.setInt(7, (toSend.isChiffre()) ? 1 : 0);
			ps.setInt(8, (toSend.isPrioritaire()) ? 1 : 0);

			ps.setString(4, toSend.getDateEnvoi());

			ps.execute();
			conn.commit();
		} catch (SQLException | RemoteException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Delete le message de la table Projet_MessagePrive
	 * 
	 * @param p
	 *            personne à supprimer de la BDD
	 */
	public void delete(Message m) {
		try {
			String req = "delete from Projet_MessagePrive where idMessage =?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, m.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Récupère en BDD la list des message privé entre 2 personnes
	 * @param id_personne1
	 * 			id de l'utilisateur courant
	 * @param id_personne2
	 * 			id de la personne avec qui l'utilisateur veux parler
	 * @return la liste des messages
	 * @throws RemoteException 
	 */
	public List<MessageInterface> findListMessagePrive(int id_personne1, int id_personne2) throws RemoteException {
		List<MessageInterface> messages = new ArrayList<MessageInterface>();
		try {
			String req = "SELECT idMessage, message, expediteur, destinataire, dateHeure, "
					+ "isReception, isExpiration, isChiffre, isPrioritaire  FROM Projet_MessagePrive "
					+ "WHERE (destinataire=? && expediteur = ?) || (destinataire=? && expediteur = ?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_personne1);
			ps.setInt(2, id_personne2);
			ps.setInt(3, id_personne2);
			ps.setInt(4, id_personne1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id_message = rs.getInt("idMessage");
				String message = rs.getString("message");
				Personne expediteur = new VirtualProxyPersonne(rs.getInt("expediteur"));
				Personne destinataire = new VirtualProxyPersonne(rs.getInt("destinataire"));
				String date = rs.getString("dateHeure");
				Message m = new MessagePrive(id_message, message, expediteur, destinataire, date);
				if (rs.getInt("isChiffre") == 1) {
					m.setContenu(Cryptage.dechiffrage(m));
					m = new MessageChiffre(m);

				}
				if (rs.getInt("isReception") == 1) {
					if (id_personne1 == destinataire.getId())
						messageLu(m);
					m = new MessageAvecAccuseReception(m);
				}
				if (rs.getInt("isPrioritaire") == 1) {
					m = new MessagePrioritaire(m);
				}
				if (rs.getInt("isExpiration") == 1) {
					m = new MessageAvecExpiration(m);
					if (m.isExpire()) {
						delete(m);
					}else{
						messages.add(m);
					}
				} else {
					messages.add(m);
				}
			}
			return messages;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Récupère en BDD les messaeges d'un salon
	 * @param id_salon
	 * 			id du salon
	 * @param utilisateur
	 * 		 	l'utilisateur courant
	 * @return la liste des messages du salon
	 * @throws RemoteException 
	 */
	public List<MessageInterface> findListMessageSalon(int id_salon, PersonneInterface utilisateur) throws RemoteException {
		List<MessageInterface> messages = new ArrayList<MessageInterface>();
		try {
			String req = "SELECT idMessage, idSalon, idPersonne, message, isReception, "
					+ "isExpiration, isChiffre, isPrioritaire, dateHeure FROM Projet_DiscussionSalon "
					+ "WHERE idSalon=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_salon);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int idMessage = rs.getInt("idMessage");
				Salon salon = SalonMapper.getInstance().findById(rs.getInt("idSalon"));
				Personne expediteur = new VirtualProxyPersonne(rs.getInt("idPersonne"));
				String message = rs.getString("message");
				String date = rs.getString("dateHeure");
				Message m = new MessageSimple(idMessage, salon, expediteur, message, date);
				if (rs.getInt("isChiffre") == 1) {
					m.setContenu(Cryptage.dechiffrage(m));
					m = new MessageChiffre(m);

				}
				if (rs.getInt("isReception") == 1) {
					System.out.println("1");
					if (utilisateur.getId() != expediteur.getId()){
						System.out.println("2");
						messageLu(m);
					}
					m = new MessageAvecAccuseReception(m);
				}
				if (rs.getInt("isPrioritaire") == 1) {
					m = new MessagePrioritaire(m);
				}
				if (rs.getInt("isExpiration") == 1) {
					m = new MessageAvecExpiration(m);
					if (m.isExpire()) {
						delete(m);
					}else{
						messages.add(m);
					}
				} else {
					messages.add(m);
				}

			}
			return messages;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * insert en BDD un message dans un salon dans Projet_DiscussionSalon
	 * @param m
	 * 			message à envoyer
	 * @param salon
	 * 			salon
	 * @throws RemoteException 
	 */
	public void insert(MessageInterface m, SalonInterface salon) throws RemoteException {
		try {
			String req = "";
			req = "insert into Projet_DiscussionSalon( idSalon, idPersonne, message, isReception, "
					+ "isExpiration, isChiffre, isPrioritaire, dateHeure ) values(?,?,?,?,?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(req);

			String contenu = "";
			if (m.isChiffre()) {
				contenu = Cryptage.chiffrage(m.getContenu());
			} else {
				contenu = m.getContenu();
			}
			
			ps.setInt(1, salon.getId());
			ps.setInt(2, (m).getExpediteur().getId());
			ps.setString(3, contenu);
			ps.setInt(4, (m.isReception()) ? 1 : 0);
			ps.setInt(5, (m.isExpiration()) ? 1 : 0);
			ps.setInt(6, (m.isChiffre()) ? 1 : 0);
			ps.setInt(7, (m.isPrioritaire()) ? 1 : 0);
			ps.setString(8, m.getDateEnvoi());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet à un message avec accusé de reception d'avoir le sigle [Vu] une fois vu par le destinataire
	 * @param message
	 * 			message à accusé de reception
	 */
	public void messageLu(Message message) {
		try {
			String req = "";
			String newMessage = "";
			if(message.getDestinataire() != null){
				System.out.println("3");
				req = "UPDATE Projet_MessagePrive SET isReception = 0, message = ? WHERE idMessage=?";
				newMessage = message.getContenu() + "  [Vu par " + message.getDestinataire() + "]";
			}else{
				System.out.println("4");
				req = "UPDATE Projet_DiscussionSalon SET isReception = 0, message = ? WHERE idMessage=?";
				newMessage = message.getContenu() + "  [Vu]";
			}
			PreparedStatement ps = conn.prepareStatement(req);
			if(message.isChiffre()){
				newMessage = Cryptage.chiffrage(newMessage);
			}
			ps.setString(1, newMessage);
			System.out.println(message.getId());
			ps.setInt(2, message.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
