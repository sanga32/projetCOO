package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import message.Message;
import persistance.MessageMapper;

public class date {

	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat dateHeureFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date d = new java.util.Date();
		String date = dateHeureFormat.format(d);
		System.out.println(date);
		Date date2 = dateHeureFormat.parse(date);
		System.out.println(date2);
		date2.setMinutes(date2.getMinutes()+10);
		System.out.println(date2);
		
		if(d.after(date2)){
			System.out.println("1");
		}else{
			System.out.println("2");
		}
		
	}

}
