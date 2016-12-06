package service;
import java.awt.Dimension;
import java.sql.SQLException;

import vue.MyFrame;

public class Main {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MyFrame l = new MyFrame("Login", new Dimension(500,500));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
