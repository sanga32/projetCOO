package Interface;

import java.rmi.Remote;

public interface PersonneInterface extends Remote{

	public void update();
	public void receiveMessage(MessageInterface m);
	public int getId();
}
