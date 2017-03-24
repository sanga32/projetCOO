package controlleurs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Interface.InfoInterface;
import domaine.Administrateur;
import domaine.Personne;
import domaine.Utilisateur;
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
		Personne p = null;
		if ( saisieID.getText().equals("") || saisieMDP.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Veuillez entrer un login et un mot de passe correct", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);


		}else{

			Registry registry;
			InfoInterface info = null;
			try {
				registry = LocateRegistry.getRegistry(10000);
				info = (InfoInterface) registry.lookup("info");
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String login = saisieID.getText();
			String mdp = saisieMDP.getText();
			int id = 0;
			try {
				id = info.connection(login, mdp);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}


			try {
				if(id > 0){
					if(id == 1){
						try {
							p = new Administrateur(id,login,mdp);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						try {
							p = new Utilisateur(id,login,mdp);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//p = PersonneMapper.getInstance().findByLogMdp(login, mdp);
				}
				InterfaceChat ip = null;
				info.connecter(p);
				ip = new InterfaceChat(p, info);
				j.removeAll();
				j.add(ip);
				j.updateUI();
			} catch (NullPointerException | SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Aucune personne avec ces identifiants", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
