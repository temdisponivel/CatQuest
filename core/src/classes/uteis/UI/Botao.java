package classes.uteis.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Buttons;
import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.uteis.Camada;
import classes.uteis.UI.Botao.EscutadorBotao.BotoesMouse;

/**
 * Classe bot�o. Utilizada para criar interface de usu�rio. Extende de {@link GameObject}, portanto herda todas as propriedades. Inclusive adi��o de filhos.
 * @author Matheus
 *
 */
public class Botao extends GameObject
{
	/**
	 * Interface do escutador do bot�o.
	 * @author Matheus
	 *
	 */
	public interface EscutadorBotao
	{
		/**
		 * Enum para representa��o dos bot�es do mouse.
		 * @author Matheus
		 *
		 */
		public enum BotoesMouse
		{
			Direito,
			Central,
			Esquerdo,
		}
		
		/**
		 * Fun��o chamada toda vez que o usu�rio clica e solta no bot�o.
		 * @param botaoClicado Referencia do bot�o que usu�rio clicou.
		 * @param botaoMouse {@link BotoesMouse Bot�o} do mouse que o usu�rio clicou.
		 */
		public void Click(Botao botaoClicado, BotoesMouse botaoMouse);
	}
	
	private Sprite _spriteClicado = null;
	private Sprite _spriteNormal = null;
	private EscutadorBotao _escutador = null;
	protected boolean _apertado = false;
	private BotoesMouse _botaoApertado = BotoesMouse.Esquerdo;
	
	/**
	 * Cria um novo bot�o em uma determinada posi��o. Cria com a cor e fundo padr�o dos bot�es do jogo.
	 * @param posicao {@link Vector2 Posi��o} do bot�o na tela.
	 * @param tamanho {@link Rectangle Tamanho} do bot�o.
	 */
	public Botao(Vector2 posicao, Rectangle tamanho, EscutadorBotao escutador)
	{
		super();
		_posicaoTela = posicao;
		_caixaColisao = tamanho;
		_caixaColisao.setPosition(_posicaoTela);
		_escutador = escutador;
		_camada = Camada.UI;
		_tipo = GameObjects.Ui;
	}

	@Override
	public void AoColidir(GameObject colidiu){};

	@Override
	public void Inicia()
	{
		super.Inicia();
		
		//cria um mapa de pixels com o tamanho do bot�o mais uma borda
		Pixmap botao = new Pixmap((int) _caixaColisao.width + 3, (int) _caixaColisao.height + 3, Format.RGB888);
		
		//seta a cor padr�o
		botao.setColor(CatQuest.instancia.GetCor());
		
		//preenche o fundo do bot�o com o tamanho do bot�o menos a borda
		botao.fillRectangle(0, 0, (int) _caixaColisao.width, (int) _caixaColisao.height);
		
		//cria a textura a partir do mapa de pixels
		_spriteNormal = new Sprite(new Texture(botao));
		
		//define a cor como preto
		botao.setColor(Color.BLACK);
		
		//preenche de preto
		botao.fill();
		
		//seta a cor de novo para padrao
		botao.setColor(CatQuest.instancia.GetCor());
		
		//preenche novamente o mapa de pixels com a borda no canto superior esquerdo
		botao.fillRectangle(3, 3, (int) _caixaColisao.width, (int) _caixaColisao.height);
		
		//cria a textura
		_spriteClicado = new Sprite(new Texture(botao));
		
		_sprite = _spriteNormal;
		
		//libera os recursos
		botao.dispose();
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);

		//se o cursor est� em cima do botao
		if (_caixaColisao.contains(Gdx.input.getX(), CatQuest.instancia.GetAlturaTela() - Gdx.input.getY()))
		{
			//se apertou o esquerdo
			if (!_apertado && Gdx.input.isButtonPressed(Buttons.LEFT))
			{
				_apertado = true;
				_sprite = _spriteClicado;
				_botaoApertado = BotoesMouse.Esquerdo;
			}
			//se nao apertou o esquerdo, mas tinha apertado antes, ou seja, se soltou o bot�o esquerdo
			else if (_apertado && _botaoApertado == BotoesMouse.Esquerdo && !Gdx.input.isButtonPressed(Buttons.LEFT))
			{
				_apertado = false;
				_sprite = _spriteNormal;
				_escutador.Click(this, BotoesMouse.Esquerdo);
			}
			//se apertou o bot�o do centro e n�o tem nenhum bot�o apertado
			else if (!_apertado && Gdx.input.isButtonPressed(Buttons.MIDDLE))
			{
				_apertado = true;
				_sprite = _spriteClicado;
				_botaoApertado = BotoesMouse.Central;
			}
			//se tinha apertado o bot�o central e soltou agora
			else if (_apertado && _botaoApertado == BotoesMouse.Central && !Gdx.input.isButtonPressed(Buttons.MIDDLE))
			{
				_apertado = false;
				_sprite = _spriteNormal;
				_escutador.Click(this, BotoesMouse.Central);
			}
			//se apertou o bot�o direito e nao tem nenhum bot�o apertado
			else if (!_apertado && Gdx.input.isButtonPressed(Buttons.RIGHT))
			{
				_apertado = true;
				_sprite = _spriteClicado;
				_botaoApertado = BotoesMouse.Direito;
			}
			//se tinha apertado o bot�o direito e soltou agora 
			else if (_apertado && _botaoApertado == BotoesMouse.Direito && !Gdx.input.isButtonPressed(Buttons.RIGHT))
			{
				_apertado = false;
				_sprite = _spriteNormal;
				_escutador.Click(this, BotoesMouse.Direito);
			}
		}
		//se nao est� sobre o bot�o
		else
		{
			//se soltou o botao esquerdo fora do botao, desconsidera click
			if (_apertado && _botaoApertado == BotoesMouse.Esquerdo && !Gdx.input.isButtonPressed(Buttons.LEFT))
			{
				_apertado = false;
				_sprite = _spriteNormal;
			}
			//se soltou o botao direito fora do botao, desconsidera click
			else if (_apertado && _botaoApertado == BotoesMouse.Central && !Gdx.input.isButtonPressed(Buttons.MIDDLE))
			{
				_apertado = false;
				_sprite = _spriteNormal;
			}
			//se soltou o botao central fora do botao, desconsidera click
			else if (_apertado && _botaoApertado == BotoesMouse.Direito && !Gdx.input.isButtonPressed(Buttons.RIGHT))
			{
				_apertado = false;
				_sprite = _spriteNormal;
			}
		}
	}
}
