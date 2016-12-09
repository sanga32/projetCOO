package controlleurs;

import java.util.List;

import javax.swing.JList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domaine.Personne;
import persistance.MessageMapper;
import persistance.PersonneMapper;
import message.Message;
import vue.East;
import vue.InterfaceChat;
import vue.West;

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
		if ("Salons".equals(interfaceChat.getWest().getSwap().getText())) {
			System.out.println("yo");
			System.out.println("Valeur de l'element: " + lsm.getModel().getElementAt(Index).toString());
			String salon = lsm.getModel().getElementAt(Index).toString();
			interfaceChat.getEast().getJListPersonneSalons(salon);
		} else if("Amis".equals(interfaceChat.getWest().getSwap().getText())){
			String personne = ""+((Personne) lsm.getModel().getElementAt(Index)).getLogin();
			interfaceChat.getEast().getPersonnePrive(personne);
			Personne utilisateur = interfaceChat.getWest().getPersonne();
			Personne destinataire = PersonneMapper.getInstance().findByLogin(personne);
			List<Message> messages = MessageMapper.getInstance().findListMessagePrive(utilisateur.getId(),destinataire.getId());
			interfaceChat.getCenter().getDiscussion(messages);
			
		}
		
		interfaceChat.getEast().updateUI();
		interfaceChat.getCenter().updateUI();

	}

}
