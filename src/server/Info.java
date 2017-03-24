package server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import Interface.InfoInterface;
import Interface.MessageInterface;
import Interface.NotifInterface;
import Interface.PersonneInterface;
import Interface.PriveInterface;
import Interface.SalonInterface;
import domaine.Notification;
import domaine.Personne;
import domaine.Salon;
import domaine.SalonPrive;
import domaine.Utilisateur;
import persistance.AmiMapper;
import persistance.MessageMapper;
import persistance.NotificationMapper;
import persistance.PersonneMapper;
import persistance.SalonMapper;

public class Info implements InfoInterface {
	
	public List<PersonneInterface> personnes = new ArrayList<PersonneInterface>();

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

	@Override
	public String salonAmi(PersonneInterface exp, PersonneInterface dest) throws RemoteException {
		Registry registry = LocateRegistry.getRegistry(10000);
		try {
			PriveInterface salon = (PriveInterface) registry.lookup(exp.getLogin()+dest.getLogin());
			return exp.getLogin()+dest.getLogin();
		} catch (NotBoundException e) {
			try {
				PriveInterface salon = (PriveInterface) registry.lookup(dest.getLogin()+exp.getLogin());
				return dest.getLogin()+exp.getLogin();
			} catch (NotBoundException e1) {
				List<PersonneInterface> list = new ArrayList<PersonneInterface>();
				list.add(dest); list.add(exp);
				PriveInterface salon = new SalonPrive(list);
				try {
					registry.bind(exp.getLogin()+dest.getLogin(), salon);
				} catch (AlreadyBoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return "";
	}

	@Override
	public void connecter(PersonneInterface p) throws RemoteException {
		personnes.add(p);
		System.out.println(personnes+"ggggggggggggggggggggggggggggggggggggggggggggggg");
	}
	
	@Override
	public void deconnecter(PersonneInterface p) throws RemoteException {
		personnes.remove(p);	
	}
	
	public List<PersonneInterface> getPersonnes() throws RemoteException{
		return personnes;
	}

}
