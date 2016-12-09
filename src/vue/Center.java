package vue;

import java.awt.Color;

import javax.swing.JPanel;

import domaine.Personne;

public class Center extends JPanel {

	InterfaceChat interfaceChat;

	public Center(Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		this.interfaceChat = interfaceChat;
		this.setBackground(Color.LIGHT_GRAY);
	}

}
