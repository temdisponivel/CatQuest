package classes.uteis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.files.FileHandle;

/**
 * Classe utilizada para carregar m�sicas na mem�ria. Feito atrav�s de thread porque pode ser um processo demorado.
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
	 * Cria uma nova {@link Music} com as configura��es do jogo.
	 * Quando a m�sica chegar ao fim, ser� chamado o listener {@link OnCompletionListener}.
	 * @param arquivo {@link FileHandle} contendo o arquivo da m�sica.
	 * @param posicaoLoop Posi��o que a m�sica deve come�ar a tocar.
	 * @param isLooping Se a m�sica deve ficar em loop.
	 * @param isPlaing Se a m�sica j� deve ser tocada ap�s criar.
	 * @param listenerCarregarMusica {@link OnCompletionListener} que ser� chamado ao completar o carregamento da m�sica na mem�ria.
	 * @param listener {@link CarregarMusicaListner} que ser� chamado ao t�rmino da m�sica.
	 */
	public CarregarMusica(FileHandle arquivo, float posicaoLoop, boolean isLooping, boolean isPlaing, 
			OnCompletionListener listener, CarregarMusicaListner listenerCarregarMusica)
	{
		if (!arquivo.exists())
		{
			Log.instancia.Logar("N�o foi poss�vel encontrar o arquivo: " + arquivo.name(), null, true);
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
			
			musica.setVolume(Configuracoes.instancia.GetVolumeMusica()/100);
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
