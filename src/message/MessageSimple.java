package message;

import java.sql.Date;

import Interface.PersonneInterface;
import Interface.SalonInterface;
import domaine.Personne;
import domaine.Salon;

/**
 * Classe qui hérite de Message et qui représente un message dans un salon
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class MessageSimple extends Message{
	SalonInterface idSalon;
	
	public MessageSimple(int idMessage, SalonInterface salon, Personne personne,String contenu, String dateEnvoi) {
		this.idSalon = salon;
		this.id = idMessage;
		this.expediteur = personne;
		this.contenu = contenu;
		this.dateEnvoi = dateEnvoi;
	}

	public MessageSimple() {
		// TODO Auto-generated constructor stub
	}
	
	public MessageSimple(String s, PersonneInterface exped, PersonneInterface dest, String date,  Salon salon) {
		// TODO Auto-generated constructor stub
		this.expediteur = exped;
		this.contenu = s;
		this.dateEnvoi = date;
		this.idSalon = salon;

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
	
	public SalonInterface getSalon(){
		return this.idSalon;
	}

	@Override
	public Personne getDestinataire() {
		return null;
	}

	@Override
	public Boolean isExpire() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContenu(String contenu) {
		this.contenu = contenu;		
	}
}
