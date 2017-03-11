package service;
import java.awt.Dimension;
import java.sql.SQLException;

import persistance.PersonneMapper;
import vue.MyFrame;

public class Main {

	/**
	 * @param args1
	 */
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//on crée un admin si jamais n'y en a pas déja un en base (admin : IDPersonne = 1)
		// pseudo = "admin" par default
		// mdp = "admin" par default
		try {
			MyFrame l = new MyFrame("Login", new Dimension(800,650));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
