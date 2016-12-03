package message;

import java.sql.Date;

import domaine.Personne;

public abstract class Message {
	String contenu;
	Personne expediteur, destinataire;
	Date dateEnvoi;
	
	public Message(String contenu, Personne expediteur, Personne destinataire, Date dateEnvoi) {
		super();
		this.contenu = contenu;
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.dateEnvoi = dateEnvoi;
	}

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
