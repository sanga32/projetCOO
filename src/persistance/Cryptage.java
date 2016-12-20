package persistance;

import message.Message;


/**
 * Class permetant de crypter un message
 * 
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
public class Cryptage {
	
	/**
	 * chiffrage d'un message
	 * @param message
	 * 				message � chiffrer
	 * @return le message chiffr�
	 */
	public static String chiffrage(String message){
		String messageChiffre = "";
		// on va rajouter 1 au code ascii de chaque caract�re
		for(int i = 0; i < message.length(); i++){
			messageChiffre = (char)((int)message.charAt(i) + 1) + messageChiffre;
		}
		
		return messageChiffre;
	}
	
	/**
	 * d�chiffrage d'un message
	 * @param message
	 * 				message � d�chiffrage
	 * @return le message d�chiffrage
	 */
	public static String dechiffrage(Message message){
		String messageDechiffre = "";
		String mess = message.getContenu();
		// on va enlever 1 au code ascii de chaque caract�re
		for(int i = 0; i < mess.length(); i++){
			messageDechiffre = (char)((int)mess.charAt(i) - 1) + messageDechiffre;
		}
		
		return messageDechiffre;
	}
}
