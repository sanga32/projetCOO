package test;

import java.sql.Date;
import java.time.LocalDate;

import domaine.Personne;
import message.MessagePrive;
import persistance.MessageMapper;
import persistance.PersonneMapper;

public class Main {

	public static void main(String args[]){
		
	PersonneMapper pm = PersonneMapper.getInstance();
	MessageMapper mm = MessageMapper.getInstance();
	Personne p1;
	Personne p2;
	

	p1 = pm.findById(1);
	p2 = pm.findById(2);
	
	mm.insert(new MessagePrive(1, "Salut", p1, p2, Date.valueOf(LocalDate.now())));
	
	System.out.println(mm.findByDestinataire(p2));
	}
	
}
