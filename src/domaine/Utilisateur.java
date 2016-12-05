package domaine;

public class Utilisateur extends Personne{

	
	public Utilisateur(int id, String login, String mdp, String prenom, String nom) {
		super(id, login, mdp, prenom, nom);
	}

	public boolean isAdmin() {
		return false;
	}

	public boolean isModo(Salon s){
		if(s.getModo() == this) return true;
		return false;
	}
}
