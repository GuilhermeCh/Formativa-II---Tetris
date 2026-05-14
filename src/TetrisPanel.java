import javax.swing.JFrame;

public class TetrisPanel extends JFrame {

	private Board area;
	
    public TetrisPanel() {
    	setTitle("Tetris");
        setSize(450, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        area = new Board();
        add(area);
        setVisible(true);
    }

    public static void main(String args[]) {
    	new TetrisPanel();
    }
}