package vue;

import java.awt.Dimension;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controlleurs.AddPersonneSalonListener;
import controlleurs.CreerSalonListener;
import controlleurs.JListAmisController;
import controlleurs.JListSalonsController;
import controlleurs.SwapSalonAmisListener;
import domaine.Personne;
import domaine.Salon;
import persistance.SalonMapper;

public class West extends JPanel {

	Personne p;
	JButton swap;
	JButton creerSalon;
	
	InterfaceChat interfaceChat;
	
	public West(Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
		this.setLayout(boxLayout);
		this.p = p;
		this.interfaceChat = interfaceChat;
		this.setPreferredSize(new Dimension(150, 150));
				
		getJListAmis();
	}
	
	
	
	public Personne getPersonne() {
		return p;
	}



	public JButton getSwap() {
		return swap;
	}

	public void getJListAmis(){
		this.removeAll();
		JList<Personne> jl = new JList<Personne>();
		DefaultListModel<Personne> lmodel = new DefaultListModel<Personne>();

		for ( Personne pers : p.getAmis()){
			lmodel.addElement(pers);
		}

		jl.setModel(lmodel);
		jl.addListSelectionListener(new JListAmisController(interfaceChat));

		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));
		swap = new JButton("Amis");
		swap.addActionListener(new SwapSalonAmisListener(this));
		creerSalon = new JButton("Creer un salon");
		creerSalon.addActionListener(new CreerSalonListener(p));
		
		this.add(swap);
		this.add(listScrollPane);
		this.add(creerSalon);

	}
	
	public void getJListSalons(){
		this.removeAll();
		JList<Salon> jl = new JList<Salon>();
		DefaultListModel<Salon> lmodel = new DefaultListModel<Salon>();

		SalonMapper.getInstance().setSalons(p);
		
		for ( Salon s : p.getSalons()){
			lmodel.addElement(s);
		}

		jl.setModel(lmodel);
		jl.addListSelectionListener(new JListSalonsController(interfaceChat, p));

		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));
		swap = new JButton("Salons");
		swap.addActionListener(new SwapSalonAmisListener(this));
		creerSalon = new JButton("Creer un salon");
		creerSalon.addActionListener(new CreerSalonListener(p));
		
		this.add(swap);
		this.add(listScrollPane);
		this.add(creerSalon);
	}
	

}
