package classes.gameobjects.herois;

import java.util.HashMap;
import classes.gameobjects.Personagem;
import classes.gameobjects.GameObject;
import classes.gameobjects.inimigos.Inimigo;
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
	 * Enumerador para os sons dos herois.
	 * @author matheus
	 *
	 */
	protected enum SomHeroi
	{
		Movimenta,
		Ativo,
		Passivo,
		Dano,
	}
	
	static public HashMap<Integer, Heroi> herois = new HashMap<Integer, Heroi>();
	protected String _descricaoHeroi = "";
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
	
	public void SetPlayer(Player player)
	{
		_player = player;
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
