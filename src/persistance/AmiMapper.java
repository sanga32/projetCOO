package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domaine.Personne;
import settings.ConnectionInfo;

public class AmiMapper {
	private Connection conn;
	static AmiMapper inst;

	/**
	 * Permet d'initialiser AmiMapper
	 */
	public AmiMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	/**
	 * Retourne l'instance de AmiMapper
	 */

	public static AmiMapper getInstance() {
		if (inst == null)
			inst = new AmiMapper();
		return inst;
	}

	/**
	 * Supprime le contenue de la table Projet_Ami
	 */

	public void clear() {
		try {
			String req = "delete from Projet_Ami";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insère 2 personne dans la table Projet_Ami
	 * 
	 * @param p1
	 * @param p2
	 *            p1 et p2 sont les deux nouveau ami à insérer en bdd
	 */
	public int insert(Personne p1, Personne p2) {
		int nbLigne = 0;
		// On insère uniquement si ils ne sont pas déja ami
		if (!isAmi(p1, p2)) {
			try {
				String req = "insert into Projet_Ami(idPersonne1,idPersonne2) values(?,?)";
				PreparedStatement ps = conn.prepareStatement(req);
				ps.setInt(1, p1.getId());
				ps.setInt(2, p2.getId());
				nbLigne = ps.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nbLigne;

	}

	/**
	 * Delete un couple d'ami
	 * 
	 * @param p
	 *            personne à supprimer de la BDD
	 */
	public void delete(Personne p1, Personne p2) {
		try {
			String req = "delete from Projet_Ami where (idPersonne1 =? && idPersonne2 =?) || (idPersonne2 =? && idPersonne1 =?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p1.getId());
			ps.setInt(2, p2.getId());
			ps.setInt(3, p1.getId());
			ps.setInt(4, p2.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Personne p) {
		try {
			String req = "delete from Projet_Ami where idPersonne1 =? || idPersonne2 =?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ps.setInt(2, p.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Vérifie si 2 personnes sont amis
	 * 
	 * @param p1
	 *            une personne
	 * @param p2
	 *            une personne
	 * @return vrai si ils sont ami, false sinon
	 */
	public boolean isAmi(Personne p1, Personne p2) {
		try {
			String req = "SELECT idPersonne FROM Projet_Ami where (idPersonne1 =? && idPersonne2 =?) || (idPersonne2 =? && idPersonne1 =?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p1.getId());
			ps.setInt(2, p2.getId());
			ps.setInt(3, p1.getId());
			ps.setInt(4, p2.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			rs.getInt(1);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public List<Personne> getAmis(int id_personne) {
		try {
			List<Personne> amis = new ArrayList<Personne>();
			String req = "SELECT idPersonne1 FROM Projet_Ami WHERE idPersonne2=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_personne);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				amis.add(PersonneMapper.getInstance().findById(rs.getInt("idPersonne1")));
			}
			
			String req2 = "SELECT idPersonne2 FROM Projet_Ami WHERE idPersonne1=?";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, id_personne);
			ResultSet rs2 = ps2.executeQuery();
			while (rs2.next()) {
				amis.add(PersonneMapper.getInstance().findById(rs2.getInt("idPersonne2")));
			}
			return amis;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
