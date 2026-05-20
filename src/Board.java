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
	private int velocidadeNormal = 250;
	private int delayVelocidade = velocidadeNormal;
	
	private Tetromino bloco;
	private Color[][] fundoBlocos;

	private boolean jogoTerminado = false;
	
	private Random random = new Random();
		
	public Board() {
		fundoBlocos = new Color[gradeLinha][gradeColuna];
		criaBloco();

		looper = new Timer(delayVelocidade, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
            	if (jogoTerminado) {
                    looper.stop();
                    return;
                } else if(colisao(bloco, bloco.getX(), bloco.getY() + 1) == true) {
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
	
	/**
	 * Seleciona aleatoriamente um bloco e gera ele na grade
	 */
	public void criaBloco() {
		int seletor = random.nextInt(7);

	    switch (seletor) {
	        case 0:
	            bloco = Tetromino.blocoI();
	            break;
	        case 1:
	            bloco = Tetromino.blocoO();
	            break;
	        case 2:
	            bloco = Tetromino.blocoT();
	            break;
	        case 3:
	            bloco = Tetromino.blocoL();
	            break;
	        case 4:
	            bloco = Tetromino.blocoJ();
	            break;
	        case 5:
	            bloco = Tetromino.blocoS();
	            break;
	        case 6:
	            bloco = Tetromino.blocoZ();
	            break;
	    }
	    bloco.spawn(gradeColuna);

	    if (verificaColisaoAoNascer()) {
	    	looper.stop();
	        jogoTerminado = true;
	    }	
	}
	
	/**
	 * Desenha o bloco e o seu contorno na grade
	 * @param g Bloco
	 * @param color Cor do bloco
	 * @param x Posição X do bloco
	 * @param y Posição Y do bloco
	 */
	private void desenhaBlocoGrade(Graphics grade, Color color, int x, int y) {
		// Desenha o bloco
		grade.setColor(color);
		grade.fillRect(x, y, gradeArea, gradeArea);
		// Desenha o contorno do bloco
		grade.setColor(Color.white);	
		grade.drawRect(x, y, gradeArea, gradeArea);
	}
	
	/**
	 * Cpnverte um bloco para fundo, assim é possivel gerar um novo bloco sem que apague o bloco que chega no fundo
	 */
	private void converteBlocoParaFundo() {
	    int[][] forma = bloco.getBloco();
	    Color cor = bloco.getCor();

	    for (int l = 0; l < bloco.getHeight(); l++) {
	        for (int c = 0; c < bloco.getWidth(); c++) {
	            if (forma[l][c] == 1) {
	                int y = l + bloco.getY();
	                int x = c + bloco.getX();

	                // Proteção contra índices inválidos
	                if (y >= 0 && y < gradeLinha && x >= 0 && x < gradeColuna) {
	                    fundoBlocos[y][x] = cor;
	                }
	            }
	        }
	    }
	}

	/**
	 * Gera o fundo quando um bloco chega ao final da grade
	 * @param g Bloco atual
	 */
	private void geraFundoBlocos(Graphics grade) {
		Color color;
		
		for(int l = 0; l < gradeLinha; l++) {
			for(int c = 0; c < gradeColuna; c++) {
				color = fundoBlocos[l][c];
				// color esta sem valor (null), fazendo isso conseguimos gerar os blocos do fundo
				if(color != null) {
					int x = c * gradeArea;
					int y = l * gradeArea;
					
					desenhaBlocoGrade(grade, color, x, y);
				}
			}
		}
	}
	
	/**
	 * Remove uma linha de bloco quando estiver completa
	 */
	public int removerLinhasCompletas() {
	    int linhasRemovidas = 0;
		boolean linhaCompleta;
		
		for(int linha = gradeLinha - 1; linha >= 0; linha--) {
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
				
	            linhasRemovidas++;
			}
		}
	    return linhasRemovidas;
	}
	
	/**
	 * Move todos os blocos acima da linha removida para a linha de baixo
	 */
	public void descerLinha(int l) {
		for(int linha = l; linha > 0; linha--) {
			for(int coluna = 0; coluna < gradeColuna; coluna++) {
				fundoBlocos[linha][coluna] = fundoBlocos[linha - 1][coluna];
			}
		}
	}
	
	/**
	 * Move o bloco para a direita enquanto estiver caindo e verifica a colisão da borda esquerda dos blocos do fundo
	 */
	public void moveBlocoDireita() {
		if (jogoTerminado) return;
			
		if (!colisao(bloco, bloco.getX() + 1, bloco.getY())) {
	        bloco.moveDireita();
	        repaint();
	    }
	}
	
	/**
	 * Move o bloco para a esquerda enquanto estiver caindo e verifica a colisão da borda direita dos blocos do fundo
	 */
	public void moveBlocoEsquerda() {
		if (jogoTerminado) return;
		
		if (!colisao(bloco, bloco.getX() - 1, bloco.getY())) {
	        bloco.moveEsquerda();
	        repaint();
	    }
	}	
	
	/**
	 * Rotaciona o bloco que esta caindo
	 */
	public void rotacionar() {
		if (jogoTerminado) return;
		bloco.rotacionarBloco(gradeColuna);
		repaint();
	}
	
	/**
	 * Move o bloco que estiver caindo na velocidade padrão
	 */
	public void retornaVelocidadeNormal(){
		delayVelocidade = velocidadeNormal;
		looper.setDelay(delayVelocidade);
	}
	
	/**
	 * Move o bloco que estiver caindo na velocidade rápida
	 */
	public void retornaVelocidadeRapida(){
		delayVelocidade = velocidadeRapida;
		looper.setDelay(delayVelocidade);
	}
	
	/**
	 * Verifica caso o bloco chegou no fundo da grade
	 * @param peca Bloco atual
	 * @param x Posição X do bloco
	 * @param y Posição Y do bloco
	 * @return Retorna verdadeiro quando ocorre uma colisão no bloco, caso o contrario, retorna falso
	 */
	private boolean colisao(Tetromino peca, int x, int y) {
		int[][]forma = peca.getBloco();	
		
		for(int coluna = 0; coluna < bloco.getWidth(); coluna++) {
			for(int linha = bloco.getHeight() - 1; linha >= 0; linha--) {
				if(forma[linha][coluna] != 0) {
					int novoX = x + coluna;
	                int novoY = y + linha;

	                // Colisão borda da grade
	                if (novoX < 0 || novoX >= gradeColuna) {
	                    return true;
	                }

	                // Colisão fundo da grade
	                if (novoY >= gradeLinha) {
	                    return true;
	                }

	                // Colisão entra os blocos
	                if (novoY >= 0 && fundoBlocos[novoY][novoX] != null) {
	                	return true;
	                }
				}
			}
		}
		return false;
	}
	
	/**
	 * Verifica se o bloco recém-criado está colidindo com blocos do fundo.
	 */
	private boolean verificaColisaoAoNascer() {
	    int[][] forma = bloco.getBloco();

	    for (int l = 0; l < bloco.getHeight(); l++) {
	        for (int c = 0; c < bloco.getWidth(); c++) {
	            if (forma[l][c] != 0) {
	                int gridX = bloco.getX() + c;
	                int gridY = bloco.getY() + l;

	                // Se a parte do bloco está dentro dos limites visíveis da grade
	                if (gridX >= 0 && gridX < gradeColuna &&gridY < gradeLinha) {
	                    if (gridY >= 0 && fundoBlocos[gridY][gridX] != null) {
	                        return true; // Colisão detectada no spawn
	                    }
	                }
	                // Parte acima da grade
                    if (gridY < 0 && fundoBlocos[0][gridX] != null) {
                        return true;
                    }
	            }
	        }
	    }
	    return false;
	}
	
	@Override
	protected void paintComponent(Graphics grade) {
		super.paintComponent(grade) ;
		// Adciona a cor no fundo da area do jogo
		grade.setColor(Color.black);
		grade.fillRect(0, 0, getWidth(), getHeight());

        // Cria os formatos do tetris
		grade.setColor(Color.white);
        for(int linha = 0; linha <= gradeLinha; linha++){
        	grade.drawLine(0, linha * gradeArea, gradeArea * gradeColuna, linha * gradeArea);
        }	
		for(int coluna = 0; coluna <= gradeColuna; coluna++){
			grade.drawLine(coluna * gradeArea, 0, coluna * gradeArea, gradeArea * gradeLinha);
		}
		
		// Gera blocos para o painel
		for(int linha = 0; linha < bloco.getHeight(); linha++) {
			for(int coluna = 0; coluna < bloco.getWidth(); coluna++){
				if(bloco.getBloco()[linha][coluna] == 1) {
					
					int x = (bloco.getX() + coluna) * gradeArea;
					int y = (bloco.getY() + linha) * gradeArea;
					
					desenhaBlocoGrade(grade, bloco.getCor(), x, y);
				}
			}
		}
		geraFundoBlocos(grade);
	}
	
}
