package catquest;

import screen.Introducao;
import util.Camada;
import util.PonteiroDe;
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

/**
 * Classe que cont�m todas as informa��es padr�es do jogo. Quase todos os m�todos e propriedades s�o est�ticos.
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
	
	static int _idObjeto = 0;
	static Tela _telaAtual = null;
	static SpriteBatch _batch = null;
	static PonteiroDe[] _camadas;
	OrthographicCamera _camera = null;
	Texture _chatuba = null;
	
	@Override
	public void create()
	{
		this.ControiCamadas();
		_batch = new SpriteBatch();
		_batch.setColor(Color.WHITE);
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		(_telaAtual = new Introducao()).Iniciar();
		_chatuba = new Texture(Gdx.files.absolute("C:\\Users\\Matheus\\Desktop\\chatuba.jpg"));
	}
	

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
		_camera.update();
		_camera.translate(10*Gdx.graphics.getDeltaTime(), 0);
		_batch.setProjectionMatrix(_camera.combined);
		
		_batch.begin();
		
		_telaAtual.Desenha(_batch);
		
		_batch.end();
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
		_camadas = new PonteiroDe[1];
		_camadas[0] = new PonteiroDe<Camada>();
	}
	
	@SuppressWarnings("unchecked")
	public static PonteiroDe<Camada> GetCamada(Camadas camada)
	{
		return _camadas[camada.ordinal()];
	}
	
	public static Integer GetNovoId()
	{
		return new Integer(_idObjeto);
	}
	
	public static SpriteBatch GetSpriteBatch()
	{
		return _batch;
	}
}
