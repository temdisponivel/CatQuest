package classe;

import util.Camada;
import util.PonteiroDe;
import catquest.CatQuest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class GameObject
{
	public enum TipoGameObject
	{
		INIMIGO,
		HEROI,
		CENARIO,
	}
	
	private Sprite _texturaObjeto = null;
	private TipoGameObject _tipo;
	private Vector2 _posicaoTela = null;
	private Animation _animacao = null;
	private Camada _camada = null;
	private Integer _id = null;
	private Tela _telaInserido = null;
	
	public GameObject()
	{
		_id = CatQuest.instancia.GetNovoId();
		_camada = CatQuest.instancia.GetCamada(CatQuest.Camadas.FUNDO);
		_posicaoTela = new Vector2();
		_tipo = TipoGameObject.HEROI;
	}
	
	public GameObject(FileHandle caminhoTextura)
	{
		this();
		_texturaObjeto = new Sprite(new Texture(caminhoTextura));
		_posicaoTela.x = CatQuest.instancia.GetTamanhoTela().x;
		_posicaoTela.y = CatQuest.instancia.GetTamanhoTela().y;
	}
	
	public GameObject(FileHandle caminhoTextura, CatQuest.Camadas camada)
	{
		this(caminhoTextura);
		_camada = CatQuest.instancia.GetCamada(camada);
	}
	
	public abstract void Atualiza(float deltaTime);
	
	public void Desenha(SpriteBatch bash)
	{
		bash.draw(_texturaObjeto, 0, 0);
	}
	
	public abstract <T extends GameObject> void AoColidir(T colidiu);
	
	public final Integer GetId()
	{
		return _id;
	}
	
	public final Camada GetCamada()
	{
		return _camada;
	}
	
	public final Vector2 GetPosicao()
	{
		return _posicaoTela;
	}
	
	public void SetPosicao(Vector2 posicao)
	{
		_posicaoTela = posicao;
	}
	
	public final Sprite GetSprite()
	{
		return new Sprite(_animacao.getKeyFrame(CatQuest.instancia.GetStateTime()));
	}
	
	public final Tela GetTela()
	{
		return _telaInserido;
	}
	
	public void SetTela(Tela tela)
	{
		_telaInserido = tela;
	}
}

