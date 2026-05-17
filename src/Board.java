import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Board extends JPanel {
	
	private int gradeColuna = 10;
	private int gradeLinha = 20;
	private int gradeArea = 30;
	
	private Timer looper; 
	private int velocidadeRapida = 16;
	private int velocidadeNormal = 200;
	private int delayVelocidade = velocidadeNormal;
	
	private Tetromino bloco;
	private Color[][] fundoBlocos;
	
	private Random random = new Random();
		
	public Board() {
		criaBloco();
		fundoBlocos = new Color[gradeLinha][gradeColuna];
		
		looper = new Timer(delayVelocidade, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Verifica se o bloco chegou no frundo e gera um novo bloco
            	if(verificaFundo() == true) {
            		converteBlocoParaFundo();
            		removerLinhasCompletas();
            		criaBloco();
            	} else {
            		bloco.descerBloco();
            	}
        		repaint();
            }
        });
        looper.start();
	}
	
	public void criaBloco() {
		bloco = new Tetromino(random.nextInt(7));
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
					
					desenhaBlocoGrade(g, bloco.getCor(), x, y);
				}
			}
		}
	}
	
	/**
	 * Gera o fundo quando um bloco chega ao final da grade
	 * @param g Valor que gera o bloco
	 */
	private void geraFundoBlocos(Graphics g) {
		Color color;
		
		for(int l = 0; l < gradeLinha; l++) {
			for(int c = 0; c < gradeColuna; c++) {
				color = fundoBlocos[l][c];
				
				// color esta sem valor (null), fazendo isso conseguimos gerar os blocos do fundo
				if(color != null) {
					
					int x = c * gradeArea;
					int y = l * gradeArea;
					
					desenhaBlocoGrade(g, color, x, y);
				}
			}
		}
	}
	
	public void removerLinhasCompletas() {
		boolean linhaCompleta;
		for(int linha = gradeLinha-1; linha >= 0; linha--) {
			linhaCompleta = true;
			for(int coluna = 0; coluna < gradeColuna; coluna++) {
				if(fundoBlocos[linha][coluna] == null) {
					linhaCompleta = false;
					break;
				}
			}
			if(linhaCompleta == true) {
				for(int c = 0; c < gradeColuna; c++) {
					fundoBlocos[linha][c] = null;
				}
				descerLinha(linha);
				linha++;
				
				repaint();
			}
		}
	}
	
	public void descerLinha(int l) {
		for(int linha = l; linha > 0; linha--) {
			for(int coluna = 0; coluna < gradeColuna; coluna++) {
				fundoBlocos[linha][coluna] = fundoBlocos[linha - 1][coluna];
			}
		}
	}
	
	/**
	 * Cpnverte um bloco para fundo, assim é possivel gerar um novo bloco sem que apague o bloco que chega no fundo
	 */
	private void converteBlocoParaFundo() {
		int[][] forma = bloco.getBloco();
		int altura = bloco.getHeight();
		int largura = bloco.getWidth();
		
		int posicaoX = bloco.getX();
		int posicaoY = bloco.getY();
		
		Color cor = bloco.getCor();
		
		for(int l = 0; l < altura; l++) {
			for(int c = 0; c < largura; c++) {
				if(forma[l][c] == 1) {
					fundoBlocos[l + posicaoY][c + posicaoX] = cor;
				}
			}
		}
	}
	
	public void moveBlocoDireita() {
		if(verificaBordaDireita() == true) return;
		bloco.moveDireita();
		repaint();
	}
	
	public void moveBlocoEsquerda() {
		if(verificaBordaEsquerda() == true) return;
		bloco.moveEsquerda();
		repaint();
	}
	
	public void rotacionar() {
		bloco.rotacionarBloco(gradeColuna);
		repaint();
	}
	
	public void retornaVelocidadeNormal(){
		delayVelocidade = velocidadeNormal;
		looper.setDelay(delayVelocidade);
	}
	
	public void retornaVelocidadeRapida(){
		delayVelocidade = velocidadeRapida;
		looper.setDelay(delayVelocidade);
	}
	
	private void desenhaBlocoGrade(Graphics g, Color color, int x, int y) {
		// Desenha o bloco
		g.setColor(color);
		g.fillRect(x, y, gradeArea, gradeArea);
		// Desenha as linhas do bloco
		g.setColor(Color.white);	
		g.drawRect(x, y, gradeArea, gradeArea);
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
		
		int[][]forma = bloco.getBloco();
		int comprimento = bloco.getWidth();
		int altura = bloco.getHeight();
		
		for(int coluna = 0; coluna < comprimento; coluna++) {
			for(int linha = altura -1; linha >= 0; linha--) {
				if(forma[linha][coluna] != 0) {
					int x = coluna + bloco.getX();
					int y = linha + bloco.getY() + 1;
					if(y < 0) break;
					if(fundoBlocos[y][x] != null) return true;
					break;
				}
			}
		}
		return false;
	}
	
	private boolean verificaBordaEsquerda() {
		if(bloco.getBordaEsquerda() == 0) return true;

		int[][]forma = bloco.getBloco();
		int comprimento = bloco.getWidth();
		int altura = bloco.getHeight();
		
		for(int linha = 0; linha < altura; linha++) {
			for(int coluna = 0; coluna < comprimento; coluna++) {
				if(forma[linha][coluna] != 0) {
					int x = coluna + bloco.getX() - 1;
					int y = linha + bloco.getY();
					if(y < 0) break;
					if(fundoBlocos[y][x] != null) return true;
					break;
				}
			}
		}
		return false;
	}
	
	private boolean verificaBordaDireita() {
		if(bloco.getBordaDireita() == gradeColuna) return true;
			
		int[][]forma = bloco.getBloco();
		int comprimento = bloco.getWidth();
		int altura = bloco.getHeight();
		
		for(int linha = 0; linha < altura; linha++) {
			for(int coluna = comprimento - 1; coluna >= 0; coluna--) {
				if(forma[linha][coluna] != 0) {
					int x = coluna + bloco.getX() + 1;
					int y = linha + bloco.getY();
					if(y < 0) break;
					if(fundoBlocos[y][x] != null) return true;
					break;
				}
			}
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
		geraFundoBlocos(g);
	}
}
