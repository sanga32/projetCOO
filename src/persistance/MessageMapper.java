package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import domaine.Administrateur;
import domaine.Personne;
import domaine.Salon;
import domaine.Utilisateur;
import message.*;
import settings.ConnectionInfo;

public class MessageMapper {
	private Connection conn;
	static MessageMapper inst;

	/**
	 * Permet d'initialiser le PersonneMapper
	 */
	public MessageMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	/**
	 * Retourne l'instance de PersonneMapper
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
	 * Insert en BDD une personne
	 * 
	 * @param p
	 *            personne à insérer en BDD
	 */
	public void insert(Message toSend) {
		try {
			String req = "";
			req = "insert into Projet_MessagePrive( message, expediteur, destinataire, "
					+ "dateHeure, isReception, isExpiration, isChiffre, isPrioritaire) values(?,?,?,?,?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(req);

			// if(classeMessage.equals("class message.MessagePrive")==true){

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
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Delete la personne de la table
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
	 * Recherche une personne à partir de son ID
	 * 
	 * @param id
	 *            id de la personne à trouver en BDD
	 * @return une personne
	 */
	public Message findByDestinataire(int id) {
		try {
			String req = "SELECT idMessage, message, expediteur, destinataire, dateHeure, "
					+ "isReception, isExpiration, isChiffre, isPrioritaire  FROM Projet_MessagePrive WHERE destinataire=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id_message = rs.getInt("idMessage");
			String message = rs.getString("message");
			Personne expediteur = new VirtualProxyPersonne(rs.getInt("expediteur"));
			Personne destinataire = new VirtualProxyPersonne(rs.getInt("destinataire"));
			String date = rs.getString("dateHeure");
			Message m = new MessagePrive(id_message, message, expediteur, destinataire, date);
			if (rs.getInt("isReception") == 1) {
				m = new MessageAvecAccuseReception(m);
			}
			if (rs.getInt("isExpiration") == 1) {
				m = new MessageAvecExpiration(m);
			}
			if (rs.getInt("isChiffre") == 1) {
				m = new MessageChiffre(m);
			}
			if (rs.getInt("isPrioritaire") == 1) {
				m = new MessagePrioritaire(m);
			}
			return m;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Message> findListMessagePrive(int id_personne1, int id_personne2) {
		List<Message> messages = new ArrayList<Message>();
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

	public List<Message> findListMessageSalon(int id_salon, Personne utilisateur) {
		List<Message> messages = new ArrayList<Message>();
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

	public void insert(Message toSend, Salon salon) {
		try {
			String req = "";
			req = "insert into Projet_DiscussionSalon( idSalon, idPersonne, message, isReception, "
					+ "isExpiration, isChiffre, isPrioritaire, dateHeure ) values(?,?,?,?,?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(req);

			String contenu = "";
			if (toSend.isChiffre()) {
				contenu = Cryptage.chiffrage(toSend.getContenu());
			} else {
				contenu = toSend.getContenu();
			}
			
			ps.setInt(1, salon.getId());
			ps.setInt(2, (toSend).getExpediteur().getId());
			ps.setString(3, contenu);
			ps.setInt(4, (toSend.isReception()) ? 1 : 0);
			ps.setInt(5, (toSend.isExpiration()) ? 1 : 0);
			ps.setInt(6, (toSend.isChiffre()) ? 1 : 0);
			ps.setInt(7, (toSend.isPrioritaire()) ? 1 : 0);
			ps.setString(8, toSend.getDateEnvoi());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
