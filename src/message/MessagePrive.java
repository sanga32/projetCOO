package message;

import java.sql.Date;

import domaine.Personne;
import domaine.Salon;

/**
 * Classe qui hérite de message et qui représente un message entre deux utilisateurs
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class MessagePrive extends Message{
	
	
	Personne destinataire;
	
	public MessagePrive(int id, String contenu, Personne expediteur, Personne destinataire, String dateEnvoi) {
		this.id=id;
		this.contenu = contenu;
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.dateEnvoi = dateEnvoi;
	}
	public MessagePrive(String contenu, Personne expediteur, Personne destinataire, String string) {
		
		this.contenu = contenu;
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.dateEnvoi = string;
	}

	public MessagePrive() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Personne getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(Personne destinataire) {
		this.destinataire = destinataire;
	}
	
	@Override
	public Salon getSalon() {
		return null;
	}
	@Override
	public Boolean isExpire() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	

}
