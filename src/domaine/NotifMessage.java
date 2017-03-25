package domaine;

import java.rmi.RemoteException;

import Interface.PersonneInterface;
import persistance.NotificationMapper;

public class NotifMessage extends Notification {

	public NotifMessage(PersonneInterface exped, PersonneInterface dest, String salon) throws RemoteException {
		super(exped, dest);
		// TODO Auto-generated constructor stub
		this.message = exped.getLogin() + " vous a envoyé un message dans le salon "+ salon;
	}

	public NotifMessage(int id_notification, PersonneInterface exped, PersonneInterface destinataire) throws RemoteException {
		// TODO Auto-generated constructor stub
		super(id_notification, exped, destinataire);
		this.message = exped.getLogin() + " vous a envoyé un message ";

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		NotificationMapper nm = NotificationMapper.getInstance();
		nm.delete(this);
	}

}
