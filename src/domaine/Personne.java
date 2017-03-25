package domaine;

import java.awt.Color;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Interface.InfoInterface;
import Interface.MessageInterface;
import Interface.NotifInterface;
import Interface.PersonneInterface;
import Interface.PriveInterface;
import Interface.SalonInterface;
import persistance.VirtualProxyListAmi;
import vue.InterfaceChat;

/**
 * Classe abtraite repr�sentant une personne
 * @author Kevin delporte, alexandre godon, teddy lequette
 *
 */

public abstract class Personne extends UnicastRemoteObject implements PersonneInterface {
	int id;
	String login;
	String mdp;
	String prenom;
	String nom;
	List<PersonneInterface> amis;
	List<Interet> interets;
	List<SousInteret> sousInterets;
	List<Salon> salons;
	List<Notification> notifications;
	InfoInterface info;
	String salon = "";
	String prive = "";
	
	

	private InterfaceChat interfaceChat;

	public Personne() throws RemoteException{
		this.amis = new VirtualProxyListAmi(id);
		this.interets = new ArrayList<Interet>();
		this.sousInterets = new ArrayList<SousInteret>();
		this.salons = new ArrayList<Salon>();
	}
	public Personne(int id, String login, String mdp) throws RemoteException {
		super();
		this.id = id;
		this.login = login;
		this.mdp = mdp;
		this.amis = new VirtualProxyListAmi(id);
		this.interets = new ArrayList<Interet>();
		this.sousInterets = new ArrayList<SousInteret>();
		this.salons = new ArrayList<Salon>();
		this.notifications = new ArrayList<Notification>();
	}

	public Personne(int id, String login, String mdp, String nom, String prenom) throws RemoteException {
		super();
		this.id = id;
		this.login = login;
		this.mdp = mdp;
		this.prenom = prenom;
		this.nom = nom;
		this.amis = new VirtualProxyListAmi(id);
		this.interets = new ArrayList<Interet>();
		this.sousInterets = new ArrayList<SousInteret>();
		this.salons = new ArrayList<Salon>();
		this.notifications = new ArrayList<Notification>();
	}

	public Personne(int id, String login, String mdp, String nom, String prenom, List<Interet>  interets, List<SousInteret> sousInterets) throws RemoteException {
		super();
		this.id = id;
		this.login = login;
		this.mdp = mdp;
		this.prenom = prenom;
		this.nom = nom;
		this.amis = new VirtualProxyListAmi(id);
		this.interets = interets;
		this.sousInterets = sousInterets;
		this.salons = new ArrayList<Salon>();
		this.notifications = new ArrayList<Notification>();
	}

	public abstract boolean isAdmin() throws SQLException;

	/**
	 * return true si la personne est mod�rateur du salon pass� en param�tre
	 * @param s
	 * @return
	 * @throws SQLException
	 */
	public abstract boolean isModo(Salon s) throws SQLException;

	public void addPersonne(Personne p){
		this.amis.add(p);
	}

	public void removePersonne(Personne p){
		this.amis.remove(p);
	}

	/**
	 * Ajoute l'int�r�t pass� en param�tre de la liste d'int�rets
	 * @param i
	 */
	public void addInteret(Interet i){
		this.interets.add(i);
	}

	/**
	 * Supprime l'int�r�t pass� en param�tre de la liste d'int�r�ts
	 * @param i
	 */
	public void removeInteret(Interet i){
		this.interets.remove(i);
	}

	/**
	 * Ajoute le sous int�r�t pass� en param�tre � la liste de sous int�r�ts
	 * @param si
	 */
	public void addSousInteret(SousInteret si){
		this.sousInterets.add(si);
	}

	/** 
	 * supprime le sous int�r�ts pass� en param�tre de la liste de sous int�r�ts
	 * @param si
	 */
	public void removeSousInteret(SousInteret si){
		this.sousInterets.remove(si);
	}
	/**
	 * Ajoute le salon pass� en param�tre a la liste des salons
	 * @param s
	 */
	public void addSalon(Salon s){
		this.salons.add(s);
	}

	/**
	 * Supprime le salon pass� en param�tre de la liste de salons 
	 * @param s
	 */
	public void removeSalon(Salon s){
		this.salons.remove(s);
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public List<PersonneInterface> getAmis() throws RemoteException {
		return ((VirtualProxyListAmi) amis).getAmis();
	}

	public void setAmis(List<PersonneInterface> amis) {
		this.amis = amis;
	}
	public List<Interet> getInterets() {
		return interets;
	}
	public void setInterets(List<Interet> interets) {
		this.interets = interets;
	}
	public List<SousInteret> getSousInterets() {
		return sousInterets;
	}
	public void setSousInterets(List<SousInteret> sousInterets) {
		this.sousInterets = sousInterets;
	}
	public List<Salon> getSalons() {
		return salons;
	}
	public void setSalons(List<Salon> salons) {
		this.salons = salons;
	}

	public String toString(){
		return login ;
	}

	/**
	 * supprime la personne pass�e en param�tre de la liste d'amis
	 * @param destinataire
	 */
	public void deleteAmi(Personne destinataire) {
		// TODO Auto-generated method stub
		amis.remove(destinataire);
	}

	/**
	 * Ajoute la personne pass�e en param�tre dans la liste d'ami
	 * @param destinataire
	 */
	public void addAmi(PersonneInterface destinataire){
		amis.add(destinataire);
	}
	public void setInterfaceChat(InterfaceChat interfaceChat, InfoInterface info) {
		// TODO Auto-generated method stub
		this.interfaceChat = interfaceChat;
		this.info = info;
	}

	public void receiveMessage(MessageInterface m)
	{
		interfaceChat.getCenter().addMessage(m);
		interfaceChat.getCenter().updateUI();
	}

	public void receiveNotif() throws RemoteException, SQLException
	{

		interfaceChat.getNorth().actNotif();
		//interfaceChat.getNorth().updateUI();
		info.getNotification(this);

	}

	public boolean equal(PersonneInterface p) throws RemoteException {
		if(this.id == p.getId()) return true;
		return false;
	}
	
	public String getSalon() {
		return salon;
	}
	public void setSalon(String salon) {
		this.salon = salon;
	}
	public String getPrive() {
		return prive;
	}
	public void setPrive(String prive) {
		this.prive = prive;
	}
	public void deconnection() throws AccessException, RemoteException, NotBoundException {
		if(!"".equals(this.getSalon())){
			SalonInterface oldSalon = (SalonInterface) InterfaceChat.registry.lookup(this.getSalon());
			oldSalon.deconnection(this);
			this.setSalon("");
		}else if(!"".equals(this.getPrive())){
			PriveInterface oldSalon = (PriveInterface) InterfaceChat.registry.lookup(this.getPrive());
			oldSalon.deconnection(this);
			this.setPrive("");
		}
		
	}

}
