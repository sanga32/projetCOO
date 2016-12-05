package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import domaine.Administrateur;
import domaine.Personne;
import domaine.Salon;
import domaine.Utilisateur;
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
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public int insert(Salon s) {
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
	
	public Salon findById(int id_salon){
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
}
