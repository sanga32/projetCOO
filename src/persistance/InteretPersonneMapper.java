package persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			ps.setInt(2, i.getIdInteret());
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
			ps.setInt(2, si.getIdInteret());
			ps.setInt(3, si.getIdSousInteret());
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
	public List<Interet> findInteret() {
		List<Interet> listeInteret = new ArrayList<Interet>();
		try {
			// on va chercher tout les Interets
			String req = "SELECT idInteret, description FROM Projet_Interet";
			PreparedStatement ps = conn.prepareStatement(req);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int idInteret = rs.getInt("idInteret");
				String description = rs.getString("description");
				Interet i = new Interet(idInteret, description);
				listeInteret.add(i);
				// On va rechercher les sous interet de l'interet trouvé
				// précédemment
				try {
					String req2 = "SELECT idSousInteret, description FROM Projet_SousInteret where idInteret=?";
					PreparedStatement ps2 = conn.prepareStatement(req2);
					ps2.setInt(1, idInteret);
					ResultSet rs2 = ps2.executeQuery();
					while (rs2.next()) {
						int idSousInteret = rs2.getInt("idSousInteret");
						String descriptionSousInteret = rs2.getString("description");
						Interet si = new SousInteret(idSousInteret, descriptionSousInteret, i);
						listeInteret.add(si);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			return listeInteret;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Interet> findInteretByPersonne(int id_personne) {
		List<Interet> interets = new ArrayList<Interet>();
		try {
			String req = "SELECT i.idInteret, description FROM Projet_Interet i join "
					+ "Projet_InteretPersonne ip on i.idInteret = ip.idInteret"
					+ " where idPersonne=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_personne);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int idInteret = rs.getInt("idInteret");
				String description = rs.getString("description");
				Interet i = new Interet(idInteret, description);
				interets.add(i);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interets;
	}

	public List<SousInteret> findSousInteretByPersonne(int id_personne) {
		List<SousInteret> sousInterets = new ArrayList<SousInteret>();
		try {
			String req = "SELECT si.idSousInteret, description, ip.idInteret FROM Projet_SousInteret si join "
					+ "Projet_InteretPersonne ip on si.idInteret = ip.idInteret"
					+ " where idPersonne=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_personne);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int idSousInteret = rs.getInt("idSousInteret");
				String descriptionSousInteret = rs.getString("description");
				Interet i = findBySousInteret(rs.getInt("idInteret"));
				SousInteret si = new SousInteret(idSousInteret, descriptionSousInteret, i);
				sousInterets.add(si);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sousInterets;
	}

	public Interet findBySousInteret(int id) {
		try {
			String req = "SELECT idInteret, description FROM Projet_Interet where idInteret=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int idInteret = rs.getInt("idInteret");
			String description = rs.getString("description");
			Interet i = new Interet(idInteret, description);
			return i;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
