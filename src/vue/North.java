package vue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlleurs.AjouterEnAmiListener;
import controlleurs.ModifierProfilListener;
import controlleurs.NotificationListener;
import controlleurs.QuitterListener;
import domaine.Personne;

public class North extends JPanel {

	InterfaceChat interfaceChat;

	public North(Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		this.interfaceChat = interfaceChat;
		JButton quitter = new JButton("Déconnecter");
		quitter.addActionListener(new QuitterListener(interfaceChat));
		JButton modifierInfos = new JButton("Editer");
		JButton notification = new JButton("Notification");
		JButton demandeAmi = new JButton("Recherche");
		demandeAmi.addActionListener(new AjouterEnAmiListener(p));
		modifierInfos.addActionListener(new ModifierProfilListener(p));
		notification.addActionListener(new NotificationListener(p,interfaceChat));
		
		
		this.add(quitter);
		this.add(new JLabel("     "+p.getLogin()+"     "));
		this.add(modifierInfos);
		this.add(demandeAmi);
		this.add(notification);
	}

}
