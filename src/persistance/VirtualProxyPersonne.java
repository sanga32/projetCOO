package persistance;

import java.sql.SQLException;

import domaine.Personne;
import domaine.Salon;

/**
 * 
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
public class VirtualProxyPersonne extends Personne{
	private int id_personne;
	private Personne personne = null;


	public VirtualProxyPersonne(int id_personne) {
		this.id_personne = id_personne;
	}
	

	public void verifieInitilisation() throws SQLException {
		// on vérifie id != 0 car c'est ce qui est retourné par la BDD si id est null
		if (personne == null && id_personne != 0) {
			personne = new VirtualProxyPersonne(id_personne);
			initialisation();
		}
	}

	public void initialisation() throws SQLException {
		personne = PersonneMapper.getInstance().findById(id_personne);
	}
	

	public Personne getPersonne() throws SQLException {
		verifieInitilisation();
		return personne;
	}
	
	public int getId() {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personne.getId();
	}
	
	public String getLogin() {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personne.getLogin();
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
	
	public String toString(){
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personne.getNom();
	}
}
