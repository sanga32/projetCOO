package domaine;

import Interface.NotifInterface;
import Interface.PersonneInterface;
import persistance.NotificationMapper;

/**
 * Classe abstraite représentant une notification
 * @author Kevin delporte, alexandre godon, teddy lequette
 *
 */

public abstract class Notification implements NotifInterface {
	int id;
	String message;
	PersonneInterface expediteur;
	PersonneInterface destinataire;
	
	public Notification(int id, PersonneInterface expediteur2, PersonneInterface destinataire2){
		this.id = id;
		this.expediteur = expediteur2;
		this.destinataire = destinataire2;
	}
	
	public Notification(PersonneInterface exped, PersonneInterface dest){
		this.expediteur = exped;
		this.destinataire = dest;
	}

	public String toString(){
		return message;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PersonneInterface getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(Personne expediteur) {
		this.expediteur = expediteur;
	}

	public PersonneInterface getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(Personne destinataire) {
		this.destinataire = destinataire;
	}
	
	
}
