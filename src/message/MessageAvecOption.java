package message;

import java.sql.Date;

import Interface.MessageInterface;
import Interface.PersonneInterface;
import Interface.SalonInterface;
import domaine.Personne;
import domaine.Salon;

/**
 * Classe qui hérite de Message et qui représente le décorateur
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */
public abstract class MessageAvecOption extends Message {

	MessageInterface message;
	
	public int getId() {
		return message.getId();
	}
	
	
	public PersonneInterface getExpediteur() {
		return message.getExpediteur();
	}

	public PersonneInterface getDestinataire() {
		return message.getDestinataire();
	}
	
	public String getContenu() {
		return message.getContenu();
	}


	public String getDateEnvoi() {
		return message.getDateEnvoi();
	}
	
	public SalonInterface getSalon() {
		return message.getSalon();
	}
	
	public String toString() {
		return message.toString();
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	public void setContenu(String contenu) {
		message.setContenu(contenu);
	}

}
