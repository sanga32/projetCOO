package domaine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistance.VirtualProxyListAmi;

public abstract class Personne {
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
	
	public Personne(){
		this.amis = new VirtualProxyListAmi(id);
		this.interets = new ArrayList<Interet>();
		this.sousInterets = new ArrayList<SousInteret>();
		this.salons = new ArrayList<Salon>();
	}
	
	public Personne(int id, String login, String mdp, String prenom, String nom) {
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
	
	public abstract boolean isAdmin();
	
	public abstract boolean isModo(Salon s) throws SQLException;
	
	public void addPersonne(Personne p){
		this.amis.add(p);
	}
	
	public void removePersonne(Personne p){
		this.amis.remove(p);
	}
	
	public void addInteret(Interet i){
		this.interets.add(i);
	}
	
	public void removeInteret(Interet i){
		this.interets.remove(i);
	}
	public void addSousInteret(SousInteret si){
		this.sousInterets.add(si);
	}
	
	public void removeSousInteret(SousInteret si){
		this.sousInterets.remove(si);
	}
	public void addSalon(Salon s){
		this.salons.add(s);
	}
	
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
}
