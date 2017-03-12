package controlleurs;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domaine.Personne;
import persistance.MessageMapper;
import persistance.SalonMapper;
import vue.InterfaceChat;

public class JListSalonsController implements ListSelectionListener {
	
	InterfaceChat interfaceChat;
	Personne p;

	public JListSalonsController(InterfaceChat interfaceChat, Personne p) {
		this.interfaceChat = interfaceChat;
		this.p = p;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList lsm = (JList) e.getSource();
		int Index = lsm.getSelectionModel().getMinSelectionIndex();

		if ("Salons".equals(interfaceChat.getWest().getSwap().getText())) {
			String salon = lsm.getModel().getElementAt(Index).toString();
			interfaceChat.getEast().getJListPersonneSalons(salon);
			MessageMapper mp = MessageMapper.getInstance();
			SalonMapper sm = SalonMapper.getInstance();
			interfaceChat.getCenter().getDiscussion(mp.findListMessageSalon(sm.findByNom(lsm.getModel().getElementAt(Index).toString()).getId(), p));
			try {
				if(sm.isModo(p, sm.findByNom(lsm.getModel().getElementAt(Index).toString()).getId()) || p.isAdmin()){
					try {
						interfaceChat.getWest().getJListSalons();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JButton addPersonneSalon = new JButton("Ajouter");
					JButton supprPersonneSalon = new JButton("Supprimer");
					JPanel pane = new JPanel();
					
					JButton quitter = new JButton("Quitter");

					addPersonneSalon.addActionListener(new AddPersonneSalonListener(p, sm.findByNom(lsm.getModel().getElementAt(Index).toString()), interfaceChat.getEast() ));
					supprPersonneSalon.addActionListener(new SupprPersonneSalonListener(p, sm.findByNom(salon), interfaceChat.getEast()));
					interfaceChat.getWest().add(addPersonneSalon);
					interfaceChat.getWest().add(supprPersonneSalon);
					quitter.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							if( sm.findByNom(salon).isEmpty()){
								sm.delete(sm.findByNom(salon));
							} else {
								JOptionPane.showMessageDialog(null, "Ce salon n'est pas vide !", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);

							}
							try {
								interfaceChat.getWest().getJListSalons();
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							interfaceChat.getWest().updateUI();
						}
					});
					interfaceChat.getWest().add(quitter);

					
				}else{
					try {
						interfaceChat.getWest().getJListSalons();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JButton quitter = new JButton("Quitter "+sm.findByNom(salon).getNom() );
					quitter.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							sm.leaveSalon(p, sm.findByNom(salon));
							try {
								interfaceChat.getWest().getJListSalons();
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							interfaceChat.getWest().updateUI();

						}
					});
					interfaceChat.getWest().add(quitter);

				}
				interfaceChat.getWest().updateUI();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		interfaceChat.getCenter().updateUI();
		interfaceChat.getEast().updateUI();
	}

}
