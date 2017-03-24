package domaine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;//github.com/sanga32/projetCOO.git
import java.util.ArrayList;
import java.util.List;

import Interface.InfoInterface;
import Interface.MessageInterface;
import Interface.PersonneInterface;
import Interface.SalonInterface;
import message.MessageAvecAccuseReception;
import message.MessageAvecExpiration;
import message.MessageChiffre;
import message.MessagePrioritaire;
import message.MessageSimple;
import persistance.MessageMapper;
import persistance.NotificationMapper;
import persistance.SalonMapper;

/**
 * Classe qui représente un salon
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class Salon extends UnicastRemoteObject implements SalonInterface{
	int id;
	String nom;
	PersonneInterface modo;
	private List<PersonneInterface> personnes;
	List<PersonneInterface> connecte;


	public Salon(int id, String nom, PersonneInterface modo, List<PersonneInterface> personnes) throws RemoteException {
		super();
		this.id = id;
		this.nom = nom;
		this.modo = modo;
		this.personnes = personnes;
		connecte = new ArrayList<PersonneInterface>();
	}

	public Salon(int id, String nom, Personne modo) throws RemoteException {
		super();
		this.id = id;
		this.nom = nom;
		this.modo = modo;
		this.personnes = new ArrayList<PersonneInterface>();
		connecte = new ArrayList<PersonneInterface>();

	}

	public boolean isEmpty(){
		if(personnes.isEmpty()) return true;
		return false;
	}

	public void addPersonne(PersonneInterface p) throws RemoteException, SQLException{
		SalonMapper.getInstance().insertPersonne(this, p);	
		personnes.add(p);
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
	public void setModo(PersonneInterface modo) {
		this.modo = modo;
	}

	public List<PersonneInterface> getPersonnes() {
		return personnes;
	}

	public void setPersonnes(List<PersonneInterface> personnes) {
		this.personnes = personnes;
	}

	@Override
	public void send(String s, PersonneInterface exped, PersonneInterface dest, String date, boolean prio, boolean chiff, boolean exp,
			boolean ack) throws RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		System.out.println(connecte+"----connecte----");

		NotificationMapper nm = NotificationMapper.getInstance();
		MessageMapper mm =  MessageMapper.getInstance();
		MessageInterface m = new MessageSimple(s, exped, dest, date, this);
		if (prio) m = new MessagePrioritaire(m);
		if (chiff) m = new MessageChiffre(m);
		if (exp) m = new MessageAvecExpiration(m);
		if (ack) m = new MessageAvecAccuseReception(m);

		mm.insert(m, this);
		//connecte.add(exped);
		for (int i = 0 ; i<personnes.size(); i++){
			nm.insert(new NotifMessage(exped, personnes.get(i), this.getNom()) );
			try{
				Registry registry = LocateRegistry.getRegistry(10000);
				InfoInterface info = (InfoInterface) registry.lookup("info");
				List<PersonneInterface> connects =info.getPersonnes(); 
				System.out.println(connects+"eeeeeeeeeeeeeeeeeeeeeeeeeerrrrrrrrrrrrrrrrrrrrrrrrrrr");
				for (int y=0;y<connects.size();y++){
					if(connects.get(y).equal(personnes.get(i))){
						connects.get(y).receiveNotif();
						System.out.println("(((((((((((((((((((((((((((((((((((((((((((((((((((((");
					}
				}
			}catch (NullPointerException e ){

			}

		}
		for (int i = 0 ; i<connecte.size(); i++){
			connecte.get(i).receiveMessage(m);
		}

	}

	public void connection(PersonneInterface p) throws RemoteException {
		System.out.println(p.getId()+"----personne------");
		connecte.add(p);
	}

	public void deconnection(PersonneInterface p){
		connecte.remove(p);
	}

	@Override
	public void updateModo(PersonneInterface p) throws RemoteException {
		SalonMapper.getInstance().updateModo(this, p);
		this.modo = p;

	}

	public boolean delete() throws RemoteException, NotBoundException {
		if (this.isEmpty()) {
			SalonMapper.getInstance().delete(this);
			Registry registry = LocateRegistry.createRegistry(10000);
			registry.unbind(this.getNom());
			return true;
		}
		return false;

	}

	@Override
	public void quitter(PersonneInterface p) throws RemoteException {
		SalonMapper.getInstance().leaveSalon(p, this);
		personnes.remove(p);
		if (connecte.contains(p))
			connecte.remove(p);
	}

}
