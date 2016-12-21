package controlleurs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domaine.DemandeAmi;
import domaine.Notification;
import domaine.Personne;
import domaine.Reponse;
import persistance.AmiMapper;
import persistance.NotificationMapper;
import persistance.PersonneMapper;
import vue.InterfaceChat;

public class NotificationListener implements ActionListener{
	Personne p;
	JPanel panel;
	JFrame jf;
	InterfaceChat interfaceChat;

	public NotificationListener(Personne p, InterfaceChat interfaceChat) {
		this.p = p;
		this.p = p;
		panel= new JPanel();
		this.interfaceChat = interfaceChat;
		jf = new JFrame("Modification de vos informations");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		JButton valider = new JButton("Ok");
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		BoxLayout boxLayout = new BoxLayout(center, BoxLayout.Y_AXIS); // top to
																		// bottom
		center.setLayout(boxLayout);

		JButton jb = (JButton) e.getSource();
		jb.setBackground(null);
		
		JList<Notification> jl = new JList<Notification>();
		DefaultListModel<Notification> lmodel = new DefaultListModel<Notification>();

		List<Notification> notifs = NotificationMapper.getInstance().findByPersonne(p.getId());
		for(Notification n : notifs){
			lmodel.addElement(n);
		}

		jl.setModel(lmodel);
		jl.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e1) {
				// TODO Auto-generated method stub
				JList lsm = (JList) e1.getSource();
				int index = lsm.getSelectionModel().getMinSelectionIndex();
				Object notif = lsm.getModel().getElementAt(index);
				if(notif instanceof DemandeAmi){
					JFrame reponse = new JFrame("Donner votre r�ponse");
					JPanel pan = new JPanel();
					pan.removeAll();
					JButton accepter = new JButton("Accepter");
					JButton refuser = new JButton("Refuser");
					accepter.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Personne expediteur = ((DemandeAmi) notif).getDestinataire();
							Personne destinataire = ((DemandeAmi) notif).getExpediteur();
							Reponse rep = new Reponse(true,expediteur,destinataire);
							NotificationMapper.getInstance().insert(rep);
							NotificationMapper.getInstance().delete((DemandeAmi) notif);
							AmiMapper.getInstance().insert(expediteur, destinataire);
							lmodel.remove(index);
							p.addAmi(destinataire);
							interfaceChat.getWest().getJListAmis();
							interfaceChat.getWest().updateUI();
							reponse.setVisible(false);
							
						}
					});
					
					refuser.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Personne expediteur = ((DemandeAmi) notif).getDestinataire();
							Personne destinataire = ((DemandeAmi) notif).getExpediteur();
							Reponse rep = new Reponse(false,expediteur,destinataire);
							NotificationMapper.getInstance().insert(rep);
							NotificationMapper.getInstance().delete((DemandeAmi) notif);
							lmodel.remove(index);
							reponse.setVisible(false);
						}
					});
					pan.add(accepter);
					pan.add(refuser);
					reponse.getContentPane().add(pan);
					reponse.setSize(200, 75);
					reponse.setResizable(false);
					reponse.setLocationRelativeTo(null);
					reponse.setVisible(true);
				}else if(notif instanceof Reponse){
					lmodel.remove(index);
					NotificationMapper.getInstance().delete((Reponse) notif);
				}
			}
		});
		
		
		valider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jf.setVisible(false);
				interfaceChat.getWest().getJListAmis();
				interfaceChat.getWest().updateUI();
			}
		});
		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));

		north.add(new JLabel("     " + p.getLogin() + "     "));
		center.add(listScrollPane);
		panel.add(north, BorderLayout.NORTH);
		panel.add(center, BorderLayout.CENTER);
		panel.add(valider, BorderLayout.SOUTH);
		jf.getContentPane().add(panel);
		jf.setSize(350, 350);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
}
