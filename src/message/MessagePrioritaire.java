package message;

import Interface.MessageInterface;

/**
 * Classe qui hérite de MessageAvecOption et qui représente un message prioritaire
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class MessagePrioritaire extends MessageAvecOption{

	public MessagePrioritaire(MessageInterface m){
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

	public Boolean isExpire() {
		return message.isExpire();
	}

}
