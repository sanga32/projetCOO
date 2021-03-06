package vue;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Interface.InfoInterface;
import Interface.PersonneInterface;
import Interface.SalonInterface;
import controlleurs.AddPersonneSalonListener;
import controlleurs.CreerSalonListener;
import controlleurs.JListAmisController;
import controlleurs.JListSalonsController;
import controlleurs.MySalonCellRenderer;
import controlleurs.SwapSalonAmisListener;
import domaine.Personne;
import persistance.SalonMapper;
import server.Salon;

/**
 * Panel permettant de g�rer sa liste d'amis et de salons
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */


public class West extends JPanel {

	Personne p;
	JButton swap;
	JButton creerSalon;
	InfoInterface info;
	
	InterfaceChat interfaceChat;
	
	public West(InfoInterface info, Personne p, InterfaceChat interfaceChat) throws RemoteException {
		this.info = info;
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
		this.setLayout(boxLayout);
		this.p = p;
		this.interfaceChat = interfaceChat;
		//this.setPreferredSize(new Dimension(150, 150));
				
		getJListAmis();
	}
	
	
	
	public Personne getPersonne() {
		return p;
	}



	public JButton getSwap() {
		return swap;
	}

	public void getJListAmis() throws RemoteException{
		this.removeAll();
		JList<PersonneInterface> jl = new JList<PersonneInterface>();
		DefaultListModel<PersonneInterface> lmodel = new DefaultListModel<PersonneInterface>();

		List<PersonneInterface> amis = info.getAmi(p);
		for ( PersonneInterface pers : amis){
			lmodel.addElement(pers);
		}

		jl.setModel(lmodel);
		jl.addListSelectionListener(new JListAmisController(interfaceChat, p));

		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));
		swap = new JButton("Amis");
		swap.addActionListener(new SwapSalonAmisListener(this));
		
		
		this.add(swap);
		this.add(listScrollPane);

	}
	
	public void getJListSalons() throws RemoteException{
		this.removeAll();
		JList<SalonInterface> jl = new JList<SalonInterface>();
		DefaultListModel<SalonInterface> lmodel = new DefaultListModel<SalonInterface>();
		
		List<SalonInterface> salons = info.getSalons(p);
		
		for ( SalonInterface s : salons){
			lmodel.addElement(s);
		}

		jl.setModel(lmodel);
		jl.addListSelectionListener(new JListSalonsController(interfaceChat, p, info));

		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));
		swap = new JButton("Salons");
		swap.addActionListener(new SwapSalonAmisListener(this));
		creerSalon = new JButton("Creer un salon");
		creerSalon.addActionListener(new CreerSalonListener(p, this));
		jl.setCellRenderer(new MySalonCellRenderer());

		this.add(swap);
		this.add(listScrollPane);
		this.add(creerSalon);
	}
	

}
