package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
					JButton addPersonneSalon = new JButton("+");
					JButton supprPersonneSalon = new JButton("-");

					addPersonneSalon.addActionListener(new AddPersonneSalonListener(p, sm.findByNom(lsm.getModel().getElementAt(Index).toString()), interfaceChat.getEast() ));
					supprPersonneSalon.addActionListener(new SupprPersonneSalonListener(p, sm.findByNom(salon), interfaceChat.getEast()));
					interfaceChat.getWest().add(addPersonneSalon);
					interfaceChat.getWest().add(supprPersonneSalon);

					
				}else{
					interfaceChat.getWest().getJListSalons();
					JButton quitter = new JButton("Quitter "+sm.findByNom(salon).getNom() );
					quitter.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							sm.leaveSalon(p, sm.findByNom(salon));
							interfaceChat.getWest().getJListSalons();
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
