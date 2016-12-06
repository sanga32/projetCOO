package vue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlleurs.ModifierProfilListener;
import controlleurs.QuitterListener;
import domaine.Personne;

public class North extends JPanel {

	public North(Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		JButton quitter = new JButton("Déconnecter");
		quitter.addActionListener(new QuitterListener(interfaceChat));
		JButton modifierInfos = new JButton("Editer");
		
		modifierInfos.addActionListener(new ModifierProfilListener(p));
		
		this.add(quitter);
		this.add(new JLabel(""+p.getLogin()));
		this.add(modifierInfos);
	}

}
