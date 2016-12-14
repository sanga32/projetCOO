package persistance;

import message.Message;

public class Cryptage {
	
	public static String chiffrage(String message){
		String messageChiffre = "";
		for(int i = 0; i < message.length(); i++){
			messageChiffre = (char)((int)message.charAt(i) + 1) + messageChiffre;
		}
		
		return messageChiffre;
	}
	
	public static String dechiffrage(Message message){
		String messageDechiffre = "";
		String mess = message.getContenu();
		for(int i = 0; i < mess.length(); i++){
			messageDechiffre = (char)((int)mess.charAt(i) - 1) + messageDechiffre;
		}
		
		return messageDechiffre;
	}
}
