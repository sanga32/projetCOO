package domaine;

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
