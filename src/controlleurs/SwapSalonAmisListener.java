package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

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
			try {
				west.getJListSalons();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		west.updateUI();
	}

}
