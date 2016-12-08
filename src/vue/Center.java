package vue;

import java.awt.Color;

import javax.swing.JPanel;

import domaine.Personne;

public class Center extends JPanel {
	
	InterfaceChat interfaceChat;
	
	public Center(InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		this.setBackground(Color.LIGHT_GRAY);
		this.interfaceChat = interfaceChat;
	}

}
