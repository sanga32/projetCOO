package domaine;

public class SousInteret extends Interet{
	int idSousInteret;
	String nomSousInteret;
	
	public SousInteret(int id, String nomSousInteret, Interet interet) {
		super(interet.getIdInteret(),interet.getNomInteret());
		this.idSousInteret = id;
		this.nomSousInteret = nomSousInteret;
	}

	public int getIdSousInteret() {
		return idSousInteret;
	}

	public void setIdSousInteret(int id) {
		this.idSousInteret = id;
	}

	public String getNomSousInteret() {
		return nomSousInteret;
	}

	public void setNomSousInteret(String nom) {
		this.nomSousInteret = nom;
	}
	
	public String toString(){
		return "     " + nomSousInteret;
	}
}
