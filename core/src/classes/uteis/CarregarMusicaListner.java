package classes.uteis;

import com.badlogic.gdx.audio.Music;

/**
 * Interface das classes que querer carregar uma m�sica. M�sicas s�o carregadas em threads, porque pode demorar para carregar.
 * A funcao {@link CarregarMusicaListner#AoCarregar(Music)} � chamada recebe como parametro a m�sica carregada.
 * @author Matheus
 *
 */
public interface CarregarMusicaListner
{
	public abstract void AoCarregar(Music musicaCarregada);
}
