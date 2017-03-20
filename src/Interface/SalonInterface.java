package Interface;

import java.rmi.Remote;
import java.util.List;

import domaine.Personne;

public interface SalonInterface extends Remote{

	public void send(String s, Personne exped, Personne dest, String date, boolean prio, boolean chiff, boolean exp,
			boolean ack);
	public String getNom();
	public int getId();
	public List<PersonneInterface> getPersonnes();

}
