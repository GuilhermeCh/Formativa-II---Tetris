import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Board extends JPanel {
	
	private int gradeColuna = 10;
	private int gradeLinha = 20;
	private int gradeArea = 30;
	
	private Timer looper; 
	
	private Tetromino bloco;
	
	public Board() {
		criaBloco();
		
		looper = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(verificaFundo() == true) return;
            	
        		bloco.descerBloco();
        		repaint();
            }
        });
        looper.start();
	}
	
	public void criaBloco() {
		bloco = new Tetromino(new int[][] { {1, 0}, {1, 0}, {1, 1} }, Color.red);
		bloco.spawn(gradeColuna);
	}
	
	/**
	 * Gera blocos para o painel
	 * @param g Valor que gera o bloco
	 */
	private void geraBloco(Graphics g) {
		for(int linha = 0; linha < bloco.getHeight(); linha++) {
			for(int coluna = 0; coluna < bloco.getWidth(); coluna++){
				if(bloco.getBloco()[linha][coluna] == 1) {
					
					int x = (bloco.getX() + coluna) * gradeArea;
					int y = (bloco.getY() + linha) * gradeArea;
					
					// Desenha o bloco
					g.setColor(bloco.getCor());
					g.fillRect(x, y, gradeArea, gradeArea);
					// Desenha as linhas do bloco
					g.setColor(Color.white);	
					g.drawRect(x, y, gradeArea, gradeArea);
				}
			}
		}
	}
	
	/**
	 * Verifica caso o bloco chegou no fundo da grade
	 * @return True
	 */
	private boolean verificaFundo() {
		// Calcula a altura do bloco mais a altura da grade restante e verifica se é do mesmo tamanho da grade total
		if(bloco.getY() + bloco.getHeight() == gradeLinha ) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g) ;
		// Adciona a cor no fundo da area do jogo
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
		
		geraBloco(g);
	}
}
