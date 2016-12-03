package message;

import java.sql.Date;

import domaine.Personne;

public abstract class Message {
	String contenu;
	Personne expediteur, destinataire;
	Date dateEnvoi;
	
	public abstract boolean isReception();
	public abstract boolean isExpiration();
	public abstract boolean isChiffre();
	public abstract boolean isPrioritaire();

	public String getContenu() {
		return contenu;
	}

	public Personne getExpediteur() {
		return expediteur;
	}

	public Personne getDestinataire() {
		return destinataire;
	}

	public Date getDateEnvoi() {
		return dateEnvoi;
	}
		
	
}
