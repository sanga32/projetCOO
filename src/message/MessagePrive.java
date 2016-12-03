package message;

import java.sql.Date;

import domaine.Personne;

public class MessagePrive extends Message{

	public MessagePrive(String contenu, Personne expediteur, Personne destinataire, Date dateEnvoi) {
		// TODO Auto-generated constructor stub
	}

	public MessagePrive() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public
	boolean isReception() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public
	boolean isExpiration() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public
	boolean isChiffre() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public
	boolean isPrioritaire() {
		// TODO Auto-generated method stub
		return false;
	}

}
