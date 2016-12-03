package test;

import message.Message;
import message.MessagePriveAvecExpirationDecorator;
import message.MessagePriveChiffreDecorator;
import message.MessagePrivePrioritaireDecorator;
import message.MessagePrive;
import message.MessagePriveAvecAccuseReceptionDecorator;

public class testMessage {

	public static void main(String[] args){
		Message m = new MessagePrive();
		Message mACK = new MessagePriveAvecAccuseReceptionDecorator(m);
		Message mExp = new MessagePriveAvecExpirationDecorator(mACK);
		Message mCh = new MessagePriveChiffreDecorator(mExp);
		Message mPrio = new MessagePrivePrioritaireDecorator(mCh);
		
		System.out.println(mPrio.isReception());
		System.out.println(mPrio.isChiffre());
		System.out.println(mPrio.isExpiration());
		System.out.println(mPrio.isPrioritaire());

	}
}
