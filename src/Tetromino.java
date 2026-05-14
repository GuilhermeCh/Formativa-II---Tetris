import java.awt.Color;

public class Tetromino {
	
	private int [][] bloco;
	private Color cor;
	private int x, y;
	
	// Construtor
	public Tetromino(int[][] bloco, Color cor) {
		this.bloco = bloco;
		this.cor = cor;
	}
	
	/**
	 * Coloca o bloco no meio da grade.
	 * @param gradeColuna Recebe o tamanho da largura da grade
	 */
	public void spawn(int gradeColuna) {
		// Colocamos no negativo para começar emcima da grade
		y = 0 - getHeight();
		// Calcula a posição X da grade. Para calcular o meio da grid colocamos a largura da grid menos o a largura do bloco e dividimos por 2
		x = (gradeColuna - getWidth()) / 2;
	}
	
	public int[][] getBloco(){ return bloco; }

	public Color getCor(){ return cor; }

	public int getHeight(){ return bloco.length; }

	public int getWidth(){ return bloco[0].length; }
	
	public int getX(){ return x; }
	
	public int getY(){ return y; }
	
	public void descerBloco(){ y++; }
	
	public void moveEsquerda(){ x--; }
	
	public void moveDireita(){ x++ ;}
}
