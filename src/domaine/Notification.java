package domaine;

import persistance.VirtualProxyPersonne;

/**
 * Classe abstraite représentant une notification
 * @author Kevin delporte, alexandre godon, teddy lequette
 *
 */

public abstract class Notification {
	int id;
	String message;
	Personne expediteur;
	Personne destinataire;
	
	public Notification(int id, Personne expediteur, Personne destinataire){
		this.id = id;
		this.expediteur = expediteur;
		this.destinataire = destinataire;
	}
	
	public Notification(Personne expediteur, Personne destinataire){
		this.expediteur = expediteur;
		this.destinataire = destinataire;
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

	public Personne getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(Personne expediteur) {
		this.expediteur = expediteur;
	}

	public Personne getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(Personne destinataire) {
		this.destinataire = destinataire;
	}
	
	
	
}
