package vue;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import javax.swing.JPanel;

import Interface.InfoInterface;
import domaine.Personne;

/**
 * Interface principale contenant tous les panels
 * @author Kevin Delporte, Alexandre Godon, Teddy lequette
 *
 */

public class InterfaceChat extends JPanel {
	
	East east;
	West west;
	North north;
	South south;
	Center center;
	Registry registry;
	
	public InterfaceChat(Personne p, InfoInterface info) throws SQLException, RemoteException {
		// TODO Auto-generated constructor stub
		super();
		this.registry = LocateRegistry.createRegistry(10000);
		this.setLayout(new BorderLayout());
		
		east = new East(info, p, this);
		west = new West(info,p, this);
		north = new North(info,p, this);
		center = new Center(info,p, this);
		south = new South(info,p, this);
		p.setInterfaceChat(this);

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
