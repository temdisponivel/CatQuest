package classes.uteis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import classes.uteis.Player.TipoPlayer;
import classes.uteis.controle.ConjuntoComandos;
import classes.uteis.controle.Controle.TipoControle;
import catquest.CatQuest;

/**
 * Classe usada para serializa��o e deserializa��o para JSON e gravar em arquivo
 * com as configura��es do usu�rio.
 * 
 * @author Matheus
 *
 */
public class Configuracoes implements Serializador
{
	public static Configuracoes instancia = null;
	private float _volumeMusica;
	private float _volumeSom;
	private int _width;
	private int _height;
	private boolean _fullscreen;
	private boolean _mostraFPS;
	private boolean _audio;
	private ConjuntoComandos _comandoPlayer1;
	private ConjuntoComandos _comandoPlayer2;
	
	public Configuracoes()
	{
		if (instancia == null)
		{
			instancia = this;
			
			if (this.Carrega())
				return;
		}
		
		this.SetAudio(true);
		this.SetFullScreen(false);
		this.SetWidth(1024);
		this.SetHeight(768);
		this.SetMostraFPS(false);
		this.SetVolumeMusica(50);
		this.SetVolumeSom(50);
		_comandoPlayer1 = new ConjuntoComandos(TipoPlayer.Primario, TipoControle.TECLADO);
		_comandoPlayer2 = new ConjuntoComandos(TipoPlayer.Secundario, TipoControle.TECLADO);
	}

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
			this._volumeMusica = volumeMusica/100;
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
	 * @param volumeSom Novo volume para o som.
	 */
	public void SetVolumeSom(float volumeSom)
	{
		if (volumeSom >= 0 && volumeSom <= 100)
			this._volumeSom = volumeSom/100;
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
	 * @param height Nova Altura para o jogo.
	 */
	public void SetHeight(int height)
	{
		this._height = height;
	}
	
	/**
	 * Retorna se o jogo est� em fullscreen ou n�o.
	 * @return True caso em fullscreen.
	 */
	public boolean GetFullscreen()
	{
		return _fullscreen;
	}

	/**
	 * Define se o jogo deve entrar em fullscreen. Efetiva��o via {@link CatQuest#AplicarConfiguracoes()}
	 * @param fullscreen True para entrar em fullscreen.
	 */
	public void SetFullScreen(boolean fullscreen)
	{
		this._fullscreen = fullscreen;
	}
	
	/**
	 * Retorna se o jogo est� mostrando a quantidade de FPS em que est� rodando.
	 * @return True se est� mostrando.
	 */
	public boolean GetMostraFPS()
	{
		return _mostraFPS;
	}

	/**
	 * Define se o jogo deve mostrar a quantidade de FPS. Efetiva��o via {@link CatQuest#AplicarConfiguracoes()}.
	 * @param mostraFPS True para mostrar FPS.
	 */
	public void SetMostraFPS(boolean mostraFPS)
	{
		this._mostraFPS = mostraFPS;
	}
	
	/**
	 * Retorna se o audio do jogo est� ativo.
	 * @return True se est� ativo.
	 */
	public boolean GetAudio()
	{
		return _audio;
	}
	
	/**
	 * Define se o audio do jogo ser� ativado. Efetiva��o via {@link CatQuest#AplicarConfiguracoes()}.
	 * @param audio True para ativar.
	 */
	public void SetAudio(boolean audio)
	{
		this._audio = audio;
	}
	
	/**
	 * @return O {@link ConjuntoComandos} do player1.
	 */
	public ConjuntoComandos GetComandoPlayerPrimario()
	{
		return _comandoPlayer1;
	}

	/**
	 * Define um novo {@link ConjuntoComandos} para o player1.
	 * @param comando Novo {@link ConjuntoComandos} do player1.
	 */
	public void SetComandoPlayer1(ConjuntoComandos comando)
	{
		this._comandoPlayer1 = comando;
	}

	/**
	 * @return O {@link ConjuntoComandos} do player2.
	 */
	public ConjuntoComandos GetComandoPlayerSecundario()
	{
		return _comandoPlayer2;
	}

	/**
	 * Define um novo {@link ConjuntoComandos} para o player2.
	 * @param comando Novo {@link ConjuntoComandos} do player2.
	 */
	public void SetComandoPlayer2(ConjuntoComandos comando)
	{
		this._comandoPlayer2 = comando;
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
		
		if (_volumeMusica < 0 || _volumeMusica > 100)
		{
			this.SetVolumeMusica(50); 
			erro = true;
		}
		
		if (_volumeSom < 0 || _volumeSom > 100)
		{
			this.SetVolumeSom(50); 
			erro = true;
		}
		
		return erro;
	}

	@Override
	public boolean Carrega()
	{
		if (Gdx.files.local("arquivos/config.data").exists())
		{
			Json json = new Json();
			String config = Gdx.files.local("arquivos/config.data").readString();
			
			//carrega do arquivo
			Configuracoes.instancia = json.fromJson(Configuracoes.class, config);
			Configuracoes.instancia.ValidaValores();
			return true;
		}
		return false;
	}

	@Override
	public void Salva()
	{
		Json json = new Json();
		json.setUsePrototypes(false);
		String config = json.toJson(this);
		Gdx.files.local("arquivos/config.data").writeString(config, false);
	}
}
