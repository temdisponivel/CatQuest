package classes.uteis;

import com.badlogic.gdx.audio.Music;

/**
 * Interface das classes que querer carregar uma música. Músicas são carregadas em threads, porque pode demorar para carregar.
 * @author Matheus
 *
 */
public interface CarregarMusicaListner
{
	/**
	 * Função chamada ao término do carregamento da {@link Music musica} na memória.
	 * @param somCarregada Música carregada.
	 */
	public abstract void AoCarregar(Music somCarregada);
}
