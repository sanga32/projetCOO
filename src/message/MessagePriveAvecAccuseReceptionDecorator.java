package message;

import java.sql.Date;

import domaine.Personne;

public class MessagePriveAvecAccuseReceptionDecorator extends MessageAvecAccuseReception{

	public MessagePriveAvecAccuseReceptionDecorator(Message m) {
		// TODO Auto-generated constructor stub
		this.message = m;
	}

}
