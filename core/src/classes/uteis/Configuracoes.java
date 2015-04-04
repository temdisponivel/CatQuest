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
	
	/**
	 * 
	 * @return O volume em que as {@link com.badlogic.gdx.audio.Music musicas} est�o sendo tocadas.
	 */
	public float GetVolumeMusica()
	{
		return _volumeMusica;
	}

	/**
	 * Define um novo volume para {@link com.badlogic.gdx.audio.Music musicas} do jogo. Ap�s definir, deve aplicar as configura��es {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * @param volumeMusica Um valor entre 0 e 100.
	 */
	public void SetVolumeMusica(float volumeMusica)
	{
		if (volumeMusica >= 0 && volumeMusica <= 100)
			this._volumeMusica = volumeMusica;
	}
	
	/**
	 * 
	 * @return O volume em que os {@link com.badlogic.gdx.audio.Sound sons} est�o sendo tocados.
	 */
	public float GetVolumeSom()
	{
		return _volumeSom;
	}
	
	/**
	 * Define um novo volume para os {@link com.badlogic.gdx.audio.Sound sons} do jogo. Ap�s definir, deve aplicar as configura��es {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * Quando {@link Configuracoes#GetFullscreen()} == true, a tela � esticada - caso necess�rio -, mas virtualmente mant�m mesma largura.
	 * @param volumeSom
	 */
	public void SetVolumeSom(float volumeSom)
	{
		if (volumeSom >= 0 && volumeSom <= 100)
			this._volumeSom = volumeSom;
	}
	
	/**
	 * Retorna a largura da tela do jogo. Quando {@link Configuracoes#GetFullscreen()} == true, a tela � esticada - caso necess�rio -, mas virtualmente mant�m mesma largura.
	 * @return Largura da tela.
	 */
	public int GetWidth()
	{
		return _width;
	}
	
	/**
	 * Define uma nova largura para o jogo. Ap�s definir, deve aplicar as configura��es {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * Quando {@link Configuracoes#GetFullscreen()} == true, a tela � esticada - caso necess�rio -, mas virtualmente mant�m mesma largura.
	 * @param width Nova largura para o jogo.
	 */
	public void SetWidth(int width)
	{
		this._width = width;
	}
	
	/**
	 * Retorna a altura da tela do jogo. Quando {@link Configuracoes#GetFullscreen()} == true, a tela � esticada - caso necess�rio -, mas virtualmente mant�m mesma altura.
	 * @return Altura da tela do jogo.
	 */
	public int GetHeight()
	{
		return _height;
	}
	
	/**
	 * Define uma nova altura para o jogo. Ap�s definir, deve aplicar as configura��es {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * Quando {@link Configuracoes#GetFullscreen()} == true, a tela � esticada - caso necess�rio -, mas virtualmente mant�m mesma altura.
	 * @param width Nova largura para o jogo.
	 */
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
