package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import vue.West;

public class SwapSalonAmisListener implements ActionListener{

	West west ;
	
	
	
	public SwapSalonAmisListener(West west) {
		super();
		this.west = west;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Salons")){
			west.getJListAmis();
		} else {
			west.getJListSalons();
		}
		west.updateUI();
	}

}
