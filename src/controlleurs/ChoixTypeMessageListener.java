package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;

import vue.South;

public class ChoixTypeMessageListener implements ActionListener {

	List<String> listeChoix;
	
	public ChoixTypeMessageListener(List<String> listeChoix2) {
		// TODO Auto-generated constructor stub
		this.listeChoix = listeChoix2;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("source : " + ((JCheckBox)e.getSource()).getText() + " - état : " + ((JCheckBox)e.getSource()).isSelected());
		if (((JCheckBox)e.getSource()).isSelected()){
			listeChoix.add(((JCheckBox)e.getSource()).getText());
		} else {
			for ( int i= 0 ; i<listeChoix.size(); i++){
				if (listeChoix.get(i).equals(((JCheckBox)e.getSource()).getText())) {
					listeChoix.remove(i);
				}
			}
		}
		
	}

}
