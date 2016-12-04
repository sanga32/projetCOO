package message;

public class MessagePrioritaire extends MessageAvecOption{

	public MessagePrioritaire(int id, Message m){
		this.id = id;
		this.message = m;
	}
	
	@Override
	public
	boolean isReception() {
		// TODO Auto-generated method stub
		if ( message.isReception())
			return true;
		return false;
	}

	@Override
	public
	boolean isExpiration() {
		// TODO Auto-generated method stub
		if(message.isExpiration()){
			return true;
		}
		return false;
	}

	@Override
	public
	boolean isChiffre() {
		// TODO Auto-generated method stub
		if (message.isChiffre()){
			return true;
		}
		return false;
	}

	@Override
	public
	boolean isPrioritaire() {
		// TODO Auto-generated method stub
		return true;
	}

}
