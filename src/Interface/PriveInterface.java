package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PriveInterface extends Remote{

	public List<MessageInterface> getMessages(PersonneInterface p, PersonneInterface p2) throws RemoteException; 
	public void connection(PersonneInterface p) throws RemoteException;
	public void deconnection(PersonneInterface p) throws RemoteException;
}
