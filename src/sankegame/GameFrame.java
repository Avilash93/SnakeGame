package sankegame;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	 GameFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setResizable(false);
		this.add(new GamePanel());
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);//locate near center
	}
}
