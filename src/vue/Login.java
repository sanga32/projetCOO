package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controlleurs.ValiderLoginListener;

/**
 * Classe Login permet la connexion d'un utilisateur 
 * @author Kevin Delporte, ALexandre Godon, Teddy Lequette
 *
 */

@SuppressWarnings("serial")
public class Login extends JPanel{

	public Login(){

		super();
		this.setLayout(new BorderLayout());
		
		JPanel j = new JPanel();
		JLabel jl = new JLabel("Votre login: ");
		JLabel mdp = new JLabel("Mot de passe: ");
		JTextField log = new JTextField();
		JPasswordField pass = new JPasswordField();
		log.setPreferredSize(new Dimension(70, 30));
		pass.setPreferredSize(new Dimension(70, 30));
		JButton valider = new JButton("OK");
		log.addActionListener(new ValiderLoginListener(log, pass, this));
		pass.addActionListener(new ValiderLoginListener(log, pass, this));
		valider.addActionListener(new ValiderLoginListener(log, pass, this));
	
		j.add(jl);
		j.add(log);
		j.add(mdp);
		j.add(pass);
		j.add(valider);
		this.add(j, BorderLayout.NORTH);

	}
}
