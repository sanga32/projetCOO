package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import domaine.Administrateur;
import domaine.Personne;
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
	 * Supprime le contenue de la table PROJET_MessagePrive
	 */

	public void clear() {
		try {
			String req = "delete from PROJET_MessagePrive";
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
	public void insert(Message m) {
		try {
			String req = "insert into PROJET_MessagePrive(idMessage, message, expediteur, destinataire, "
					+ "dateHeure, isReception, isExpiration, isChiffre, isPrioritaire) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, m.getId());
			ps.setString(2, m.getContenu());
			ps.setInt(3, m.getExpediteur().getId());
			ps.setInt(4, m.getDestinataire().getId());
			ps.setDate(5, m.getDateEnvoi());
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
			String req = "delete from PROJET_MessagePrive whene id =?";
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
	public Message findById(int id) {
		if (idValide(id)) {
			try {
				// on va chercher la personne
				String req = "SELECT idMessage, message, expediteur, destinataire, dateHeure, "
						+ "isReception, isExpiration, isChiffre, isPrioritaire  FROM PROJET_MessagePrive WHERE idMessage=?";
				PreparedStatement ps = conn.prepareStatement(req);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				rs.next();
				int id_message = rs.getInt("idMessage");
				String message = rs.getString("message");
				Personne expediteur = new Utilisateur(rs.getInt("expediteur")); //proxy par la suite
				Personne destinataire = new Utilisateur(rs.getInt("destinataire")); //proxy par la suite
				Date date = rs.getDate("dateHeure");
				Message m = new MessagePrive(id_message,message,expediteur,destinataire,date);
				ArrayList<Message> mess = new ArrayList<Message>();
				mess.add(m);
				if(rs.getInt("isReception") == 1){
					Message mACK = new MessageAvecAccuseReception(mess.get(mess.size()-1));
					mess.add(mACK);
				}
				if(rs.getInt("isExpiration") == 1){
					Message mExp = new MessageAvecAccuseReception(mess.get(mess.size()-1));
					mess.add(mExp);
				}
				if(rs.getInt("isChiffre") == 1){
					Message mCh = new MessageAvecAccuseReception(mess.get(mess.size()-1));
					mess.add(mCh);
				}
				if(rs.getInt("isPrioritaire") == 1){
					Message mPrio = new MessageAvecAccuseReception(mess.get(mess.size()-1));
					mess.add(mPrio);
				}
				return mess.get(mess.size()-1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Vérifie si l'id passé en paramètre se trouve en BDD
	 * 
	 * @param id
	 *            id à chercher en BDD
	 * @return vrai si une personne existe sinon false
	 */
	public boolean idValide(int id) {
		try {
			String req = "SELECT id FROM PROJET_MessagePrive WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			rs.getInt(1);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

}
