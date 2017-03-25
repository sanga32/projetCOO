package controlleurs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domaine.Personne;
import persistance.SalonMapper;
import server.Salon;
import vue.West;

public class CreerSalonListener implements ActionListener{

	Personne p;
	JPanel panel;
	JFrame jf;
	West west;
	
	public CreerSalonListener(Personne p, West west) {
		// TODO Auto-generated constructor stub
		this.p=p;
		panel = new JPanel();
		jf = new JFrame("Création d'un salon");
		this.west=west;
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
				Salon s;
				try {
					s = new Salon(4, tsalon.getText(), p);
					SalonMapper.getInstance().insertSalon(s);
				} catch (SQLException | RemoteException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Ce nom de salon est deja pris", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);

					e1.printStackTrace();
				}
				try {
					west.getJListSalons();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				west.updateUI();
			}
		});
	}

}
