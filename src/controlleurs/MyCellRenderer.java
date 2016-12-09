package controlleurs;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import message.Message;

public class MyCellRenderer extends JLabel implements ListCellRenderer {
	final static ImageIcon warningIcon = new ImageIcon("warning.gif");
	final static ImageIcon errorIcon = new ImageIcon("error.gif");
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		// TODO Auto-generated method stub    
		String s = value.toString();
		setText(s); // affiche la ligne
		if(s.indexOf("erreur") != -1)
			setIcon(errorIcon);
		else if(s.indexOf("warning") != -1)
			setIcon(warningIcon);

		Message m = (Message) value;

		System.out.println(m.isPrioritaire());
		/* if (isSelected) 
	         {
	             setBackground(Color.red);
		setForeground(list.getSelectionForeground());
	         }
	         else 
	         {
		setBackground(list.getBackground());
		setForeground(list.getForeground());
	         }
		 */
		if ( m.isPrioritaire()){
			setBackground(Color.magenta);
			setForeground(list.getSelectionForeground());
		}else 
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}
}