package controlleurs;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vue.East;
import vue.West;

public class JListAmisController implements ListSelectionListener {

	East east;
	West west;

	public JListAmisController(West w, East e) {
		this.west = w;
		this.east = e;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList lsm = (JList) e.getSource();
		int Index = lsm.getSelectionModel().getMinSelectionIndex();

		System.out.println("\nChangement de la selection de liste! ");
		if ("Salons".equals(west.getSwap().getText())) {
			System.out.println("yo");
			System.out.println("Valeur de l'element: " + lsm.getModel().getElementAt(Index).toString());
			String salon = lsm.getModel().getElementAt(Index).toString();
			east.getJListPersonneSalons(salon);
		} else if("Amis".equals(west.getSwap().getText())){
			String personne = lsm.getModel().getElementAt(Index).toString();
			east.getJListPersonneSalons(personne);
		}

	}

}
