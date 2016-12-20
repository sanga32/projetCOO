package persistance;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import domaine.Personne;

/**
 * ProxyListAmi permet de charger la liste d'ami d'une personne
 * lorsqu'on lui demande
 * @author Alexandre Godon, Kevin Delporte, Teddy Lequette
 *
 */
public class VirtualProxyListAmi implements List<Personne> {
	private int id_personne;
	private List<Personne> amis = null;


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
	 */
	public void verifieInitilisation() throws SQLException {
		if (amis == null) {
			amis = new ArrayList<Personne>();
			initialisation();
		}

	}

	/**
	 * C'est ici qu'on récupère la liste d'ami en BDD
	 * @throws SQLException
	 */
	public void initialisation() throws SQLException {
		amis = AmiMapper.getInstance().getAmis(id_personne);
	}

	/**
	 * On va chercher la liste d'ami en BDD si ça n'est pas déja fait et on la retourne 
	 * @return la liste d'ami
	 * @throws SQLException
	 */
	public List<Personne> getAmis() {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amis;
	}
	
	@Override
	public boolean add(Personne e) {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		this.amis.add(e);
		return true;
	}

	@Override
	public void add(int index, Personne element) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addAll(Collection<? extends Personne> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Personne> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
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

	@Override
	public Personne get(int index) {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return amis.get(index);
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (amis.isEmpty())
			return true;
		return false;
	}

	@Override
	public Iterator<Personne> iterator() {
		// TODO Auto-generated method stub
		return amis.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<Personne> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<Personne> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		amis.remove(o);
		return true;
	}

	@Override
	public Personne remove(int index) {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
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
	public Personne set(int index, Personne element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amis.size();
	}

	@Override
	public List<Personne> subList(int fromIndex, int toIndex) {
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
