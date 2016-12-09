package controlleurs;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JList;
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
		// TODO Auto-generated constructor stub
		this.interfaceChat = interfaceChat;
		this.p = p;
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
			try {
				if(sm.isModo(p, sm.findByNom(lsm.getModel().getElementAt(Index).toString()).getId())){
					interfaceChat.getWest().getJListSalons();
					JButton addPersonneSalon = new JButton("Ajouter une personne");
					addPersonneSalon.addActionListener(new AddPersonneSalonListener(p));
					interfaceChat.getWest().add(addPersonneSalon);
					
				}else{
					interfaceChat.getWest().getJListSalons();
				}
				interfaceChat.getWest().updateUI();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		interfaceChat.getCenter().updateUI();
	}

}
