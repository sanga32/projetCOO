package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import domaine.Personne;
import message.Message;

public interface SalonInterface extends Remote{
	
	
	public List<Personne> getPersonnes()throws RemoteException;
	public void sendMessage(Message m);
}
