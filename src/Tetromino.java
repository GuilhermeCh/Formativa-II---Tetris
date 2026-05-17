import java.awt.Color;

public class Tetromino {
	
	private int [][] bloco;
	private Color cor;
	private int x, y;
	private int rotacaoAtual;
	private int [][][] blocos;
	
	// Construtor
	public Tetromino(int numero) {
		fabricaBloco(numero);
		rotacionar();
	}
	
	private void rotacionar() {
		blocos = new int[2][][];
		
		blocos[0] = bloco;
		
		int linhas = bloco.length;
	    int colunas = bloco[0].length;
	    int[][] blocoRotacionado = new int[colunas][linhas];

	    for (int y = 0; y < linhas; y++) {
	        for (int x = 0; x < colunas; x++) {
	        	blocoRotacionado[x][linhas - 1 - y] = bloco[y][x];
	        }
	    }
	    blocos[1] = blocoRotacionado;
	}
	
	public void rotacionarBloco(int gradeColuna) {
		rotacaoAtual++;
		if(rotacaoAtual > 1) rotacaoAtual = 0;
		bloco = blocos[rotacaoAtual];
		
		// Move o bloco caso o bloco ultrapasse a grade direita
	    while(getBordaDireita() > gradeColuna) {
	        x--;
	    }
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
