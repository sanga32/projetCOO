package vue;

import java.awt.Color;
import java.sql.SQLException;

import javax.swing.JButton;
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
	
	public North(InfoInterface info, Personne p, InterfaceChat interfaceChat) throws SQLException {
		this.info = info;
		this.interfaceChat = interfaceChat;
		JButton quitter = new JButton("Déconnecter");
		quitter.addActionListener(new QuitterListener(interfaceChat));
		JButton modifierInfos = new JButton("Editer");
		JButton notification = new JButton("Notification");
		JButton demandeAmi = new JButton("Recherche");
		JButton gererComptes = new JButton("Gérer les comptes");
		JButton addInteret = new JButton("Intérêts");
		demandeAmi.addActionListener(new AjouterEnAmiListener(p));
		modifierInfos.addActionListener(new ModifierProfilListener(p));
		gererComptes.addActionListener(new GererComptesListener());
		notification.addActionListener(new NotificationListener(p,interfaceChat));

		addInteret.addActionListener(new AddInteret(p));
		if(NotificationMapper.getInstance().newNotification(p)){
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

}
