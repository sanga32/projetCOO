package Interface;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import domaine.Notification;
import domaine.Personne;
import domaine.Salon;
import domaine.Utilisateur;

public interface InfoInterface extends Remote, Serializable{
	public int connection(String login, String mdp) throws RemoteException; //renvoie l'id associé au pseudo et mdp
	public List<PersonneInterface> getAmis(PersonneInterface p) throws RemoteException;
	public List<SalonInterface> getSalons(PersonneInterface p) throws RemoteException;
	public SalonInterface getSalon(String s) throws RemoteException;
	public List<NotifInterface> getNotification(PersonneInterface p) throws RemoteException, SQLException;
	public List<PersonneInterface> getAmi(PersonneInterface p) throws RemoteException;
	public List<MessageInterface> getMessage(SalonInterface s, PersonneInterface p) throws RemoteException, SQLException;
	public String salonAmi(PersonneInterface exp, PersonneInterface dest) throws RemoteException;
	public void connecter(PersonneInterface p)throws RemoteException;
	public void deconnecter(PersonneInterface p) throws RemoteException;
	public List<PersonneInterface> getPersonnes() throws RemoteException;
}
