package controlleurs;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import vue.Login;

/**
 * Classe listener du bouton annuler qui permet un retour à la page de login
 * @author Kevin Delporte, Alexandre Godon, Teddy Lequette 
 *
 */

public class QuitterListener implements ActionListener {

	private JPanel j;
	
	public QuitterListener(JPanel j) {
		// TODO Auto-generated constructor stub
		this.j = j;
	}

		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Login log = new Login();
		j.removeAll();
		
		j.add(log);
		j.updateUI();
	}

}
