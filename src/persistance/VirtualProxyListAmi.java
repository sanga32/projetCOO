package persistance;

import java.rmi.RemoteException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import Interface.PersonneInterface;
import domaine.Personne;

/**
 * ProxyListAmi permet de charger la liste d'ami d'une personne
 * lorsqu'on lui demande
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
public class VirtualProxyListAmi implements List<PersonneInterface> {
	private int id_personne;
	private List<PersonneInterface> amis = null;


	/**
	 * Contructeur du VirtualProxyListAmi qui 
	 * récupère l'id de la personne à qui on récupèrera les amis en BDD plus tard
	 * @param id_personne
	 */
	public VirtualProxyListAmi(int id_personne) {
		this.id_personne = id_personne;
	}

	/**
	 * On vérifie si on a déja récupéré la liste d'ami en base
	 * Sinon on la récupère
	 * @throws SQLException
	 * @throws RemoteException 
	 */
	public void verifieInitilisation() throws SQLException, RemoteException {
		if (amis == null) {
			amis = new ArrayList<PersonneInterface>();
			initialisation();
		}

	}

	/**
	 * C'est ici qu'on récupère la liste d'ami en BDD
	 * @throws SQLException
	 * @throws RemoteException 
	 */
	public void initialisation() throws SQLException, RemoteException {
		amis = AmiMapper.getInstance().getAmis(id_personne);
	}

	/**
	 * On va chercher la liste d'ami en BDD si ça n'est pas déja fait et on la retourne 
	 * @return la liste d'ami
	 * @throws RemoteException 
	 * @throws SQLException
	 */
	public List<PersonneInterface> getAmis() throws RemoteException {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amis;
	}
	
	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on ajoute la personne passé en paramètre à la liste
	 * @param e 
	 * 		personne à ajouter aux amis
	 * @return true si l'ajout a bien été fait
	 * @throws SQLException
	 */
	@Override
	public boolean add(PersonneInterface e) {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.amis.add(e);
		return true;
	}

	@Override
	public void add(int index, PersonneInterface element) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addAll(Collection<? extends PersonneInterface> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends PersonneInterface> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on vide la liste
	 * @throws SQLException
	 */
	@Override
	public void clear() {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		amis.clear();
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on retourne l'ami qui se situe
	 * à l'index passé en paramètre
	 * @param index 
	 * 		index de la liste d'ami qu'on veut retourner
	 * @return Une personne
	 * @throws SQLException
	 */
	
	public PersonneInterface get(int index) {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return amis.get(index);
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * On va chercher la personne en BDD si ça n'est pas déja fait et on retourne True si la liste d'ami est vide
	 * @return True si la liste d'ami est vide
	 * @throws SQLException
	 */
	@Override
	public boolean isEmpty() {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (amis.isEmpty())
			return true;
		return false;
	}

	@Override
	public Iterator<PersonneInterface> iterator() {
		// TODO Auto-generated method stub
		return amis.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<PersonneInterface> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<PersonneInterface> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		amis.remove(o);
		return true;
	}

	@Override
	public PersonneInterface remove(int index) {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return amis.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Personne set(int index, PersonneInterface element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return amis.size();
	}

	@Override
	public List<PersonneInterface> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
}
