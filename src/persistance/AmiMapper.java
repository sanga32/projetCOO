package persistance;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Interface.PersonneInterface;
import domaine.Personne;
import settings.ConnectionInfo;


/**
 * AmiMapper est la classe permettant de faire le lien avec la BDD Elle
 * permet d'ins�rer, modifier, supprimer ou chercher un ami
 * 
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
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
	 * Ins�re 2 personne dans la table Projet_Ami
	 * 
	 * @param expediteur
	 * @param destinataire
	 *            p1 et p2 sont les deux nouveau ami � ins�rer en bdd
	 * @return le nombre de ligne ins�r� en BDD
	 */
	public int insert(PersonneInterface expediteur, PersonneInterface destinataire) {
		int nbLigne = 0;
		// On ins�re uniquement si ils ne sont pas d�ja ami
		if (!isAmi(expediteur, destinataire)) {
			try {
				String req = "insert into Projet_Ami(idPersonne1,idPersonne2) values(?,?)";
				PreparedStatement ps = conn.prepareStatement(req);
				ps.setInt(1, expediteur.getId());
				ps.setInt(2, destinataire.getId());
				nbLigne = ps.executeUpdate();
				conn.commit();
			} catch (SQLException | RemoteException e) {
				e.printStackTrace();
			}
		}
		return nbLigne;

	}

	/**
	 * Delete un couple d'ami
	 * 
	 * @param p
	 * @param p2
	 *           p1 et p2 sont les 2 amis � supprimer en BDD
	 * @throws RemoteException 
	 */
	public void delete(PersonneInterface p, PersonneInterface p2) throws RemoteException {
		try {
			String req = "delete from Projet_Ami where (idPersonne1 =? && idPersonne2 =?) || (idPersonne2 =? && idPersonne1 =?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ps.setInt(2, p2.getId());
			ps.setInt(3, p.getId());
			ps.setInt(4, p2.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * on delete tout les amis de la personne pass� en param�tre
	 * @param p
	 * 			personne
	 */
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
	 * V�rifie si 2 personnes sont amis
	 * 
	 * @param expediteur
	 *            une personne
	 * @param destinataire
	 *            une personne
	 * @return vrai si ils sont ami, false sinon
	 */
	public boolean isAmi(PersonneInterface expediteur, PersonneInterface destinataire) {
		try {
			String req = "SELECT idPersonne FROM Projet_Ami where (idPersonne1 =? && idPersonne2 =?) || (idPersonne2 =? && idPersonne1 =?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, expediteur.getId());
			ps.setInt(2, destinataire.getId());
			ps.setInt(3, expediteur.getId());
			ps.setInt(4, destinataire.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			rs.getInt(1);
			return true;
		} catch (SQLException | RemoteException e) {
			return false;
		}
	}
	
	/**
	 * r�cup�re les amis de la personne pass� en param�tre
	 * @param id_personne
	 * 					id de la personne
	 * @return la liste d'ami de la personne
	 * @throws RemoteException 
	 */
	public List<PersonneInterface> getAmis(int id_personne) throws RemoteException {
		try {
			List<PersonneInterface> amis = new ArrayList<PersonneInterface>();
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
