package classes.uteis.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import classes.gameobjects.GameObject;

public class Mensagem extends GameObject
{
	private float tempoInicio = 0;
	private float duracao = 0;
	
	/**
	 * Mensagem que fica alguns segundos na tela.
	 * @param mensagem Mensagem a ser apresentada.
	 * @param segundos Quantidade de tempo que fica na tela.
	 * @param caixaFundo Caixa de fundo.
	 */
	public Mensagem(String mensagem, float segundos, Vector2 posicao, Color cor)
	{
		super();
		this.AdicionaFilho(new Etiqueta(mensagem, posicao.cpy(), cor));
		tempoInicio = CatQuest.instancia.GetTempoJogo();
		duracao = segundos;
		this.ControiFundo();
	}
	
	private void ControiFundo()
	{
		//cria um mapa de pixels com o tamanho do botão mais uma borda
		Pixmap botao = new Pixmap((int) _caixaColisao.width + 3, (int) _caixaColisao.height + 3, Format.RGB888);
		
		//seta a cor padrão
		botao.setColor(CatQuest.instancia.GetCor());
		
		//preenche o fundo do botão com o tamanho do botão menos a borda
		botao.fillRectangle(0, 0, (int) _caixaColisao.width, (int) _caixaColisao.height);
		
		//cria a textura a partir do mapa de pixels
		//_spriteNormal = new Sprite(new Texture(botao));
		
		//define a cor como preto
		botao.setColor(Color.BLACK);
		
		//preenche de preto
		botao.fill();
		
		//seta a cor de novo para padrao
		botao.setColor(CatQuest.instancia.GetCor());
		
		//preenche novamente o mapa de pixels com a borda no canto superior esquerdo
		botao.fillRectangle(3, 3, (int) _caixaColisao.width, (int) _caixaColisao.height);
		
		//cria a textura
		//_spriteClicado = new Sprite(new Texture(botao));
		
		//_sprite = _spriteNormal;
		
		//libera os recursos
		botao.dispose();
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (CatQuest.instancia.GetTempoJogo() >= tempoInicio + duracao)
		{
			this.Encerra();
		}
	}
	
	@Override
	public void AoColidir(GameObject colidiu){}
}
