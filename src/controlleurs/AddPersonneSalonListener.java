package controlleurs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domaine.Personne;
import domaine.Salon;
import persistance.AmiMapper;
import persistance.PersonneMapper;
import persistance.SalonMapper;

public class AddPersonneSalonListener implements ActionListener {

	Personne p;
	JPanel panel;
	JFrame jf;
	
	public AddPersonneSalonListener(Personne p) {
		this.p = p;
		this.p = p;
		panel= new JPanel();
		jf = new JFrame("Modification de vos informations");
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		JButton valider = new JButton("Valider");
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		BoxLayout boxLayout = new BoxLayout(center, BoxLayout.Y_AXIS); // top to bottom
		center.setLayout(boxLayout);

		
		JList<Personne> jl = new JList<Personne>();
		DefaultListModel<Personne> lmodel = new DefaultListModel<Personne>();

		for ( Personne pers : p.getAmis()){
			lmodel.addElement(pers);
		}

		jl.setModel(lmodel);
		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));

		north.add(new JLabel("     "+p.getLogin()+"     "));
		center.add(listScrollPane);
		panel.add(north, BorderLayout.NORTH);
		panel.add(center, BorderLayout.CENTER);
		panel.add(valider, BorderLayout.SOUTH);
		jf.getContentPane().add(panel);
		jf.setSize(350, 350);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}

}
