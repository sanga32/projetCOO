package persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domaine.Interet;
import domaine.Personne;
import message.Message;
import message.MessageAvecAccuseReception;
import message.MessageAvecExpiration;
import message.MessageChiffre;
import message.MessagePrioritaire;
import message.MessagePrive;
import settings.ConnectionInfo;

public class InteretMapper {
	private Connection conn;
	static InteretMapper inst;

	/**
	 * Permet d'initialiser le PersonneMapper
	 */
	public InteretMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	/**
	 * Retourne l'instance de PersonneMapper
	 */

	public static InteretMapper getInstance() {
		if (inst == null)
			inst = new InteretMapper();
		return inst;
	}

	/**
	 * Supprime le contenue de la table Projet_Interet
	 */

	public void clear() {
		try {
			String req = "delete from Projet_Interet";
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
	public void insert(Interet i) {
		try {
			String req = "insert into Projet_Interet(idInteret, description) values(?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, i.getId());
			ps.setString(2, i.getNom());
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
			String req = "delete from Projet_Interet where idInteret =?";
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
			// on va chercher la personne
			String req = "SELECT idInteret, message FROM Projet_Interet WHERE idInteret=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id_message = rs.getInt("idMessage");
			String message = rs.getString("message");
			Personne expediteur = new VirtualProxyPersonne(rs.getInt("expediteur"));
			Personne destinataire = new VirtualProxyPersonne(rs.getInt("destinataire"));
			Date date = rs.getDate("dateHeure");
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
}
