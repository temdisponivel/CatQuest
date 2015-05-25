package classes.uteis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import classes.uteis.controle.ConjuntoComandos;
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
	private int _widthViewPort;
	private int _heightViewPort;
	private boolean _fullscreen;
	private boolean _mostraFPS;
	private boolean _audio;
	private ConjuntoComandos _conjuntoPrimario;
	private ConjuntoComandos _conjuntoSecundario;
	
	public Configuracoes()
	{
		if (instancia == null)
		{
			instancia = this;
			
			if (!this.Carrega())
			{
				this.SetPadrao();
				this.Salva();
			}
		}
		else
			return;
	}
	
	/**
	 * Define os valores padr�es.
	 */
	private void SetPadrao()
	{
		this.SetAudio(true);
		this.SetFullScreen(false);
		this.SetWidth(1024);
		this.SetHeight(768);
		this.SetMostraFPS(false);
		this.SetVolumeMusica(50);
		this.SetVolumeSom(50);
		this.SetWidthViewPort(1024);
		this.SetHeigthViewPort(768);
		_conjuntoPrimario = ConjuntoComandos.tecladoPrimario;
		_conjuntoSecundario = ConjuntoComandos.tecladoSecundario;
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
	 * Define uma nova altura para o view port. Ap�s definir, deve aplicar as configura��es {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * Quando {@link Configuracoes#GetFullscreen()} == true, a tela � esticada - caso necess�rio -, mas virtualmente mant�m mesma largura.
	 * @param widthViewPort Nova largura para o jogo.
	 */
	public void SetHeigthViewPort(int heigthViewPort)
	{
		this._heightViewPort = heigthViewPort;
	}
	
	/**
	 * Retorna a altura do view port. O view port garante que pelo menos o tamanho dele vai ser apresentado na tela.
	 * @return Largura do viewPort (largura do mundo).
	 */
	public int GetHeightViewPort()
	{
		return _heightViewPort;
	}
	
	/**
	 * Define uma nova largura para o view port. Ap�s definir, deve aplicar as configura��es {@link catquest.CatQuest#AplicarConfiguracoes()}.
	 * Quando {@link Configuracoes#GetFullscreen()} == true, a tela � esticada - caso necess�rio -, mas virtualmente mant�m mesma largura.
	 * @param widthViewPort Nova largura para o jogo.
	 */
	public void SetWidthViewPort(int widthViewPort)
	{
		this._widthViewPort = widthViewPort;
	}
	
	/**
	 * Retorna a largura do view port. O view port garante que pelo menos o tamanho dele vai ser apresentado na tela.
	 * @return Largura do viewPort (largura do mundo).
	 */
	public int GetWidthViewPort()
	{
		return _widthViewPort;
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
	 * @return O {@link ConjuntoComandos} prim�rio.
	 */
	public ConjuntoComandos GetConjuntoPrimario()
	{
		return _conjuntoPrimario;
	}

	/**
	 * Define um novo {@link ConjuntoComandos} prim�rio.
	 * @param comando Novo {@link ConjuntoComandos} prim�rio.
	 */
	public void SetConjuntoPrimario(ConjuntoComandos comando)
	{
		this._conjuntoPrimario = comando;
	}

	/**
	 * @return O {@link ConjuntoComandos} secund�rio.
	 */
	public ConjuntoComandos GetConjuntoSecundario()
	{
		return _conjuntoSecundario;
	}

	/**
	 * Define um novo {@link ConjuntoComandos} secund�rio.
	 * @param comando Novo {@link ConjuntoComandos} secund�rio.
	 */
	public void SetConjuntoSecundario(ConjuntoComandos comando)
	{
		this._conjuntoSecundario = comando;
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
		
		if (_widthViewPort < 600)
		{
			this.SetWidthViewPort(600);
			erro = true;
		}
		
		if (_heightViewPort < 600)
		{
			this.SetHeigthViewPort(600); 
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
