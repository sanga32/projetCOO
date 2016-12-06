package vue;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controlleurs.JListAmisController;
import domaine.Personne;

public class West extends JPanel {

	Personne p;
	
	public West(Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
		this.setLayout(boxLayout);
		this.p = p;
		JButton swap = new JButton("Salons");
		this.add(swap);
		this.add(getJListAmis());
	}
	
	public JScrollPane getJListAmis(){
		JList<Personne> jl = new JList<Personne>();
		DefaultListModel<Personne> lmodel = new DefaultListModel<Personne>();

		for ( Personne pers : p.getAmis()){
			lmodel.addElement(pers);
		}

		jl.setModel(lmodel);
		jl.getSelectionModel().addListSelectionListener(new JListAmisController());

		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setPreferredSize(new Dimension(115, 150));
		
		return listScrollPane;
	}

}
