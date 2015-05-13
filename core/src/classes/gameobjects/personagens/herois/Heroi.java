package classes.gameobjects.personagens.herois;

import java.util.HashMap;

import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.Personagem;
import classes.gameobjects.personagens.Personagem.Estado;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.Player;
import classes.uteis.Serializador;

/**
 * Classe que representa um herói do jogo.
 * @author matheus
 *
 */
public abstract class Heroi extends Personagem implements Serializador
{
	/**
	 * Enumerador para os {@link Sound sons} dos {@link Heroi heróis}.
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
	 * Enumerador para as {@link Animation animações} dos {@link Heroi heróis}.
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
	protected String _descricaoHeroi = "";
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
	
	/**
	 * Define um player para este {@link Heroi herói}.
	 * @param player {@link Player} para controlar este herói.
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
		if (_estado == Estado.Atacando)
			return;
		
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
}
