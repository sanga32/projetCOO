package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		JLabel jl = new JLabel("Votre id: ");
		jl.setPreferredSize(new Dimension(50, 50));
		JTextField id = new JTextField();
		id.setPreferredSize(new Dimension(50, 30));
		JButton valider = new JButton("OK");
		id.addActionListener(new ValiderLoginListener(id, this));
		valider.addActionListener(new ValiderLoginListener(id, this));
		j.add(jl);
		
		j.add(id);
		j.add(valider);
		this.add(j, BorderLayout.NORTH);

	}
}
