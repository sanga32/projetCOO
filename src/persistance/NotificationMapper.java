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
			ps.setInt(1, n.getId());
			ps.setString(2, n.getMessage());
			ps.setString(3, n.getMessage());
			nbLigne1 = ps.executeUpdate();
			String req2 = "insert into Projet_DemandeAmi(idNotification,expediteur) values(?,?)";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, n.getId());
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
			String req = "insert into Projet_Notification(message,destinataire) values(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setString(1, n.getMessage());
			ps.setString(2, n.getMessage());
			nbLigne1 = ps.executeUpdate();
			String req2 = "insert into Projet_Reponse(reponse,expediteur) values(?,?)";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, (n.isReponse()) ? 1 : 0);
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

	/**
	 * Delete un couple d'ami
	 * 
	 * @param p
	 *            personne à supprimer de la BDD
	 */
	public void delete(Notification n) {
		try {
			String req = "delete from Projet_Notification where idNotification=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, n.getId());
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Notification> findById(int id_personne) {
		List<Notification> notifs = new ArrayList<Notification>();
		try {
			// on va chercher la personne
			String req = "SELECT n.idNotification, message, destinataire, expediteur, reponse  "
					+ "FROM Projet_Notification n join Projet_DemandeAmi d on n.idNotification = d.idNotification "
					+ "join Projet_reponse r on r.idNotification=n.idNotification WHERE destinataire=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id_personne);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id_notification = rs.getInt("n.idNotification");
				String message = rs.getString("message");
				Personne destinataire = new VirtualProxyPersonne(rs.getInt("destinataire"));
				Personne expediteur = new VirtualProxyPersonne(rs.getInt("expediteur"));
				Notification notif;
				try{
					Boolean reponse = (rs.getInt("reponse") ==0)?false:true;
					
					notif= new Reponse(id_notification,reponse,destinataire,expediteur);
				} catch (SQLException e) {
					notif= new DemandeAmi(id_notification,destinataire,expediteur);
				}
				notifs.add(notif);
			}
			return notifs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
