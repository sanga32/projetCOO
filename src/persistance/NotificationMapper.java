package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domaine.Administrateur;
import domaine.DemandeAmi;
import domaine.Notification;
import domaine.Personne;
import domaine.Reponse;
import domaine.Utilisateur;
import settings.ConnectionInfo;

public class NotificationMapper {
	private Connection conn;
	static NotificationMapper inst;

	/**
	 * Permet d'initialiser NotificationMapper
	 */
	public NotificationMapper() {
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	/**
	 * Retourne l'instance de NotificationMapper
	 */

	public static NotificationMapper getInstance() {
		if (inst == null)
			inst = new NotificationMapper();
		return inst;
	}

	/**
	 * Supprime le contenue de la table Projet_Notification
	 */

	public void clear() {
		try {
			String req = "delete from Projet_Notification";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.execute();
			String req2 = "delete from Projet_DemandeAmi";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.execute();
			String req3 = "delete from Projet_Reponse";
			PreparedStatement ps3 = conn.prepareStatement(req3);
			ps3.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insère 2 personne dans la table Projet_Notification
	 * 
	 * @param p1
	 * @param p2
	 *            p1 et p2 sont les deux nouveau ami à insérer en bdd
	 */
	public int insert(DemandeAmi n) {
		int nbLigne1 = 0;
		int nbLigne2 = 0;
		try {
			String req = "insert into Projet_Notification(idNotification,message,destinataire) values(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			int idNotif = idMax() + 1;
			ps.setInt(1, idNotif);
			ps.setString(2, n.getMessage());
			ps.setInt(3, n.getDestinataire().getId());
			nbLigne1 = ps.executeUpdate();
			String req2 = "insert into Projet_DemandeAmi(idNotification,expediteur) values(?,?)";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, idNotif);
			ps2.setInt(2, n.getExpediteur().getId());
			nbLigne2 = ps2.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (nbLigne1 * nbLigne2 > 0)
			return (nbLigne1 + nbLigne2);
		return 0;
	}

	public int insert(Reponse n) {
		int nbLigne1 = 0;
		int nbLigne2 = 0;
		try {
			String req = "insert into Projet_Notification(idNotification, message,destinataire) values(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			int idNotif = idMax() + 1;
			ps.setInt(1, idNotif);
			ps.setString(2, n.getMessage());
			ps.setInt(3, n.getDestinataire().getId());
			nbLigne1 = ps.executeUpdate();
			String req2 = "insert into Projet_Reponse(idNotification, reponse,expediteur) values(?,?,?)";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, idNotif);
			ps2.setInt(2, (n.isReponse()) ? 1 : 0);
			ps2.setInt(3, n.getExpediteur().getId());
			nbLigne2 = ps2.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (nbLigne1 * nbLigne2 > 0)
			return (nbLigne1 + nbLigne2);
		return 0;

	}

	public int idMax() {
		int idMax = 0;
		try {
			String req = "Select max(idNotification) as idMax from Projet_Notification ";
			PreparedStatement ps = conn.prepareStatement(req);
			ResultSet rs = ps.executeQuery();
			rs.next();
			idMax = rs.getInt("idMax");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idMax;
	}

	/**
	 * Delete un couple d'ami
	 * 
	 * @param p
	 *            personne à supprimer de la BDD
	 */
	public void delete(DemandeAmi n) {
		try {
			String req = "delete from Projet_Notification where idNotification=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, n.getId());
			ps.execute();
			String req2 = "delete from Projet_DemandeAmi where idNotification=?";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, n.getId());
			ps2.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Reponse n) {
		try {
			String req = "delete from Projet_Notification where idNotification=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, n.getId());
			ps.execute();
			
			String req2 = "delete from Projet_Reponse where idNotification=?";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, n.getId());
			ps2.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Notification> findByPersonne(int id_personne) {
		List<Notification> notifs = new ArrayList<Notification>();
		try {
			String req = "SELECT n.idNotification, message, destinataire, d.expediteur  "
					+ "FROM Projet_Notification n join Projet_DemandeAmi d on n.idNotification = d.idNotification "
					+ "WHERE destinataire=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_personne);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				try {
					int id_notification = rs.getInt("n.idNotification");
					String message = rs.getString("message");
					Personne destinataire = new VirtualProxyPersonne(rs.getInt("destinataire"));
					Personne expediteur = new VirtualProxyPersonne(rs.getInt("d.expediteur"));
					Notification notif;
					notif = new DemandeAmi(id_notification, expediteur, destinataire);
					notifs.add(notif);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			String req2 = "SELECT n.idNotification, destinataire, r.expediteur, reponse  "
					+ "FROM Projet_Notification n "
					+ "join Projet_Reponse r on r.idNotification=n.idNotification WHERE destinataire=?";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, id_personne);
			ResultSet rs2 = ps2.executeQuery();
			while (rs2.next()) {
				try {
					int id_notification = rs2.getInt("n.idNotification");
					Personne destinataire = new VirtualProxyPersonne(rs2.getInt("destinataire"));
					Personne expediteur = new VirtualProxyPersonne(rs2.getInt("expediteur"));
					Notification notif;
					Boolean reponse = (rs2.getInt("reponse") == 0) ? false : true;
					notif = new Reponse(id_notification, reponse, expediteur, destinataire);
					notifs.add(notif);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			return notifs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean newNotification(Personne p){
		try {
			String req = "SELECT idNotification FROM Projet_Notification WHERE destinataire=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			rs.getInt("IdNotification");
			return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return false;
	}

}
