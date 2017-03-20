package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import domaine.Notification;
import domaine.Personne;
import domaine.Salon;
import domaine.Utilisateur;

public interface InfoInterface extends Remote{
	public int connection(String login, String mdp) throws RemoteException;
	public List<Salon> getSalon(PersonneInterface p) throws RemoteException;
	public List<Notification> getNotification(PersonneInterface p) throws RemoteException;
	public List<Personne> getAmi(PersonneInterface p) throws RemoteException;
	public List<MessageInterface> getMessage(Salon s, PersonneInterface p) throws RemoteException;
}
