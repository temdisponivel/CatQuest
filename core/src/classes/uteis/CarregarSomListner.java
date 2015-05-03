package classes.uteis;

import com.badlogic.gdx.audio.Sound;

/**
 * Interface das classes que querer carregar um som. Sons s�o carregados em threads, porque pode demorar para carregar.
 * @author Matheus
 *
 */
public interface CarregarSomListner
{
	/**
	 * Fun��o chamada ao t�rmino do carregamento do {@link Sound som} na mem�ria.
	 * @param somCarregada Som carregado.
	 */
	public abstract void AoCarregar(Sound somCarregada);
}