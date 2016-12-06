package controlleurs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domaine.Personne;
import persistance.PersonneMapper;

public class ModifierProfilListener implements ActionListener{

	Personne p;
	JPanel panel;
	JFrame jf;

	public ModifierProfilListener(Personne p){
		this.p = p;
		panel= new JPanel();
		jf = new JFrame("Modification de vos informations");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		JButton valider = new JButton("Valider");
		JPanel north = new JPanel();
		JPanel center = new JPanel();

		BoxLayout boxLayout = new BoxLayout(center, BoxLayout.Y_AXIS); // top to bottom
		center.setLayout(boxLayout);

		JLabel mdp = new JLabel("Nouveau mot de passe");
		JTextField tmdp = new JTextField();
		mdp.setPreferredSize(new Dimension(150, 50));

		JLabel nom = new JLabel("Nouveau nom");
		JTextField tnom = new JTextField();
		nom.setPreferredSize(new Dimension(150, 50));

		JLabel prenom = new JLabel("Nouveau prenom");
		JTextField tprenom = new JTextField();
		prenom.setPreferredSize(new Dimension(150, 50));

		center.add(mdp);
		center.add(tmdp);
		center.add(nom);
		center.add(tnom);
		center.add(prenom);
		center.add(tprenom);

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
				if (!tmdp.getText().equals(""))
					p.setMdp(tmdp.getText());
				if (!tnom.getText().equals(""))
					p.setNom(tnom.getText());
				if (!tprenom.getText().equals(""))
					p.setPrenom(tprenom.getText());
				PersonneMapper.getInstance().update(p);
			}
		});


	}

}
