package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.JTextField;

public class EnvoyerMessageListener implements ActionListener{


	JTextField j ;
	List<String> listeChoix;

	public EnvoyerMessageListener(JTextField j, List<String> listeChoix2) {
		super();
		this.j = j;
		this.listeChoix = listeChoix2;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(j.getText());
		System.out.println(listeChoix);
	}

}
