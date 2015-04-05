package classes.uteis;

import catquest.CatQuest;

/**
 * Classe usada para serialização e deserialização para JSON e gravar em arquivo
 * com as configurações do usuário.
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
	 * @return O volume em que as {@link com.badlogic.gdx.audio.Music musicas} estão sendo tocadas.
	 */
	public float GetVolumeMusica()
	{
		return _volumeMusica;
	}

	/**
	 * Define um novo volume para {@link com.badlogic.gdx.audio.Music musicas} do jogo. Após definir, deve aplicar as configurações {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * @param volumeMusica Um valor entre 0 e 100.
	 */
	public void SetVolumeMusica(float volumeMusica)
	{
		if (volumeMusica >= 0 && volumeMusica <= 100)
			this._volumeMusica = volumeMusica;
	}
	
	/**
	 * 
	 * @return O volume em que os {@link com.badlogic.gdx.audio.Sound sons} estão sendo tocados.
	 */
	public float GetVolumeSom()
	{
		return _volumeSom;
	}
	
	/**
	 * Define um novo volume para os {@link com.badlogic.gdx.audio.Sound sons} do jogo. Após definir, deve aplicar as configurações {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * Quando {@link Configuracoes#GetFullscreen()} == true, a tela é esticada - caso necessário -, mas virtualmente mantém mesma largura.
	 * @param volumeSom Novo volume para o som.
	 */
	public void SetVolumeSom(float volumeSom)
	{
		if (volumeSom >= 0 && volumeSom <= 100)
			this._volumeSom = volumeSom;
	}
	
	/**
	 * Retorna a largura da tela do jogo. Quando {@link Configuracoes#GetFullscreen()} == true, a tela é esticada - caso necessário -, mas virtualmente mantém mesma largura.
	 * @return Largura da tela.
	 */
	public int GetWidth()
	{
		return _width;
	}
	
	/**
	 * Define uma nova largura para o jogo. Após definir, deve aplicar as configurações {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * Quando {@link Configuracoes#GetFullscreen()} == true, a tela é esticada - caso necessário -, mas virtualmente mantém mesma largura.
	 * @param width Nova largura para o jogo.
	 */
	public void SetWidth(int width)
	{
		this._width = width;
	}
	
	/**
	 * Retorna a altura da tela do jogo. Quando {@link Configuracoes#GetFullscreen()} == true, a tela é esticada - caso necessário -, mas virtualmente mantém mesma altura.
	 * @return Altura da tela do jogo.
	 */
	public int GetHeight()
	{
		return _height;
	}
	
	/**
	 * Define uma nova altura para o jogo. Após definir, deve aplicar as configurações {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * Quando {@link Configuracoes#GetFullscreen()} == true, a tela é esticada - caso necessário -, mas virtualmente mantém mesma altura.
	 * @param height Nova Altura para o jogo.
	 */
	public void SetHeight(int height)
	{
		this._height = height;
	}
	
	/**
	 * Retorna se o jogo está em fullscreen ou não.
	 * @return True caso em fullscreen.
	 */
	public boolean GetFullscreen()
	{
		return _fullscreen;
	}

	/**
	 * Define se o jogo deve entrar em fullscreen. Efetivação via {@link CatQuest#AplicarConfiguracoes()}
	 * @param fullscreen True para entrar em fullscreen.
	 */
	public void SetFullScreen(boolean fullscreen)
	{
		this._fullscreen = fullscreen;
	}
	
	/**
	 * Retorna se o jogo está mostrando a quantidade de FPS em que está rodando.
	 * @return True se está mostrando.
	 */
	public boolean GetMostraFPS()
	{
		return _mostraFPS;
	}

	/**
	 * Define se o jogo deve mostrar a quantidade de FPS. Efetivação via {@link CatQuest#AplicarConfiguracoes()}.
	 * @param mostraFPS True para mostrar FPS.
	 */
	public void SetMostraFPS(boolean mostraFPS)
	{
		this._mostraFPS = mostraFPS;
	}
	
	/**
	 * Retorna se o audio do jogo está ativo.
	 * @return True se está ativo.
	 */
	public boolean GetAudio()
	{
		return _audio;
	}
	
	/**
	 * Define se o audio do jogo será ativado. Efetivação via {@link CatQuest#AplicarConfiguracoes()}.
	 * @param audio True para ativar.
	 */
	public void SetAudio(boolean audio)
	{
		this._audio = audio;
	}
	
	/**
	 * Função que valida os valores das configurações, para garantir que não haja valores negativos ou inválidos.
	 * @return True caso haja valores inválidos.
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
		
		if (_volumeMusica < 0 || _volumeMusica > 100)
		{
			this.SetVolumeMusica(50); 
			erro = true;
		}
		
		if (_volumeSom < 0 || _volumeMusica > 100)
		{
			this.SetVolumeSom(50); 
			erro = true;
		}
		
		return erro;
	}
}
