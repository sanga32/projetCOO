package domaine;

public class Salon {
	int id;
	String nom;
	Personne modo;
	public Salon(int id, String nom, Personne modo) {
		super();
		this.id = id;
		this.nom = nom;
		this.modo = modo;
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
