import java.awt.Color;

public class Tetromino {
	
	private int [][] bloco;
	private Color cor;
	
	private int x;
	private int y;
	
	private int rotacaoAtual;
	private int [][][] blocos;
	
	// Construtor
	public Tetromino(int bloco[][], Color cor) {
		this.bloco = bloco;
		this.cor = cor;
		
		rotacionar();
	}
	
	/**
	 * Fabrica o bloco I
	 * @return Retorna o bloco I
	 */
    public static Tetromino blocoI() {
        return new Tetromino( new int[][]
        	{
                {1},
                {1},
                {1},
                {1}
            }, Color.cyan);
    }
    
    /**
	 * Fabrica o bloco O
	 * @return Retorna o bloco O
	 */
    public static Tetromino blocoO() {
        return new Tetromino( new int[][]
        	{
				{1, 1},
				{1, 1}
        	}, Color.yellow);
    }
    
    /**
	 * Fabrica o bloco T
	 * @return Retorna o bloco T
	 */
    public static Tetromino blocoT() {
        return new Tetromino( new int[][]
        	{
	        	{1, 1, 1},
				{0, 1, 0}
        	}, Color.magenta);
    }
    
    /**
	 * Fabrica o bloco L
	 * @return Retorna o bloco L
	 */
    public static Tetromino blocoL() {
        return new Tetromino( new int[][]
        	{
	        	{1, 0}, 
				{1, 0}, 
				{1, 1} 
        	}, Color.pink);
    }
    
    /**
	 * Fabrica o bloco J
	 * @return Retorna o bloco J
	 */
    public static Tetromino blocoJ() {
        return new Tetromino( new int[][]
        	{
	        	{0, 1}, 
				{0, 1}, 
				{1, 1} 
        	}, Color.blue);
    }
    
    /**
	 * Fabrica o bloco S
	 * @return Retorna o bloco S
	 */
    public static Tetromino blocoS() {
        return new Tetromino( new int[][]
        	{
	        	{0, 1, 1}, 
				{1, 1, 0}
        	}, Color.green);
    }
    
    /**
	 * Fabrica o bloco Z
	 * @return Retorna o bloco Z
	 */
    public static Tetromino blocoZ() {
        return new Tetromino( new int[][]
        	{
	        	{1, 1, 0}, 
				{0, 1, 1}
        	}, Color.red);
    }
	
	/**
	 * Faz a rotação do bloco, retornando um novo bloco quando rotacionado
	 */
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
	
	/**
	 * Gera qual a posição da rotação do bloco e verifica se o bloco irá ultrapassar a grade
	 * @param Tamanho das colunas da grade
	 */
	public void rotacionarBloco(int gradeColuna) {
		rotacaoAtual++;
		if(rotacaoAtual > 3) rotacaoAtual = 0;
		bloco = blocos[rotacaoAtual];
		
		// Move o bloco caso o bloco ultrapasse a grade direita
	    while(getBordaDireita() > gradeColuna) {
	        x--;
	    }
	}
	
	/**
	 * Gera o bloco no meio da grade.
	 * @param gradeColuna Tamanho da largura da grade
	 */
	public void spawn(int gradeColuna) {
		rotacaoAtual = 3;
		bloco = blocos[rotacaoAtual];
		
		y = 0 - getHeight();
		x = (gradeColuna - getWidth()) / 2;
	}

	/**
	 * Retorna o bloco atual
	 * @return Bloco atual
	 */
	public int[][] getBloco() { return bloco; }

	/**
	 * Retorna a cor do bloco atual
	 * @return Cor do bloco atual
	 */
	public Color getCor() { return cor; }

	/**
	 * Retorna a altura do bloco atual
	 * @return Altura do bloco atual
	 */
	public int getHeight() { return bloco.length; }

	/**
	 * Retorna a largura do bloco atual
	 * @return Largura do bloco atual
	 */
	public int getWidth() { return bloco[0].length; }
	
	/**
	 * Retorna a posição X do bloco atual
	 * @return Posição X do bloco atual
	 */
	public int getX() { return x; }
	
	/**
	 * Retorna a posição Y do bloco atual
	 * @return Posição Y do bloco atual
	 */
	public int getY() { return y; }
	
	/**
	 * Desce o bloco na grade
	 */
	public void descerBloco() { y++; }
	
	/**
	 * Move o bloco para a esquerda na grade
	 */
	public void moveEsquerda() { x--; }
	
	/**
	 * Move o bloco para a esquerda na grade
	 */
	public void moveDireita() { x++ ;}
	
	/**
	 * Retorna a borda esquerda da grade
	 * @return Returna o X da grade
	 */
	public int getBordaEsquerda() { return x; }
	
	/**
	 * Retorna a borda direita da grade
	 * @return Returna o X + largura da grade
	 */
	public int getBordaDireita() { return x + getWidth(); }
	
}
