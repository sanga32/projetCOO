package test;

import java.util.List;

import message.Message;
import persistance.MessageMapper;

public class chiffrage {

	public static void main(String[] args) {
		MessageMapper mm = new MessageMapper().getInstance();
		List<Message> m = mm.findListMessagePrive(1, 2);
		String chiffrage = m.get(m.size()-1).getContenu();
		String dechiffrage = m.get(m.size()-1).dechiffrage();
		System.out.println("Chiffrage : " + chiffrage );
		System.out.println("Dechiffrage : " + dechiffrage );
	}

}
