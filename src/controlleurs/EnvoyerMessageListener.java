package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;

import domaine.Personne;
import message.Message;
import message.MessageAvecAccuseReception;
import message.MessageAvecExpiration;
import message.MessageChiffre;
import message.MessagePrioritaire;
import message.MessagePrive;
import persistance.MessageMapper;
import vue.East;

public class EnvoyerMessageListener implements ActionListener{


	JTextField j ;
	List<String> listeChoix;
	East east;
	Personne expediteur;

	public EnvoyerMessageListener(JTextField j, List<String> listeChoix2, East east, Personne p) {
		super();
		this.j = j;
		this.listeChoix = listeChoix2;
		this.east = east;
		expediteur =p;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		MessageMapper mp = MessageMapper.getInstance();
		Message toSend = new MessagePrive(j.getText(), expediteur, east.getDestinataire(),new Date());
		
		
		for (String s : listeChoix){
			if (s.equals("Prioritaire")){
				toSend = new MessagePrioritaire(toSend);
			} else if (s.equals("Chiffre")){
				toSend = new MessageChiffre(toSend);
			}else if (s.equals("Expiration")) {
				toSend = new MessageAvecExpiration(toSend);
			} else if (s.equals("ACK")) {
				toSend = new MessageAvecAccuseReception(toSend);
			} else {
				
			}
		}
		
		mp.insert(toSend);
		/*System.out.println(east.getDestinataire());
		System.out.println(expediteur);
		System.out.println(j.getText());
		System.out.println(listeChoix);
		*/
		
	}

}
