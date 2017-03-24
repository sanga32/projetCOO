package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Interface.InfoInterface;
import Interface.MessageInterface;
import Interface.PersonneInterface;
import Interface.PriveInterface;
import Interface.SalonInterface;
import domaine.Personne;
import domaine.SalonPrive;
import message.Message;
import persistance.AmiMapper;
import persistance.MessageMapper;
import persistance.PersonneMapper;
import vue.InterfaceChat;

public class JListAmisController implements ListSelectionListener {

	InterfaceChat interfaceChat;
	PersonneInterface p;

	public JListAmisController(InterfaceChat interfaceChat, PersonneInterface p) {
		this.interfaceChat = interfaceChat;
		this.p = p;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList lsm = (JList) e.getSource();
		int Index = lsm.getSelectionModel().getMinSelectionIndex();

		if ("Amis".equals(interfaceChat.getWest().getSwap().getText())) {

			String personne = "" + ((Personne) lsm.getModel().getElementAt(Index)).getLogin();
			try {
				interfaceChat.getEast().getPersonnePrive(personne);
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Personne destinataire = (((Personne) lsm.getModel().getElementAt(Index)));
			List<MessageInterface> messages = null;
			try {
				InfoInterface info = (InfoInterface) interfaceChat.registry.lookup("info");

				String nomSalonPrive;
				nomSalonPrive = info.salonAmi(p, destinataire);
				PriveInterface salonPrive = (PriveInterface) interfaceChat.registry.lookup(nomSalonPrive);
				messages = salonPrive.getMessages(p, destinataire);
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			interfaceChat.getCenter().getDiscussion(messages);
			JButton supprAmi = new JButton("Supprimer " + destinataire.getLogin());
			interfaceChat.getWest().removeAll();
			try {
				interfaceChat.getWest().getJListAmis();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			interfaceChat.getWest().add(supprAmi);

			supprAmi.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						InfoInterface info = (InfoInterface) interfaceChat.registry.lookup("info");
						String nomSalonPrive;
						nomSalonPrive = info.salonAmi(p, destinataire);
						PriveInterface salonPrive = (PriveInterface) interfaceChat.registry.lookup(nomSalonPrive);
						salonPrive.delete(p, destinataire);
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						interfaceChat.getWest().getJListAmis();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					interfaceChat.getWest().updateUI();
					interfaceChat.getEast().updateUI();
					interfaceChat.getCenter().removeAll();
				}
			});
		}
		interfaceChat.getWest().updateUI();

		interfaceChat.getEast().updateUI();
		interfaceChat.getCenter().updateUI();

	}

}
