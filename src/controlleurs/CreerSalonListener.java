package controlleurs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domaine.Personne;
import domaine.Salon;
import persistance.SalonMapper;

public class CreerSalonListener implements ActionListener{

	Personne p;
	JPanel panel;
	JFrame jf;
	
	public CreerSalonListener(Personne p) {
		// TODO Auto-generated constructor stub
		this.p=p;
		panel = new JPanel();
		jf = new JFrame("Création d'un salon");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		JButton valider = new JButton("Valider");
		JPanel north = new JPanel();
		JPanel center = new JPanel();

		center.setLayout(new FlowLayout());

		JLabel salon = new JLabel("Salon : ");
		JTextField tsalon = new JTextField();
		tsalon.setPreferredSize(new Dimension(150, 30));

		center.add(salon);
		center.add(tsalon);

		north.add(new JLabel("     "+p.getLogin()+"     "));
		panel.add(north, BorderLayout.NORTH);
		panel.add(center, BorderLayout.CENTER);
		panel.add(valider, BorderLayout.SOUTH);
		jf.getContentPane().add(panel);
		jf.setSize(350, 350);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);

		valider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jf.setVisible(false);
				Salon s = new Salon(4, tsalon.getText(), p);
				SalonMapper.getInstance().insertSalon(s);
			}
		});
	}

}
