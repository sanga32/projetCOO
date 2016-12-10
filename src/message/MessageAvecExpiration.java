package message;

public class MessageAvecExpiration extends MessageAvecOption {

	public MessageAvecExpiration(Message m) {
		// TODO Auto-generated constructor stub
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
		return true;
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
		if ( message.isPrioritaire()){
			return true;
		}
		return false;
	}
	
	public String chiffrage() {
		return message.chiffrage();
	}

	public String dechiffrage() {
		return message.dechiffrage();
	}

}
