package classes.uteis.sons;

import classes.uteis.Configuracoes;
import classes.uteis.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.files.FileHandle;

/**
 * Classe utilizada para carregar m�sicas. Feito atrav�s de thread porque pode ser um processo demorado.
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
	 * Inicia o singleton para o {@link CarregarMusica carregador de m�sicas}.
	 */
	public CarregarMusica()
	{
		if (instancia == null)
			instancia = this;
	}
	
	/**
	 * Cria uma nova {@link Music} com as configura��es do jogo.
	 * Quando a m�sica chegar ao fim, ser� chamado o listener {@link OnCompletionListener}.
	 * @param arquivo {@link FileHandle} contendo o arquivo da m�sica.
	 * @param posicaoLoop Posi��o que a m�sica deve come�ar a tocar.
	 * @param isLooping Se a m�sica deve ficar em loop.
	 * @param isPlaing Se a m�sica j� deve ser tocada ap�s criar.
	 * @param listenerCarregarMusica {@link OnCompletionListener} que ser� chamado ao completar o carregamento da m�sica na mem�ria. Se nulo, � chamado o {@link Music#dispose()} ao t�rmino da m�sica.
	 * @param listener {@link CarregarMusicaListner} que ser� chamado ao t�rmino da m�sica.
	 */
	public void Carrega(FileHandle arquivo, float posicaoLoop, boolean isLooping, boolean isPlaing, 
			OnCompletionListener listener, CarregarMusicaListner listenerCarregarMusica)
	{
		if (!arquivo.exists())
		{
			Log.instancia.Logar("N�o foi poss�vel encontrar o arquivo: " + arquivo.name(), null, false);
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
	 * Cria uma nova {@link Music} com as configura��es do jogo. Carrega a m�sica iniciando tocando, sem loop, a partir de 0 segundo.
	 * Quando a m�sica chegar ao fim, ela ser� liberada da mem�ria.
	 * @param arquivo Arquivo da m�sica.
	 */
	public void Carrega(FileHandle arquivo)
	{
		CarregarMusica.instancia.Carrega(arquivo, 0, false, true, this, null);
	}
	
	/**
	 * Cria uma nova {@link Music} com as configura��es do jogo. Carrega a m�sica iniciando tocando, sem loop, a partir de 0 segundo.
	 * Quando a m�sica chegar ao fim, ela ser� liberada da mem�ria. Portanto, n�o mexer na referencia.
	 * @param arquivo Arquivo da m�sica.
	 * @param listener Listener que ser� chamado ao completar o carregamento da m�sica na mem�ria. Pode ser null.
	 */
	public void Carrega(FileHandle arquivo, CarregarMusicaListner listener)
	{
		CarregarMusica.instancia.Carrega(arquivo, 0, false, true, this, listener);
	}
	
	/**
	 * Cria uma nova {@link Music} com as configura��es do jogo.
	 * Quando a m�sica chegar ao fim, ela ser� liberada da mem�ria. Portanto, n�o mexer na referencia.
	 * @param arquivo {@link FileHandle} contendo o arquivo da m�sica.
	 * @param posicaoLoop Posi��o que a m�sica deve come�ar a tocar.
	 * @param isLooping Se a m�sica deve ficar em loop.
	 * @param isPlaing Se a m�sica j� deve ser tocada ap�s criar.
	 * @param listener Listener que ser� chamado ao completar o carregamento da m�sica na mem�ria. Pode ser null. Pode ser null.
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
