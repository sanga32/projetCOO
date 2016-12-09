package message;

import java.sql.Date;

import domaine.Personne;
import domaine.Salon;

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
	
	@Override
	public
	boolean isReception() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public
	boolean isExpiration() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public
	boolean isChiffre() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public
	boolean isPrioritaire() {
		// TODO Auto-generated method stub
		return false;
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
	
	

}
