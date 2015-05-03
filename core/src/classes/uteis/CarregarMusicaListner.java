package classes.uteis;

import com.badlogic.gdx.audio.Music;

/**
 * Interface das classes que querer carregar uma m�sica. M�sicas s�o carregadas em threads, porque pode demorar para carregar.
 * @author Matheus
 *
 */
public interface CarregarMusicaListner
{
	/**
	 * Fun��o chamada ao t�rmino do carregamento da {@link Music musica} na mem�ria.
	 * @param somCarregada M�sica carregada.
	 */
	public abstract void AoCarregar(Music somCarregada);
}
