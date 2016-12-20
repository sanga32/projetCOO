package message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe qui h�rite de MessageAvecOption et qui repr�sente un message avec un temps d'expiration
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class MessageAvecExpiration extends MessageAvecOption {

	public MessageAvecExpiration(Message m) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.message = m;
	}
	
	@Override
	public
	boolean isReception() {
		// TODO Auto-generated method stub
		if ( message.isReception())
			return true;
		return false;
	}

	@Override
	public
	boolean isExpiration() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public
	boolean isChiffre() {
		// TODO Auto-generated method stub
		if (message.isChiffre()){
			return true;
		}
		return false;
	}

	@Override
	public
	boolean isPrioritaire() {
		// TODO Auto-generated method stub
		if ( message.isPrioritaire()){
			return true;
		}
		return false;
	}

	/**
	 * Permet de tester si un message a expir� ou non
	 * @return false si le message n'a pas expir� et retourne true si il a expir�
	 */
	public Boolean isExpire() {
		SimpleDateFormat dateHeureFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date dateEnvoi = new Date();
		Date expiration = new Date();
		Date dateCourante = new Date();
		try {
			dateEnvoi = dateHeureFormat.parse(message.getDateEnvoi());
			expiration = dateHeureFormat.parse(message.getDateEnvoi());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		expiration.setHours(expiration.getHours()+ 12);
		expiration.setSeconds(dateEnvoi.getSeconds()+ 20);
		System.out.println("DateCourante" +dateCourante);
		System.out.println("Date EXPI" +expiration);
		if(dateCourante.before(expiration)){
			return false;
		}
		
		return true;
	}

}
