package domaine;

import java.util.List;

public class Administrateur extends Personne{

	
	public Administrateur(int id, String login, String mdp, String nom, String prenom) {
		super(id, login, mdp, nom, prenom);
	}

	public Administrateur(int id, String login, String mdp, String nom, String prenom, List<Interet> interets, List<SousInteret> sousInterets) {
		super(id, login, mdp, nom, prenom,interets,sousInterets);
	}
	
	public boolean isAdmin() {
		return true;
	}
	
	public boolean isModo(Salon s){
		return true;
	}
}
