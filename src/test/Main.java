package test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domaine.*;
import message.Message;
import message.MessagePrive;
import persistance.AmiMapper;
import persistance.MessageMapper;
import persistance.NotificationMapper;
import persistance.PersonneMapper;
import persistance.SalonMapper;

public class Main {

	public static void main(String args[]) {

		PersonneMapper pm = PersonneMapper.getInstance();
		MessageMapper mm = MessageMapper.getInstance();
		AmiMapper am = AmiMapper.getInstance();
		NotificationMapper nm = NotificationMapper.getInstance();
		SalonMapper sm = SalonMapper.getInstance();
		Personne p1 = new Utilisateur(1, "godona", "1234", "godon", "alexandre");
		Personne p2 = new Utilisateur(2, "delportek", "4321", "delporte", "kevin");
		Personne p3 = new Utilisateur(3, "lequette", "5748", "lequette", "teddy");
		;

		am.clear();
		pm.clear();
		nm.clear();
		sm.clear();
		pm.insert(p1);
		pm.insert(p2);
		pm.insert(p3);


		System.out.println(mm.findByDestinataire(p2.getId()));
		System.out.println(pm.findById(1));

		am.insert(p1, p2);
		am.insert(p3, p1);
		Personne alex = pm.findById(1);
		System.out.println("Ami de " + alex.getPrenom());
		for (int i = 0; i < alex.getAmis().size(); i++)
			System.out.println(alex.getAmis().get(i).toString());
		
		Notification n1 = new DemandeAmi(1,p1,p2);
		nm.insert((DemandeAmi) n1);
		List<Notification> ln = nm.findByPersonne(p2.getId());
		for(int i=0; i<ln.size();i++){
			System.out.println(ln.get(i).toString());
		}
		Notification n2 = new Reponse(2,true,p2,p1);
		nm.insert((Reponse) n2);
		List<Notification> ln2 = nm.findByPersonne(p1.getId());
		for(int i=0; i<ln2.size();i++){
			System.out.println(ln2.get(i).toString());
		}
		Salon s = new Salon(1,"les enculés",p3, new ArrayList<Personne>());
		sm.insertSalon(s);
		sm.insertPersonne(s, p1);
		sm.insertPersonne(s, p2);
		List<Personne> occupeSalon = sm.getPersonnes(1);
		System.out.println("Salon :"+ s.toString());
		for(int i=0; i<occupeSalon.size();i++){
			System.out.println("    "+occupeSalon.get(i).toString());
		}
		
		pm.setSalons(p1);
		for(int i=0; i<p1.getSalons().size();i++){
			System.out.println(p1.getSalons().get(i).toString());
		}
		
		List<Message> messages = mm.findListMessagePrive(1,2);
		System.out.println();
		System.out.println("Discussion");
		for(Message m : messages){
			System.out.println(m.toString());
		}
		
	}

}
