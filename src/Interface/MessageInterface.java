package Interface;

import java.io.Serializable;

import domaine.Personne;
import domaine.Salon;
import message.Message;

public interface MessageInterface extends Serializable{
	
	public String getContenu();
	public String getDateEnvoi() ;
	public PersonneInterface getExpediteur() ;
	public Personne getDestinataire();
	
	/**
	 * 
	 * @return true si c'est un message avec accus� de r�ception
	 */
	public boolean isReception() ;

	/**
	 * 
	 * @return true si c'est un message avec expiration
	 */
	public boolean isExpiration() ;

	/**
	 * 
	 * @return true si c'est un message chiffr�
	 */
	public boolean isChiffre();
	/**
	 * 
	 * @return true si c'est un message prioritaire
	 */
	public boolean isPrioritaire() ;
	public int getId();
	public Boolean isExpire();
	public SalonInterface getSalon();
	public void setContenu(String contenu);
}
