package Interface;

import java.io.Serializable;
import java.rmi.Remote;

public interface MessageInterface extends Remote, Serializable{
	
	public String getMessage();
	
}
