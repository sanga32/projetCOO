package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domaine.Personne;
import message.Message;
import persistance.AmiMapper;
import persistance.MessageMapper;
import persistance.PersonneMapper;
import vue.InterfaceChat;

public class JListAmisController implements ListSelectionListener {

	InterfaceChat interfaceChat;

	public JListAmisController(InterfaceChat interfaceChat) {
		this.interfaceChat = interfaceChat;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList lsm = (JList) e.getSource();
		int Index = lsm.getSelectionModel().getMinSelectionIndex();
		
		System.out.println("\nChangement de la selection de liste! ");
		if("Amis".equals(interfaceChat.getWest().getSwap().getText())){

			String personne = ""+((Personne) lsm.getModel().getElementAt(Index)).getLogin();
			interfaceChat.getEast().getPersonnePrive(personne);
			Personne utilisateur = interfaceChat.getWest().getPersonne();
			Personne destinataire = (((Personne) lsm.getModel().getElementAt(Index)));
			List<Message> messages = MessageMapper.getInstance().findListMessagePrive(utilisateur.getId(),destinataire.getId());
			System.out.println("yo");
			interfaceChat.getCenter().getDiscussion(messages);
			JButton supprAmi = new JButton("Supprimer "+destinataire.getLogin());
			interfaceChat.getWest().removeAll();
			interfaceChat.getWest().getJListAmis();
			interfaceChat.getWest().add(supprAmi);

			supprAmi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					AmiMapper am = new AmiMapper().getInstance();
					am.delete(utilisateur, destinataire);
					utilisateur.deleteAmi(destinataire);
					interfaceChat.getWest().getJListAmis();
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
