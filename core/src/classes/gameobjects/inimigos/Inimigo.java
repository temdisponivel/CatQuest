package classes.gameobjects.inimigos;

import java.util.HashMap;
import com.badlogic.gdx.utils.Pool.Poolable;
import classes.gameobjects.Personagem;

/**
 * Classe base para todos os inimigos do jogo.
 * @author matheus
 *
 */
public abstract class Inimigo extends Personagem implements Poolable
{
	static public HashMap<Integer, Inimigo> inimigos = new HashMap<Integer, Inimigo>();
	
	/**
	 * Cria um novo inimigo.
	 */
	public Inimigo()
	{
		super();
		inimigos.put(this.GetId(), this);
	}
	
	@Override
	public void reset() 
	{
		this.Redefine();
	}
}
