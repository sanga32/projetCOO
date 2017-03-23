package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Interface.InfoInterface;
import Interface.PersonneInterface;
import Interface.SalonInterface;
import controlleurs.CreerSalonListener;
import controlleurs.JListAmisController;
import controlleurs.MySalonCellRenderer;
import controlleurs.SwapSalonAmisListener;
import domaine.Personne;
import domaine.Salon;
import persistance.PersonneMapper;
import persistance.SalonMapper;
import sun.security.x509.IssuerAlternativeNameExtension;

/**
 * panel représentant soit la liste des personnes d'un salon selectionné, soit
 * l'ami selectionné
 * 
 * @author Kevin delporte, alexandre godon, teddy lequette
 *
 */

public class East extends JPanel {

<<<<<<< HEAD
	SalonInterface s;
	Personne p, p2;
=======
	Salon s;
	Personne p;
	PersonneInterface p2;
>>>>>>> branch 'master' of https://github.com/sanga32/projetCOO.git
	Personne destinataire;
	InterfaceChat interfaceChat;
	InfoInterface info;

	public East(InfoInterface info, Personne p2, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		this.info = info;
		this.interfaceChat = interfaceChat;
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to
		// bottom
		this.p = p2;
		this.setLayout(boxLayout);
		this.setPreferredSize(new Dimension(120, 300));
		this.setBackground(Color.white);
	}

	public void getPersonnePrive(String personne) throws RemoteException {
		this.removeAll();
		JList<Personne> jl = new JList<Personne>();
		DefaultListModel<Personne> lmodel = new DefaultListModel<Personne>();
		destinataire = PersonneMapper.getInstance().findByLogin(personne);
		lmodel.addElement(destinataire);
		jl.setModel(lmodel);
		this.add(jl);
	}

	public SalonInterface getSalon() {
		return s;
	}

	public void setSalon(SalonInterface s) {
		this.s = s;
	}

	public void getJListPersonneSalons(String salon) throws RemoteException {
		this.removeAll();
		JList<PersonneInterface> jl = new JList<PersonneInterface>();
		DefaultListModel<PersonneInterface> lmodel = new DefaultListModel<PersonneInterface>();
		SalonMapper sm = new SalonMapper().getInstance();
		s = info.getSalon(salon);

		jl.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				//SalonMapper sm = SalonMapper.getInstance();
				try {
					SalonInterface s = info.getSalon(salon);

					JList lsm = (JList) e.getSource();
					int index = lsm.getSelectionModel().getMinSelectionIndex();
					lsm.setCellRenderer(new MySalonCellRenderer());
					p2 = (PersonneInterface) lsm.getModel().getElementAt(index);
					if (sm.isModo(p, s.getId())
							&& !p.getLogin().equals(((Personne) lsm.getModel().getElementAt(index)).getLogin())) {
						JButton donnerDroits = new JButton("Donner droits de modération");
						donnerDroits.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
<<<<<<< HEAD
								//sm.leaveSalon(p2, s);
								s.UpdateModo(s, p2);
=======
>>>>>>> branch 'master' of https://github.com/sanga32/projetCOO.git
								try {
									s.quitter(p2);
									s.updateModo(p2);
								} catch (RemoteException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								s.addPersonne(p);
								try {
									interfaceChat.getWest().getJListSalons();
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								interfaceChat.getWest().updateUI();
								try {
									interfaceChat.getEast().getJListPersonneSalons(salon);
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								interfaceChat.getEast().updateUI();
								lsm.setCellRenderer(new MySalonCellRenderer());

							}
						});
						interfaceChat.getEast().getJListPersonneSalons(salon);

						interfaceChat.getEast().add(donnerDroits);

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				interfaceChat.getEast().updateUI();
			}
		});

		for (int i = 0; i < s.getPersonnes().size(); i++) {
			lmodel.addElement(s.getPersonnes().get(i));
		}

		jl.setModel(lmodel);
		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));
		this.add(listScrollPane);

	}

	public Personne getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(Personne destinataire) {
		this.destinataire = destinataire;
	}

}
