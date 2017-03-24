package message;

import java.sql.Date;

import Interface.MessageInterface;
import domaine.Personne;

/**
 * Classe qui hérite de MessageAvecOption et qui représente un message avec accusé de réception
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class MessageAvecAccuseReception extends MessageAvecOption {

	public MessageAvecAccuseReception(MessageInterface m) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.message = m;
	}
	
	@Override
	public
	boolean isReception() {
		// TODO Auto-generated method stub
		return true;
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
		if ( message.isPrioritaire()){
			return true;
		}
		return false;
	}

	public Boolean isExpire() {
		return message.isExpire();
	}
	
}
