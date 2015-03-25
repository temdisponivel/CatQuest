package catquest;

import util.Camada;
import util.PonteiroDe;
import classe.Tela;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	
	static int _idObjeto = 0;
	static Tela _telaAtual = null;
	static SpriteBatch _batch = null;
	static PonteiroDe[] _camadas;
	Camera _camera = null;
	
	@Override
	public void create()
	{
		this.ControiCamadas();
		_batch.setColor(Color.BLACK);
	}
	

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void render()
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
