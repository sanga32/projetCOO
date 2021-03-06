package domaine;

import java.rmi.RemoteException;
import java.util.List;

import server.Salon;

/**
 * Classe qui h�rite de personne et qui repr�sente un utilisateur qui n'est pas l'administrateur
 * @author Kevin delporte, alexandre godon, tedy lequette
 *
 */

public class Utilisateur extends Personne{

	
	public Utilisateur(int id, String login, String mdp) throws RemoteException {
		super(id, login, mdp);
	}
	
	public Utilisateur(int id, String login, String mdp, String nom, String prenom) throws RemoteException {
		super(id, login, mdp, nom, prenom);
	}
	
	public Utilisateur(int id, String login, String mdp, String nom, String prenom, List<Interet> interets, List<SousInteret> sousInterets) throws RemoteException {
		super(id, login, mdp, nom, prenom,interets,sousInterets);
	}

	public boolean isAdmin() {
		return false;
	}

	/**
	 * retourne true si l'utilisateur est le mod�rateur du salon pass� en param�tre
	 */
	public boolean isModo(Salon s){
		if(s.getModo() == this) return true;
		return false;
	}

	@Override
	public void update() {
		
	}
	
	
}
