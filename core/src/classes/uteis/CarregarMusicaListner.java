package classes.uteis;

import com.badlogic.gdx.audio.Music;

/**
 * Interface das classes que querer carregar uma música. Músicas são carregadas em threads, porque pode demorar para carregar.
 * A funcao {@link CarregarMusicaListner#AoCarregar(Music)} é chamada recebe como parametro a música carregada.
 * @author Matheus
 *
 */
public interface CarregarMusicaListner
{
	public abstract void AoCarregar(Music musicaCarregada);
}
