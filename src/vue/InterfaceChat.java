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

	public InterfaceChat(Personne p) {
		// TODO Auto-generated constructor stub
		super();
		this.setLayout(new BorderLayout());
		
		East east = new East(p);
		West west = new West(p);
		North north = new North(p);
		South south = new South(p);
		Center center = new Center(p);
		
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);
		this.add(north, BorderLayout.NORTH);
		this.add(south, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);
		
	}

}