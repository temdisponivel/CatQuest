package catquest;

import screen.Titulo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Classe que cont�m todas as informa��es padr�es do jogo. Quase todos os m�todos e propriedades s�o est�ticos.
 * @author Matheus
 *
 */
public class CatQuest extends Game
{
	static private SpriteBatch _spriteBatch;
	static private long _deltaTime;
	
	@Override
	public void create()
	{
		_spriteBatch = new SpriteBatch();
		screen = new Titulo(this);
		_deltaTime = 0;
	}

	@Override
	public void render()
	{
		screen.render(_deltaTime);
	}
}
