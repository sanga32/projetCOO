package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import domaine.Personne;
import message.Message;
import persistance.PersonneMapper;

public class Center extends JPanel {

	InterfaceChat interfaceChat;

	public Center(Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		this.interfaceChat = interfaceChat;
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to
		// bottom
		this.setLayout(boxLayout);
		this.setPreferredSize(new Dimension(120, 300));
		this.setBackground(Color.GRAY);
	}

	
	public void getDiscussion(List<Message> messages) {
		this.removeAll();
		JList<Message> jl = new JList<Message>();
		DefaultListModel<Message> lmodel = new DefaultListModel<Message>();
		for ( Message mess : messages){
			lmodel.addElement(mess);
		}
		jl.setModel(lmodel);
		this.add(jl);
	}
}
