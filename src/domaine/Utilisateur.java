package domaine;

import java.util.List;

public class Utilisateur extends Personne{

	
	public Utilisateur(int id, String login, String mdp, String nom, String prenom) {
		super(id, login, mdp, nom, prenom);
	}
	
	public Utilisateur(int id, String login, String mdp, String nom, String prenom, List<Interet> interets, List<SousInteret> sousInterets) {
		super(id, login, mdp, nom, prenom,interets,sousInterets);
	}

	public boolean isAdmin() {
		return false;
	}

	public boolean isModo(Salon s){
		if(s.getModo() == this) return true;
		return false;
	}
}
