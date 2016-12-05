package vue;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ValiderLoginListener;

/**
 * Classe Login permet la connexion d'un utilisateur 
 * @author Kevin
 *
 */

@SuppressWarnings("serial")
public class Login extends JPanel{

	public Login(){

		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0; // la grille commence en (0, 0)
		gbc.gridheight = 1; // valeur par défaut - peut s'étendre sur une seule ligne.
		gbc.gridwidth = 1; // valeur par défaut - peut s'étendre sur une seule ligne.
		gbc.anchor = GridBagConstraints.PAGE_START; // ou BASELINE_LEADING mais pas WEST.
		gbc.insets = new Insets(10, 15, 0, 0);
		gbc.fill = GridBagConstraints.NONE;	
		
		
		JLabel jl = new JLabel("Votre id: ");
		JTextField id = new JTextField();
		JButton valider = new JButton("OK");
		
		jl.setPreferredSize(new Dimension(50, 35));
		id.setPreferredSize(new Dimension(80, 35));
		id.addActionListener(new ValiderLoginListener(id, this));
		valider.setPreferredSize(new Dimension(80, 35));
		valider.addActionListener(new ValiderLoginListener(id, this));
		this.add(jl, gbc);
		
		gbc.gridx = 1;
		this.add(id, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		this.add(valider, gbc);
		

	}
}
