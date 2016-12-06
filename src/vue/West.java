package vue;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controlleurs.JListAmisController;
import domaine.Personne;

public class West extends JPanel {

	public West(Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		JList<Personne> jl = new JList<Personne>();
		DefaultListModel<Personne> lmodel = new DefaultListModel<Personne>();

		for ( Personne pers : p.getAmis()){
			lmodel.addElement(pers);
		}

		jl.setModel(lmodel);
		jl.getSelectionModel().addListSelectionListener(new JListAmisController());

		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(listScrollPane);
	}

}
