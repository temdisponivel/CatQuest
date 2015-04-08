//TODO: EDITAR TODO O CÓDIGO PARA QUE FIQUE EM UM SÓ IDIOMA, PADRONIZADO E COM FINAL NOS PARAMETROS ETC. FAZER CONTROLES< TERMINAR SOM

package catquest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

import classes.uteis.Camada;
import classes.uteis.Camada.*;
import classes.uteis.CarregarMusica;
import classes.uteis.CarregarMusicaListner;
import classes.uteis.Configuracoes;
import classes.uteis.Log;
import classes.uteis.Player;
import classes.uteis.Player.TipoPlayer;
import classes.gameobjects.GameObject;
import classes.telas.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Classe que contém todas as informações padrões do jogo. Quase todos os métodos e propriedades são estáticos.
 * @author Matheus
 *
 */
public class CatQuest implements ApplicationListener, OnCompletionListener
{	
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
	private Array<Player> _players = null;
	
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
		try
		{
			this.IniciaJogo(true);
		}
		catch (Exception e)
		{
			Log.Logar("Erro ao iniciar o jogo.", e, true);
		}
	}
	

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void render()
	{
		try
		{
			if (_atualiza)
				this.Atualiza();
	
			if (_desenha)
				this.Desenha();
		}
		catch (Exception e)
		{
			Log.Logar("Erro no loop principal", e, true);
		}
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
			if (_telas[i].GetSeAtualiza())
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
	    	if (_telas[i].GetSeDesenha())
	    		_telas[i].Desenha(_batch);
		}
	    
	    if (_configuracoes.GetMostraFPS())
			_fonte.draw(_batch, new StringBuilder(3).append(Gdx.graphics.getFramesPerSecond()), this.GetLarguraTela()-50, this.GetAlturaTela()-25);
			
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
	 * @throws IOException Quando não for possível criar arquivo de log.
	 */
	private void IniciaJogo(boolean intro) throws IOException
	{
		if (!Log.log.exists())
			Log.log.file().createNewFile();
		
		//CARREGA CONFIGURAÇÕES E APLICA
		this.CarregarConfig();
		this.AplicarConfiguracoes();
		
		//CONTRÓI OS PLAYERS
		this.ControiPlayers();
		
		//CONTRÓI AS CAMADAS DO JOGO
		this.ControiCamadas();
		
		//ATUALIZA E DESENHA COMO TRUE PARA GAMELOOP COMPLETO
		_atualiza = true;
		_desenha = true;
		
		//CARREGA FONTE
		_fonte = new BitmapFont(Gdx.files.local("fonte\\catquest.fnt"));
		
		//CRIA SPRITEBATCH PARA DESENHAR COISAS NA TELA
		_batch = new SpriteBatch();
		
		//CRIA CAMERA ORTOGRAFICA PARA QUE NÃO TENHA DIFERENÇA ENTRE PROFUNDIDADE.
		//CRIA COM O TAMANHO DAS CONFIGURAÇÕES
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, _configuracoes.GetWidth(), _configuracoes.GetHeight());

		//CRIA UM NOVO VETOR DE TELAS - INICIALMENTE COM 1 POSICAO E PREENCHE COM AS TELAS DA PILHA
		//UM VETOR E UMA PILHA PARA QUE NÃO TENHAMOS QUE DAR POP E PUSH TODA HORA. O VETOR É ATUALIZA SEMPRE QUE UMA NOVA TELA
		//É CRIADA OU REMOVIDA
		//_telas = new Tela[1];
		
		//SE FOR PRA COMEÇAR O JOGO DA TELA DE INTRO (DO INICIO), ADICIONA A INTRO NA PILHA
		if (intro)
			this.AdicionaTela(new Introducao(), false, false);
		//SE NÃO, COMEÇA DO MENU (USADO QUANDO REINICIAR O JOGO).
		else
			this.AdicionaTela(new Titulo(), false, false);
	}
	
	/**
	 * Reinicia o jogo do inicio a partir do titulo.
	 */
	public void ReiniciaJogo()
	{
		try
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
		catch (Exception e)
		{
			Log.Logar("Erro ao reiniciar o jogo.", e, true);
		}
	}
	
	/**
	 * Função que fecha o jogo.
	 */
	public void EncerraJogo()
	{
		_atualiza = false;
		_desenha = false;
		
		//ENCERRA TODAS AS TELAS, LIMPA O VETOR E ENCERRA O JOGO
		if (_telas != null)
		{
			for (int i = _telas.length - 1; i >= 0; i--)
			{
				_telas[i].Encerrar();
			}
			
			_telas = null;
		}
		
		Gdx.app.exit();
	}
	
	/**
	 * Função para construção de todas as camadas do jogo e suas propriedades.
	 */
	private void ControiCamadas()
	{
		_camadas = new Camada[3];
		_camadas[Camadas.OBJETOS_ESTATICOS.ordinal()] = new Camada(Camadas.OBJETOS_ESTATICOS);
		_camadas[Camadas.PERSONAGENS.ordinal()] = new Camada(Camadas.PERSONAGENS);
		_camadas[Camadas.UI.ordinal()] = new Camada(Camadas.UI);
	}
	
	/**
	 * Função para construção dos players.
	 */
	private void ControiPlayers()
	{
		if (_players == null)
			_players = new Array<Player>();
			
		_players.add(new Player(Player.TipoPlayer.UM));
		_players.add(new Player(Player.TipoPlayer.DOIS));
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
		
		if (Gdx.files.local("arquivos\\config.data").exists())
		{
			Json json = new Json();
			String config = Gdx.files.local("arquivos\\config.data").readString();
			_configuracoes = json.fromJson(Configuracoes.class, config);
		}
		
		this.GravarConfig();
		this.AplicarConfiguracoes();
	}
	
	/**
	 * Define as configurações atuais do jogo. Caso as configurações ainda não tenham sido definidas, chama {@link CatQuest#CarregarConfig()} e depois define.
	 */
	public void AplicarConfiguracoes()
	{
		if (_configuracoes == null)
			this.CarregarConfig();
		
		Gdx.graphics.setDisplayMode(_configuracoes.GetWidth(), _configuracoes.GetHeight(), _configuracoes.GetFullscreen());
		
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
	 * Retorna um novo ID único para {@link GameObject}.
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
	 * Adiciona uma tela a pilha de telas do jogo. A nova tela será a última a ser atualizada/desenhada.
	 * @param tela {@link Tela} a ser adicionada na pilha.
	 * @param atualizaAntiga Se true, a tela atualmente no topo da pilha continuará sendo atualizada.
	 * @param desenhaAntiga Se true, a tela atualmente no topo da pilha continuará sendo desenhada.
	 * @see {@link Tela#Atualiza(float)}
	 * @see {@link Tela#Desenha(SpriteBatch)}
	 */
	@SuppressWarnings("javadoc")
	public void AdicionaTela(Tela tela, boolean atualizaAntiga, boolean desenhaAntiga)
	{
		if (_pilhaTelas == null)
			_pilhaTelas = new Stack<Tela>();
		
		if (_telas == null)
			_telas = new Tela[0];
		
		if (!_pilhaTelas.isEmpty())
		{
			_pilhaTelas.lastElement().SetSeAtualiza(atualizaAntiga);
			_pilhaTelas.lastElement().SetSeDesenha(desenhaAntiga);
		}
		
		_pilhaTelas.add(tela);
		
		_telas = _pilhaTelas.toArray(_telas);
		
		tela.Iniciar();
	}
	
	/**
	 * Retirar a última tela da pilha. A tela que está abaixo será definida como ativa.
	 * @see {@link Tela#SetAtiva(boolean)}
	 */
	@SuppressWarnings("javadoc")
	public void RetiraTela()
	{
		_pilhaTelas.pop();
		_pilhaTelas.lastElement().SetAtiva(true);
		_pilhaTelas.toArray(_telas);
	}
	
	/**
	 * Retorna a {@link Tela} atual do jogo. A última tela da pilha.
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
	 * Definir uma nova posição a {@link OrthographicCamera} do jogo.
	 * @param posicao {@link Vector2} com o x e y do canto superior esquerda da camera.
	 */
	public void SetPosicaoCamera(Vector2 posicao)
	{
		if (posicao != null)
			_camera.translate(posicao);
	}
	
	/**
	 * Cria uma nova {@link Music} com as configurações do jogo. Carrega a música iniciando tocando, sem loop, a partir de 0 segundo.
	 * Quando a música chegar ao fim, ela será liberada da memória.
	 * @param arquivo Arquivo da música.
	 */
	public void CriarNovaMusica(FileHandle arquivo)
	{
		if (arquivo.exists())
			new CarregarMusica(arquivo, 0, false, true, this, null).run();
		else
			Log.Logar("Não foi possível encontrar o arquivo: " + arquivo.path(), new FileNotFoundException("Arquivo não encontrado."), true);
	}
	
	/**
	 * Cria uma nova {@link Music} com as configurações do jogo. Carrega a música iniciando tocando, sem loop, a partir de 0 segundo.
	 * Quando a música chegar ao fim, ela será liberada da memória. Portanto, não mexer na referencia.
	 * @param arquivo Arquivo da música.
	 * @param listener Listener que será chamado ao completar o carregamento da música na memória. Pode ser null.
	 */
	public void CriarNovaMusica(FileHandle arquivo, CarregarMusicaListner listener)
	{
		if (arquivo.exists())
			new CarregarMusica(arquivo, 0, false, true, this, listener).run();
		else
			Log.Logar("Não foi possível encontrar o arquivo: " + arquivo.path(), new FileNotFoundException("Arquivo não encontrado."), true);
	}
	
	/**
	 * Cria uma nova {@link Music} com as configurações do jogo.
	 * Quando a música chegar ao fim, ela será liberada da memória. Portanto, não mexer na referencia.
	 * @param arquivo {@link FileHandle} contendo o arquivo da música.
	 * @param posicaoLoop Posição que a música deve começar a tocar.
	 * @param isLooping Se a música deve ficar em loop.
	 * @param isPlaing Se a música já deve ser tocada após criar.
	 * @param listener Listener que será chamado ao completar o carregamento da música na memória. Pode ser null. Pode ser null.
	 */
	public void CriarNovaMusica(FileHandle arquivo, float posicaoLoop, boolean isLooping, boolean isPlaing, CarregarMusicaListner listener)
	{
		if (arquivo.exists())
			new CarregarMusica(arquivo, posicaoLoop, isLooping, isPlaing, this, listener).run();
		else
			Log.Logar("Não foi possível encontrar o arquivo: " + arquivo.path(), new FileNotFoundException("Arquivo não encontrado."), true);
	}
	
	/**
	 * Cria uma nova {@link Music} com as configurações do jogo.
	 * Quando a música chegar ao fim, será chamado o listener {@link OnCompletionListener}. Caso seja nulo, a música será liberada da memória.
	 * @param arquivo {@link FileHandle} contendo o arquivo da música.
	 * @param posicaoLoop Posição que a música deve começar a tocar.
	 * @param isLooping Se a música deve ficar em loop.
	 * @param isPlaing Se a música já deve ser tocada após criar.
	 * @param listener Listener que será chamado ao completar o carregamento da música na memória. Pode ser null.
	 * @param listenerFimMusica Listener que será chamado ao término da música. Pode ser null.
	 */
	public void CriarNovaMusica(FileHandle arquivo, float posicaoLoop, boolean isLooping, boolean isPlaing, OnCompletionListener listenerFimMusica, CarregarMusicaListner listener)
	{
		if (arquivo.exists())
			new CarregarMusica(arquivo, posicaoLoop, isLooping, isPlaing, listenerFimMusica != null ? listenerFimMusica : this, listener).run();
		else
			Log.Logar("Não foi possível encontrar o arquivo: " + arquivo.path(), new FileNotFoundException("Arquivo não encontrado."), true);
	}
	
	/**
	 * Cria um novo {@link Sound som}. Quando não for mais utilizar, chamar {@link Sound#dispose()}.
	 * Ao tocar os sons, sempre utilize o volume: {@link Configuracoes#GetVolumeSom()}/100.
	 * @param arquivo Arquivo de som.
	 * @return Novo som ou nulo caso não encontre o arquivo (caso isso ocorra, o jogo será encerrado em seguida).
	 */
	public Sound CriarNovoSom(FileHandle arquivo)
	{
		if (arquivo.exists())
			return Gdx.audio.newSound(arquivo);
		else
		{
			Log.Logar("Não foi possível encontrar o arquivo: " + arquivo.path(), new FileNotFoundException("Arquivo não encontrado."), true);
			return null;
		}
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
	 * @param desenha True para rodar a rotina de desenha do gameloop.
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

	@Override
	public void onCompletion(Music music)
	{
		music.dispose();
	}
	
	/**
	 * Retorna a instancia do player desejado.
	 * @return {@link Player} que representa o player.
	 */
	public Player GetPlayer(TipoPlayer tipo)
	{
		return _players.get(tipo.ordinal());
	}
}
