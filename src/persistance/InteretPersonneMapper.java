package persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domaine.Interet;
import domaine.Personne;
import domaine.SousInteret;
import message.Message;
import message.MessageAvecAccuseReception;
import message.MessageAvecExpiration;
import message.MessageChiffre;
import message.MessagePrioritaire;
import message.MessagePrive;
import settings.ConnectionInfo;

public class InteretPersonneMapper {
	private Connection conn;
	static InteretPersonneMapper inst;

	/**
	 * Permet d'initialiser le PersonneMapper
	 */
	public InteretPersonneMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	/**
	 * Retourne l'instance de PersonneMapper
	 */

	public static InteretPersonneMapper getInstance() {
		if (inst == null)
			inst = new InteretPersonneMapper();
		return inst;
	}

	/**
	 * Supprime le contenue de la table Projet_Interet
	 */

	public void clear() {
		try {
			String req = "delete from Projet_InteretPersonne";
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
	public void insert(Personne p, Interet i) {
		try {
			String req = "insert into Projet_InteretPersonne(idPersonne, idInteret) values(?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ps.setInt(2, i.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void insert(Personne p, SousInteret si) {
		try {
			String req = "insert into Projet_InteretPersonne(idPersonne, idInteret,idSousInteret) values(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ps.setInt(2, si.getInteret().getId());
			ps.setInt(3, si.getId());
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
	/*
	public Interet find(int id) {
		try {
			// on va chercher la personne
			String req = "SELECT idInteret, message FROM Projet_Interet WHERE idInteret=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int idInteret = rs.getInt("idMessage");
			String message = rs.getString("message");
			Interet i = new Interet(idInteret, message);
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	*/
}
