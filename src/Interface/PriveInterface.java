package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import domaine.Personne;

public interface PriveInterface extends Remote{

	public List<MessageInterface> getMessages(PersonneInterface p, PersonneInterface p2) throws RemoteException; 
	public void connection(PersonneInterface p) throws RemoteException;
	public void deconnection(PersonneInterface p) throws RemoteException;
	public void delete(PersonneInterface p, PersonneInterface p2) throws RemoteException;
	public void send(String s, PersonneInterface exped, PersonneInterface dest, String date, boolean prio, boolean chiff, boolean exp,
			boolean ack) throws RemoteException;
	public String getNom() throws RemoteException;
}
