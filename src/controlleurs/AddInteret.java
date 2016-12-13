package controlleurs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import domaine.Interet;
import domaine.Personne;
import domaine.Salon;
import domaine.SousInteret;
import persistance.InteretPersonneMapper;
import persistance.SalonMapper;
import vue.East;

public class AddInteret implements ActionListener{

	Personne p;
	JPanel panel;
	JFrame jf;

	public AddInteret(Personne p) {
		this.p = p;
		panel= new JPanel();
		jf = new JFrame("Modification de vos informations");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
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

		InteretPersonneMapper ipm = InteretPersonneMapper.getInstance();

		JList<Interet> jl = new JList<Interet>();
		DefaultListModel<Interet> lmodel = new DefaultListModel<Interet>();
		JList<Interet> jl2 = new JList<Interet>();
		DefaultListModel<Interet> lmodel2 = new DefaultListModel<Interet>();

		List<Interet> listInterets = ipm.findInteret();

		for ( Interet i : listInterets){
			lmodel.addElement(i);
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
				if (lsm.getModel().getElementAt(index) instanceof SousInteret){
					if(! lmodel2.contains((SousInteret) lsm.getModel().getElementAt(index)))
						lmodel2.addElement((SousInteret) lsm.getModel().getElementAt(index));
				}else {
					if(! lmodel2.contains((Interet) lsm.getModel().getElementAt(index)))
						lmodel2.addElement((Interet) lsm.getModel().getElementAt(index));

				}
						
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
			InteretPersonneMapper ipm = InteretPersonneMapper.getInstance();
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i< lmodel2.size(); i++){
					System.out.println(lmodel2.getElementAt(i));
					if ( lmodel2.getElementAt(i) instanceof SousInteret)
						ipm.insert(p, lmodel2.getElementAt(i));
					else 
						ipm.insert(p, lmodel2.getElementAt(i));

				}

				JOptionPane.showMessageDialog(null, "Modifications enregistrées", "Validation",  JOptionPane.INFORMATION_MESSAGE);
				jf.setVisible(false);
			}
		});

		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));

		JScrollPane listScrollPane2 = new JScrollPane(jl2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane2.setPreferredSize(new Dimension(115, 150));

		west.add(new JLabel("     "+p.getLogin()+"     "));
		west.add(listScrollPane);
		east.add(new JLabel("     "+"Ajouter"+"     "));
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
