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
			String req = "insert into Projet_Personne(login,mdp,nom,prenom) values(?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setString(1, p.getLogin());
			ps.setString(2, p.getMdp());
			ps.setString(3, p.getNom());
			ps.setString(4, p.getPrenom());
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
			AmiMapper.getInstance().delete(p);
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
		try {
			// on va chercher la personne
			String req = "SELECT idPersonne, login, mdp, nom, prenom, admin  FROM Projet_Personne WHERE idPersonne=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id_personne = rs.getInt("idPersonne");
			String login = rs.getString("login");
			String mdp = rs.getString("mdp");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			List<Interet> interets = InteretPersonneMapper.getInstance().findInteretByPersonne(id_personne);
			List<SousInteret> sousInterets = InteretPersonneMapper.getInstance().findSousInteretByPersonne(id_personne);
			Personne p;
			if (rs.getInt("admin") == 0) {
				p = new Utilisateur(id_personne, login, mdp, nom, prenom, interets, sousInterets);
			} else {
				p = new Administrateur(id_personne, login, mdp, nom, prenom, interets, sousInterets);
			}
			SalonMapper.getInstance().setSalons(p); //init salon de la personne
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Personne findByLogin(String login) {
		try {
			// on va chercher la personne
			String req = "SELECT idPersonne, login, mdp, nom, prenom, admin  FROM Projet_Personne WHERE login=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id_personne = rs.getInt(1);
			String l = rs.getString("login");
			String mdp = rs.getString("mdp");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			List<Interet> interets = InteretPersonneMapper.getInstance().findInteretByPersonne(id_personne);
			List<SousInteret> sousInterets = InteretPersonneMapper.getInstance().findSousInteretByPersonne(id_personne);
			Personne p;
			if (rs.getInt("admin") == 0) {
				p = new Utilisateur(id_personne, login, mdp, nom, prenom, interets, sousInterets);
			} else {
				p = new Administrateur(id_personne, login, mdp, nom, prenom, interets, sousInterets);
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public void setSalons(Personne p) {
		List<Salon> salons = new ArrayList<Salon>();
		try {
			String req = "SELECT s.idSalon, nom, modo  FROM "
					+ "Projet_OccupeSalon o join Projet_Salon s on o.idSalon=s.idSalon  WHERE idPersonne=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("idSalon");
				String nom = rs.getString("nom");
				Personne modo = new VirtualProxyPersonne(rs.getInt("modo"));
				List<Personne> personnes = SalonMapper.getInstance().getPersonnes(id);
				Salon s = new Salon(id, nom, p,personnes);
				salons.add(s);
			}
			String req2 = "SELECT idSalon, nom, modo  FROM Projet_Salon  WHERE modo=?";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, p.getId());
			ResultSet rs2 = ps2.executeQuery();
			while (rs2.next()) {
				int id = rs2.getInt("idSalon");
				String nom = rs2.getString("nom");
				Personne modo = new VirtualProxyPersonne(rs2.getInt("modo"));
				List<Personne> personnes = SalonMapper.getInstance().getPersonnes(id);
				Salon s = new Salon(id, nom, p,personnes);
				salons.add(s);
			}
			p.setSalons(salons);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Personne> findNewPersonne(int id){
		List<Personne> personnes = new ArrayList<Personne>();
		try {
			String req = "SELECT idPersonne, login, mdp, prenom, nom, admin from Projet_Personne  WHERE idPersonne !=? and "
					+ "idPersonne not in (select idPersonne1 from Projet_Ami where idPersonne2=?) and idPersonne not in "
					+ "(select idPersonne2 from Projet_Ami where idPersonne1=?) and idPersonne not"
					+ " in (select destinataire from Projet_DemandeAmi pd join Projet_Notification pn on pd.idNotification = pn.idNotification where expediteur = ? )";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ps.setInt(2, id);
			ps.setInt(3, id);
			ps.setInt(4, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id_personne = rs.getInt("idPersonne");
				String login = rs.getString("login");
				String mdp = rs.getString("mdp");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				List<Interet> interets = InteretPersonneMapper.getInstance().findInteretByPersonne(id_personne);
				List<SousInteret> sousInterets = InteretPersonneMapper.getInstance().findSousInteretByPersonne(id_personne);
				Personne p;
				if (rs.getInt("admin") == 0) {
					p = new Utilisateur(id_personne, login, mdp, nom, prenom, interets, sousInterets);
				} else {
					p = new Administrateur(id_personne, login, mdp, nom, prenom, interets, sousInterets);
				}
				personnes.add(p);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personnes;
	}
	
	public Personne findByLogMdp(String login, String mdp) {
		// TODO Auto-generated method stub
		try {
			// on va chercher la personne
			String req = "SELECT idPersonne, login, mdp, nom, prenom, admin  FROM Projet_Personne WHERE login=? AND mdp=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setString(1, login);
			ps.setString(2, mdp);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id_personne = rs.getInt(1);
			String l = rs.getString("login");
			String pass = rs.getString("mdp");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			List<Interet> interets = InteretPersonneMapper.getInstance().findInteretByPersonne(id_personne);
			List<SousInteret> sousInterets = InteretPersonneMapper.getInstance().findSousInteretByPersonne(id_personne);
			Personne p;
			if (rs.getInt("admin") == 0) {
				p = new Utilisateur(id_personne, login, pass, nom, prenom, interets, sousInterets);
			} else {
				p = new Administrateur(id_personne, login, pass, nom, prenom, interets, sousInterets);
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}