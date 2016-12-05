package test;

import java.sql.Date;
import java.time.LocalDate;

import domaine.Personne;
import domaine.Utilisateur;
import message.MessagePrive;
import persistance.AmiMapper;
import persistance.MessageMapper;
import persistance.PersonneMapper;

public class Main {

	public static void main(String args[]) {

		PersonneMapper pm = PersonneMapper.getInstance();
		MessageMapper mm = MessageMapper.getInstance();
		AmiMapper am = AmiMapper.getInstance();
		Personne p1 = new Utilisateur(1, "godona", "1234", "godon", "alexandre");
		Personne p2 = new Utilisateur(2, "delportek", "4321", "delporte", "kevin");
		Personne p3 = new Utilisateur(3, "lequette", "5748", "vaze", "amandine");
		;

		mm.clear();
		am.clear();
		pm.clear();
		pm.insert(p1);
		pm.insert(p2);
		pm.insert(p3);

		mm.insert(new MessagePrive(1, "Salut", p1, p2, Date.valueOf(LocalDate.now())));

		System.out.println(mm.findByDestinataire(p2.getId()));
		System.out.println(pm.findById(1));

		am.insert(p1, p2);
		am.insert(p3, p1);
		Personne alex = pm.findById(1);
		System.out.println("Ami de " + alex.getPrenom());
		for (int i = 0; i < alex.getAmis().size(); i++)
			System.out.println(alex.getAmis().get(i).toString());

	}

}
