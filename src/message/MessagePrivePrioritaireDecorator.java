package message;

public class MessagePrivePrioritaireDecorator extends MessagePrioritaire{

	public MessagePrivePrioritaireDecorator(Message m){
		this.message = m;
	}
}
