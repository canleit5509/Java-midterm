import java.awt.Graphics;

import javax.swing.JFrame;

public class GameBreakOut extends JFrame {
	public static void main(String[] args) {
		new GameBreakOut();
	}
	// Start Variable
	int w = 700;
	int h = 500;
	// End Variable
	
	
	public GameBreakOut() {
		this.setTitle("Game Break Out");
		this.setSize(w,h);
		this.setDefaultCloseOperation(3);
		
		
		
		
		this.setVisible(true);
	}
	
	public void paint(Graphics g1) {
		
	}
}
