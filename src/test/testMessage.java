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
		Message mACK = new MessageAvecAccuseReception(m);
		Message mExp = new MessageAvecExpiration(mACK);
		Message mCh = new MessageChiffre(mExp);
		Message mPrio = new MessagePrioritaire(mACK);
		
		System.out.println(mPrio.isReception());
		System.out.println(mPrio.isChiffre());
		System.out.println(mPrio.isExpiration());
		System.out.println(mPrio.isPrioritaire());

	}
}
