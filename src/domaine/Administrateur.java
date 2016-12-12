package domaine;

public class Administrateur extends Personne{

	
	public Administrateur(int id, String login, String mdp, String nom, String prenom) {
		super(id, login, mdp, nom, prenom);
	}

	public boolean isAdmin() {
		return true;
	}
	
	public boolean isModo(Salon s){
		return true;
	}
}
