package message;

import java.sql.Date;

import domaine.Personne;
import domaine.Salon;

public class MessageSimple extends Message{
	Salon idSalon;
	
	public MessageSimple(Salon salon, Personne personne,String contenu, String dateEnvoi) {
		this.idSalon = salon;
		this.expediteur = personne;
		this.contenu = contenu;
		this.dateEnvoi = dateEnvoi;
	}

	public MessageSimple() {
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
	
	public Salon getSalon(){
		return this.idSalon;
	}

	@Override
	public Personne getDestinataire() {
		return null;
	}
}
