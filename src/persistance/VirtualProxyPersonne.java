package persistance;

import java.sql.SQLException;

import domaine.Personne;
import domaine.Salon;

/**
 * ProxyPersonne permet de charger une personne uniquement
 * lorsqu'on lui demande
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
public class VirtualProxyPersonne extends Personne{
	private int id_personne;
	private Personne personne = null;

	/**
	 * Contructeur du virtualProxyListPersonne qui 
	 * récupère l'id de la personne qu'on récupéra en BDD plus tard
	 * @param id_personne
	 */
	public VirtualProxyPersonne(int id_personne) {
		this.id_personne = id_personne;
	}
	
	/**
	 * On vérifie si on a déja initialisé la personne
	 * sinon on l'inisialise
	 * @throws SQLException
	 */
	public void verifieInitilisation() throws SQLException {
		// on vérifie id != 0 car c'est ce qui est retourné par la BDD si id est null
		if (personne == null && id_personne != 0) {
			personne = new VirtualProxyPersonne(id_personne);
			initialisation();
		}
	}
	/**
	 * C'est ici qu'on récupère la personne en BDD
	 * @throws SQLException
	 */
	public void initialisation() throws SQLException {
		personne = PersonneMapper.getInstance().findById(id_personne);
	}
	
	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on le retourne 
	 * @return une personne 
	 * @throws SQLException
	 */
	public Personne getPersonne() throws SQLException {
		verifieInitilisation();
		return personne;
	}

	@Override
	public boolean isAdmin() {
		if(id_personne == 1) return true;
		return false;
	}

	@Override
	public boolean isModo(Salon s) throws SQLException {
		verifieInitilisation();
		if(s.getModo() == this) return true;
		return false;
	}
}
