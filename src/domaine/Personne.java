package domaine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Interface.MessageInterface;
import Interface.PersonneInterface;
import persistance.VirtualProxyListAmi;
import vue.InterfaceChat;

/**
 * Classe abtraite représentant une personne
 * @author Kevin delporte, alexandre godon, teddy lequette
 *
 */

public abstract class Personne implements PersonneInterface{
	int id;
	String login;
	String mdp;
	String prenom;
	String nom;
	List<Personne> amis;
	List<Interet> interets;
	List<SousInteret> sousInterets;
	List<Salon> salons;
	List<Notification> notifications;
	private InterfaceChat interfaceChat;

	public Personne(){
		this.amis = new VirtualProxyListAmi(id);
		this.interets = new ArrayList<Interet>();
		this.sousInterets = new ArrayList<SousInteret>();
		this.salons = new ArrayList<Salon>();
	}
	public Personne(int id, String login, String mdp) {
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

	public Personne(int id, String login, String mdp, String nom, String prenom) {
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

	public Personne(int id, String login, String mdp, String nom, String prenom, List<Interet>  interets, List<SousInteret> sousInterets) {
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
	 * return true si la personne est modérateur du salon passé en paramètre
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
	 * Ajoute l'intérêt passé en paramètre de la liste d'intérets
	 * @param i
	 */
	public void addInteret(Interet i){
		this.interets.add(i);
	}

	/**
	 * Supprime l'intérêt passé en paramètre de la liste d'intérêts
	 * @param i
	 */
	public void removeInteret(Interet i){
		this.interets.remove(i);
	}
	
	/**
	 * Ajoute le sous intérêt passé en paramètre à la liste de sous intérêts
	 * @param si
	 */
	public void addSousInteret(SousInteret si){
		this.sousInterets.add(si);
	}

	/** 
	 * supprime le sous intérêts passé en paramètre de la liste de sous intérêts
	 * @param si
	 */
	public void removeSousInteret(SousInteret si){
		this.sousInterets.remove(si);
	}
	/**
	 * Ajoute le salon passé en paramètre a la liste des salons
	 * @param s
	 */
	public void addSalon(Salon s){
		this.salons.add(s);
	}

	/**
	 * Supprime le salon passé en paramètre de la liste de salons 
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
	public List<Personne> getAmis() {
		return ((VirtualProxyListAmi) amis).getAmis();
	}

	public void setAmis(List<Personne> amis) {
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
	 * supprime la personne passée en paramètre de la liste d'amis
	 * @param destinataire
	 */
	public void deleteAmi(Personne destinataire) {
		// TODO Auto-generated method stub
		amis.remove(destinataire);
	}

	/**
	 * Ajoute la personne passée en paramètre dans la liste d'ami
	 * @param p
	 */
	public void addAmi(Personne p){
		amis.add(p);
	}
	public void setInterfaceChat(InterfaceChat interfaceChat) {
		// TODO Auto-generated method stub
		this.interfaceChat = interfaceChat;
	}

	public void receiveMessage(MessageInterface m)
	{
		interfaceChat.getCenter().addMessage(m.getMessage());
	}
}
