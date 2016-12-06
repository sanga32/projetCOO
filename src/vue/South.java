package vue;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlleurs.ChoixTypeMessageListener;
import domaine.Personne;

public class South extends JPanel {

	public South(Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		this.setLayout(new GridLayout(2, 0));
		JPanel message = new JPanel();
		JPanel choix = new JPanel();
		JTextField msg = new JTextField();
		msg.setPreferredSize(new Dimension(400, 30));
		JButton envoyer = new JButton("Envoyer");
		message.add(msg);
		message.add(envoyer);

		JCheckBox check1 = new JCheckBox("Prioritaire");
		JCheckBox check2 = new JCheckBox("ACK");
		JCheckBox check3 = new JCheckBox("Chiffré");
		JCheckBox check4 = new JCheckBox("Expiration");
		
		check1.addActionListener(new ChoixTypeMessageListener());
		check2.addActionListener(new ChoixTypeMessageListener());
		check3.addActionListener(new ChoixTypeMessageListener());
		check4.addActionListener(new ChoixTypeMessageListener());

		choix.add(check1);
		choix.add(check2);
		choix.add(check3);
		choix.add(check4);

		this.add(message);
		this.add(choix);
	}

}
