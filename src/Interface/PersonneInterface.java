package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface PersonneInterface extends Remote{

	public void update() throws RemoteException;
	public void receiveMessage(MessageInterface m) throws RemoteException;
	public int getId() throws RemoteException;
	public String getLogin() throws RemoteException;
	public void receiveNotif() throws RemoteException, SQLException;
	public boolean equal(PersonneInterface personneInterface) throws RemoteException;
}
