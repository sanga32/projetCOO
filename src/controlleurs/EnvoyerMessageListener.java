package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTextField;

import Interface.InfoInterface;
import Interface.PriveInterface;
import Interface.SalonInterface;
import domaine.Personne;
import message.Message;
import message.MessageAvecAccuseReception;
import message.MessageAvecExpiration;
import message.MessageChiffre;
import message.MessagePrioritaire;
import message.MessagePrive;
import persistance.MessageMapper;
import server.Salon;
import vue.Center;
import vue.East;
import vue.InterfaceChat;
import vue.West;

public class EnvoyerMessageListener implements ActionListener{


	JTextField j ;
	List<String> listeChoix;
	East east;
	Personne expediteur;
	Center center;
	West west;
	InterfaceChat interfaceChat;

	public EnvoyerMessageListener(JTextField j, List<String> listeChoix2, East east, West west, Personne p, Center center, InterfaceChat interfaceChat) {
		super();
		this.j = j;
		this.listeChoix = listeChoix2;
		this.east = east;
		expediteur =p;
		this.center = center;
		this.west = west;
		this.interfaceChat = interfaceChat;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		//string containing date
		SimpleDateFormat dateHeureFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		java.util.Date date = new java.util.Date();
		String strDate = dateHeureFormat.format(date);

		System.out.println(strDate);
		if (west.getSwap().getText().equals("Amis")){
			//envoiMessagePrive(new MessagePrive(j.getText(), expediteur, east.getDestinataire(),strDate));
			try {
				envoiMessagePrive(j.getText(), expediteur, east.getDestinataire(),strDate);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			//envoiMessageSalon(new MessagePrive(j.getText(), expediteur, east.getDestinataire(),strDate), east.getSalon());
			try {
				envoiMessageSalon(j.getText(), expediteur, east.getDestinataire(),strDate, east.getSalon());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void envoiMessagePrive(String s, Personne exped, Personne dest, String date) throws RemoteException, NotBoundException{
		//MessageMapper mp = MessageMapper.getInstance();

		boolean ack;
		boolean exp;
		boolean chiff;
		boolean prio = chiff = exp = ack = false;
		
		for (String st : listeChoix){
			if (st.equals("Prioritaire")){
				prio = true;
			} else if (st.equals("Chiffre")){
				chiff = true;

			}else if (st.equals("Expiration")) {
				exp = true;

			} else if (st.equals("ACK")) {
				ack = true;

			}
		}
		
		InfoInterface info = (InfoInterface) interfaceChat.registry.lookup("info");

		String nomSalonPrive;
		nomSalonPrive = info.salonAmi(exped, dest);
		PriveInterface pi = (PriveInterface) interfaceChat.registry.lookup(nomSalonPrive);
		pi.send(s, exped, dest, date, prio, chiff, exp, ack);
		//Ici on en aura peut être plus besoin
		//Ici il faut avoir lookup un salon, je sais pas encore comment on procède pour ça
		//s.send(s, exped, dest, date, prio, chiff, exp, ack);
		//mp.insert(toSend);
		j.setText("");
		center.updateUI();
	}

	public void envoiMessageSalon(String s, Personne exped, Personne dest, String date, SalonInterface salon) throws RemoteException, NotBoundException, SQLException{
		boolean ack;
		boolean exp;
		boolean chiff;
		boolean prio = chiff = exp = ack = false;
		
		for (String st : listeChoix){
			if (st.equals("Prioritaire")){
				prio = true;
			} else if (st.equals("Chiffre")){
				chiff = true;

			}else if (st.equals("Expiration")) {
				exp = true;

			} else if (st.equals("ACK")) {
				ack = true;

			}
		}
		salon = (SalonInterface) interfaceChat.registry.lookup(salon.getNom());
		salon.send(s, exped, dest, date, prio, chiff, exp, ack);
		j.setText("");
		center.updateUI();
	}

}
