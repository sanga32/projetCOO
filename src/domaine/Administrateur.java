package domaine;

import java.util.List;

/** 
 * Classe qui hérite de Personne et qui représente un administrateur
 * @author Kevin delporte, alexandre godon, teddy lequetet
 *
 */

public class Administrateur extends Personne{

	public Administrateur(int id, String login, String mdp) {
		super(id, login, mdp);
	}
	
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
