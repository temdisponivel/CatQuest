package classes.uteis.sons;

import com.badlogic.gdx.audio.Sound;

/**
 * Interface das classes que querer carregar um som. Sons são carregados em threads, porque pode demorar para carregar.
 * @author Matheus
 *
 */
public interface CarregarSomListner
{
	/**
	 * Função chamada ao término do carregamento do {@link Sound som} na memória.
	 * @param somCarregada Som carregado.
	 */
	public abstract void AoCarregar(Sound somCarregada);
}