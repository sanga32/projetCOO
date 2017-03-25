package domaine;

import java.rmi.RemoteException;
import java.util.List;

import server.Salon;

/** 
 * Classe qui hérite de Personne et qui représente un administrateur
 * @author Kevin delporte, alexandre godon, teddy lequetet
 *
 */

public class Administrateur extends Personne{

	public Administrateur(int id, String login, String mdp) throws RemoteException {
		super(id, login, mdp);
	}
	
	public Administrateur(int id, String login, String mdp, String nom, String prenom) throws RemoteException {
		super(id, login, mdp, nom, prenom);
	}

	public Administrateur(int id, String login, String mdp, String nom, String prenom, List<Interet> interets, List<SousInteret> sousInterets) throws RemoteException {
		super(id, login, mdp, nom, prenom,interets,sousInterets);
	}
	
	public boolean isAdmin() {
		return true;
	}
	
	public boolean isModo(Salon s){
		return true;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
