package message;

import java.sql.Date;

import domaine.Personne;

public abstract class Message {
	String contenu;
	int id;
	String dateEnvoi;
	
	public abstract boolean isReception();
	public abstract boolean isExpiration();
	public abstract boolean isChiffre();
	public abstract boolean isPrioritaire();



	@Override
	public String toString() {
		return "Message [id=" + id + ", contenu=" + contenu  + ", dateEnvoi=" + dateEnvoi + "]";
	}
	public String getContenu() {
		return contenu;
	}



	public String getDateEnvoi() {
		return dateEnvoi;
	}
		

	public int getId() {
		return this.id;
	}
}
