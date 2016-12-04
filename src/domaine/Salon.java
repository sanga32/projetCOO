package domaine;

import java.util.ArrayList;
import java.util.List;

public class Salon {
	int id;
	String nom;
	Personne modo;
	List<Personne> personnes;
	public Salon(int id, String nom, Personne modo) {
		super();
		this.id = id;
		this.nom = nom;
		this.modo = modo;
		this.personnes = new ArrayList<Personne>(); //proxy � faire
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
	public Personne getModo() {
		return modo;
	}
	public void setModo(Personne modo) {
		this.modo = modo;
	}
	
	
}