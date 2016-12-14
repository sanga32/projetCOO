package controlleurs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
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
	private JTextField saisieMDP;
	private JPanel j;

	public ValiderLoginListener(JTextField id, JTextField pass, JPanel f) {
		// TODO Auto-generated constructor stub
		saisieID = id;
		saisieMDP = pass;
		j = f;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Personne p;
		if ( saisieID.getText().equals("") || saisieMDP.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Veuillez entrer un login et un mot de passe correct", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);


		}else{


			String login = saisieID.getText();
			String mdp = saisieMDP.getText();

			p = PersonneMapper.getInstance().findByLogMdp(login, mdp);


			try {

				InterfaceChat ip = null;
				ip = new InterfaceChat(p);
				j.removeAll();
				j.add(ip);
				j.updateUI();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Aucune personne avec ces identifiants", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

	}

}
