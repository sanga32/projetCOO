package controlleurs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domaine.Notification;
import domaine.Personne;
import persistance.AmiMapper;
import persistance.NotificationMapper;
import persistance.PersonneMapper;

public class AjouterEnAmiListener implements ActionListener {
	Personne p;
	JPanel panel;
	JFrame jf;
	String amiLogin="";

	public AjouterEnAmiListener(Personne p) {
		this.p = p;
		this.p = p;
		panel= new JPanel();
		jf = new JFrame("Recherche Personne");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		JButton valider = new JButton("Ok");
		JButton actualiser = new JButton("Actualiser");
		JButton rechercheParNom = new JButton("Recherche par nom/prenom");
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		BoxLayout boxLayout = new BoxLayout(center, BoxLayout.Y_AXIS); // top to
																		// bottom
		center.setLayout(boxLayout);

		JList<Personne> jl = new JList<Personne>();
		DefaultListModel<Personne> lmodel = new DefaultListModel<Personne>();

		List<Personne> personnes = PersonneMapper.getInstance().findNewPersonne(p.getId());
		for(Personne p : personnes){
			lmodel.addElement(p);
		}

		jl.setModel(lmodel);
		
		jl.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				JList lsm = (JList) e.getSource();
				int index = lsm.getSelectionModel().getMinSelectionIndex();
				amiLogin = ((Personne) lsm.getModel().getElementAt(index)).getLogin();

			}
		});
		
		valider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(amiLogin != ""){
					System.out.println("yo");
					Personne newAmi = PersonneMapper.getInstance().findByLogin(amiLogin);
					AmiMapper.getInstance().insert(p, newAmi);
				}
			}
		});
		
		actualiser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(Personne p : personnes){
					lmodel.addElement(p);
				}
			}
		});
		
		rechercheParNom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame recherche = new JFrame("Recherche par Nom/Prenom");
				JPanel pan = new JPanel();
				JTextField nom = new JTextField();
				JTextField prenom = new JTextField();
				JButton validerRecherche = new JButton("Rechercher");
				nom.setPreferredSize(new Dimension(100, 30));
				prenom.setPreferredSize(new Dimension(100, 30));
				validerRecherche.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						for(Personne p : personnes){ 
							System.out.println(nom.getText() +  "   " + prenom.getText());
							System.out.println(p.getNom() + "   " + p.getPrenom());
							if(!nom.getText().equals(p.getNom()) || !prenom.getText().equals(p.getPrenom()))
								lmodel.removeElement(p);
						}
						recherche.setVisible(false);
					}
				});
				pan.add(nom);
				pan.add(prenom);
				pan.add(validerRecherche);
				recherche.getContentPane().add(pan);
				recherche.setSize(350, 350);
				recherche.setResizable(false);
				recherche.setLocationRelativeTo(null);
				recherche.setVisible(true);
			}
		});
		
		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));

		north.add(new JLabel("     " + p.getLogin() + "     "));
		north.add(rechercheParNom);
		center.add(listScrollPane);
		south.add(actualiser);
		south.add(valider);
		panel.add(north, BorderLayout.NORTH);
		panel.add(center, BorderLayout.CENTER);
		panel.add(south, BorderLayout.SOUTH);
		jf.getContentPane().add(panel);
		jf.setSize(350, 350);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
}
