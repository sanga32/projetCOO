package vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlleurs.ChoixTypeMessageListener;
import controlleurs.EnvoyerMessageListener;
import domaine.Personne;
import message.Message;
import message.MessagePrive;

public class South extends JPanel {

	Message m;
	JPanel message;
	List<String> listeChoix;
	
	public South(Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		this.setLayout(new GridLayout(2, 0));
		m = new MessagePrive();
		message = new JPanel();
		JPanel choix = new JPanel();
		JTextField msg = new JTextField();
		msg.setPreferredSize(new Dimension(400, 30));
		JButton envoyer = new JButton("Envoyer");
		listeChoix = new ArrayList<String>();
		envoyer.addActionListener(new EnvoyerMessageListener(msg, listeChoix));
		message.add(msg);
		message.add(envoyer);

		JCheckBox check1 = new JCheckBox("Prioritaire");
		JCheckBox check2 = new JCheckBox("ACK");
		JCheckBox check3 = new JCheckBox("Chiffre");
		JCheckBox check4 = new JCheckBox("Expiration");
		
		check1.addActionListener(new ChoixTypeMessageListener(listeChoix));
		check2.addActionListener(new ChoixTypeMessageListener(listeChoix));
		check3.addActionListener(new ChoixTypeMessageListener(listeChoix));
		check4.addActionListener(new ChoixTypeMessageListener(listeChoix));

		choix.add(check1);
		choix.add(check2);
		choix.add(check3);
		choix.add(check4);

		this.add(message);
		this.add(choix);
	}

	
	
}
