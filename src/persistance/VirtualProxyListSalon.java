package persistance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import domaine.Salon;

public class VirtualProxyListSalon implements List<Salon>{
	private int id_personne;
	private List<Salon> salons = null;


	public VirtualProxyListSalon(int id_personne) {
		this.id_personne = id_personne;
	}


	public void verifieInitilisation() throws SQLException {
		if (salons == null) {
			salons = new ArrayList<Salon>();
			initialisation();
		}

	}


	public void initialisation() throws SQLException {
		//salons = SalonMapper.getInstance().getSalons(id_personne);
	}


	public List<Salon> getsalons() {
		try {
			verifieInitilisation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return salons;
	}


	@Override
	public boolean add(Salon e) {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		this.salons.add(e);
		return true;
	}

	@Override
	public void add(int index, Salon element) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addAll(Collection<? extends Salon> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Salon> c) {
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
		salons.clear();
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
	public Salon get(int index) {
		try {
			verifieInitilisation();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return salons.get(index);
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
		if (salons.isEmpty())
			return true;
		return false;
	}

	@Override
	public Iterator<Salon> iterator() {
		// TODO Auto-generated method stub
		return salons.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<Salon> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<Salon> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Salon remove(int index) {
		return salons.remove(index);
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
	public Salon set(int index, Salon element) {
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
		return salons.size();
	}

	@Override
	public List<Salon> subList(int fromIndex, int toIndex) {
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
