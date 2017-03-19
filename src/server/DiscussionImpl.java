package server;

import java.util.List;

import Interface.DiscussionInterface;
import Interface.MessageInterface;
import Interface.PersonneInterface;
import domaine.Salon;
import persistance.MessageMapper;

public class DiscussionImpl implements DiscussionInterface{

	List<PersonneInterface> connecte;
	Salon s;

	
	
	@Override
	public void send(MessageInterface m) {
		// TODO Auto-generated method stub
		MessageMapper mm =  MessageMapper.getInstance();
		mm.insert(m.getMessage(), s);
		for (int i = 0 ; i<connecte.size(); i++){
			connecte.get(i).receiveMessage(m);
		}
	}

}
