package vue;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import domaine.Personne;

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
	
	public InterfaceChat(Personne p) {
		// TODO Auto-generated constructor stub
		super();
		this.setLayout(new BorderLayout());
		
		east = new East(this);
		west = new West(p, this);
		north = new North(p, this);
		center = new Center(p, this);
		south = new South(p, this);

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

}
