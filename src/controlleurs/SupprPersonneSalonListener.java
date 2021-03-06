package controlleurs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Interface.PersonneInterface;
import Interface.SalonInterface;
import domaine.Personne;
import persistance.SalonMapper;
import server.Salon;
import vue.East;

public class SupprPersonneSalonListener implements ActionListener{
	Personne p;
	JPanel panel;
	SalonInterface salon;
	JFrame jf;
	East east2;

	public SupprPersonneSalonListener(Personne p, SalonInterface salon, East east) {
		this.p = p;
		this.p = p;
		panel= new JPanel();
		jf = new JFrame("Modification de vos informations");
		this.salon = salon;
		this.east2 = east;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		JButton valider = new JButton("Valider");
		JPanel west = new JPanel();
		JPanel east = new JPanel();
		JPanel south = new JPanel();
		BoxLayout boxLayout = new BoxLayout(east, BoxLayout.Y_AXIS); // top to bottom
		BoxLayout boxLayout2 = new BoxLayout(west, BoxLayout.Y_AXIS); // top to bottom

		east.setLayout(boxLayout);
		west.setLayout(boxLayout2);


		JList<PersonneInterface> jl = new JList<PersonneInterface>();
		DefaultListModel<PersonneInterface> lmodel = new DefaultListModel<PersonneInterface>();
		JList<PersonneInterface> jl2 = new JList<PersonneInterface>();
		DefaultListModel<PersonneInterface> lmodel2 = new DefaultListModel<PersonneInterface>();

		try {
			for ( PersonneInterface pers : salon.getPersonnes()){
				lmodel.addElement(pers);
			}
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		jl.setModel(lmodel);
		jl2.setModel(lmodel2);

		jl.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				JList lsm = (JList) e.getSource();
				int index = lsm.getSelectionModel().getMinSelectionIndex();
				jl2.clearSelection();
				if(! lmodel2.contains((Personne) lsm.getModel().getElementAt(index)))
					lmodel2.addElement((Personne) lsm.getModel().getElementAt(index));

			}
		});

		jl2.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				JList lsm = (JList) e.getSource();
				int index = lsm.getSelectionModel().getMinSelectionIndex();

				try {
					lmodel2.remove(index);

				} catch (Exception e1){

				}

			}
		});

		valider.addActionListener(new ActionListener() {
			SalonMapper sm = SalonMapper.getInstance();
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i< lmodel2.size(); i++){
					try {
						sm.leaveSalon( lmodel2.getElementAt(i), salon);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
				
				JOptionPane.showMessageDialog(null, "Modifications enregistrées", "Validation",  JOptionPane.INFORMATION_MESSAGE);
				jf.setVisible(false);
				try {
					east2.getJListPersonneSalons(salon.getNom());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				east2.updateUI();
			}
		});

		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));

		JScrollPane listScrollPane2 = new JScrollPane(jl2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane2.setPreferredSize(new Dimension(115, 150));

		west.add(new JLabel("     "+p.getLogin()+"     "));
		west.add(listScrollPane);
		east.add(new JLabel("     "+"Supprimer"+"     "));
		east.add(listScrollPane2);
		south.add(valider);
		panel.add(east, BorderLayout.EAST);
		panel.add(west, BorderLayout.WEST);
		panel.add(south, BorderLayout.SOUTH);
		jf.getContentPane().add(panel);
		jf.setSize(350, 350);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
}
