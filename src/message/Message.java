package message;

import java.sql.Date;

import domaine.Personne;
import domaine.Salon;

public abstract class Message {
	
	String contenu;
	int id;
	String dateEnvoi;
	Personne expediteur;
	
	public abstract boolean isReception();
	public abstract boolean isExpiration();
	public abstract boolean isChiffre();
	public abstract boolean isPrioritaire();
	public abstract Personne getDestinataire();
	public abstract Salon getSalon();

	
	public String getContenu() {
		return contenu;
	}


	public String getDateEnvoi() {
		return dateEnvoi;
	}
	
	public String toString() {
		return "["+dateEnvoi+"]"+expediteur.getLogin()+ " : " + contenu;
	}
	
	
	public int getId() {
		return this.id;
	}
	
	public Personne getExpediteur() {
		return expediteur;
	}
	public void setExpediteur(Personne expediteur) {
		this.expediteur = expediteur;
	}

	
}
