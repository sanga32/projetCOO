package server;

import java.rmi.RemoteException;
import java.util.List;

import Interface.SalonInterface;
import domaine.Personne;
import domaine.Salon;
import message.Message;
import persistance.MessageMapper;

public class SalonImpl implements SalonInterface {

	MessageMapper mm = MessageMapper.getInstance();
	
	@Override
	public List<Personne> getPersonnes() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(Message m, Salon s) {
		// TODO Auto-generated method stub
		mm.insert(m);

	}



}
