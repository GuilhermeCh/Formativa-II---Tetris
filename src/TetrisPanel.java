import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class TetrisPanel extends JFrame implements KeyListener{

	private Board area;
	
    public TetrisPanel() {
    	setTitle("Tetris");
        setSize(450, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        area = new Board();
        add(area);
        
        addKeyListener(this);
        setFocusable(true);
        
        setVisible(true);
    }

    public static void main(String args[]) {
    	new TetrisPanel();
    }

    @Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			area.moveBlocoDireita();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			area.moveBlocoEsquerda();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			area.retornaVelocidadeRapida();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			area.rotacionar();
		} 
	}
    
    @Override
	public void keyReleased(KeyEvent e) {
		area.retornaVelocidadeNormal();
	}
    
	@Override
	public void keyTyped(KeyEvent e) {}

}