package classes.uteis;

import catquest.CatQuest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.files.FileHandle;

/**
 * Classe utilizada para carregar músicas na memória. Feito através de thread porque pode ser um processo demorado.
 * @author Matheus
 *
 */
public class CarregarMusica implements Runnable
{
	FileHandle _arquivo;
	float _posicaoLoop = 0;
	boolean _isLooping = false, _isPlaing = false;
	CarregarMusicaListner _listenerCarregar;
	OnCompletionListener _listenerOnTermino;
	
	/**
	 * Cria uma nova {@link Music} com as configurações do jogo.
	 * Quando a música chegar ao fim, será chamado o listener {@link OnCompletionListener}.
	 * @param arquivo {@link FileHandle} contendo o arquivo da música.
	 * @param posicaoLoop Posição que a música deve começar a tocar.
	 * @param isLooping Se a música deve ficar em loop.
	 * @param isPlaing Se a música já deve ser tocada após criar.
	 * @param listenerCarregarMusica {@link OnCompletionListener} que será chamado ao completar o carregamento da música na memória.
	 * @param listener {@link CarregarMusicaListner} que será chamado ao término da música.
	 */
	public CarregarMusica(FileHandle arquivo, float posicaoLoop, boolean isLooping, boolean isPlaing, 
			OnCompletionListener listener, CarregarMusicaListner listenerCarregarMusica)
	{
		if (!arquivo.exists())
		{
			Log.Logar("Não foi possível encontrar o arquivo: " + arquivo.name(), null, true);
		}
			
		_arquivo = arquivo;
		_posicaoLoop = posicaoLoop;
		_isLooping = isLooping;
		_isPlaing = isPlaing;
		_listenerOnTermino = listener;
		_listenerCarregar = listenerCarregarMusica;
	}
	
	@Override
	public void run()
	{		
		Music musica = null;
		
		if (_arquivo != null)
		{
			musica = Gdx.audio.newMusic(_arquivo);
			
			if (musica == null)
				return;
			
			musica.setVolume(CatQuest.instancia.GetConfig().GetVolumeMusica()/100);
			musica.setLooping(_isLooping);
			musica.setOnCompletionListener(_listenerOnTermino);
			
			if(_isPlaing)
				musica.play();
			
			musica.setPosition(_posicaoLoop);
		}
		
		if (_listenerCarregar != null)
			_listenerCarregar.AoCarregar(musica);
	}
}
