package domaine;

import java.util.ArrayList;
import java.util.List;

import Interface.PersonneInterface;
import Interface.SalonInterface;
import message.MessageSimple;
import persistance.MessageMapper;

/**
 * Classe qui représente un salon
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class Salon implements SalonInterface{
	int id;
	String nom;
	PersonneInterface modo;
	private List<PersonneInterface> personnes;
	List<PersonneInterface> connecte;


	public Salon(int id, String nom, PersonneInterface modo, List<PersonneInterface> personnes) {
		super();
		this.id = id;
		this.nom = nom;
		this.modo = modo;
		this.personnes = personnes;
	}

	public Salon(int id, String nom, Personne modo) {
		super();
		this.id = id;
		this.nom = nom;
		this.modo = modo;
		this.personnes = new ArrayList<PersonneInterface>();
	}

	public boolean isEmpty(){
		if(personnes.isEmpty()) return true;
		return false;
	}

	public void addPersonne(Personne p){
		this.personnes.add(p);
	}

	public void removePersonne(Personne p){
		this.personnes.remove(p);
	}

	public String toString(){
		return nom;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public PersonneInterface getModo() {
		return modo;
	}
	public void setModo(Personne modo) {
		this.modo = modo;
	}

	public List<PersonneInterface> getPersonnes() {
		return personnes;
	}

	public void setPersonnes(List<PersonneInterface> personnes) {
		this.personnes = personnes;
	}

	@Override
	public void send(String s, Personne exped, Personne dest, String date, boolean prio, boolean chiff, boolean exp,
			boolean ack) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		MessageMapper mm =  MessageMapper.getInstance();
		MessageSimple m = new MessageSimple();
		mm.insert(m, this);
		for (int i = 0 ; i<connecte.size(); i++){
			connecte.get(i).receiveMessage(m);
		}
	}




}
