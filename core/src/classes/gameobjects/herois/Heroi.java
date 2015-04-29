package classes.gameobjects.herois;

import java.util.ArrayList;
import classes.gameobjects.Classe;
import classes.gameobjects.GameObject;
import classes.gameobjects.inimigos.Inimigo;
import classes.uteis.Player;

/**
 * Classe que representa um herói do jogo.
 * @author matheus
 *
 */
public class Heroi extends Classe
{
	static public ArrayList<Heroi> herois = null;
	private Player _player = null;
	
	/**
	 * Cria um novo herói.
	 */
	public Heroi(Classes classe)
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
