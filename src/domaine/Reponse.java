package domaine;

import java.rmi.RemoteException;

import Interface.NotifInterface;
import Interface.PersonneInterface;
import persistance.NotificationMapper;

/** 
 * Classe que hérite de Notification et qui représente la réponse à une demande d'ami
 * @author Kevin delporte, alexandre godon, teddy lequette 
 *
 */

public class Reponse extends Notification implements NotifInterface{

	boolean reponse;
	public Reponse(int id, boolean rep, Personne expediteur, Personne destinataire) {
		super(id, expediteur, destinataire);
		this.reponse = rep;
		if(rep){
			this.message = expediteur.getLogin() + " a accepté votre demande !"; 
		}else{
			this.message = expediteur.getLogin() + " a refusé votre demande !";
		}
	}
	
	public Reponse(boolean rep, PersonneInterface expediteur, PersonneInterface destinataire) throws RemoteException {
		super(expediteur, destinataire);
		this.reponse = rep;
		if(rep){
			this.message = expediteur.getLogin() + " a accepté votre demande !"; 
		}else{
			this.message = expediteur.getLogin() + " a refusé votre demande !";
		}
	}
	
	public boolean isReponse() {
		return reponse;
	}
	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		NotificationMapper nm = NotificationMapper.getInstance();
		nm.delete(this);
	}
	
	
}
