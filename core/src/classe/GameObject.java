package classe;

import util.Camada;
import util.PonteiroDe;
import catquest.CatQuest;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameObject
{
	public enum TipoGameObject
	{
		
	}
	
	private Texture _texturaObjeto = null;
	private int _tipo = 0;
	private Rectangle _posicaoTela = null;
	private Animation _animacao = null;
	private PonteiroDe<Camada> _camada = null;
	private Integer _id = null;
	
	public GameObject()
	{
		_id = CatQuest.GetNovoId();
		_camada = CatQuest.GetCamada(CatQuest.Camadas.FUNDO);
	}
	
	public GameObject(FileHandle caminhoTextura)
	{
		this();
		_texturaObjeto = new Texture(caminhoTextura);
	}
	
	public GameObject(FileHandle caminhoTextura, CatQuest.Camadas camada)
	{
		this(caminhoTextura);
		_camada = CatQuest.GetCamada(camada);
	}
	
	public void Atualiza(float deltaTime){};
	
	public void Desenha(SpriteBatch bash)
	{
		bash.draw(_texturaObjeto, 0, 0);
	}
	
	public Integer GetId()
	{
		return _id;
	}
	
	public Camada GetCamada()
	{
		return _camada.GetRef();
	}
}
