package catquest;

import java.util.Stack;

import classes.uteis.Camada;
import classes.uteis.Configuracoes;
import classes.telas.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

/**
 * Classe que contém todas as informações padrões do jogo. Quase todos os métodos e propriedades são estáticos.
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
	static public enum Camadas
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
	static public enum ModoJogo
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
	 * Função que roda a lógica de atualização do jogo. AI, colições, mudança de posições, etc; são gerenciadas aqui.
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
	 * Função que roda a lógica de desenho do jogo.
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
		_batch.end();
		
		/* ----------------- FIM DESENHA  --------------------*/
	}
	
	/**
	 * Trata toda a interação com o usuário.
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
	 * Inicia o jogo. Roda toda a rotina de iniciar as propriedades, chamas as primeiras funções, carregar telas, etc.
	 */
	private void IniciaJogo(boolean intro)
	{
		this.CarregarConfig();
		Gdx.graphics.setDisplayMode(_configuracoes.GetWidth(), _configuracoes.GetHeight(), _configuracoes.GetFullscreen());
		
		this.ControiCamadas();
		_atualiza = true;
		_desenha = true;
		_batch = new SpriteBatch();
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		_pilhaTelas = new Stack<Tela>();
		
		if (intro)
			_pilhaTelas.add(new Introducao());
		else
			_pilhaTelas.add(new Titulo());
		
		_pilhaTelas.lastElement().Iniciar();
		_telas = new Tela[1];
		_telas = _pilhaTelas.toArray(_telas);
	}
	
	/**
	 * Reinicia o jogo do inicio a partir do titulo.
	 */
	public void ReiniciaJogo()
	{
		_batch.dispose();

		for (int i = _telas.length - 1; i >= 0; i--)
		{
			_telas[i].Encerrar();
			_telas = null;
		}
		
		_pilhaTelas.clear();
		
		this.IniciaJogo(false);
	}
	
	/**
	 * Função que fecha o jogo.
	 */
	public void EncerraJogo()
	{
		_atualiza = false;
		_desenha = false;
		
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
	 * Função para construção de todas as camadas do jogo e suas propriedades.
	 */
	private void ControiCamadas()
	{
		_camadas = new Camada[3];
		_camadas[CatQuest.Camadas.OBJETOS_ESTATICOS.ordinal()] = new Camada(CatQuest.Camadas.OBJETOS_ESTATICOS);
		_camadas[CatQuest.Camadas.PERSONAGENS.ordinal()] = new Camada(CatQuest.Camadas.PERSONAGENS);
		_camadas[CatQuest.Camadas.UI.ordinal()] = new Camada(CatQuest.Camadas.UI);
	}
	
	/**
	 * Grava as informações de configurações no arquivo padrão.
	 */
	public void GravarConfig()
	{
		Json json = new Json();
		json.setUsePrototypes(false);
		String config = json.toJson(_configuracoes);
		Gdx.files.local("arquivos\\config.data").writeString(config, false);
	}
	
	/**
	 * Carrega as configurações do usuário.
	 */
	public void CarregarConfig()
	{
		if (_configuracoes == null)
			_configuracoes = new Configuracoes();
		
		if (!Gdx.files.local("arquivos\\config.data").exists())
		{
			_configuracoes.SetAudio(true);
			_configuracoes.SetFullScreen(false);
			_configuracoes.SetHeight(768);
			_configuracoes.SetWidth(1024);
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
	}
	
	/**
	 * Retorna as {@link Configuracoes} do jogo. Repare que caso altere as configurações, deve chamar {@link #GravarConfig()}.
	 * @return Configurações do jogo.
	 */
	public Configuracoes GetConfig()
	{
		if (_configuracoes == null)
			this.CarregarConfig();
		
		return _configuracoes;
	}
	
	/**
	 * Define novas configurações para o jogo e salva em arquivo em seguida.
	 * @param config Novas configurações para o jogo.
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
	 * Retorna um novo ID único para gameobjects.
	 * @return
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
	 * Adiciona uma tela a pilha de telas do jogo. A última tela adiciona sempre será renderizada por último.
	 * @param tela {@link Tela} a ser adicionada na pilha.
	 */
	public void AdicionaTela(Tela tela)
	{
		_pilhaTelas.add(tela);
		_telas = _pilhaTelas.toArray(_telas);
	}
	
	/**
	 * Retirar a última tela da pilha.
	 */
	public void RetiraTela()
	{
		_pilhaTelas.pop();
		_pilhaTelas.toArray(_telas);
	}
	
	/**
	 * Retorna a {@link Tela} atual do jogo. A última tela da pilha.
	 * @return
	 */
	public Tela GetTelaAtual()
	{
		return _pilhaTelas.lastElement();
	}
	
	/**
	 * Retorna a {@link OrthographicCamera} do jogo.
	 * @return
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
	 * Definir uma nova posição a {@link OrthographicCamera} do jogo.
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
	 * Retorna se está em full screen.
	 * @return True se está em full screen.
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
	 * Define se o jogo deve rodar a rotina de atualização.
	 * @param atualiza True para rodar a rotina de atualização.
	 */
	public void SetSeAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}
	
	/**
	 * Define se o jogo deve rodar a rotina de desenho.
	 * @param atualiza True para rodar a rotina de desenho.
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
	 * Seta um novo modo para o jogo. O jogo é reiniciado após setar.
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
