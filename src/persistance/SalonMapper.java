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
import Interface.SalonInterface;
import domaine.*;
import server.Salon;
import settings.ConnectionInfo;


/**
 * SalonMapper est la classe permettant de faire le lien avec la BDD 
 * Elle permet d'insérer, modifier, supprimer ou chercher un salon
 * 
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
public class SalonMapper {
	private Connection conn;
	static SalonMapper inst;

	/**
	 * Permet d'initialiser le SalonMapper
	 */
	public SalonMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	/**
	 * Retourne l'instance de SalonMapper
	 */
	public static SalonMapper getInstance() {
		if (inst == null)
			inst = new SalonMapper();
		return inst;
	}

	/**
	 * Supprime le contenue de la table Projet_Salon et Projet_OccupeSalon
	 */
	public void clear() {
		try {
			String req = "delete from Projet_Salon";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Regarde si le salon ne contient personne
	 * @return True si le modo est le seul occupant du salon
	 */
	public boolean isEmpty(Salon s) {
		try {
			String req = "Select idSalon from Projet_OccupeSalon where idSalon";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, s.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			rs.getInt("IdSalon");
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * insert un salon en BDD (Projet_Salon)
	 * @param s
	 * 		 le salon a mettre en base
	 * @return le nombre de ligne inséré en base
	 * @throws RemoteException 
	 */
	public int insertSalon(Salon s) throws SQLException, RemoteException {
		int nbLigne = 0;
		String req = "insert into Projet_Salon( nom, modo) values(?,?)";
		PreparedStatement ps = conn.prepareStatement(req);
		ps.setString(1, s.getNom());
		ps.setInt(2, s.getModo().getId());
		nbLigne = ps.executeUpdate();
		conn.commit();

		return nbLigne;
	}

	/**
	 * insert une personne dans le salon passé en paramètre en BDD (Projet_OccupeSalon)
	 * @param s
	 * 		 le salon 
	 * @param p
	 * 		 la personne à mettre dans le salon
	 * @return le nombre de ligne inséré en base
	 * @throws RemoteException 
	 */
	public int insertPersonne(SalonInterface s, PersonneInterface p) throws SQLException, RemoteException {
		int nbLigne = 0;

		String req = "insert into Projet_OccupeSalon(idSalon, idPersonne) values(?,?)";
		PreparedStatement ps = conn.prepareStatement(req);
		ps.setInt(1, s.getId());
		ps.setInt(2, p.getId());
		nbLigne = ps.executeUpdate();
		conn.commit();

		return nbLigne;
	}

	/**
	 * delete le salon passé en paramètre
	 * @param s
	 * 		salon à delete de la BDD
	 */
	public void delete(Salon s) {
		try {
			String req = "delete from Projet_Salon where idSalon=? ";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, s.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * une personne quitte le salon
	 * @param p
	 * 		personne qui quitte le salon
	 * @param s
	 * 		salon que le personne quitte
	 * @throws RemoteException 
	 */
	public void leaveSalon(PersonneInterface p, SalonInterface s) throws RemoteException{
		try {
			String req = "delete from Projet_OccupeSalon where idSalon=? AND idPersonne=? ";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, s.getId());
			ps.setInt(2, p.getId());

			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * récupère un salon en BDD à partir de son ID
	 * @param id_salon
	 * 			id du salon à récupérer
	 * @return
	 * @throws RemoteException 
	 */
	public Salon findById(int id_salon) throws RemoteException {
		try {
			String req = "SELECT idSalon, nom, modo  FROM Projet_Salon WHERE idSalon=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_salon);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt("idSalon");
			String nom = rs.getString("nom");
			Personne p = new VirtualProxyPersonne(rs.getInt("modo"));
			List<PersonneInterface> personnes = getPersonnes(id);
			Salon s = new Salon(id, nom, p,personnes);
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SalonInterface> getSalons() throws SQLException, RemoteException{
		List<SalonInterface> salons = new ArrayList<SalonInterface>();
		String req = "SELECT idSalon, nom, modo  FROM Projet_Salon";
		PreparedStatement ps = conn.prepareStatement(req);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("idSalon");
			String nom = rs.getString("nom");
			Personne modo = new VirtualProxyPersonne(rs.getInt("modo"));
			List<PersonneInterface> personnes = getPersonnes(id);
			Salon s = new Salon(id, nom, modo,personnes);
			salons.add(s);
		}
		return salons;
	}
	/**
	 * recupère un salon en BDD à partir de son nom
	 * @param nom
	 * 		nom du salon à récupérer
	 * @return un salon
	 * @throws RemoteException 
	 */
	public Salon findByNom(String nom) throws RemoteException {
		try {
			String req = "SELECT idSalon, nom, modo  FROM Projet_Salon WHERE nom=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt("idSalon");
			String n = rs.getString("nom");
			Personne p = new VirtualProxyPersonne(rs.getInt("modo"));
			List<PersonneInterface> personnes = getPersonnes(id);
			Salon s = new Salon(id, nom, p,personnes);
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Récupère les personnes d'un salon en BDD 
	 * @param id_salon
	 * 			id du salon 
	 * @return la liste de personne dans le salon passé en paramètre
	 * @throws RemoteException 
	 */
	public List<PersonneInterface> getPersonnes(int id_salon) throws RemoteException {
		List<PersonneInterface> personnes = new ArrayList<PersonneInterface>();
		try {
			String req = "SELECT idPersonne  FROM Projet_OccupeSalon WHERE idSalon=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_salon);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("idPersonne");
				Personne p = new VirtualProxyPersonne(id);
				personnes.add(p);
			}
			return personnes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Récupère les salons d'une personne et initialise ses salons
	 * @param p
	 * 		Personne à qui on récupère les salons
	 * @throws RemoteException 
	 */
	public List<SalonInterface> getSalons(PersonneInterface p) throws RemoteException {
		List<SalonInterface> salons = new ArrayList<SalonInterface>();
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
				List<PersonneInterface> personnes = getPersonnes(id);
				Salon s = new Salon(id, nom, modo,personnes);
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
				List<PersonneInterface> personnes = getPersonnes(id);
				Salon s = new Salon(id, nom, modo,personnes);
				salons.add(s);
			}
			return salons;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * vérifie si une personne est modo d'un salon
	 * @param p
	 * 		personne 
	 * @param id
	 * 		id du salon
	 * @return True si la personne est modo du salon
	 * @throws SQLException
	 */
	public boolean isModo(Personne p, int id) throws SQLException {
		// TODO Auto-generated method stub
		String req = "SELECT idSalon, nom, modo  FROM   Projet_Salon  WHERE modo=? AND idSalon=?";
		PreparedStatement ps = conn.prepareStatement(req);
		ps.setInt(1, p.getId());
		ps.setInt(2, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Modifie le modo d'un salon
	 * @param s
	 * 		salon
	 * @param p
	 * 		le nouveau modo
	 * @throws RemoteException 
	 */
	public void updateModo(SalonInterface s, PersonneInterface p) throws RemoteException{
		try {
			String req = "UPDATE Projet_Salon SET  modo=? WHERE idSalon=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ps.setInt(2, s.getId());
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
