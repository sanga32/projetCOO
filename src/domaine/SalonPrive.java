package domaine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Interface.MessageInterface;
import Interface.PersonneInterface;
import Interface.PriveInterface;
import Interface.SalonInterface;
import message.MessageAvecAccuseReception;
import message.MessageAvecExpiration;
import message.MessageChiffre;
import message.MessagePrioritaire;
import message.MessagePrive;
import message.MessageSimple;
import persistance.AmiMapper;
import persistance.MessageMapper;
import persistance.NotificationMapper;

public class SalonPrive extends UnicastRemoteObject implements PriveInterface {

	public String nom;
	private List<PersonneInterface> personnes;
	List<PersonneInterface> connecte;
	
	public SalonPrive(String string, List<PersonneInterface> personnes) throws RemoteException {
		super();
		this.nom = nom;
		this.personnes = personnes;
		connecte = new ArrayList<PersonneInterface>();
	}

	public void send(String s, PersonneInterface exped, PersonneInterface dest, String date, boolean prio, boolean chiff, boolean exp,
			boolean ack) throws RemoteException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		System.out.println(connecte+"----connecte----");

		MessageMapper mm =  MessageMapper.getInstance();
		MessageInterface m = new MessagePrive(s, exped, dest, date);
		if (prio) m = new MessagePrioritaire(m);
		if (chiff) m = new MessageChiffre(m);
		if (exp) m = new MessageAvecExpiration(m);
		if (ack) m = new MessageAvecAccuseReception(m);

		mm.insert(m);
		//connecte.add(exped);
		for (int i = 0 ; i<connecte.size(); i++){
			connecte.get(i).receiveMessage(m);
		}

	}


	public void connection(PersonneInterface p) throws RemoteException {
		connecte.add(p);
	}
	
	public void deconnection(PersonneInterface p){
		connecte.remove(p);
	}

	@Override
	public List<MessageInterface> getMessages(PersonneInterface p, PersonneInterface p2) throws RemoteException, SQLException {
		List<MessageInterface> messages = MessageMapper.getInstance().findListMessagePrive(p.getId(),p2.getId());
		return messages;
		
	}

	@Override
	public void delete(PersonneInterface p, PersonneInterface p2) throws RemoteException {
			AmiMapper.getInstance().delete(p, p2);		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	
}
