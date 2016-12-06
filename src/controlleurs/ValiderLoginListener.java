package controlleurs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domaine.Personne;
import persistance.PersonneMapper;
import vue.InterfaceChat;

/**
 * Listener du bouton permettant de valider le login et d'accéder à la page d'information
 * @author Kevin delporte, Alexandre Godon, Teddy Lequette
 *
 */

public class ValiderLoginListener implements ActionListener{

	private JTextField saisieID;
	private JPanel j;

	public ValiderLoginListener(JTextField id, JPanel f) {
		// TODO Auto-generated constructor stub
		saisieID = id;
		j = f;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Personne p;
		if ( saisieID.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Veuillez entrer un ID", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);


		}else{

			try {
				
				int id = Integer.parseInt(saisieID.getText());
				p = PersonneMapper.getInstance().findById(id);


				try {
					
					InterfaceChat ip = null;
					ip = new InterfaceChat(p);
					j.removeAll();
					j.add(ip);
					j.updateUI();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Aucune personne avec cet ID", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			} catch (NumberFormatException e ){
				JOptionPane.showMessageDialog(null, "Veuillez entrer un ID correct !", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}
