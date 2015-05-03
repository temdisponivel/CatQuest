package classes.uteis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/**
 * Classe utilizada para carregar sons. Feito através de thread porque pode ser um processo demorado.
 * @author Matheus
 *
 */
public class CarregarSom implements Runnable
{
	static public CarregarSom instancia = null;
	FileHandle _arquivo;
	CarregarSomListner _listner = null;
	
	/**
	 * Inicia o singleton para o {@link CarregarMusica carregador de músicas}.
	 */
	public CarregarSom()
	{
		if (instancia == null)
			instancia = this;
	}
	
	/**
	 * Cria um novo {@link Sound}.
	 * @param arquivo {@link FileHandle} contendo o arquivo do som.
	 * @param listener {@link CarregarMusicaListner} que será chamado ao terminar de carregar o som.
	 */
	public void Carrega(FileHandle arquivo, CarregarSomListner listenerCarregarMusica)
	{
		if (!arquivo.exists())
		{
			Log.instancia.Logar("Não foi possível encontrar o arquivo: " + arquivo.name(), null, false);
			return;
		}
			
		_arquivo = arquivo;
		_listner = listenerCarregarMusica;
		
		this.run();
	}
	
	@Override
	public void run()
	{		
		Sound som = null;
		
		if (_arquivo != null)
		{
			som = Gdx.audio.newSound(_arquivo);
			
			if (som == null)
				return;
		}
		
		if (_listner != null)
			_listner.AoCarregar(som);
	}
}
