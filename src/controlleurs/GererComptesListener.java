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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domaine.Personne;
import domaine.Utilisateur;
import persistance.PersonneMapper;

public class GererComptesListener implements ActionListener {

	JPanel panel;
	JFrame jf;

	public GererComptesListener() {
		panel = new JPanel();
		jf = new JFrame("Gestion des comptes");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		panel.removeAll();
		panel.setLayout(new FlowLayout());

		JButton creerCompte = new JButton("Créer un compte");
		JButton supprCompte = new JButton("Supprimer un compte");
		JButton modifCompte = new JButton("Modifier un compte");

		modifCompte.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.removeAll();
				JLabel login = new JLabel("login: ");
				JTextField log = new JTextField();
				log.setPreferredSize(new Dimension(100, 30));
				JButton ok = new JButton("OK");

				ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if (log.getText().equals("")){
							JOptionPane.showMessageDialog(null, "Veuillez entrer un login", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);
						} else {
							panel.removeAll();
							PersonneMapper pm = PersonneMapper.getInstance();


							Personne pc = pm.findByLogin(log.getText());
							if ( pc != null ) {
								JButton valider = new JButton("Valider");


								JPanel center = new JPanel();
								BoxLayout boxLayout = new BoxLayout(center, BoxLayout.Y_AXIS); // top to bottom
								center.setLayout(boxLayout);

								JLabel login = new JLabel("Nouveau login");
								JTextField tlogin = new JTextField(pc.getLogin());
								login.setPreferredSize(new Dimension(150, 50));

								JLabel mdp = new JLabel("Nouveau mot de passe");
								JTextField tmdp = new JTextField(pc.getMdp());
								mdp.setPreferredSize(new Dimension(150, 50));

								JLabel nom = new JLabel("Nouveau nom");
								JTextField tnom = new JTextField(pc.getNom());
								nom.setPreferredSize(new Dimension(150, 50));

								JLabel prenom = new JLabel("Nouveau prenom");
								JTextField tprenom = new JTextField(pc.getPrenom());
								prenom.setPreferredSize(new Dimension(150, 50));

								center.add(login);
								center.add(tlogin);
								center.add(mdp);
								center.add(tmdp);
								center.add(nom);
								center.add(tnom);
								center.add(prenom);
								center.add(tprenom);

								valider.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										jf.setVisible(false);
										if (!tlogin.getText().equals(""))
											pc.setLogin(tlogin.getText());
										if (!tmdp.getText().equals(""))
											pc.setMdp(tmdp.getText());
										if (!tnom.getText().equals(""))
											pc.setNom(tnom.getText());
										if (!tprenom.getText().equals(""))
											pc.setPrenom(tprenom.getText());
										PersonneMapper.getInstance().update(pc);
										JOptionPane.showMessageDialog(null, "Modifications enregistrées", "Information",  JOptionPane.INFORMATION_MESSAGE);

										jf.setVisible(false);
									}
								});
								center.add(valider);
								panel.add(center, BorderLayout.CENTER);
								panel.updateUI();
							}else {
								JOptionPane.showMessageDialog(null, "Ce login n'existe pas", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);
								jf.setVisible(false);
							}
						}
					}
				});

				panel.add(login);
				panel.add(log);
				panel.add(ok);
				panel.updateUI();

			}
		});

		supprCompte.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.removeAll();
				JLabel login = new JLabel("login: ");
				JTextField log = new JTextField();
				log.setPreferredSize(new Dimension(100, 30));
				JButton ok = new JButton("OK");

				ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if (log.getText().equals("")){
							JOptionPane.showMessageDialog(null, "Veuillez entrer un login", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);
						} else {
							panel.removeAll();
							PersonneMapper pm = PersonneMapper.getInstance();


							Personne pc = pm.findByLogin(log.getText());
							if ( pc != null ) {
								JButton valider = new JButton("Valider");


								JPanel center = new JPanel();
								BoxLayout boxLayout = new BoxLayout(center, BoxLayout.Y_AXIS); // top to bottom
								center.setLayout(boxLayout);

								JLabel login = new JLabel("Login");
								JTextField tlogin = new JTextField(pc.getLogin());
								login.setPreferredSize(new Dimension(150, 50));

								JLabel mdp = new JLabel("Mot de passe");
								JTextField tmdp = new JTextField(pc.getMdp());
								mdp.setPreferredSize(new Dimension(150, 50));

								JLabel nom = new JLabel("Nom");
								JTextField tnom = new JTextField(pc.getNom());
								nom.setPreferredSize(new Dimension(150, 50));

								JLabel prenom = new JLabel("Prenom");
								JTextField tprenom = new JTextField(pc.getPrenom());
								prenom.setPreferredSize(new Dimension(150, 50));

								center.add(login);
								center.add(tlogin);
								center.add(mdp);
								center.add(tmdp);
								center.add(nom);
								center.add(tnom);
								center.add(prenom);
								center.add(tprenom);

								valider.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										jf.setVisible(false);
										
										PersonneMapper.getInstance().delete(pc);
										JOptionPane.showMessageDialog(null, "Modifications enregistrées", "Information",  JOptionPane.INFORMATION_MESSAGE);

										jf.setVisible(false);
									}
								});
								center.add(valider);
								panel.add(center, BorderLayout.CENTER);
								panel.updateUI();
							}else {
								JOptionPane.showMessageDialog(null, "Ce login n'existe pas", "Message d'erreur",  JOptionPane.ERROR_MESSAGE);
								jf.setVisible(false);
							}
						}
					}
				});

				panel.add(login);
				panel.add(log);
				panel.add(ok);
				panel.updateUI();
			}
		});

		creerCompte.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.removeAll();
				PersonneMapper pm = PersonneMapper.getInstance();

				JButton valider = new JButton("Valider");


				JPanel center = new JPanel();
				BoxLayout boxLayout = new BoxLayout(center, BoxLayout.Y_AXIS); // top to bottom
				center.setLayout(boxLayout);

				JLabel login = new JLabel("Login");
				JTextField tlogin = new JTextField();
				login.setPreferredSize(new Dimension(150, 50));

				JLabel mdp = new JLabel("Mot de passe");
				JTextField tmdp = new JTextField();
				mdp.setPreferredSize(new Dimension(150, 50));

				JLabel nom = new JLabel("Nom");
				JTextField tnom = new JTextField();
				nom.setPreferredSize(new Dimension(150, 50));

				JLabel prenom = new JLabel("Prenom");
				JTextField tprenom = new JTextField();
				prenom.setPreferredSize(new Dimension(150, 50));

				center.add(login);
				center.add(tlogin);
				center.add(mdp);
				center.add(tmdp);
				center.add(nom);
				center.add(tnom);
				center.add(prenom);
				center.add(tprenom);

				valider.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						jf.setVisible(false);
						Personne pc;
						if (!tlogin.getText().equals("") &&!tmdp.getText().equals("")&&!tnom.getText().equals("")&&!tprenom.getText().equals("")){
							pc = new Utilisateur(3000, tlogin.getText(), tmdp.getText(), tnom.getText(), tprenom.getText());
							PersonneMapper.getInstance().insert(pc);
							JOptionPane.showMessageDialog(null, "Modifications enregistrées", "Information",  JOptionPane.INFORMATION_MESSAGE);

							jf.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(null, "Un des champs est vide", "Erreur",  JOptionPane.ERROR_MESSAGE);

						}

					}
				});
				center.add(valider);
				panel.add(center, BorderLayout.CENTER);
				panel.updateUI();
			}

		});

		panel.add(creerCompte, BorderLayout.NORTH);
		panel.add(supprCompte, BorderLayout.CENTER);
		panel.add(modifCompte, BorderLayout.SOUTH);

		jf.getContentPane().add(panel);
		jf.setSize(350, 350);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);



	}

}
