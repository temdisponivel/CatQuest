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
 * Classe botão. Utilizada para criar interface de usuário. Extende de {@link GameObject}, portanto herda todas as propriedades. Inclusive adição de filhos.
 * @author Matheus
 *
 */
public class Botao extends GameObject
{
	/**
	 * Interface do escutador do botão.
	 * @author Matheus
	 *
	 */
	public interface EscutadorBotao
	{
		/**
		 * Enum para representação dos botões do mouse.
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
		 * Função chamada toda vez que o usuário clica e solta no botão.
		 * @param botaoClicado Referencia do botão que usuário clicou.
		 * @param botaoMouse {@link BotoesMouse Botão} do mouse que o usuário clicou.
		 */
		public void Click(Botao botaoClicado, BotoesMouse botaoMouse);
	}
	
	private Sprite _spriteClicado = null;
	private Sprite _spriteNormal = null;
	private EscutadorBotao _escutador = null;
	protected boolean _apertado = false;
	private BotoesMouse _botaoApertado = BotoesMouse.Esquerdo;
	
	/**
	 * Cria um novo botão em uma determinada posição. Cria com a cor e fundo padrão dos botões do jogo.
	 * @param posicao {@link Vector2 Posição} do botão na tela.
	 * @param tamanho {@link Rectangle Tamanho} do botão.
	 */
	public Botao(Rectangle tamanho, Vector2 posicao, EscutadorBotao escutador)
	{
		super();
		_posicaoTela = posicao;
		_caixaColisao = tamanho;
		_caixaColisao.setPosition(_posicaoTela);
		_escutador = escutador;
		_camada = Camada.UI;
		_tipo = GameObjects.Ui;
		this.Inicia();
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
	}

	@Override
	public void Inicia()
	{
		super.Inicia();
		
		//cria um mapa de pixels com o tamanho do botão mais uma borda
		Pixmap botao = new Pixmap((int) _caixaColisao.width + 3, (int) _caixaColisao.height + 3, Format.RGB888);
		
		//seta a cor padrão
		botao.setColor(CatQuest.instancia.GetCor());
		
		//preenche o fundo do botão com o tamanho do botão menos a borda
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
		
		if (!_atualiza)
			return;
		
		//se o cursor está em cima do botao
		if (_caixaColisao.contains(Gdx.input.getX(), CatQuest.instancia.GetAlturaTela() - Gdx.input.getY()))
		{
			//se apertou o esquerdo
			if (!_apertado && Gdx.input.isButtonPressed(Buttons.LEFT))
			{
				_apertado = true;
				_sprite = _spriteClicado;
				_botaoApertado = BotoesMouse.Esquerdo;
			}
			//se nao apertou o esquerdo, mas tinha apertado antes, ou seja, se soltou o botão esquerdo
			else if (_apertado && _botaoApertado == BotoesMouse.Esquerdo && !Gdx.input.isButtonPressed(Buttons.LEFT))
			{
				_apertado = false;
				_sprite = _spriteNormal;
				_escutador.Click(this, BotoesMouse.Esquerdo);
			}
			//se apertou o botão do centro e não tem nenhum botão apertado
			else if (!_apertado && Gdx.input.isButtonPressed(Buttons.MIDDLE))
			{
				_apertado = true;
				_sprite = _spriteClicado;
				_botaoApertado = BotoesMouse.Central;
			}
			//se tinha apertado o botão central e soltou agora
			else if (_apertado && _botaoApertado == BotoesMouse.Central && !Gdx.input.isButtonPressed(Buttons.MIDDLE))
			{
				_apertado = false;
				_sprite = _spriteNormal;
				_escutador.Click(this, BotoesMouse.Central);
			}
			//se apertou o botão direito e nao tem nenhum botão apertado
			else if (!_apertado && Gdx.input.isButtonPressed(Buttons.RIGHT))
			{
				_apertado = true;
				_sprite = _spriteClicado;
				_botaoApertado = BotoesMouse.Direito;
			}
			//se tinha apertado o botão direito e soltou agora 
			else if (_apertado && _botaoApertado == BotoesMouse.Direito && !Gdx.input.isButtonPressed(Buttons.RIGHT))
			{
				_apertado = false;
				_sprite = _spriteNormal;
				_escutador.Click(this, BotoesMouse.Direito);
			}
		}
		//se nao está sobre o botão
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
