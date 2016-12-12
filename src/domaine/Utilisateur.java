package domaine;

public class Utilisateur extends Personne{

	
	public Utilisateur(int id, String login, String mdp, String nom, String prenom) {
		super(id, login, mdp, nom, prenom);
	}

	public boolean isAdmin() {
		return false;
	}

	public boolean isModo(Salon s){
		if(s.getModo() == this) return true;
		return false;
	}
}
