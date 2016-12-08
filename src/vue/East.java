package vue;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import controlleurs.JListAmisController;
import domaine.Personne;
import domaine.Salon;
import persistance.SalonMapper;

public class East extends JPanel {
	
	Salon s;
	Personne p;

	public East(InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
		this.setLayout(boxLayout);
	
	}

	public void getPersonnePrive(String personne) {
	}

	public void getJListPersonneSalons(String salon) {
		this.removeAll();
		JList<Personne> jl = new JList<Personne>();
		DefaultListModel<Personne> lmodel = new DefaultListModel<Personne>();
		Salon s = SalonMapper.getInstance().findByNom(salon);
		for ( Personne pers : s.getPersonnes()){
			lmodel.addElement(pers);
		}
		
		jl.setModel(lmodel);
	}

}
