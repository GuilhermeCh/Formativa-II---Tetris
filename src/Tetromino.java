import java.awt.Color;

public class Tetromino {
	
	private int [][] bloco;
	private Color cor;
	private int x, y;
	private int rotacaoAtual;
	private int [][][] blocos;
	
	// Construtor
	public Tetromino(int[][] bloco, Color cor) {
		this.bloco = bloco;
		this.cor = cor;
		
		initShape();
	}
	
	private void initShape() {
		blocos = new int[2][][];
		
		blocos[0] = bloco;
		
		int linhas = bloco.length;
	    int colunas = bloco[0].length;
	    int[][] bloco90 = new int[colunas][linhas];

	    for (int l = 0; l < linhas; l++) {
	        for (int c = 0; c < colunas; c++) {
	        	bloco90[c][linhas - 1 - l] = bloco[l][c];
	        }
	    }
	    blocos[1] = bloco90;
	}
	
	/**
	 * Coloca o bloco no meio da grade.
	 * @param gradeColuna Recebe o tamanho da largura da grade
	 */
	public void spawn(int gradeColuna) {
		// Colocamos no negativo para começar em cima da grade
		y = 0 - getHeight();
		// Calcula a posição X da grade. Para calcular o meio da grid colocamos a largura da grid menos o a largura do bloco e dividimos por 2
		x = (gradeColuna - getWidth()) / 2;
	}
	
	public int[][] getBloco() { return bloco; }

	public Color getCor() { return cor; }

	public int getHeight() { return bloco.length; }

	public int getWidth() { return bloco[0].length; }
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public void descerBloco() { y++; }
	
	public void moveEsquerda() { x--; }
	
	public void moveDireita() { x++ ;}
	
	public void rotacionar() {
		rotacaoAtual++;
		if(rotacaoAtual > 1) rotacaoAtual = 0;
		bloco = blocos[rotacaoAtual];
	}
	
	public int getBordaEsquerda() { return x; }
	
	public int getBordaDireita() { return x + getWidth(); }
}
