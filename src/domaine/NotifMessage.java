package domaine;

import java.rmi.RemoteException;

import Interface.PersonneInterface;

public class NotifMessage extends Notification {

	public NotifMessage(PersonneInterface exped, PersonneInterface dest, String salon) throws RemoteException {
		super(exped, dest);
		// TODO Auto-generated constructor stub
		this.message = exped.getLogin() + " vous a envoyé un message dans le salon "+ salon;
	}

	public NotifMessage(int id_notification, PersonneInterface expediteur, PersonneInterface destinataire) {
		// TODO Auto-generated constructor stub
		super(id_notification, expediteur, destinataire);

	}

}
