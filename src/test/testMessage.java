package test;

import message.Message;
import message.MessageAvecAccuseReception;
import message.MessageAvecExpiration;
import message.MessageChiffre;
import message.MessagePrioritaire;
import message.MessagePrive;

public class testMessage {

	public static void main(String[] args){
		Message m = new MessagePrive();
		m = new MessageAvecAccuseReception(m);
		m = new MessageAvecExpiration(m);
		//m = new MessageChiffre(m);
		m = new MessagePrioritaire(m);
		
		System.out.println(m.isReception());
		System.out.println(m.isChiffre());
		System.out.println(m.isExpiration());
		System.out.println(m.isPrioritaire());

	}
}
