package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InterfaceAddress;

import javax.swing.JPanel;

import vue.InterfaceChat;
import vue.West;

public class SwapSalonAmisListener implements ActionListener{

	InterfaceChat interfaceChat;
	
	
	
	public SwapSalonAmisListener(InterfaceChat interfaceChat) {
		super();
		this.interfaceChat = interfaceChat;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Salons")){
			interfaceChat.getWest().getJListAmis();
			System.out.println("test");
		} else {
			interfaceChat.getWest().getJListSalons();
		}
		interfaceChat.getWest().updateUI();
	}

}
