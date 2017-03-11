package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import domaine.Notification;
import domaine.Salon;
import domaine.Utilisateur;

public interface InfoInterface extends Remote{
	public boolean connection(String login, String mdp) throws RemoteException;
	public List<SalonInterface> getSalon(Utilisateur p) throws RemoteException;
	public List<NotificationInterface> getNotification(Utilisateur p) throws RemoteException;
	public List<AmiInterface> getAli(Utilisateur p) throws RemoteException;
}
