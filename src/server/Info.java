package server;

import java.rmi.RemoteException;
import java.util.List;

import Interface.InfoInterface;
import Interface.PersonneInterface;
import domaine.Notification;
import domaine.Personne;
import domaine.Salon;
import domaine.Utilisateur;
import persistance.AmiMapper;
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
	public List<Salon> getSalon(PersonneInterface p) throws RemoteException {
		List<Salon> salons = SalonMapper.getInstance().getSalons(p);
		return salons;
	}

	@Override
	public List<Notification> getNotification(PersonneInterface p) throws RemoteException {
		List<Notification> notifs = NotificationMapper.getInstance().findByPersonne(p.getId());
		return notifs;
	}

	@Override
	public List<Personne> getAmi(PersonneInterface p) throws RemoteException {
		List<Personne> amis = AmiMapper.getInstance().getAmis(p.getId());
		return null;
	}

}