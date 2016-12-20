package message;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import domaine.Personne;
import domaine.Salon;

public abstract class Message {

	String contenu;
	int id;
	String dateEnvoi;
	Personne expediteur;

	public abstract Personne getDestinataire();

	public abstract void setContenu(String contenu);

	public abstract Salon getSalon();

	public abstract Boolean isExpire();

	public String getContenu() {
		return contenu;
	}

	public String getDateEnvoi() {
		return dateEnvoi;
	}

	public String toString() {
		System.out.println(contenu);
		return "[" + dateEnvoi + "]" + expediteur.getLogin() + " : " + contenu;
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

	public boolean isReception() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isExpiration() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isChiffre() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPrioritaire() {
		// TODO Auto-generated method stub
		return false;
	}

}
