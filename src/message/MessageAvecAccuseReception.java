package message;

import java.sql.Date;

import domaine.Personne;

public class MessageAvecAccuseReception extends MessageAvecOption {

	public MessageAvecAccuseReception(Message m) {
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

}
