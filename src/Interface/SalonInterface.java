package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import domaine.Personne;

public interface SalonInterface extends Remote{
	public List<Personne> getPersonnes()throws RemoteException;
}
