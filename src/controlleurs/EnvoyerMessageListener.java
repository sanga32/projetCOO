package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTextField;

import domaine.Personne;
import domaine.Salon;
import message.Message;
import message.MessageAvecAccuseReception;
import message.MessageAvecExpiration;
import message.MessageChiffre;
import message.MessagePrioritaire;
import message.MessagePrive;
import persistance.MessageMapper;
import vue.Center;
import vue.East;
import vue.West;

public class EnvoyerMessageListener implements ActionListener{


	JTextField j ;
	List<String> listeChoix;
	East east;
	Personne expediteur;
	Center center;
	West west;

	public EnvoyerMessageListener(JTextField j, List<String> listeChoix2, East east, West west, Personne p, Center center) {
		super();
		this.j = j;
		this.listeChoix = listeChoix2;
		this.east = east;
		expediteur =p;
		this.center = center;
		this.west = west;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		//string containing date
		SimpleDateFormat dateHeureFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		java.util.Date date = new java.util.Date();
		String strDate = dateHeureFormat.format(date);
		System.out.println("DAAAAAATE" + date);

		/*
		 * To convert String to java.sql.Date, use
		 * Date (long date) constructor.
		 *
		 * It creates java.sql.Date object from the milliseconds provided.
		 */

		//first convert string to java.util.Date object using SimpleDateFormat


		/* java.sql.Date sqlDate = new Date(date.getTime());

        System.out.println("String converted to java.sql.Date :" + sqlDate);
		 */
		System.out.println(strDate);
		if (west.getSwap().getText().equals("Amis")){
			envoiMessagePrive(new MessagePrive(j.getText(), expediteur, east.getDestinataire(),strDate));
		} else {
			envoiMessageSalon(new MessagePrive(j.getText(), expediteur, east.getDestinataire(),strDate), east.getSalon());
		}


		/*System.out.println(east.getDestinataire());
		System.out.println(expediteur);
		System.out.println(j.getText());
		System.out.println(listeChoix);
		 */

	}

	public void envoiMessagePrive(Message toSend){
		MessageMapper mp = MessageMapper.getInstance();

		for (String s : listeChoix){
			if (s.equals("Prioritaire")){
				toSend = new MessagePrioritaire(toSend);
				System.out.println(s);
			} else if (s.equals("Chiffre")){
				toSend = new MessageChiffre(toSend);
				System.out.println(s);

			}else if (s.equals("Expiration")) {
				toSend = new MessageAvecExpiration(toSend);
				System.out.println(s);

			} else if (s.equals("ACK")) {
				toSend = new MessageAvecAccuseReception(toSend);
				System.out.println(s);

			} else {

			}
		}
		toSend.isChiffre();
		toSend.isExpiration();
		toSend.isPrioritaire();
		toSend.isReception();
		System.out.println(toSend);
		center.addMessage(toSend);
		mp.insert(toSend);
		j.setText("");
		center.updateUI();
	}

	public void envoiMessageSalon(Message toSend, Salon salon){
		MessageMapper mp = MessageMapper.getInstance();

		for (String s : listeChoix){
			if (s.equals("Prioritaire")){
				toSend = new MessagePrioritaire(toSend);
				System.out.println(s);
			} else if (s.equals("Chiffre")){
				toSend = new MessageChiffre(toSend);
				System.out.println(s);

			}else if (s.equals("Expiration")) {
				toSend = new MessageAvecExpiration(toSend);
				System.out.println(s);

			} else if (s.equals("ACK")) {
				toSend = new MessageAvecAccuseReception(toSend);
				System.out.println(s);

			} else {

			}
		}
		toSend.isChiffre();
		toSend.isExpiration();
		toSend.isPrioritaire();
		toSend.isReception();
		System.out.println(toSend);
		center.addMessage(toSend);
		mp.insert(toSend, salon);
		j.setText("");
		center.updateUI();
	}

}
