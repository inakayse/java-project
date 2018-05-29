package pong;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class renderer extends JPanel{

	
	private static final long serialVersionUID = 1L;
	
	protected void paintComponent (Graphics g) {
		
		super.paintComponent (g);
		
		pong.Pong.render((Graphics2D)g);
		   
	}

}
