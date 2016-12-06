package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domaine.*;
import settings.ConnectionInfo;

/**
 * PersonneMapper est la classe permettant de faire le lien avec la BDD Elle
 * permet d'insérer, modifier, supprimer ou chercher une personne
 * 
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
public class PersonneMapper {
	private Connection conn;
	static PersonneMapper inst;

	/**
	 * Permet d'initialiser le PersonneMapper
	 */
	public PersonneMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	/**
	 * Retourne l'instance de PersonneMapper
	 */

	public static PersonneMapper getInstance() {
		if (inst == null)
			inst = new PersonneMapper();
		return inst;
	}

	/**
	 * Supprime le contenue de la table Projet_personne
	 */

	public void clear() {
		try {
			String req = "delete from Projet_Personne";
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
	public int insert(Personne p) {
		int nbLigne = 0;
		try {
			String req = "insert into Projet_Personne(idPersonne,login,mdp,nom,prenom) values(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ps.setString(2, p.getLogin());
			ps.setString(3, p.getMdp());
			ps.setString(4, p.getNom());
			ps.setString(5, p.getPrenom());
			nbLigne = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbLigne;

	}

	/**
	 * Delete la personne de la table
	 * 
	 * @param p
	 *            personne à supprimer de la BDD
	 */
	public void delete(Personne p) {
		try {
			String req = "delete from Projet_Personne where idPersonne =?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Modifie une personne dans la BDD
	 * 
	 * @param p
	 *            personne à modifier
	 */
	public int update(Personne p) {
		int nbLigne = 0;
		try {
			String req = "UPDATE Projet_Personne SET login =?, mdp=? , nom=? , prenom=? WHERE idPersonne=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setString(1, p.getLogin());
			ps.setString(2, p.getMdp());
			ps.setString(3, p.getNom());
			ps.setString(4, p.getPrenom());
			ps.setInt(5, p.getId());
			nbLigne = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbLigne;
	}

	/**
	 * Recherche une personne à partir de son ID
	 * 
	 * @param id
	 *            id de la personne à trouver en BDD
	 * @return une personne
	 */
	public Personne findById(int id) {
		if (idValide(id)) {
			try {
				// on va chercher la personne
				String req = "SELECT idPersonne, login, mdp, nom, prenom, admin  FROM Projet_Personne WHERE idPersonne=?";
				PreparedStatement ps = conn.prepareStatement(req);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				rs.next();
				int id_personne = rs.getInt(1);
				String login = rs.getString("login");
				String mdp = rs.getString("mdp");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Personne p;
				if(rs.getInt("admin") == 0){
					p = new Utilisateur(id_personne, login, mdp, nom, prenom);
				}else{
					p = new Administrateur(id_personne, login, mdp, nom, prenom);
				}
				return p;
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
			String req = "SELECT idPersonne FROM Projet_Personne WHERE idPersonne=?";
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