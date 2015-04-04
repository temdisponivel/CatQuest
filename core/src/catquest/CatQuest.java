package catquest;

import java.util.Stack;

import classes.uteis.Camada;
import classes.telas.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

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
	public enum Camadas
	{
		FUNDO,
		PERSONAGENS,
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
	
	//singleton
	static public CatQuest instancia;
	
	//propriedades do jogo
	int _idObjeto = 0;
	Stack<Tela> _pilhaTelas = null;
	Tela[] _telas = null;
	SpriteBatch _batch = null;
	Camada[] _camadas = null;
	OrthographicCamera _camera = null;
	float _stateTime = 0;
	boolean _atualiza = true, _desenha = true;
	
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
		this.ControiCamadas();
		_batch = new SpriteBatch();
		_batch.setColor(Color.WHITE);
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		(_pilhaTelas = new Stack<Tela>()).add(new Introducao());
		_pilhaTelas.lastElement().Iniciar();
		_telas = new Tela[1];
		_telas = _pilhaTelas.toArray(_telas);
	}
	

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void render()
	{
		if (_atualiza)
			Atualiza();
		
		if (_desenha)
			Desenha();
	}
	
	/**
	 * Função que roda a lógica de atualização do jogo. AI, colições, mudança de posições, etc; são gerenciadas aqui.
	 */
	private void Atualiza()
	{
		/* ------------------ ATUALIZA ------------------- */
		TrataEntradaUsuario();
		
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
	    _batch.begin();
	    for (int i = _telas.length - 1; i >= 0; i--)
		{
			_telas[i].Desenha(_batch);
		}
		_batch.end();
		
		/* ----------------- FIM DESENHA  --------------------*/
	}
	
	private void TrataEntradaUsuario()
	{
		
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Função para construção de todas as camadas do jogo e suas propriedades.
	 */
	private void ControiCamadas()
	{
		_camadas = new Camada[1];
		_camadas[0] = new Camada();
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
	 * Retorna um {@link Vector2} com o tamanho da tela. X = Width e Y = Height.
	 * @return {@link Vector2} com o tamanho da tela.
	 */
	public final Vector2 GetTamanhoTela()
	{
		return new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); 
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
}
