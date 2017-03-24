package Interface;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import domaine.Personne;

public interface SalonInterface extends Remote{

	public void send(String s, PersonneInterface exped, PersonneInterface dest, String date, boolean prio, boolean chiff, boolean exp,
			boolean ack) throws RemoteException, NotBoundException;
	public String getNom() throws RemoteException;
	public int getId() throws RemoteException;
	public PersonneInterface getModo() throws RemoteException;
	public List<PersonneInterface> getPersonnes() throws RemoteException;
	public void connection(PersonneInterface p) throws RemoteException;
	public void deconnection(PersonneInterface p) throws RemoteException;
	public boolean isEmpty() throws RemoteException;
	public void addPersonne(PersonneInterface personneInterface) throws RemoteException, SQLException;
	public boolean delete() throws RemoteException, NotBoundException;
	public void quitter(PersonneInterface p) throws RemoteException;
	public void updateModo(PersonneInterface p) throws RemoteException;
}
