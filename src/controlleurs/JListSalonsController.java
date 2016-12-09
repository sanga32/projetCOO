package controlleurs;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import persistance.MessageMapper;
import persistance.SalonMapper;
import vue.InterfaceChat;

public class JListSalonsController implements ListSelectionListener {
	
	InterfaceChat interfaceChat;

	public JListSalonsController(InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		this.interfaceChat = interfaceChat;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		JList lsm = (JList) e.getSource();
		int Index = lsm.getSelectionModel().getMinSelectionIndex();

		System.out.println("\nChangement de la selection de liste! ");
		if ("Salons".equals(interfaceChat.getWest().getSwap().getText())) {
			System.out.println("yo");
			System.out.println("Valeur de l'element: " + lsm.getModel().getElementAt(Index).toString());
			String salon = lsm.getModel().getElementAt(Index).toString();
			interfaceChat.getEast().getJListPersonneSalons(salon);
			MessageMapper mp = MessageMapper.getInstance();
			SalonMapper sm = SalonMapper.getInstance();
			interfaceChat.getCenter().getDiscussion(mp.findListMessageSalon(sm.findByNom(lsm.getModel().getElementAt(Index).toString()).getId()));
		} 
		interfaceChat.getCenter().updateUI();
	}

}
