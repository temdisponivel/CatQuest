//TODO: EDITAR TODO O Cï¿½DIGO PARA QUE FIQUE EM UM Sï¿½ IDIOMA, PADRONIZADO E COM FINAL NOS PARAMETROS ETC. FAZER UI's. FAZER MAPAS, TROCAR TODOS FOREACH POR FOR
//TODO: viewport
package catquest;

import java.io.IOException;
import java.util.Stack;
import classes.uteis.CarregarMusica;
import classes.uteis.CarregarSom;
import classes.uteis.Configuracoes;
import classes.uteis.Log;
import classes.uteis.Player;
import classes.gameobjects.GameObject;
import classes.telas.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe que contï¿½m todas as informaï¿½ï¿½es padrï¿½es do jogo. Quase todos os mï¿½todos e propriedades sï¿½o estï¿½ticos.
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
	private SpriteBatch _batch = null;
	private OrthographicCamera _camera = null;
	private float _tempoJogo = 0;
	private ModoJogo _modoJogo = ModoJogo.SINGLE;
	private boolean _atualiza = true, _desenha = true;
	private BitmapFont _fonte = null;
	private Color _corJogo = null;
	private TextureAtlas _textureAtlas = null;
	private boolean _trocaTela = false, _removeTela = false;
	private Tela _proximaTela = null;
	private boolean _encerrar = false;
	
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
			Log.instancia.Logar("Erro ao iniciar o jogo.", e, true);
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
			if (_encerrar)
				this.Encerrar();
			
			if (_atualiza)
				this.Atualiza();
	
			if (_desenha)
				this.Desenha();
		}
		catch (Exception e)
		{
			Log.instancia.Logar("Erro no loop principal", e, true);
		}
	}
	
	/**
	 * Funï¿½ï¿½o que roda a lï¿½gica de atualizaï¿½ï¿½o do jogo. AI, coliï¿½ï¿½es, mudanï¿½a de posiï¿½ï¿½es, etc; sï¿½o gerenciadas aqui.
	 */
	private void Atualiza()
	{
		this.GerenciaTelas();
		
		for (Tela tela : _pilhaTelas)
		{
			if (tela.GetSeAtualiza())
				tela.Atualiza(Gdx.graphics.getDeltaTime());
		}
		
		_tempoJogo += Gdx.graphics.getDeltaTime();
		
		//ATUALIZA CAMERA E SETA AS MATRIZES DA CAMERA NO BATCH
		_camera.update();
		_batch.setProjectionMatrix(_camera.combined);
		
		/* ---------------- FIM ATUALIZA --------------------*/
	}
	
	/**
	 * Funï¿½ï¿½o que roda a lï¿½gica de desenho do jogo.
	 */
	private void Desenha()
	{
		/* ---------------- DESENHA -----------------------*/
		
		//LIMPA TELA
		Gdx.gl.glClearColor(_pilhaTelas.lastElement().GetCorFundo().r, _pilhaTelas.lastElement().GetCorFundo().g, _pilhaTelas.lastElement().GetCorFundo().b, 1f);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	    //DESENHA
	    _batch.setColor(Color.WHITE);
	    
	    _batch.begin();
	    for (Tela tela : _pilhaTelas)
		{
	    	if (tela.GetSeDesenha())
	    		tela.Desenha(_batch);
		}
	    
	    if (Configuracoes.instancia.GetMostraFPS())
	    {
			_fonte.draw(_batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), this.GetLarguraTela()-50, this.GetAlturaTela()-25);
			_fonte.draw(_batch, String.valueOf(Gdx.graphics.getDeltaTime()), this.GetLarguraTela()-300, this.GetAlturaTela()-25);
	    }
			
		_batch.end();
		
		/* ----------------- FIM DESENHA  --------------------*/
	}

	@Override
	public void pause()	{}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}
	
	/**
	 * Inicia o jogo. Roda toda a rotina de iniciar as propriedades, chamas as primeiras funï¿½ï¿½es, carregar telas, etc.
	 * @throws IOException Quando nï¿½o for possï¿½vel criar arquivo de log.
	 */
	private void IniciaJogo(boolean intro) throws IOException
	{
		//INICIA SINGLETON do LOG
		new Log();
		
		//CARREGA CONFIGURAï¿½ï¿½ES E APLICA
		new Configuracoes();
		this.AplicarConfiguracoes();
		
		//Inicia os singletons dos carregadores de música e som
		new CarregarMusica();
		new CarregarSom();
		
		//CONTRï¿½I OS PLAYERS
		Player.ControiPlayers();
		
		//ATUALIZA E DESENHA COMO TRUE PARA GAMELOOP COMPLETO
		_atualiza = true;
		_desenha = true;
		
		//CARREGA FONTE
		_fonte = new BitmapFont(Gdx.files.local("fonte/catquest.fnt"));
		
		//CRIA SPRITEBATCH PARA DESENHAR COISAS NA TELA
		_batch = new SpriteBatch();
		
		//CRIA A COR PADRAO PARA OS OBJETOS DO JOGO
		_corJogo = new Color(1, 0.8f, 0.8f, 1); //ROSINHA: RGB: 255, 204, 204, 255. Conversï¿½o via 1/255*quantidadeRGB
		
		//Controi o texture atlas
		_textureAtlas = new TextureAtlas(Gdx.files.local("pack/CatQuest.atlas"));
		
		//CRIA CAMERA ORTOGRAFICA PARA QUE Nï¿½O TENHA DIFERENï¿½A ENTRE PROFUNDIDADE.
		//CRIA COM O TAMANHO DAS CONFIGURAï¿½ï¿½ES
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, Configuracoes.instancia.GetWidth(), Configuracoes.instancia.GetHeight());
		
		//SE FOR PRA COMEï¿½AR O JOGO DA TELA DE INTRO (DO INICIO), ADICIONA A INTRO NA PILHA
		if (intro)
			this.AdicionaTela(new Introducao(), false, false);
		//SE Nï¿½O, COMEï¿½A DO MENU (USADO QUANDO REINICIAR O JOGO).
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
			for (Tela tela : _pilhaTelas)
			{
				tela.Encerrar();
				tela = null;
			}
			
			_pilhaTelas.clear();
			
			this.IniciaJogo(false);
		}
		catch (Exception e)
		{
			Log.instancia.Logar("Erro ao reiniciar o jogo.", e, true);
		}
	}
	
	/**
	 * Funï¿½ï¿½o que fecha o jogo.
	 */
	public void EncerraJogo()
	{
		_encerrar = true;
	}
	
	/**
	 * Funï¿½ï¿½o que fecha o jogo.
	 */
	private void Encerrar()
	{
		_atualiza = false;
		_desenha = false;
		
		//ENCERRA TODAS AS TELAS, LIMPA O VETOR E ENCERRA O JOGO
		if (_pilhaTelas != null)
		{
			for (Tela tela : _pilhaTelas)
			{
				tela.Encerrar();
			}
			
			_pilhaTelas = null;
		}
		
		Gdx.app.exit();
	}
	
	/**
	 * Define as configuraï¿½ï¿½es atuais do jogo. Caso as configuraï¿½ï¿½es ainda nï¿½o tenham sido definidas, chama {@link CatQuest#CarregarConfig()} e depois define.
	 */
	public void AplicarConfiguracoes()
	{		
		Gdx.graphics.setDisplayMode(Configuracoes.instancia.GetWidth(), Configuracoes.instancia.GetHeight(), Configuracoes.instancia.GetFullscreen());
	}
	
	/**
	 * Retorna um novo ID ï¿½nico para {@link GameObject}.
	 * @return Um novo ID para GameObject
	 */
	public Integer GetNovoId()
	{
		return new Integer(_idObjeto++);
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
	 * Adiciona uma tela a pilha de telas do jogo. A nova tela serï¿½ a ï¿½ltima a ser atualizada/desenhada.
	 * @param tela {@link Tela} a ser adicionada na pilha.
	 * @param atualizaAntiga Se true, a tela atualmente no topo da pilha continuarï¿½ sendo atualizada.
	 * @param desenhaAntiga Se true, a tela atualmente no topo da pilha continuarï¿½ sendo desenhada.
	 * @see {@link Tela#Atualiza(float)}
	 * @see {@link Tela#Desenha(SpriteBatch)}
	 */
	public void AdicionaTela(Tela tela, boolean atualizaAntiga, boolean desenhaAntiga)
	{
		if (_pilhaTelas == null)
			_pilhaTelas = new Stack<Tela>();
		
		if (!_pilhaTelas.isEmpty())
		{
			_pilhaTelas.lastElement().SetSeAtualiza(atualizaAntiga);
			_pilhaTelas.lastElement().SetSeDesenha(desenhaAntiga);
		}
		
		//_pilhaTelas.add(tela);
		_proximaTela = tela;
		_trocaTela = true;
		
		tela.Iniciar();
	}
	
	/**
	 * Retirar a ï¿½ltima tela da pilha. A tela que estï¿½ abaixo serï¿½ definida como ativa.
	 * @see {@link Tela#SetAtiva(boolean)}
	 */
	public void RetiraTela()
	{
		_removeTela = true;
	}
	
	/**
	 * Adiciona uma tela ou remove caso haja alteraï¿½ï¿½es na pilha ainda nï¿½o feitas.
	 */
	private void GerenciaTelas()
	{
		if (_removeTela)
		{
			_pilhaTelas.pop();
			
			if (!_pilhaTelas.isEmpty())
				_pilhaTelas.lastElement().SetAtiva(true);
			
			_removeTela = false;
		}
		
		if (_trocaTela)
		{
			if (_proximaTela != null)
			{
				_pilhaTelas.add(_proximaTela);
				_trocaTela = false;
			}
		}
	}
	
	/**
	 * Retorna a {@link Tela} atual do jogo. A ï¿½ltima tela da pilha.
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
	 * Definir uma nova posiï¿½ï¿½o a {@link OrthographicCamera} do jogo.
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
	 * Retorna se estï¿½ em full screen.
	 * @return True se estï¿½ em full screen.
	 */
	public boolean GetFullScreen()
	{
		return Gdx.graphics.isFullscreen();
	}
	
	/**
	 * Retorna o state time. A soma de todos os {@link com.badlogic.gdx.Graphics#getDeltaTime()} desde o inicio do jogo.
	 * @return Retorna um float com a soma dos deltatimes do jogo.
	 */
	public final float GetTempoJogo()
	{
		return _tempoJogo;
	}
	
	/**
	 * Define se o jogo deve rodar a rotina de atualizaï¿½ï¿½o.
	 * @param atualiza True para rodar a rotina de atualizaï¿½ï¿½o.
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
	 * Seta um novo modo para o jogo. O jogo ï¿½ reiniciado apï¿½s setar.
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
	 * Retorna a fonte do jogo.
	 * @return {@link BitmapFont} com a fonte do jogo.
	 */
	public BitmapFont GetFonte()
	{
		return _fonte;
	}
	
	/**
	 * @return {@link Color Cor} padrï¿½o para textos e objetos do jogo.
	 */
	public Color GetCor()
	{
		return _corJogo;
	}
	
	/**
	 * Funï¿½ï¿½o que retorna a {@link TextureRegion} desejada.
	 * @param caminho 
	 * 		Caminho do arquivo fï¿½sico da textura, sem a extenï¿½ï¿½o do arquivo e sem a pasta raiz. Exemplo: arquivo//imagens//imagem.png (a partir da pasta raiz da plataforma) - parametrizar como: imagens//imagem
	 * @return Retorna uma nova {@link TextureRegion} com a imagem desejada.
	 * @see {@link TextureAtlas#findRegion(String)}
	 */
	public TextureRegion GetTextura(String caminho)
	{
		return _textureAtlas.findRegion(caminho);
	}
	
	/**
	 * @param texto Texto para calcular o tamanho.
	 * @param bordas Tamanho da borda entre o texto e o fim do retangulo.
	 * @return {@link Rectangle Retangulo} com o tamanho do texto mais as bordas.
	 */
	public Rectangle GetTamanhoTexto(String texto, int bordas)
	{
		float largura = _fonte.getBounds(texto).width + bordas;
		float altura =  _fonte.getBounds(texto).height + bordas;
		
		return new Rectangle(0, 0, largura, altura);
	}
	
	/**
	 * @return Escala para redimencionar as sprites.
	 */
	public float GetEscala()
	{
		return GetLarguraTela() / GetAlturaTela();
	}
	
	/**
	 * Funï¿½ï¿½o que retorna a {@link TextureRegion} desejada.
	 * @param caminho {@link FileHandle Caminho} do arquivo fï¿½sico da textura.
	 * @return Retorna uma nova {@link TextureRegion} com a imagem desejada.
	 */
	public TextureRegion GetTextura(FileHandle caminho)
	{
		return this.GetTextura(caminho.path());
	}
}
