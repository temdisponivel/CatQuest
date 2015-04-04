package classes.uteis;
/**
 * Classe usada para serializa��o e deserializa��o para JSON e gravar em arquivo
 * com as configura��es do usu�rio.
 * 
 * @author Matheus
 *
 */
public class Configuracoes
{
	private float _volumeMusica;
	private float _volumeSom;
	private int _width;
	private int _height;
	private boolean _fullscreen;
	private boolean _mostraFPS;
	private boolean _audio;
	
	public float GetVolumeMusica()
	{
		return _volumeMusica;
	}

	public void SetVolumeMusica(float volumeMusica)
	{
		this._volumeMusica = volumeMusica;
	}

	public float GetVolumeSom()
	{
		return _volumeSom;
	}

	public void SetVolumeSom(float volumeSom)
	{
		this._volumeSom = volumeSom;
	}

	public int GetWidth()
	{
		return _width;
	}

	public void SetWidth(int width)
	{
		this._width = width;
	}

	public int GetHeight()
	{
		return _height;
	}

	public void SetHeight(int height)
	{
		this._height = height;
	}

	public boolean GetFullscreen()
	{
		return _fullscreen;
	}

	public void SetFullScreen(boolean fullscreen)
	{
		this._fullscreen = fullscreen;
	}

	public boolean GetMostraFPS()
	{
		return _mostraFPS;
	}

	public void SetMostraFPS(boolean mostraFPS)
	{
		this._mostraFPS = mostraFPS;
	}

	public boolean GetAudio()
	{
		return _audio;
	}

	public void SetAudio(boolean audio)
	{
		this._audio = audio;
	}
	
	/**
	 * Fun��o que valida os valores das configura��es, para garantir que n�o haja valores negativos ou inv�lidos.
	 * @return True caso haja valores inv�lidos.
	 */
	public boolean ValidaValores()
	{
		boolean erro = false;
		
		if (_width < 600)
		{
			this.SetWidth(600);
			erro = true;
		}
		
		if (_height < 600)
		{
			this.SetHeight(600); 
			erro = true;
		}
		
		if (_volumeMusica < 0)
		{
			this.SetVolumeMusica(0); 
			erro = true;
		}
		
		if (_volumeSom < 0)
		{
			this.SetVolumeSom(0); 
			erro = true;
		}
		
		return erro;
	}
}
