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
		JPanel north = new JPanel();
		JPanel center = new JPanel();
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
					Personne newAmi = PersonneMapper.getInstance().findByLogin(amiLogin);
					AmiMapper.getInstance().insert(p, newAmi);
				}
			}
		});
		
		JScrollPane listScrollPane = new JScrollPane(jl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScrollPane.setPreferredSize(new Dimension(115, 150));

		north.add(new JLabel("     " + p.getLogin() + "     "));
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
