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

	public int insertSalon(Salon s) {
		int nbLigne = 0;
		if (!salonExiste(s.getId())) {
			try {
				String req = "insert into Projet_Salon(idSalon, nom, modo) values(?,?,?)";
				PreparedStatement ps = conn.prepareStatement(req);
				ps.setInt(1, s.getId());
				ps.setString(2, s.getNom());
				ps.setInt(3, s.getModo().getId());
				nbLigne = ps.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nbLigne;
	}

	public int insertPersonne(Salon s, Personne p) {
		int nbLigne = 0;
		try {
			String req = "insert into Projet_OccupeSalon(idSalon, idPersonne) values(?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, s.getId());
			ps.setInt(2, p.getId());
			nbLigne = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public boolean salonExiste(int id_salon) {
		try {
			String req = "SELECT idSalon FROM Projet_Salon where idSalon=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_salon);
			ResultSet rs = ps.executeQuery();
			rs.next();
			rs.getInt(1);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public Salon findById(int id_salon) {
		if (salonExiste(id_salon)) {
			try {
				String req = "SELECT idSalon, nom, modo  FROM Projet_Salon WHERE idSalon=?";
				PreparedStatement ps = conn.prepareStatement(req);
				ps.setInt(1, id_salon);
				ResultSet rs = ps.executeQuery();
				rs.next();
				int id = rs.getInt("idSalon");
				String nom = rs.getString("nom");
				Personne p = new VirtualProxyPersonne(rs.getInt("modo"));
				Salon s = new Salon(id, nom, p);
				return s;
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
	
	public List<Salon> getSalons(int id_personne) {
		List<Salon> salons = new ArrayList<Salon>();
		try {
			String req = "SELECT s.idSalon, nom, modo  FROM "
					+ "Projet_OccupeSalon o join Projet_Salon s on o.idSalon=s.idSalon  WHERE idPersonne=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_personne);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("idSalon");
				String nom = rs.getString("nom");
				Personne modo = new VirtualProxyPersonne(rs.getInt("modo"));
				Salon s = new Salon(id, nom, modo);
				salons.add(s);
			}
			String req2 = "SELECT idSalon, nom, modo  FROM Projet_Salon  WHERE modo=?";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, id_personne);
			ResultSet rs2 = ps2.executeQuery();
			while (rs2.next()) {
				int id = rs2.getInt("idSalon");
				String nom = rs2.getString("nom");
				Personne modo = new VirtualProxyPersonne(rs2.getInt("modo"));
				Salon s = new Salon(id, nom, modo);
				salons.add(s);
			}
			return salons;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
