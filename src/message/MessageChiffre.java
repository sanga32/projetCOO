package message;

public class MessageChiffre extends MessageAvecOption{

	public MessageChiffre(Message m){
		this.id = id;
		this.message=m;
	}

	
	@Override
	public
	boolean isReception() {
		// TODO Auto-generated method stub
		if( message.isReception())
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

		return true;
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
	
	public String chiffrage(){
		String messageChiffre = "";
		String mess = message.getContenu();
		for(int i = 0; i < mess.length(); i++){
			messageChiffre = (char)((int)mess.charAt(i) + 1) + messageChiffre;
		}
		
		return messageChiffre;
	}
	
	public String dechiffrage(){
		String messageDechiffre = "";
		String mess = message.getContenu();
		for(int i = 0; i < mess.length(); i++){
			messageDechiffre = (char)((int)mess.charAt(i) - 1) + messageDechiffre;
		}
		
		return messageDechiffre;
	}
}
