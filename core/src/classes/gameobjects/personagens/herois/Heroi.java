package classes.gameobjects.personagens.herois;

import java.util.HashMap;
import java.util.LinkedList;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.ObjetoQuebravel;
import classes.gameobjects.personagens.Personagem;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.Player;
import classes.uteis.Serializador;
import classes.uteis.controle.Controle.Direcoes;

/**
 * Classe que representa um herói do jogo.
 * 
 * @author Victor
 *
 */
public abstract class Heroi extends Personagem implements Serializador
{
	/**
	 * Enumerador para os {@link Sound sons} dos {@link Heroi heróis}.
	 * 
	 */
	protected enum SomHeroi
	{
		Movimenta, Morte, Dano, Ativo, Passivo,
	}

	/**
	 * Enumerador para as {@link Animation animações} dos {@link Heroi heróis}.
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
		_colidiveis.put(GameObjects.Inimigo, Colisoes.Passavel);
		_colidiveis.put(GameObjects.GameObject, Colisoes.Livre);
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		this.Movimenta(_player.GetControle().GetDirecao(), deltaTime, false);

		if (_player.GetControle().GetDirecaoAtaque() != Direcoes.CENTRO)
			this.AtaqueBasico();

		if (_player.GetControle().GetAcao())
			this.Acao();

		if (_player.GetControle().GetHabilidade())
			this.HabilidadeAtiva();
	}

	/**
	 * Define um player para este {@link Heroi herói}. Também define no player
	 * que este é o personagem que ele está controlando.
	 * 
	 * @param player
	 *            {@link Player} para controlar este herói.
	 */
	public void SetPlayer(Player player)
	{
		_player = player;
		_player.SetPersonagem(this);
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
		else if (colidiu instanceof ObjetoQuebravel)
		{
			this.InflingeDano((ObjetoQuebravel) colidiu);
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
	 * Função chamada quando o herói deve executar um ataque normal
	 */
	protected abstract void AtaqueBasico();
	
	/**
	 * REtorna o {@link Player player} que esta controlando este herói.
	 */
	public Player GetPlayer()
	{
		return _player;
	}
	
	/**
	 * Retorna todos os {@link Heroi heróis} ativos - que tem algum player definido.
	 */
	static public LinkedList<Heroi> GetHeroisAtivos()
	{
		LinkedList<Heroi> ativos = new LinkedList<Heroi>();
		for (Heroi heroi : herois.values())
		{
			if (heroi.GetPlayer() != null)
				ativos.add(heroi);
		}
		
		return ativos;
	}
}
