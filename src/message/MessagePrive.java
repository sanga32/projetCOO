package message;

import java.rmi.RemoteException;
import java.sql.Date;

import Interface.PersonneInterface;
import domaine.Personne;
import server.Salon;

/**
 * Classe qui hérite de message et qui représente un message entre deux utilisateurs
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class MessagePrive extends Message{
	
	
	PersonneInterface destinataire;
	
	public MessagePrive(int id, String contenu, PersonneInterface expediteur, PersonneInterface destinataire, String dateEnvoi) {
		this.id=id;
		this.contenu = contenu;
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.dateEnvoi = dateEnvoi;
	}
	public MessagePrive(String contenu, PersonneInterface expediteur, PersonneInterface destinataire, String string) {
		
		this.contenu = contenu;
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.dateEnvoi = string;
	}

	public MessagePrive() {
		// TODO Auto-generated constructor stub
	}
	
	
	public PersonneInterface getDestinataire() {
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
