package persistance;

import message.Message;

public class Cryptage {
	
	public static String chiffrage(Message message){
		String messageChiffre = "";
		String mess = message.getContenu();
		for(int i = 0; i < mess.length(); i++){
			messageChiffre = (char)((int)mess.charAt(i) + 1) + messageChiffre;
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
