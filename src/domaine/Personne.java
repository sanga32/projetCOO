package domaine;

import java.util.List;

public abstract class Personne {
	int id;
	String login;
	String mdp;
	String prenom;
	String nom;
	List<Personne> personnes;
	List<Interet> interets;
	List<SousInteret> sousInterets;
	List<Salon> salons;

}
