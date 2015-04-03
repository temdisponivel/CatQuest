package catquest;

import java.util.Stack;
import java.util.function.Consumer;

import screen.Introducao;
import sun.misc.GC;
import util.Camada;
import util.PonteiroDe;
import classe.GameObject;
import classe.Tela;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe que contém todas as informações padrões do jogo. Quase todos os métodos e propriedades são estáticos.
 * @author Matheus
 *
 */
public class CatQuest implements ApplicationListener
{
	public enum Camadas
	{
		FUNDO,
		PERSONAGENS,
	};
	
	public enum ModoJogo
	{
		COOP,
		SINGLE,
	};
	
	static public CatQuest instancia;
	int _idObjeto = 0;
	Stack<Tela> _pilhaTelas = null;
	Tela[] _telas = null;
	SpriteBatch _batch = null;
	Camada[] _camadas = null;
	OrthographicCamera _camera = null;
	float _stateTime = 0;
	
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
		/* ------------------ ATUALIZA ------------------- */
		
		for (int i = _telas.length - 1; i >= 0; i--)
		{
			_telas[i].Atualiza(Gdx.graphics.getDeltaTime());
		}
		
		_stateTime += Gdx.graphics.getDeltaTime();
		
		//ATUALIZA CAMERA E SETA AS MATRIZES DA CAMERA NO BATCH
		_camera.update();
		_batch.setProjectionMatrix(_camera.combined);
		
		/* ---------------- FIM ATUALIZA --------------------*/
	
		/* ---------------- DESENHA -----------------------*/
		
		//LIMPA TELA
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
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
	
	private void ControiCamadas()
	{
		_camadas = new Camada[1];
		_camadas[0] = new Camada();
	}
	
	public Camada GetCamada(Camadas camada)
	{
		return _camadas[camada.ordinal()];
	}
	
	public Integer GetNovoId()
	{
		return new Integer(_idObjeto);
	}
	
	public SpriteBatch GetSpriteBatch()
	{
		return _batch;
	}
	
	public void AdicionaTela(Tela tela)
	{
		_pilhaTelas.add(tela);
		_pilhaTelas.toArray(_telas);
	}
	
	public void RetiraTela()
	{
		_pilhaTelas.pop();
		_pilhaTelas.toArray(_telas);
	}
	
	public Tela GetTelaAtual()
	{
		return _pilhaTelas.lastElement();
	}
	
	public OrthographicCamera GetCamera()
	{
		return _camera;
	}
	
	public final Camada[] GetCamadas()
	{
		return _camadas;
	}
	
	public void SetPosicaoCamera(Vector2 posicao)
	{
		if (posicao != null)
			_camera.translate(posicao);
	}
	
	public final Vector2 GetTamanhoTela()
	{
		return new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); 
	}
	
	public final float GetStateTime()
	{
		return _stateTime;
	}
	
	public void PosicionaGameObject(Vector2 posicaoRelativaTextura, Vector2 posicaoRelativaTela, GameObject gameObjectCentralizar)
	{
		posicaoRelativaTextura.x = gameObjectCentralizar.GetSprite().getWidth() - posicaoRelativaTela.x;
		posicaoRelativaTextura.y = gameObjectCentralizar.GetSprite().getHeight() - posicaoRelativaTela.y;
		
		posicaoRelativaTela.x = Gdx.graphics.getWidth() - posicaoRelativaTela.x;
		posicaoRelativaTela.y = Gdx.graphics.getHeight() - posicaoRelativaTela.y;
		
		gameObjectCentralizar.GetPosicao().x = posicaoRelativaTela.x - posicaoRelativaTextura.x;
		gameObjectCentralizar.GetPosicao().y = posicaoRelativaTela.y - posicaoRelativaTextura.y;
	}
}
