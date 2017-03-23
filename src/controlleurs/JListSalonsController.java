package controlleurs;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Interface.InfoInterface;
import Interface.MessageInterface;
import Interface.PersonneInterface;
import Interface.SalonInterface;
import persistance.MessageMapper;
import persistance.SalonMapper;
import vue.InterfaceChat;

public class JListSalonsController implements ListSelectionListener {

	InterfaceChat interfaceChat;
	PersonneInterface p;
	InfoInterface info;

	public JListSalonsController(InterfaceChat interfaceChat, PersonneInterface p, InfoInterface info) {
		this.interfaceChat = interfaceChat;
		this.p = p;
		this.info = info;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList lsm = (JList) e.getSource();
		int Index = lsm.getSelectionModel().getMinSelectionIndex();

		if ("Salons".equals(interfaceChat.getWest().getSwap().getText())) {
			String nomSalon = lsm.getModel().getElementAt(Index).toString();
			try {
				interfaceChat.getEast().getJListPersonneSalons(nomSalon);
			} catch (RemoteException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			try {
				SalonInterface salon = info.getSalon(nomSalon);
				List<MessageInterface> message = info.getMessage(salon, p);
				interfaceChat.getCenter().getDiscussion(message);
				salon.connection(p);
				/*if(sm.isModo(p, sm.findByNom(lsm.getModel().getElementAt(Index).toString()).getId()) || p.isAdmin()){
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

					addPersonneSalon.addActionListener(new AddPersonneSalonListener(p, salon, interfaceChat.getEast() ));
					supprPersonneSalon.addActionListener(new SupprPersonneSalonListener(p, salon, interfaceChat.getEast()));
					interfaceChat.getWest().add(addPersonneSalon);
					interfaceChat.getWest().add(supprPersonneSalon);
					quitter.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							try {
								if(!salon.delete()){
										JOptionPane.showMessageDialog(null, "Ce salon n'est pas vide !", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);
								}
							} catch (HeadlessException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} catch (RemoteException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} catch (NotBoundException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
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
					JButton quitter = new JButton("Quitter "+salon.getNom() );
					quitter.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							try {
								salon.quitter(p);
							} catch (RemoteException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
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

				}*/
				interfaceChat.getWest().updateUI();
			/*} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			*/} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		interfaceChat.getCenter().updateUI();
		interfaceChat.getEast().updateUI();
	}

}
