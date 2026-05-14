import java.awt.Color;
import java.awt.Graphics;

public class Tetromino {
	private int[][] bloco = { {1, 0}, {1, 0}, {1, 1} };

	public void geraBloco(Graphics g, int gradeArea, int moveLinha, int moveColuna) {
		for(int linha = 0; linha < bloco.length; linha++) {
			for(int coluna = 0; coluna < bloco[0].length; coluna++){
				if(bloco[linha][coluna] == 1) {
					// Desenha o desenho
					g.setColor(Color.red);
					g.fillRect(coluna * gradeArea + moveColuna * gradeArea, linha * gradeArea + moveLinha * gradeArea, gradeArea, gradeArea);
					// Desenha as linhas do bloco
					g.setColor(Color.white);
					g.drawRect(coluna * gradeArea + moveColuna * gradeArea, linha * gradeArea + moveLinha * gradeArea, gradeArea, gradeArea);
				}
			}
		}
	}

}
