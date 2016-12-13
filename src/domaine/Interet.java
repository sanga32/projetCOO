package domaine;

public class Interet {
	int idInteret;
	String nomInteret;
	
	public Interet(int id,String nom){
		this.idInteret = id;
		this.nomInteret = nom;
	}
	
	public int getIdInteret() {
		return idInteret;
	}
	public void setIdInteret(int id) {
		this.idInteret = id;
	}
	public String getNomInteret() {
		return nomInteret;
	}
	public void setNomInteret(String nom) {
		this.nomInteret = nom;
	}
	
	public String toString(){
		return nomInteret;
	}
	
}
