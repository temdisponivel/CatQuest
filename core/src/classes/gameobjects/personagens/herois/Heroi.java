package classes.gameobjects.personagens.herois;

import java.util.HashMap;

import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.Personagem;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.Log;
import classes.uteis.Player;
import classes.uteis.Serializador;
import classes.uteis.controle.Controle.Direcoes;

/**
 * Classe que representa um herói do jogo.
 * 
 * @author victor
 *
 */
public abstract class Heroi extends Personagem implements Serializador
{
	/**
	 * Enumerador para os {@link Sound sons} dos {@link Heroi heróis}.
	 * 
	 * @author victor
	 *
	 */
	protected enum SomHeroi
	{
		Movimenta, Morte, Dano, Ativo, Passivo,
	}

	/**
	 * Enumerador para as {@link Animation animações} dos {@link Heroi heróis}.
	 * 
	 * @author victor
	 *
	 */
	protected enum AnimacaoHeroi
	{
		Parado, Movimento, Morto, Dano, Ativo, Passivo,
	}

	static public HashMap<Integer, Heroi> herois = new HashMap<Integer, Heroi>();
	protected Player _player = null;

	/**
	 * Cria um novo herói.
	 */
	public Heroi()
	{
		super();

		herois.put(this.GetId(), this);
		_tipo = GameObjects.Heroi;
<<<<<<< HEAD
		_campoVisao = 10f;
	}
	
=======
		_player = Player.playerPrimario;
		_player.SetHeroi(this);
	}

>>>>>>> origin/classes
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
 
		int playerDirecao;
		float auxMovimento = this._agilidade * deltaTime;
		float x = _posicaoTela.x;
		float y = _posicaoTela.y;
		
		//Log.instancia.Logar(_posicaoTela.toString());

		
		if (_player != null)
		{
			playerDirecao = _player.GetControle().GetDirecao();
			
			if (_player.GetControle().GetAcao())
				this.Acao();
			if (_player.GetControle().GetHabilidade())
				this.HabilidadeAtiva();

			if (playerDirecao != Direcoes.CENTRO)
			{

				if (playerDirecao == Direcoes.CIMA)
					this.SetPosicao(x, y + auxMovimento);

				else if (playerDirecao == Direcoes.BAIXO)
					this.SetPosicao(x, y - auxMovimento);

				else if (playerDirecao == Direcoes.ESQUERDA)
					this.SetPosicao(x - auxMovimento, y);

				else if (playerDirecao == Direcoes.DIREITA)
					this.SetPosicao(x + auxMovimento, y);

				else if (playerDirecao == Direcoes.NORDESTE)
					this.SetPosicao(x + auxMovimento, y + auxMovimento);

				else if (playerDirecao == Direcoes.NOROESTE)
					this.SetPosicao(x - auxMovimento, y + auxMovimento);

				else if (playerDirecao == Direcoes.SUDESTE)
					this.SetPosicao(x + auxMovimento, y - auxMovimento);

				else if (playerDirecao == Direcoes.SUDOESTE)
					this.SetPosicao(x - auxMovimento, y - auxMovimento);

			}

		}
		_player.GetControle().GetDirecaoAtaque();
	}

	/**
	 * Define um player para este {@link Heroi herói}.
	 * 
	 * @param player
	 *            {@link Player} para controlar este herói.
	 */
	public void SetPlayer(Player player)
	{
		_player = player;
	}

	@Override
	public void Morre()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void RecebeDano(float dano)
	{
		super.RecebeDano(dano);
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
		_colidido = true;

		if (colidiu instanceof Inimigo)
		{
			this.InflingeDano((Inimigo) colidiu);
			Log.instancia.Logar("Colidiu CARAI");
		}
	}

	/**
	 * Função chamada quando o herói deve executar sua ação.
	 */
	protected abstract void Acao();

	/**
	 * Função chamada quando o herói deve executar sua habilidade ativa.
	 */
	protected abstract void HabilidadeAtiva();

	/**
	 * Função chamada quando o herói deve executar um atque normal
	 */
	protected abstract void AtaqueBasico();
}
