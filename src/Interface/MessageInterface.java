package Interface;

import java.io.Serializable;

import message.Message;

public interface MessageInterface extends Serializable{
	
	public Message getMessage();
	
	public void isPrio();
	public void isExp();
	public void isChiffre();
	public void isAck();

	
}
