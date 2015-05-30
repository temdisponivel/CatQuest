package classes.gameobjects.personagens.herois;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.badlogic.gdx.math.Vector2;
import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.Personagem;
import classes.uteis.Player;
import classes.uteis.Serializador;
import classes.uteis.controle.Controle.Direcoes;

/**
 * Classe que representa um her�i do jogo.
 * 
 * @author Victor
 *
 */
public abstract class Heroi extends Personagem implements Serializador
{
	/**
	 * Enumerador para os {@link Sound sons} dos {@link Heroi her�is}.
	 * 
	 */
	protected enum SomHeroi
	{
		Movimenta, Morte, Dano, Ativo, Passivo,
	}

	/**
	 * Enumerador para as {@link Animation anima��es} dos {@link Heroi her�is}.
	 * 
	 */
	protected enum AnimacaoHeroi
	{
		Parado, MovimentoCima, MovimentoBaixo, MovimentoEsquerda, MovimentoDireita, Morto, Dano, Ativo, Passivo,
	}

	static public HashMap<Integer, Heroi> herois = new HashMap<Integer, Heroi>();
	protected Player _player = null;
	private float _tempoMorto = 0;
	private boolean _morto = false;

	/**
	 * Cria um novo her�i.
	 */
	public Heroi()
	{
		super();
		herois.put(this.GetId(), this);
		_tipo = GameObjects.Heroi;
		_colidiveis.put(GameObjects.Inimigo, Colisoes.Passavel);
		_colidiveis.put(GameObjects.Heroi, Colisoes.Passavel);		
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (_morto && CatQuest.instancia.GetTempoJogo() - _tempoMorto > 3)
		{
			CatQuest.instancia.RetiraTela();
			CatQuest.instancia.GetCamera().setToOrtho(false);
			return;
		}
		
		this.Movimenta(_player.GetControle().GetDirecao(), deltaTime, false);

		if (_player.GetControle().GetDirecaoAtaque() != Direcoes.CENTRO)
			this.AtaqueBasico();

		if (_player.GetControle().GetAcao())
			this.Acao();

		if (_player.GetControle().GetHabilidade())
			this.HabilidadeAtiva();
	}

	/**
	 * Define um player para este {@link Heroi her�i}. Tamb�m define no player
	 * que este � o personagem que ele est� controlando.
	 * 
	 * @param player
	 *            {@link Player} para controlar este her�i.
	 */
	public void SetPlayer(Player player)
	{
		_player = player;
		_player.SetPersonagem(this);
	}

	@Override
	public void Morre()
	{
		_tempoMorto = CatQuest.instancia.GetTempoJogo();
		_morto = true;
	}

	@Override
	public void RecebeDano(float dano)
	{
		super.RecebeDano(dano);
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
		if (colidiu instanceof Heroi)
		{
			_morto = false;
		}
	}

	/**
	 * Fun��o chamada quando o her�i deve executar sua a��o.
	 */
	protected abstract void Acao();

	/**
	 * Fun��o chamada quando o her�i deve executar sua habilidade ativa.
	 */
	protected abstract void HabilidadeAtiva();

	/**
	 * Fun��o chamada quando o her�i deve executar um ataque normal
	 */
	protected abstract void AtaqueBasico();
	
	/**
	 * REtorna o {@link Player player} que esta controlando este her�i.
	 */
	public Player GetPlayer()
	{
		return _player;
	}
	
	/**
	 * Retorna todos os {@link Heroi her�is} ativos - que tem algum player definido.
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
	
	/**
	 * Retorna o {@link Heroi her�i} mais pr�ximo da posi��o informada.
	 * @param posicao {@link Vector2 Posi��o} para procurar pelo mais pr�ximo.
	 * @return Her�i mais pr�ximo.
	 */
	static public Heroi GetHeroiMaisProximo(Vector2 posicao)
	{
		Iterator<Heroi> herois = Heroi.GetHeroisAtivos().iterator();
		Heroi retorno = null, aux = null;
		
		float menorDistancia = Integer.MAX_VALUE;
		while (herois.hasNext())
		{
			aux = herois.next();
			
			if (posicao.dst(aux.GetPosicao()) < menorDistancia)
			{
				retorno = aux;
				menorDistancia = posicao.dst(aux.GetPosicao());
			}
		}
		
		return retorno;
	}
}
