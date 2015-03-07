package catquest;

import classe.Tela;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classe que cont�m todas as informa��es padr�es do jogo. Quase todos os m�todos e propriedades s�o est�ticos.
 * @author Matheus
 *
 */
public class CatQuest implements ApplicationListener
{
	static int _idObjeto = 0;
	static Tela _telaAtual = null;
	static SpriteBatch _bash = null;
	Camera _camera = null;
	
	@Override
	public void create()
	{
		// TODO Auto-generated method stub
		
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
	
	public static Integer GetNovoId()
	{
		return new Integer(_idObjeto);
	}
	
	public static SpriteBatch GetSpriteBash()
	{
		return _bash;
	}
}
