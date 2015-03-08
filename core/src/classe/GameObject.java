package classe;

import util.Camada;
import catquest.CatQuest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public abstract class GameObject
{
	public enum TipoGameObject
	{
		
	}
	
	static private Texture _texturaObjeto = null;
	static private Array<TextureRegion> _regiaoTextura = null;
	static private int _tipo = 0;
	private Rectangle _posicaoTela = null;
	private Animation _animacao = null;
	private Camada _camada = null;
	private Integer _id = null;
	
	public GameObject()
	{
		_id = CatQuest.GetNovoId();
	}
	
	public abstract void Atualiza(float deltaTime);
	public abstract void Desenha(SpriteBatch bash);
	
	public Integer GetId()
	{
		return _id;
	}
	
	public Camada GetCamada()
	{
		return _camada;
	}
}
