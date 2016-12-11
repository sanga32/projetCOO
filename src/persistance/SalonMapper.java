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

public class SalonMapper {
	private Connection conn;
	static SalonMapper inst;

	public SalonMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	public static SalonMapper getInstance() {
		if (inst == null)
			inst = new SalonMapper();
		return inst;
	}

	public void clear() {
		try {
			String req = "delete from Projet_Salon";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.execute();
			String req2 = "delete from Projet_OccupeSalon";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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

	public int insertSalon(Salon s) throws SQLException {
		int nbLigne = 0;
		String req = "insert into Projet_Salon( nom, modo) values(?,?)";
		PreparedStatement ps = conn.prepareStatement(req);
		ps.setString(1, s.getNom());
		ps.setInt(2, s.getModo().getId());
		nbLigne = ps.executeUpdate();
		conn.commit();

		return nbLigne;
	}

	public int insertPersonne(Salon s, Personne p) throws SQLException {
		int nbLigne = 0;

		String req = "insert into Projet_OccupeSalon(idSalon, idPersonne) values(?,?)";
		PreparedStatement ps = conn.prepareStatement(req);
		ps.setInt(1, s.getId());
		ps.setInt(2, p.getId());
		nbLigne = ps.executeUpdate();
		conn.commit();

		return nbLigne;
	}

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
	
	public void leaveSalon(Personne p, Salon s){
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

	public Salon findById(int id_salon) {
		try {
			String req = "SELECT idSalon, nom, modo  FROM Projet_Salon WHERE idSalon=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_salon);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt("idSalon");
			String nom = rs.getString("nom");
			Personne p = new VirtualProxyPersonne(rs.getInt("modo"));
			List<Personne> personnes = getPersonnes(id);
			Salon s = new Salon(id, nom, p,personnes);
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Salon findByNom(String nom) {
		try {
			String req = "SELECT idSalon, nom, modo  FROM Projet_Salon WHERE nom=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt("idSalon");
			String n = rs.getString("nom");
			Personne p = new VirtualProxyPersonne(rs.getInt("modo"));
			List<Personne> personnes = getPersonnes(id);
			Salon s = new Salon(id, nom, p,personnes);
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Personne> getPersonnes(int id_salon) {
		List<Personne> personnes = new ArrayList<Personne>();
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

	// init les salons d'une personne
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
				List<Personne> personnes = getPersonnes(id);
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
				List<Personne> personnes = getPersonnes(id);
				Salon s = new Salon(id, nom, p,personnes);
				salons.add(s);
			}
			p.setSalons(salons);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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

}
