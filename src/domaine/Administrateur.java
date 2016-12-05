package domaine;

public class Administrateur extends Personne{

	
	public Administrateur(int id, String login, String mdp, String prenom, String nom) {
		super(id, login, mdp, prenom, nom);
	}

	public boolean isAdmin() {
		return true;
	}
	
	public boolean isModo(Salon s){
		return true;
	}
}
