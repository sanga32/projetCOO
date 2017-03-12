package Interface;

import java.io.Serializable;
import java.rmi.Remote;

import message.Message;

public interface MessageInterface extends Remote, Serializable{
	
	public Message getMessage();
	
}
