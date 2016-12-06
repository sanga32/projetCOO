package vue;

import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JFrame;

/**
 * Classe de la fenêtre qui va contenir les différents JPanel de l'application
 * @author Kevin Delporte, Alexandre Godon, Teddy Lequette
 *
 */

@SuppressWarnings("serial")
public class MyFrame extends JFrame{
	
	public MyFrame(String name, Dimension d) throws SQLException {
		super(name);
		Login l = new Login();
		this.setContentPane(l);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(d);
		this.setLocationRelativeTo(null);

	}
	
}
