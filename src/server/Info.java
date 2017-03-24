package server;

import java.rmi.RemoteException;
import java.util.List;

import Interface.InfoInterface;
import Interface.MessageInterface;
import Interface.NotifInterface;
import Interface.PersonneInterface;
import Interface.SalonInterface;
import domaine.Notification;
import domaine.Personne;
import domaine.Salon;
import domaine.Utilisateur;
import persistance.AmiMapper;
import persistance.MessageMapper;
import persistance.NotificationMapper;
import persistance.PersonneMapper;
import persistance.SalonMapper;

public class Info implements InfoInterface {

	@Override
	public int connection(String login, String mdp) throws RemoteException {
		Personne p = PersonneMapper.getInstance().findByLogMdp(login, mdp);
		return p.getId();
	}

	@Override
	public List<SalonInterface> getSalons(PersonneInterface p) throws RemoteException {
		List<SalonInterface> salons = SalonMapper.getInstance().getSalons(p);
		return salons;
	}

	@Override
	public List<NotifInterface> getNotification(PersonneInterface p) throws RemoteException {
		List<NotifInterface> notifs = NotificationMapper.getInstance().findByPersonne(p.getId());
		return notifs;
	}

	@Override
	public List<PersonneInterface> getAmi(PersonneInterface p) throws RemoteException {
		List<PersonneInterface> amis = AmiMapper.getInstance().getAmis(p.getId());
		return amis;
	}

	@Override
	public List<MessageInterface> getMessage(SalonInterface s, PersonneInterface p) throws RemoteException {
		List<MessageInterface> messages = MessageMapper.getInstance().findListMessageSalon(s.getId(), p);
		return messages;
	}


	@Override
	public List<PersonneInterface> getAmis(PersonneInterface p) throws RemoteException {
		List<PersonneInterface> amis = AmiMapper.getInstance().getAmis(p.getId());
		return amis;
	}

	@Override
	public SalonInterface getSalon(String s) throws RemoteException {
		SalonInterface salon = SalonMapper.getInstance().findByNom(s); 
		return salon;
	}

}
