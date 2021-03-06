package persistance;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Interface.NotifInterface;
import domaine.Administrateur;
import domaine.DemandeAmi;
import domaine.NotifMessage;
import domaine.Notification;
import domaine.Personne;
import domaine.Reponse;
import domaine.Utilisateur;
import settings.ConnectionInfo;

/**
 * NotificationMapper est la classe permettant de faire le lien avec la BDD Elle
 * permet d'ins�rer, supprimer ou chercher une notification
 * 
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
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
	 * Cr�e une notification demande d'ami
	 * @param n
	 *          la demande d'ami � ins�rer en BDD
	 * @return le nombre de ligne ins�r� en BDD
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
		} catch (SQLException | RemoteException e) {
			e.printStackTrace();
		}
		if (nbLigne1 * nbLigne2 > 0)
			return (nbLigne1 + nbLigne2);
		return 0;
	}

	/**
	 * Cr�e une notification r�ponse
	 * @param n
	 * 			la r�ponse � ins�rer en BDD
	 * @return le nombre de ligne ins�r� en BDD
	 */
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
		} catch (SQLException | RemoteException e) {
			e.printStackTrace();
		}
		if (nbLigne1 * nbLigne2 > 0)
			return (nbLigne1 + nbLigne2);
		return 0;

	}

	/**
	 * l'id le plus grand dans la table Projet_Notification
	 * @return l'id le plus grand
	 */
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
	 * Supprime la notification de demande d'ami
	 * 
	 * @param n
	 *            notification � supprimer
	 */
	public void delete(DemandeAmi n) {
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
	
	/**
	 * Supprime la notification de reponse
	 * 
	 * @param n
	 *            notification � supprimer
	 */
	public void delete(Reponse n) {
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

	/**
	 * R�cup�re la liste des notifications d'une personne 
	 * @param id_personne
	 * 						id de la personne 
	 * @return la liste des notification de la personne
	 * @throws RemoteException 
	 * @throws SQLException 
	 */
	public List<NotifInterface> findByPersonne(int id_personne) throws RemoteException, SQLException {
		conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
		List<NotifInterface> notifs = new ArrayList<NotifInterface>();
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
					DemandeAmi notif;
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
					Reponse notif;
					Boolean reponse = (rs2.getInt("reponse") == 0) ? false : true;
					notif = new Reponse(id_notification, reponse, expediteur, destinataire);
					notifs.add(notif);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			String req3 = "SELECT n.idNotification, message, destinataire, d.expediteur  "
					+ "FROM Projet_Notification n join Projet_NotifMessage d on n.idNotification = d.idNotification "
					+ "WHERE destinataire=?";
			PreparedStatement ps3 = conn.prepareStatement(req3);
			ps3.setInt(1, id_personne);
			ResultSet rs3 = ps3.executeQuery();
			while (rs3.next()) {
				try {
					int id_notification = rs3.getInt("n.idNotification");
					String message = rs3.getString("message");
					Personne destinataire = new VirtualProxyPersonne(rs3.getInt("destinataire"));
					Personne expediteur = new VirtualProxyPersonne(rs3.getInt("d.expediteur"));
					Notification notif;
					notif = new NotifMessage(id_notification, expediteur, destinataire);
					notif.setMessage(message);
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
	
	/**
	 * V�rifie si une personne a de nouvelle notification
	 * @param p
	 * 			personne
	 * @return true si la personne a de nouvelle notification
	 * @throws SQLException 
	 */
	public boolean newNotification(Personne p) throws SQLException{
		conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
		try {
			String req = "SELECT idNotification FROM Projet_Notification WHERE destinataire=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			rs.getInt("IdNotification");
			return true;
			} catch (SQLException e) {
				return false;
			}

	}

	/**
	 * Cr�e une notification message
	 * @param n
	 * 			la r�ponse � ins�rer en BDD
	 * @return le nombre de ligne ins�r� en BDD
	 */
	
	public int insert(NotifMessage n) {
		// TODO Auto-generated method stub
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
			String req2 = "insert into Projet_NotifMessage(idNotification,expediteur) values(?,?)";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, idNotif);
			ps2.setInt(2, n.getExpediteur().getId());
			nbLigne2 = ps2.executeUpdate();
			conn.commit();
		} catch (SQLException | RemoteException e) {
			e.printStackTrace();
		}
		if (nbLigne1 * nbLigne2 > 0)
			return (nbLigne1 + nbLigne2);
		return 0;
	}
	
	/**
	 * Supprime la notification de message
	 * 
	 * @param n
	 *            notification � supprimer
	 */
	public void delete(NotifMessage n) {
		try {
			String req = "delete from Projet_Notification where idNotification=?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, n.getId());
			ps.execute();
			String req2 = "delete from Projet_NotifMessage where idNotification=?";
			PreparedStatement ps2 = conn.prepareStatement(req2);
			ps2.setInt(1, n.getId());
			ps2.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
