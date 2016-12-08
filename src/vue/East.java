package vue;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controlleurs.CreerSalonListener;
import controlleurs.JListAmisController;
import controlleurs.SwapSalonAmisListener;
import domaine.Personne;
import domaine.Salon;
import persistance.PersonneMapper;
import persistance.SalonMapper;

public class East extends JPanel {

	Salon s;
	Personne p;

	public East(InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to
		// bottom
		this.setLayout(boxLayout);
		this.setPreferredSize(new Dimension(120, 300));
		this.setBackground(Color.white);
	}

	public void getPersonnePrive(String personne) {
		this.removeAll();
		JList<Personne> jl = new JList<Personne>();
		DefaultListModel<Personne> lmodel = new DefaultListModel<Personne>();
		Personne p = PersonneMapper.getInstance().findByLogin(personne);
		lmodel.addElement(p);

		jl.setModel(lmodel);
		this.add(jl);
	}

	public void getJListPersonneSalons(String salon) {
		this.removeAll();
		JList<Personne> jl = new JList<Personne>();
		DefaultListModel<Personne> lmodel = new DefaultListModel<Personne>();
		Salon s = SalonMapper.getInstance().findByNom(salon);
		for (int i =0; i<s.getPersonnes().size();i++) {
			lmodel.addElement(s.getPersonnes().get(i));
		}

		jl.setModel(lmodel);
		this.add(jl);
	}

	public void init(){
		this.removeAll();
		JList<Salon> jl = new JList<Salon>();
		DefaultListModel<Salon> lmodel = new DefaultListModel<Salon>();


		jl.setModel(lmodel);

		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));


		this.add(listScrollPane);
	}

}
