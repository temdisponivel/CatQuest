package classes.gameobjects.personagens.herois;

import java.util.HashMap;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.Personagem;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.Player;
import classes.uteis.Serializador;

/**
 * Classe que representa um her�i do jogo.
 * @author matheus
 *
 */
public abstract class Heroi extends Personagem implements Serializador
{
	/**
	 * Enumerador para os {@link Sound sons} dos {@link Heroi her�is}.
	 * @author matheus
	 *
	 */
	protected enum SomHeroi
	{
		Movimenta,
		Morte,
		Dano,
		Ativo,
		Passivo,
	}
	
	/**
	 * Enumerador para as {@link Animation anima��es} dos {@link Heroi her�is}.
	 * @author matheus
	 *
	 */
	protected enum AnimacaoHeroi
	{
		Parado,
		Movimento,
		Morto,
		Dano,
		Ativo,
		Passivo,
	}
	
	static public HashMap<Integer, Heroi> herois = new HashMap<Integer, Heroi>();
	protected Player _player = null;
	
	/**
	 * Cria um novo her�i.
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
	}
	
	/**
	 * Define um player para este {@link Heroi her�i}.
	 * @param player {@link Player} para controlar este her�i.
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
			this.InfligeDano((Inimigo) colidiu);
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
}
