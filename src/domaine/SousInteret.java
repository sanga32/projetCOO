package domaine;

public class SousInteret {
	int id;
	String nom;
	Interet interet;
	
	public SousInteret(int id, String nom, Interet interet) {
		this.id = id;
		this.nom = nom;
		this.interet = interet;
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

	public Interet getInteret() {
		return interet;
	}

	public void setInteret(Interet interet) {
		this.interet = interet;
	}
	
	
}
