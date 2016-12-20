package message;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import domaine.Personne;
import domaine.Salon;

/**
 * Classe abstraite repr�sentant un message
 * @author Kevin delporte, alexandre godon, teddy lequette
 *
 */
public abstract class Message {

	String contenu;
	int id;
	String dateEnvoi;
	Personne expediteur;

	public abstract Personne getDestinataire();

	public abstract void setContenu(String contenu);

	public abstract Salon getSalon();

	/**
	 * Permet de tester si un message a expir� ou non
	 * @return false si le message n'a pas expir� et retourne true si il a expir�
	 */
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

	/**
	 * 
	 * @return true si c'est un message avec accus� de r�ception
	 */
	public boolean isReception() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 * @return true si c'est un message avec expiration
	 */
	public boolean isExpiration() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 * @return true si c'est un message chiffr�
	 */
	public boolean isChiffre() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 * @return true si c'est un message prioritaire
	 */
	public boolean isPrioritaire() {
		// TODO Auto-generated method stub
		return false;
	}

}
