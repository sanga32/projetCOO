package message;

import java.sql.Date;

import domaine.Personne;
import domaine.Salon;

public abstract class MessageAvecOption extends Message {

	Message message;
	
	public Personne getExpediteur() {
		return message.getExpediteur();
	}

	public Personne getDestinataire() {
		return message.getDestinataire();
	}
	
	public String getContenu() {
		return message.getContenu();
	}


	public String getDateEnvoi() {
		return message.getDateEnvoi();
	}
	
	public Salon getSalon() {
		return message.getSalon();
	}
	
	public String toString() {
		return message.toString();
	}

}
