package message;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import Interface.MessageInterface;
import Interface.PersonneInterface;
import Interface.SalonInterface;
import domaine.Personne;
import server.Salon;

/**
 * Classe abstraite représentant un message
 * @author Kevin delporte, alexandre godon, teddy lequette
 *
 */
public abstract class Message implements MessageInterface {

	String contenu;
	int id;
	String dateEnvoi;
	PersonneInterface expediteur;

	public abstract void setContenu(String contenu);

	public abstract SalonInterface getSalon();

	/**
	 * Permet de tester si un message a expirï¿½ ou non
	 * @return false si le message n'a pas expirï¿½ et retourne true si il a expirï¿½
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
		try {
			return "[" + dateEnvoi + "]" + expediteur.getLogin() + " : " + contenu;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contenu;
	}

	public int getId() {
		return this.id;
	}

	public PersonneInterface getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(Personne expediteur) {
		this.expediteur = expediteur;
	}

	/**
	 * 
	 * @return true si c'est un message avec accusï¿½ de rï¿½ception
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
	 * @return true si c'est un message chiffrï¿½
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
