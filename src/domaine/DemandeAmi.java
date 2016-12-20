package domaine;

/** 
 * Classe qui hérite de notification et qui représente une demande d'ami qu'un utilisateur pourra accepter ou refuser
 * @author Kevin delporte, alexandre godon , teddy lequette
 *
 */

public class DemandeAmi extends Notification{

	public DemandeAmi(int id, Personne expediteur, Personne destinataire) {
		super(id, expediteur, destinataire);
		this.message = expediteur.getLogin() + " vous demande en ami !";
	}
	
	public DemandeAmi(Personne expediteur, Personne destinataire) {
		super(expediteur, destinataire);
		this.message = expediteur.getLogin() + " vous demande en ami !";
	}

}
