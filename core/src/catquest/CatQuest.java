package catquest;

import java.util.Stack;

import classes.uteis.Camada;
import classes.uteis.Configuracoes;
import classes.gameobjects.GameObject;
import classes.telas.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Classe que cont�m todas as informa��es padr�es do jogo. Quase todos os m�todos e propriedades s�o est�ticos.
 * @author Matheus
 *
 */
public class CatQuest implements ApplicationListener
{
	/**
	 * Enumerador com todas as camadas do jogo
	 * @author Matheus
	 *
	 */
	public enum Camadas
	{
		OBJETOS_ESTATICOS,
		PERSONAGENS,
		UI,
	};
	
	/**
	 * Enumerador com todos os modos do jogo
	 * @author Matheus
	 *
	 */
	public enum ModoJogo
	{
		COOP,
		SINGLE,
	};
	
	static public CatQuest instancia;
	private int _idObjeto = 0;
	private Stack<Tela> _pilhaTelas = null;
	private Tela[] _telas = null;
	private SpriteBatch _batch = null;
	private Camada[] _camadas = null;
	private OrthographicCamera _camera = null;
	private float _stateTime = 0;
	private Configuracoes _configuracoes = null;
	private ModoJogo _modoJogo = ModoJogo.SINGLE;
	private boolean _atualiza = true, _desenha = true;
	private BitmapFont _fonte = null;
	
	/**
	 * Contrutor do singleton.
	 */
	public CatQuest()
	{
		if (instancia == null)
			instancia = this;
		else
			this.dispose();
	}
	
	@Override
	public void create()
	{
		this.IniciaJogo(true);
	}
	

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void render()
	{
		if (_atualiza)
			this.Atualiza();

		if (_desenha)
			this.Desenha();
	}
	
	/**
	 * Fun��o que roda a l�gica de atualiza��o do jogo. AI, coli��es, mudan�a de posi��es, etc; s�o gerenciadas aqui.
	 */
	private void Atualiza()
	{
		/* ------------------ ATUALIZA ------------------- */
		this.TrataEntradaUsuario();
		
		for (int i = _telas.length - 1; i >= 0; i--)
		{
			_telas[i].Atualiza(Gdx.graphics.getDeltaTime());
		}
		
		_stateTime += Gdx.graphics.getDeltaTime();
		
		//ATUALIZA CAMERA E SETA AS MATRIZES DA CAMERA NO BATCH
		_camera.update();
		_batch.setProjectionMatrix(_camera.combined);
		
		/* ---------------- FIM ATUALIZA --------------------*/
	}
	
	/**
	 * Fun��o que roda a l�gica de desenho do jogo.
	 */
	private void Desenha()
	{
		/* ---------------- DESENHA -----------------------*/
		
		//LIMPA TELA
		Gdx.gl.glClearColor(0, 0, 0f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	    //DESENHA
	    _batch.setColor(Color.WHITE);
	    
	    _batch.begin();
	    for (int i = _telas.length - 1; i >= 0; i--)
		{
			_telas[i].Desenha(_batch);
		}
	    
	    if (_configuracoes.GetMostraFPS())
			//_fonte.draw(_batch, new StringBuilder(3).append(Gdx.graphics.getFramesPerSecond()), this.GetLarguraTela()/2, this.GetAlturaTela()/2);
			
		_batch.end();
		
		/* ----------------- FIM DESENHA  --------------------*/
	}
	
	/**
	 * Trata toda a intera��o com o usu�rio.
	 */
	private void TrataEntradaUsuario()
	{
	}

	@Override
	public void pause()	{}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}
	
	/**
	 * Inicia o jogo. Roda toda a rotina de iniciar as propriedades, chamas as primeiras fun��es, carregar telas, etc.
	 */
	private void IniciaJogo(boolean intro)
	{
		//CARREGA CONFIGURA��ES E APLICA
		this.CarregarConfig();
		this.AplicarConfiguracoes();
		
		//CONTR�I AS CAMADAS DO JOGO
		this.ControiCamadas();
		
		//ATUALIZA E DESENHA COMO TRUE PARA GAMELOOP COMPLETO
		_atualiza = true;
		_desenha = true;
		
		//CARREGA FONTE
		_fonte = new BitmapFont(Gdx.files.local("fonte\\catquest.fnt"));
		
		//CRIA SPRITEBATCH PARA DESENHAR COISAS NA TELA
		_batch = new SpriteBatch();
		
		//CRIA CAMERA ORTOGRAFICA PARA QUE N�O TENHA DIFEREN�A ENTRE PROFUNDIDADE.
		//CRIA COM O TAMANHO DAS CONFIGURA��ES
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, _configuracoes.GetWidth(), _configuracoes.GetHeight());
		
		//CRIA A PILHA DE TELAS
		_pilhaTelas = new Stack<Tela>();
		
		//SE FOR PRA COME�AR O JOGO DA TELA DE INTRO (DO INICIO), ADICIONA A INTRO NA PILHA
		if (intro)
			_pilhaTelas.add(new Introducao());
		//SE N�O, COME�A DO MENU (USADO QUANDO REINICIAR O JOGO).
		else
			_pilhaTelas.add(new Titulo());
		
		//INICIA A TELA ATUAL
		_pilhaTelas.lastElement().Iniciar();
		
		//CRIA UM NOVO VETOR DE TELAS - INICIALMENTE COM 1 POSICAO E PREENCHE COM AS TELAS DA PILHA
		//UM VETOR E UMA PILHA PARA QUE N�O TENHAMOS QUE DAR POP E PUSH TODA HORA. O VETOR � ATUALIZA SEMPRE QUE UMA NOVA TELA
		//� CRIADA OU REMOVIDA
		_telas = new Tela[1];
		_telas = _pilhaTelas.toArray(_telas);
	}
	
	/**
	 * Reinicia o jogo do inicio a partir do titulo.
	 */
	public void ReiniciaJogo()
	{
		_batch.dispose();
		
		//ENCERRA TODAS AS TELAS, LIMPA O VETOR E REINICIA O JOGO
		for (int i = _telas.length - 1; i >= 0; i--)
		{
			_telas[i].Encerrar();
			_telas = null;
		}
		
		_pilhaTelas.clear();
		
		this.IniciaJogo(false);
	}
	
	/**
	 * Fun��o que fecha o jogo.
	 */
	public void EncerraJogo()
	{
		_atualiza = false;
		_desenha = false;
		
		//ENCERRA TODAS AS TELAS, LIMPA O VETOR E ENCERRA O JOGO
		for (int i = _telas.length - 1; i >= 0; i--)
		{
			_telas[i].Encerrar();
			_telas = null;
		}
		
		_batch.flush();
		_batch.dispose();
		Gdx.app.exit();
	}
	
	/**
	 * Fun��o para constru��o de todas as camadas do jogo e suas propriedades.
	 */
	private void ControiCamadas()
	{
		_camadas = new Camada[3];
		_camadas[CatQuest.Camadas.OBJETOS_ESTATICOS.ordinal()] = new Camada(CatQuest.Camadas.OBJETOS_ESTATICOS);
		_camadas[CatQuest.Camadas.PERSONAGENS.ordinal()] = new Camada(CatQuest.Camadas.PERSONAGENS);
		_camadas[CatQuest.Camadas.UI.ordinal()] = new Camada(CatQuest.Camadas.UI);
	}
	
	/**
	 * Grava as informa��es de configura��es no arquivo padr�o.
	 */
	public void GravarConfig()
	{
		Json json = new Json();
		json.setUsePrototypes(false);
		String config = json.toJson(_configuracoes);
		Gdx.files.local("arquivos\\config.data").writeString(config, false);
	}
	
	/**
	 * Carrega as configura��es do usu�rio.
	 */
	public void CarregarConfig()
	{
		if (_configuracoes == null)
			_configuracoes = new Configuracoes();
		
		if (!Gdx.files.local("arquivos\\config.data").exists())
		{
			_configuracoes.SetAudio(true);
			_configuracoes.SetFullScreen(false);
			_configuracoes.SetWidth(1024);
			_configuracoes.SetHeight(768);
			_configuracoes.SetMostraFPS(false);
			_configuracoes.SetVolumeMusica(50);
			_configuracoes.SetVolumeSom(50);
		}
		else
		{
			Json json = new Json();
			String config = Gdx.files.local("arquivos\\config.data").readString();
			_configuracoes = json.fromJson(Configuracoes.class, config);
		}
		
		this.GravarConfig();
		this.AplicarConfiguracoes();
	}
	
	/**
	 * Define as configura��es atuais do jogo. Caso as configura��es ainda n�o tenham sido definidas, chama {@link CatQuest#CarregarConfig()} e depois define.
	 */
	public void AplicarConfiguracoes()
	{
		if (_configuracoes == null)
			this.CarregarConfig();
		
		Gdx.graphics.setDisplayMode(_configuracoes.GetWidth(), _configuracoes.GetHeight(), _configuracoes.GetFullscreen());
	}
	
	/**
	 * Retorna as {@link Configuracoes} do jogo. Repare que caso altere as configura��es, deve chamar {@link #GravarConfig()}.
	 * @return Configura��es do jogo.
	 */
	public Configuracoes GetConfig()
	{
		if (_configuracoes == null)
			this.CarregarConfig();
		
		return _configuracoes;
	}
	
	/**
	 * Define novas configura��es para o jogo e salva em arquivo em seguida.
	 * @param config Novas configura��es para o jogo.
	 */
	public void SetConfig(Configuracoes config)
	{
		_configuracoes = config;
		this.GravarConfig();
	}
	
	/**
	 * Retorna uma referencia para a instacia da camada desejada.
	 * @param camada {@link Camada} a retornar referencia.
	 * @return Referencia para a camada.
	 */
	public Camada GetCamada(Camadas camada)
	{
		return _camadas[camada.ordinal()];
	}
	
	/**
	 * Retorna um novo ID �nico para {@link GameObject}.
	 * @return Um novo ID para GameObject
	 */
	public Integer GetNovoId()
	{
		return new Integer(_idObjeto);
	}
	
	/**
	 * Retorna a SpriteBatch do jogo.
	 * @return Referencia para {@link SpriteBatch} do jogo.
	 */
	public SpriteBatch GetSpriteBatch()
	{
		return _batch;
	}
	
	/**
	 * Adiciona uma tela a pilha de telas do jogo. A �ltima tela adiciona sempre ser� renderizada por �ltimo.
	 * @param tela {@link Tela} a ser adicionada na pilha.
	 */
	public void AdicionaTela(Tela tela)
	{
		_pilhaTelas.add(tela);
		_telas = _pilhaTelas.toArray(_telas);
	}
	
	/**
	 * Retirar a �ltima tela da pilha.
	 */
	public void RetiraTela()
	{
		_pilhaTelas.pop();
		_pilhaTelas.toArray(_telas);
	}
	
	/**
	 * Retorna a {@link Tela} atual do jogo. A �ltima tela da pilha.
	 * @return A tela atual.
	 */
	public Tela GetTelaAtual()
	{
		return _pilhaTelas.lastElement();
	}
	
	/**
	 * Retorna a {@link OrthographicCamera} do jogo.
	 * @return Camera do jogo.
	 */
	public OrthographicCamera GetCamera()
	{
		return _camera;
	}
	
	/**
	 * Retornas todas as {@link Camada} do jogo.
	 * @return Um vetor de {@link Camada}
	 */
	public final Camada[] GetCamadas()
	{
		return _camadas;
	}
	
	/**
	 * Definir uma nova posi��o a {@link OrthographicCamera} do jogo.
	 * @param posicao {@link Vector2} com o x e y do canto superior esquerda da camera.
	 */
	public void SetPosicaoCamera(Vector2 posicao)
	{
		if (posicao != null)
			_camera.translate(posicao);
	}
	
	/**
	 * Retorna a largura da tela.
	 * @return Largura da tela.
	 */
	public float GetLarguraTela()
	{
		return Gdx.graphics.getWidth(); 
	}
	
	/**
	 * Retorna a altura da tela.
	 * @return Altura da tela.
	 */
	public float GetAlturaTela()
	{
		return Gdx.graphics.getHeight(); 
	}
	
	/**
	 * Retorna se est� em full screen.
	 * @return True se est� em full screen.
	 */
	public boolean GetFullScreen()
	{
		return Gdx.graphics.isFullscreen();
	}
	
	/**
	 * Retorna o state time. A soma de todos os {@link com.badlogic.gdx.Graphics#getDeltaTime()} desde o inicio do jogo.
	 * @return Retorna um float com a soma dos deltatimes do jogo.
	 */
	public final float GetStateTime()
	{
		return _stateTime;
	}
	
	/**
	 * Define se o jogo deve rodar a rotina de atualiza��o.
	 * @param atualiza True para rodar a rotina de atualiza��o.
	 */
	public void SetSeAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}
	
	/**
	 * Define se o jogo deve rodar a rotina de desenho.
	 * @param desenha True para rodar a rotina de {@link #Desenha()}.
	 */
	public void SetSeDesenha(boolean desenha)
	{
		_desenha = desenha;
	}
	
	/**
	 * 
	 * @return Modo do jogo.
	 */
	public ModoJogo GetModoJogo()
	{
		return _modoJogo;
	}
	
	/**
	 * Seta um novo modo para o jogo. O jogo � reiniciado ap�s setar.
	 * @param modo Novo modo de jogo.
	 */
	public void SetModoJogo(ModoJogo modo)
	{
		_atualiza = false;
		_desenha = false;
		_modoJogo = modo;
		this.ReiniciaJogo();
	}	
}
