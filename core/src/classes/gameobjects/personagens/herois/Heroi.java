package classes.gameobjects.personagens.herois;

import java.util.HashMap;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.Personagem;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.Player;
import classes.uteis.Serializador;

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
	}

	@Override
	public void Inicia()
	{
		_tipo = GameObjects.Heroi;
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		if (_player != null)
		{
			_player.GetControle().GetAcao();
			_player.GetControle().GetHabilidade();
			_player.GetControle().GetDirecao();
			_player.GetControle().GetDirecaoAtaque();
		}
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
