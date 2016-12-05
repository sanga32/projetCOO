package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domaine.Administrateur;
import domaine.Interet;
import domaine.Personne;
import domaine.SousInteret;
import domaine.Utilisateur;
import settings.ConnectionInfo;

public class SousInteretMapper {

	private Connection conn;
	static SousInteretMapper inst;

	/**
	 * Permet d'initialiser le SousInteretMapper
	 */
	public SousInteretMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	/**
	 * Retourne l'instance de SousInteretMapper
	 */

	public static SousInteretMapper getInstance() {
		if (inst == null)
			inst = new SousInteretMapper();
		return inst;
	}

	/**
	 * Supprime le contenue de la table Projet_SousInteret
	 */

	public void clear() {
		try {
			String req = "delete from Projet_SousInteret";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert en BDD un Sous Interet
	 * 
	 * @param s
	 *            SousInteret à insérer en BDD
	 */
	public int insert(SousInteret s) {
		int nbLigne = 0;
		try {
			String req = "insert into Projet_SousInteret(idSousInteret,description,idInteret) values(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, s.getId());
			ps.setString(2, s.getNom());
			ps.setInt(3, s.getInteret().getId());
			nbLigne = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbLigne;

	}

	/**
	 * Delete le SousInteret de la table
	 * 
	 * @param s
	 *            SousInteret à supprimer de la BDD
	 */
	public void delete(SousInteret s) {
		try {
			String req = "delete from Projet_SousInteret where idPersonne =?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, s.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Modifie un SousInteret dans la BDD
	 * 
	 * @param s
	 *            SousInteret à modifier
	 */
	public void update(SousInteret s) {
		try {
			String req = "UPDATE Projet_SousInteret SET idSousInteret =?, description=? , idInteret=?  WHERE idSousInteret=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, s.getId());
			ps.setString(2, s.getNom());
			ps.setInt(3, s.getInteret().getId());
			ps.setInt(4, s.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recherche un SOusInteret à partir de son ID
	 * 
	 * @param id
	 *            id du SousInteret à trouver en BDD
	 * @return une personne
	 */
	public SousInteret findById(int id) {

		try {
			// on va chercher la personne
			String req = "SELECT idSousInteret, description, idInteret  FROM Projet_SousInteret WHERE idSousInteret=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id_sousInteret = rs.getInt(1);
			Interet i;
			i = InteretMapper.getInstance().findById(rs.getInt("idInteret"));
			SousInteret s;
			s = new SousInteret(id_sousInteret, rs.getString("description"), i);
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
