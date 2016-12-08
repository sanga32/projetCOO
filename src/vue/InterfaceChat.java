package vue;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import domaine.Personne;
import domaine.Salon;
import domaine.Utilisateur;

/**
 * 
 * @author Kevin Delporte, Alexandre Godon, Teddy lequette
 *
 */

public class InterfaceChat extends JPanel {
	
	East east;
	West west;
	North north;
	South south;
	Center center;
	Personne utilisateur;
	Boolean inSalon;
	Salon salon;
	static Personne destinataire;
	
	public InterfaceChat(Personne p) {
		// TODO Auto-generated constructor stub
		super();
		this.setLayout(new BorderLayout());
		this.utilisateur = p;
		inSalon = false;
		salon = new Salon();
		destinataire = new Utilisateur() ;
		east = new East(this);
		west = new West(this);
		north = new North(this);
		south = new South(this);
		center = new Center(this);
		
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);
		this.add(north, BorderLayout.NORTH);
		this.add(south, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);
		
	}

	public East getEast() {
		return east;
	}

	public West getWest() {
		return west;
	}

	public North getNorth() {
		return north;
	}


	public South getSouth() {
		return south;
	}


	public Center getCenter() {
		return center;
	}

	public Boolean getInSalon() {
		return inSalon;
	}

	public void setInSalon(Boolean inSalon) {
		this.inSalon = inSalon;
	}

	public Salon getSalon() {
		return salon;
	}

	public void setSalon(Salon salon) {
		this.salon = salon;
	}

	public Personne getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(Personne destinataire) {
		this.destinataire = destinataire;
	}

	public Personne getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Personne utilisateur) {
		this.utilisateur = utilisateur;
	}
	

}
