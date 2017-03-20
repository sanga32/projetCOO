package server;

import Interface.MessageInterface;
import message.Message;
import message.MessageAvecAccuseReception;
import message.MessageAvecExpiration;
import message.MessageChiffre;
import message.MessagePrioritaire;

public class MessageImpl implements MessageInterface{

	Message m ;
	
	public MessageImpl(Message m) {
		super();
		this.m = m;
	}

	@Override
	public Message getMessage() {
		// TODO Auto-generated method stub
		return m;
	}
	
	@Override
	public void isPrio() {
		// TODO Auto-generated method stub
		m = new MessagePrioritaire(m);

	}

	@Override
	public void isExp() {
		// TODO Auto-generated method stub
		m = new MessageAvecExpiration(m);

	}

	@Override
	public void isChiffre() {
		// TODO Auto-generated method stub
		m = new MessageChiffre(m);

	}

	@Override
	public void isAck() {
		// TODO Auto-generated method stub
		m = new MessageAvecAccuseReception(m);

	}

	//Faire méthodes pour les différents types de messages
}
