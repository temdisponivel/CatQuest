package classes.gameobjects;

import java.util.ArrayList;

import classes.uteis.Camada;
import classes.telas.Tela;
import catquest.CatQuest;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject
{
	public enum TipoGameObject
	{
		INIMIGO,
		HEROI,
		CENARIO,
	}
	
	private Sprite _sprite = null;
	private TipoGameObject _tipo;
	private Vector2 _posicaoTela = null;
	private Animation _animacao = null;
	private Camada _camada = null;
	private Integer _id = null;
	private Tela _telaInserido = null;
	private boolean _ativo = true;
	private ArrayList<GameObject> _colidiveis = null;
	
	public void Atualiza(float deltaTime)
	{
		if (!_ativo)
			return;
		
		_sprite = (Sprite) _animacao.getKeyFrame(CatQuest.instancia.GetStateTime());
	}
	
	public void Desenha(SpriteBatch bash)
	{
		if (_ativo)
			bash.draw(_sprite, _posicaoTela.x, _posicaoTela.y);
	}
	
	public abstract <T extends GameObject> void AoColidir(T colidiu);
	public abstract void Inicia();
	
	public void Redefine()
	{
		this.Inicia();
	}
	
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
	
	public boolean GetAtivo()
	{
		return _ativo;
	}
	
	public void SetAtivo(boolean ativo)
	{
		_ativo = ativo;
	}
	
	public TipoGameObject GetTipo()
	{
		return _tipo;
	}
	
	public boolean ValidaColisao(GameObject colidiu)
	{
		if (colidiu == null || _colidiveis.contains(colidiu.GetTipo()))
			return false;
		
		if (this._sprite.getBoundingRectangle().overlaps(colidiu.GetSprite().getBoundingRectangle()))
		{
			this.AoColidir(colidiu);
			colidiu.AoColidir(this);
			return true;
		}
		
		return false;
	}
}

