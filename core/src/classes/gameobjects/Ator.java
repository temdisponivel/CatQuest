package classes.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Interface que representa um objeto ator, ou seja, um objeto que age no ambiente.
 * @author matheus
 *
 */
public interface Ator 
{
	/**
	 * Enumerador dos possíveis estados do {@link Ator ator}.
	 * @author matheus
	 *
	 */
	public enum Estado
	{
		Atacando,
		Defendendo,
		Parado,
		Andando,
	}
	
	public Estado _estado = Estado.Parado;
	
	/**
	 * Move o {@link Ator ator} para o {@link Vector2 destino} usando como <i>alpha</i> a agilidade do ator.
	 * @param destino {@link Vector2 Posição} final do ator. É possível que o objeto não chegue nesta posição no frame atual -
	 * caso a velocidade dele não seja suficiente.
	 * @see {@link Vector2#lerp(Vector2, float)}.
	 */
	public void Mover(Vector2 destino);
	
	 
}
