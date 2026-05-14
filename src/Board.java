import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JPanel implements KeyListener {
	
	private Timer looper; 
	private Tetromino bloco = new Tetromino();
	private int gradeColuna = 10;
	private int gradeLinha = 20;
	private int gradeArea = 30;
	
	int moveLinha = 0, moveColuna = 0;
	
	public Board(){
		setFocusable(true);
        addKeyListener(this);
		
		looper = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	moveLinha++;
                repaint();
            }
        });
        looper.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//Adciona a cor no fundo da area do jogo
		super.paintComponent(g) ;
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Cria os formatos do tetris
        g.setColor(Color.white);
        for(int linha = 0; linha <= gradeLinha; linha++){
        	g.drawLine(0, linha * gradeArea, gradeArea * gradeColuna, linha * gradeArea);
        }	
		for(int coluna = 0; coluna <= gradeColuna; coluna++){
			g.drawLine(coluna * gradeArea, 0, coluna * gradeArea, gradeArea * gradeLinha);
		}
		
		bloco.geraBloco(g, gradeArea, moveLinha, moveColuna);
		
	}
	
	@Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveColuna++;
        } 
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveColuna--;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
	
	
}
