package classes.uteis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.files.FileHandle;

/**
 * Classe utilizada para carregar músicas. Feito através de thread porque pode ser um processo demorado.
 * @author Matheus
 *
 */
public class CarregarMusica implements Runnable, OnCompletionListener
{
	public static CarregarMusica instancia = null;
	FileHandle _arquivo;
	float _posicaoLoop = 0;
	boolean _isLooping = false, _isPlaing = false;
	CarregarMusicaListner _listenerCarregar;
	OnCompletionListener _listenerOnTermino;
	
	/**
	 * Inicia o singleton para o {@link CarregarMusica carregador de músicas}.
	 */
	public CarregarMusica()
	{
		if (instancia == null)
			instancia = this;
	}
	
	/**
	 * Cria uma nova {@link Music} com as configurações do jogo.
	 * Quando a música chegar ao fim, será chamado o listener {@link OnCompletionListener}.
	 * @param arquivo {@link FileHandle} contendo o arquivo da música.
	 * @param posicaoLoop Posição que a música deve começar a tocar.
	 * @param isLooping Se a música deve ficar em loop.
	 * @param isPlaing Se a música já deve ser tocada após criar.
	 * @param listenerCarregarMusica {@link OnCompletionListener} que será chamado ao completar o carregamento da música na memória. Se nulo, é chamado o {@link Music#dispose()} ao término da música.
	 * @param listener {@link CarregarMusicaListner} que será chamado ao término da música.
	 */
	public void Carrega(FileHandle arquivo, float posicaoLoop, boolean isLooping, boolean isPlaing, 
			OnCompletionListener listener, CarregarMusicaListner listenerCarregarMusica)
	{
		if (!arquivo.exists())
		{
			Log.instancia.Logar("Não foi possível encontrar o arquivo: " + arquivo.name(), null, false);
			return;
		}
			
		_arquivo = arquivo;
		_posicaoLoop = posicaoLoop;
		_isLooping = isLooping;
		_isPlaing = isPlaing;
		
		if (listener != null)
			_listenerOnTermino = listener;
		else
			_listenerOnTermino = this;
		
		_listenerCarregar = listenerCarregarMusica;
		
		this.run();
	}
	
	/**
	 * Cria uma nova {@link Music} com as configuraï¿½ï¿½es do jogo. Carrega a mï¿½sica iniciando tocando, sem loop, a partir de 0 segundo.
	 * Quando a mï¿½sica chegar ao fim, ela serï¿½ liberada da memï¿½ria.
	 * @param arquivo Arquivo da mï¿½sica.
	 */
	public void Carrega(FileHandle arquivo)
	{
		CarregarMusica.instancia.Carrega(arquivo, 0, false, true, this, null);
	}
	
	/**
	 * Cria uma nova {@link Music} com as configuraï¿½ï¿½es do jogo. Carrega a mï¿½sica iniciando tocando, sem loop, a partir de 0 segundo.
	 * Quando a mï¿½sica chegar ao fim, ela serï¿½ liberada da memï¿½ria. Portanto, nï¿½o mexer na referencia.
	 * @param arquivo Arquivo da mï¿½sica.
	 * @param listener Listener que serï¿½ chamado ao completar o carregamento da mï¿½sica na memï¿½ria. Pode ser null.
	 */
	public void Carrega(FileHandle arquivo, CarregarMusicaListner listener)
	{
		CarregarMusica.instancia.Carrega(arquivo, 0, false, true, this, listener);
	}
	
	/**
	 * Cria uma nova {@link Music} com as configuraï¿½ï¿½es do jogo.
	 * Quando a mï¿½sica chegar ao fim, ela serï¿½ liberada da memï¿½ria. Portanto, nï¿½o mexer na referencia.
	 * @param arquivo {@link FileHandle} contendo o arquivo da mï¿½sica.
	 * @param posicaoLoop Posiï¿½ï¿½o que a mï¿½sica deve comeï¿½ar a tocar.
	 * @param isLooping Se a mï¿½sica deve ficar em loop.
	 * @param isPlaing Se a mï¿½sica jï¿½ deve ser tocada apï¿½s criar.
	 * @param listener Listener que serï¿½ chamado ao completar o carregamento da mï¿½sica na memï¿½ria. Pode ser null. Pode ser null.
	 */
	public void Carrega(FileHandle arquivo, float posicaoLoop, boolean isLooping, boolean isPlaing, CarregarMusicaListner listener)
	{
		CarregarMusica.instancia.Carrega(arquivo, posicaoLoop, isLooping, isPlaing, this, listener);
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
			
			musica.setVolume(Configuracoes.instancia.GetVolumeMusica());
			musica.setLooping(_isLooping);
			musica.setOnCompletionListener(_listenerOnTermino);
			
			if(_isPlaing)
				musica.play();
			
			musica.setPosition(_posicaoLoop);
		}
		
		if (_listenerCarregar != null)
			_listenerCarregar.AoCarregar(musica);
	}

	@Override
	public void onCompletion(Music music)
	{
		music.dispose();		
	}
}
