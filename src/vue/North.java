package vue;

import java.awt.Color;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interface.InfoInterface;
import controlleurs.AddInteret;
import controlleurs.AjouterEnAmiListener;
import controlleurs.GererComptesListener;
import controlleurs.ModifierProfilListener;
import controlleurs.NotificationListener;
import controlleurs.QuitterListener;
import domaine.Personne;
import persistance.NotificationMapper;


/**
 * Panel représentant la partie permettant de gérer ses informations
 * @author Kevin delporte, alexandre godon, teddy lequette
 *
 */
public class North extends JPanel {

	InterfaceChat interfaceChat;
	InfoInterface info;
	JButton notification;
	
	public North(InfoInterface info, Personne p, InterfaceChat interfaceChat) throws SQLException, RemoteException {
		this.info = info;
		this.interfaceChat = interfaceChat;
		JButton quitter = new JButton("Déconnecter");
		quitter.addActionListener(new QuitterListener(interfaceChat));
		JButton modifierInfos = new JButton("Editer");
		notification = new JButton("Notification");
		JButton demandeAmi = new JButton("Recherche");
		JButton gererComptes = new JButton("Gérer les comptes");
		JButton addInteret = new JButton("Intérêts");
		demandeAmi.addActionListener(new AjouterEnAmiListener(p));
		modifierInfos.addActionListener(new ModifierProfilListener(p));
		gererComptes.addActionListener(new GererComptesListener());
		notification.addActionListener(new NotificationListener(p,interfaceChat, info));

		addInteret.addActionListener(new AddInteret(p));
		if(info.getNotification(p).size()!=0){
			notification.setBackground(Color.RED);
		}

		this.add(quitter);
		this.add(new JLabel("     "+p.getLogin()+"     "));
		if ( p.isAdmin()){
			this.add(gererComptes);
		}else{
			this.add(modifierInfos);
		}
		this.add(demandeAmi);
		this.add(addInteret);
		this.add(notification);
	}

	public void actNotif() {
		// TODO Auto-generated method stub
		notification.setBackground(Color.RED);
	}

}
