package persistance;

import java.rmi.RemoteException;
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
	 * Contructeur du virtualProxyPersonne qui 
	 * récupère l'id de la personne qu'on récupéra en BDD plus tard
	 * @param id_personne
	 */
	public VirtualProxyPersonne(int id_personne) throws RemoteException{
		this.id_personne = id_personne;
	}
	

	/**
	 * On vérifie si on a déja récupéré la personne en base
	 * Sinon on la récupère
	 * @throws SQLException
	 * @throws RemoteException 
	 */
	public void verifieInitilisation() throws SQLException, RemoteException {
		// on vérifie id != 0 car c'est ce qui est retourné par la BDD si id est null
		if (personne == null && id_personne != 0) {
			personne = new VirtualProxyPersonne(id_personne);
			initialisation();
		}
	}

	/**
	 * C'est ici qu'on récupère la personne en BDD
	 * @throws SQLException
	 * @throws RemoteException 
	 */
	public void initialisation() throws SQLException, RemoteException {
		personne = PersonneMapper.getInstance().findById(id_personne);
	}
	

	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on le retourne 
	 * @return une personne 
	 * @throws SQLException
	 * @throws RemoteException 
	 */
	public Personne getPersonne() throws SQLException, RemoteException {
		verifieInitilisation();
		return personne;
	}
	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on retourne son ID 
	 * @return id de la personne
	 * @throws SQLException
	 */
	public int getId() {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personne.getId();
	}
	
	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on retourne son login 
	 * @return login de la personne
	 * @throws SQLException
	 */
	public String getLogin() {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personne.getLogin();
	}

	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on retourne true si elle est admin
	 * @return True si la personne est admin
	 * @throws SQLException
	 */
	@Override
	public boolean isAdmin() throws SQLException {
		try {
			verifieInitilisation();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(id_personne == 1) return true;
		return false;
	}

	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on retourne true si elle est modo du salon passé en paramettre
	 * @param s
	 * 		Salon où l'on vérifie si la personne est le modo
	 * @return True si la personne est modo du salon
	 * @throws SQLException
	 */
	public boolean isModo(Salon s) throws SQLException {
		try {
			verifieInitilisation();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(s.getModo() == this) return true;
		return false;
	}
	
	public String toString(){
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personne+"";
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
