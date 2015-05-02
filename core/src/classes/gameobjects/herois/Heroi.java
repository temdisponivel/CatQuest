package classes.gameobjects.herois;

import java.util.ArrayList;

import classes.gameobjects.Personagem;
import classes.gameobjects.GameObject;
import classes.gameobjects.inimigos.Inimigo;
import classes.uteis.Player;
import classes.uteis.Serializador;

/**
 * Classe que representa um herói do jogo.
 * @author matheus
 *
 */
public abstract class Heroi extends Personagem implements Serializador
{
	static public ArrayList<Heroi> herois = null;
	protected String _descricaoHeroi = "";
	protected String _nome = "";
	protected Player _player = null;
	
	/**
	 * Cria um novo herói.
	 */
	public Heroi()
	{
		if (herois == null)
			herois = new ArrayList<Heroi>();
		
		herois.add(this);
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
