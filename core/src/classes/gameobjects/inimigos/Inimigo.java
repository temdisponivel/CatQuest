package classes.gameobjects.inimigos;

import java.util.HashMap;
import classes.gameobjects.Personagem;
import classes.uteis.Reciclador;
import classes.uteis.Reciclavel;

/**
 * Classe base para todos os inimigos do jogo.
 * @author matheus
 *
 */
public abstract class Inimigo extends Personagem implements Reciclavel
{
	/**
	 * Enumerador para os {@link Sound sons} dos {@link Inimigo inimigos}.
	 * @author matheus
	 *
	 */
	protected enum SomInimigo
	{
		Movimenta,
		Morte,
		Dano,
		Ataque,
	}
	
	/**
	 * Enumerador para as {@link Animation anima��es} dos {@link Inimigo inimigos}.
	 * @author matheus
	 *
	 */
	protected enum AnimacaoInimigo
	{
		Parado,
		Movimento,
		Morto,
		Dano,
		Ataque,
	}
	
	static public HashMap<Integer, Inimigo> inimigos = new HashMap<Integer, Inimigo>();
	static private Reciclador<Inimigo> _reciclador = new Reciclador<Inimigo>(); 
	
	/**
	 * Cria um novo inimigo.
	 */
	public Inimigo()
	{
		super();
		inimigos.put(this.GetId(), this);
	}
	
	@Override
	public void Morre()
	{
		_reciclador.Recicla(this);
	}
	
	@Override
	public void Recicla()
	{
		this.Redefine();
	}
}
