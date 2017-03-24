package domaine;

import Interface.NotifInterface;
import persistance.NotificationMapper;

/** 
 * Classe qui hérite de notification et qui représente une demande d'ami qu'un utilisateur pourra accepter ou refuser
 * @author Kevin delporte, alexandre godon , teddy lequette
 *
 */

public class DemandeAmi extends Notification implements NotifInterface{

	public DemandeAmi(int id, Personne expediteur, Personne destinataire) {
		super(id, expediteur, destinataire);
		this.message = expediteur.getLogin() + " vous demande en ami !";
	}
	
	public DemandeAmi(Personne expediteur, Personne destinataire) {
		super(expediteur, destinataire);
		this.message = expediteur.getLogin() + " vous demande en ami !";
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		NotificationMapper nm = NotificationMapper.getInstance();
		nm.delete(this);
	}

}
