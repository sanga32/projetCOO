package Interface;

import java.rmi.Remote;

public interface DiscussionInterface extends Remote{

	public void send(MessageInterface m);
}
