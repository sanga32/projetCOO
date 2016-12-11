package test;

import domaine.DemandeAmi;
import domaine.Notification;
import domaine.Personne;
import persistance.AmiMapper;
import persistance.NotificationMapper;
import persistance.PersonneMapper;

public class Notif {

	public static void main(String[] args) {
		NotificationMapper nm = NotificationMapper.getInstance();
		PersonneMapper pm = PersonneMapper.getInstance();
		nm.clear();
		Personne p1= pm.findById(1);
		Personne p2= pm.findById(2);
		
		AmiMapper.getInstance().delete(p1, p2);
		Notification n1 = new DemandeAmi(1,p1,p2);
		nm.insert((DemandeAmi) n1);
		
	}

}
