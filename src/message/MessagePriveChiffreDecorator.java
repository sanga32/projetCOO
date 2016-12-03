package message;

public class MessagePriveChiffreDecorator extends MessageChiffre {
	
	public MessagePriveChiffreDecorator(Message m){
		this.message=m;
	}

}
