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
	 *            personne � ins�rer en BDD
	 */
	public void insert(Message m) {
		try {
			String req = "insert into Projet_MessagePrive(idMessage, message, expediteur, destinataire, "
					+ "dateHeure, isReception, isExpiration, isChiffre, isPrioritaire) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, m.getId());
			ps.setString(2, m.getContenu());
			ps.setInt(3, m.getExpediteur().getId());
			ps.setInt(4, m.getDestinataire().getId());
			ps.setDate(5, m.getDateEnvoi());
			ps.setInt(6, (m.isReception())?1:0);
			ps.setInt(7, (m.isExpiration())?1:0);
			ps.setInt(8, (m.isChiffre())?1:0);
			ps.setInt(9, (m.isPrioritaire())?1:0);
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
	 *            personne � supprimer de la BDD
	 */
	public void delete(Message m) {
		try {
			String req = "delete from Projet_MessagePrive whene id =?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, m.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recherche une personne � partir de son ID
	 * 
	 * @param id
	 *            id de la personne � trouver en BDD
	 * @return une personne
	 */
	public Message findByDestinataire(int id) {
		try {
			// on va chercher la personne
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
