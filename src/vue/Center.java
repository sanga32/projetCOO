package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Interface.InfoInterface;
import controlleurs.MyCellRenderer;
import domaine.Personne;
import message.Message;
import persistance.PersonneMapper;

/**
 * Panel représentant l'historique d'une discussion 
 * @author Kevin Delporte, Alexandre Godon, Teddy Lequette
 *
 */

public class Center extends JPanel {

	InterfaceChat interfaceChat;
	JList<Message> jl;
	DefaultListModel<Message> lmodel;
	InfoInterface info;

	public Center(InfoInterface info, Personne p, InterfaceChat interfaceChat) {
		// TODO Auto-generated constructor stub
		this.interfaceChat = interfaceChat;
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to
		// bottom
		this.setLayout(boxLayout);
		this.setPreferredSize(new Dimension(120, 300));
		this.setBackground(Color.GRAY);
		this.info = info;
	}

	
	public void getDiscussion(List<Message> messages) {
		this.removeAll();
		jl = new JList<Message>();
		lmodel = new DefaultListModel<Message>();
		for ( Message mess : messages){
			lmodel.addElement(mess);
		}
		jl.setCellRenderer(new MyCellRenderer());
		jl.setModel(lmodel);
		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));
		listScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
			 
		    private int lastmax=-1;
		 
		    public void adjustmentValueChanged(AdjustmentEvent e) {  
		    if ( !e.getValueIsAdjusting() ) {
			int max=e.getAdjustable().getMaximum();
			int pos=e.getAdjustable().getValue();
		 
			if ( lastmax==-1 || lastmax==pos+e.getAdjustable().getVisibleAmount()) {
			    e.getAdjustable().setValue(max);  
			}
			lastmax=max;
			}
		    }
		 
		});

		this.add(listScrollPane);
	}
	
	public void addMessage(Message m){
		lmodel.addElement(m);
	}
	
}
