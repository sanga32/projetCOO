package message;

public class MessagePriveAvecExpirationDecorator extends MessageAvecExpiration {

	public MessagePriveAvecExpirationDecorator(Message m){
		this.message= m;
	}
}
