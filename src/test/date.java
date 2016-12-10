package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import message.Message;
import persistance.MessageMapper;

public class date {

	public static void main(String[] args) throws ParseException {
		/*
		MessageMapper mm = MessageMapper.getInstance();
		Message m = mm.findByDestinataire(1);
		System.out.println(m.getDateEnvoi());
		*/
		SimpleDateFormat dateHeureFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date d = new java.util.Date();
		String date = dateHeureFormat.format(d);
		System.out.println(date);
		Date date2 = dateHeureFormat.parse(date);
		System.out.println(date2);
		
	}

}
