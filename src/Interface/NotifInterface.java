package Interface;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotifInterface extends Remote, Serializable{

	public void delete() throws RemoteException;

}
