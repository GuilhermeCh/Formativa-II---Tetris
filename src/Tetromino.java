import java.awt.Color;

public class Tetromino {
	
	private int [][] bloco;
	private Color cor;
	
	private int x;
	private int y;
	
	private int rotacaoAtual;
	private int [][][] blocos;
	
	// Construtor
	public Tetromino(int numero) {
		fabricaBloco(numero);
		rotacionar();
	}
	
	private void rotacionar() {
		blocos = new int[4][][];
		
		
		for(int i = 0; i < 4; i++) {
			int linhas = bloco.length;
		    int colunas = bloco[0].length;
		    blocos[i] = new int[colunas][linhas];
		    
		    for(int y = 0; y < colunas; y++) {
		        for (int x = 0; x < linhas; x++) {
		        	blocos[i][y][x] = bloco[linhas - 1 - x][y];
		        }
		    }
			bloco = blocos[i];
		}
	}
	
	public void rotacionarBloco() {
		rotacaoAtual++;
		if(rotacaoAtual > 3) rotacaoAtual = 0;
		bloco = blocos[rotacaoAtual];
	}
	
	/**
	 * Coloca o bloco no meio da grade.
	 * @param gradeColuna Recebe o tamanho da largura da grade
	 */
	public void spawn(int gradeColuna) {
		rotacaoAtual = 3;
		bloco = blocos[rotacaoAtual];
		
		// Colocamos no negativo para começar em cima da grade
		y = 0 - getHeight();
		// Calcula a posição X da grade. Para calcular o meio da grid colocamos a largura da grid menos o a largura do bloco e dividimos por 2
		x = (gradeColuna - getWidth()) / 2;
	}
	
	
	public void fabricaBloco(int numero) {
		
		int[][][] blocoSelecionado = {

				{ // Bloco I
					{1},
					{1},
					{1},
					{1}
				},
				
				{ // Bloco O
					{1, 1},
					{1, 1}
				},
				
				{ // Bloco T
					{1, 1, 1},
					{0, 1, 0}
				},
				
				{ // Bloco L
					{1, 0}, 
					{1, 0}, 
					{1, 1} 
				},
				
				{ // Bloco J
					{0, 1}, 
					{0, 1}, 
					{1, 1} 
				},
				
				{ // Bloco S
					{0, 1, 1}, 
					{1, 1, 0}, 
				},
				
				{ // Bloco Z
					{1, 1, 0}, 
					{0, 1, 1}, 
				}
		};
		
		Color[] cores = {
			Color.cyan,     // Cor Bloco I
			Color.yellow,   // Cor Bloco O
			Color.magenta,  // Cor Bloco T
			Color.orange,   // Cor Bloco L
			Color.blue,     // Cor Bloco J
			Color.green,    // Cor Bloco S
			Color.red       // Cor Bloco Z
		};
		
		 this.bloco = blocoSelecionado[numero];
		 this.cor = cores[numero];
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
	
	public int getBordaEsquerda() { return x; }
	
	public int getBordaDireita() { return x + getWidth(); }
	
}
