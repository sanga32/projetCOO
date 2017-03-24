package message;

import Interface.MessageInterface;

/**
 * Classe qui h�rite de MessageAvecOption et qui repr�sente un message chiffr�
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class MessageChiffre extends MessageAvecOption{

	public MessageChiffre(MessageInterface m){
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
	
	public Boolean isExpire() {
		return message.isExpire();
	}
}
