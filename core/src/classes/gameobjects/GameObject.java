package classes.gameobjects;

import java.util.HashSet;

import classes.uteis.Camada;
import classes.telas.Tela;
import catquest.CatQuest;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Classe base para todos os objetos do que aparecem no jogo.
 * @author Matheus
 *
 */
public abstract class GameObject implements Poolable
{
	/**
	 * Enumerador para os tipos de GameObject. Utilizado para evitar casts dinamicos e lista de colidiveis.
	 * @author Matheus
	 *
	 */
	public enum TipoGameObject
	{
		INIMIGO,
		HEROI,
		CENARIO,
	};
	
	protected Sprite _sprite = null;
	protected TipoGameObject _tipo;
	protected Vector2 _posicaoTela = null;
	protected Animation _animacao = null;
	protected Camada _camada = null;
	protected Integer _id = null;
	protected Tela _telaInserido = null;
	protected boolean _atualiza = true, _desenha = true;
	protected HashSet<GameObject.TipoGameObject> _colidiveis = null;
	
	public GameObject()
	{
		_id = CatQuest.instancia.GetNovoId();
	}
	
	/**
	 * Função chamada a todo frame. Não esquecer de chamar nas classes filhas, ao final da função reescrita.
	 * @param deltaTime Dirença do tempo entre os dois últimos loops.
	 */
	public void Atualiza(float deltaTime)
	{
		if (!_atualiza)
			return;
		
		_sprite = (Sprite) _animacao.getKeyFrame(CatQuest.instancia.GetStateTime());
	}
	
	/**
	 * Função que desenha a sprite atual - atualizada a todo frame pela {@link #Atualiza(float)} baseada na {@link Animation}.
	 * @param bash {@link SpriteBatch} utilizada para desenhar objetos na tela.
	 */
	public void Desenha(SpriteBatch bash)
	{
		if (_desenha)
			bash.draw(_sprite, _posicaoTela.x, _posicaoTela.y);
	}
	
	/**
	 * Função chamada sempre que este objeto colidi com um objeto da lista objetos colidiveis.
	 * @param colidiu {@link GameObject} que colidiu com este.
	 * @param <T> Qualquer classe que herde de GameObject.
	 */
	public abstract <T extends GameObject> void AoColidir(T colidiu);
	
	/**
	 * Função com toda a rotina de iniciação das propriedades do objeto. Com excessão do ID, porque o ID já é definido no contrutor desta classe.
	 */
	public abstract void Inicia();
	
	/**
	 * Redefine todas as propriedades do objeto.
	 */
	public void Redefine()
	{
		this.Encerra();
		this.Inicia();
	}
	
	@Override
	public void reset() 
	{
		this.Redefine();
	}
	
	/**
	 * Retorna o id do game object.
	 * @return ID do game object.
	 */
	public final Integer GetId()
	{
		return _id;
	}
	
	/**
	 * Retorna a camada do game objeto.
	 * @return Uma constante para a {@link Camada} deste game object.
	 */
	public final Camada GetCamada()
	{
		return _camada;
	}
	
	/**
	 * Seta uma nova camada.
	 * @param novaCamada {@link Camada} nova camada do objeto.
	 */
	public void SetCamada(Camada novaCamada)
	{
		_camada = novaCamada;
	}
	
	/**
	 * Retorna a posição deste game object na tela;
	 * @return {@link Vector2}
	 */
	public final Vector2 GetPosicao()
	{
		return _posicaoTela;
	}
	
	/**
	 * Seta a nova posição do game object.
	 * @param posicao {@link Vector2} com a nova posição do game object. 
	 */
	public void SetPosicao(Vector2 posicao)
	{
		_posicaoTela = posicao;
	}
	
	/**
	 * Retorna a {@link Sprite} atual do game object.
	 * @return {@link Sprite} atual do object.
	 */
	public final Sprite GetSprite()
	{
		return _sprite;
	}
	
	/**
	 * Retorna a {@link Tela} que este game object está inserido.
	 * @return Referencia para {@link Tela} deste game object.
	 */
	public final Tela GetTela()
	{
		return _telaInserido;
	}
	
	/**
	 * Seta uma nova {@link Tela} para este game object.
	 * @param tela Nova {@link Tela} para este game object. 
	 */
	public void SetTela(Tela tela)
	{
		_telaInserido = tela;
	}
	
	/**
	 * Retorna se objeto será atualizado ou não. Se não (false), não roda a rotina de {@link #Atualiza(float)}. 
	 * @return True caso atualize.
	 */
	public final boolean GetSeAtualiza()
	{
		return _atualiza;
	}
	
	/**
	 * Retorna se objeto será desenhado ou não. Se não (false), não roda a rotina de {@link #Desenha(SpriteBatch)}.  
	 * @return True caso desenhe.
	 */
	public final boolean GetSeDesenha()
	{
		return _desenha;
	}
	
	/**
	 * Define se o game object será atualiza. Se false, não roda a rotina de {@link #Atualiza(float)}. 
	 * @param atualiza True para rodar a rotina de {@link #Atualiza(float)}. 
	 */
	public void SetSeAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}
	
	/**
	 * Define se o game object será desenhado. Se false, não roda a rotina de {@link #Desenha(SpriteBatch)}. 
	 * @param desenha True para rodar a rotina de {@link #Desenha(SpriteBatch)}. 
	 */
	public void SetSeDesenha(boolean desenha)
	{
		_desenha = desenha;
	}
	
	/**
	 * 
	 * @return False caso não esteja rodando a rotina de {@link #Atualiza(float)} <b>e</b> {@link #Desenha(SpriteBatch)}. True caso esteja rodando qualquer uma - ou ambas - as duas.
	 */
	public boolean GetSeAtivo()
	{
		return _desenha || _atualiza;
	}
	
	/**
	 * @param ativo True para ativar a chamada das rotinas de {@link #Atualiza(float)} e {@link #Desenha(SpriteBatch)}. False para desabilitar ambas.
	 */
	public void SetAtivo(boolean ativo)
	{
		_desenha = _atualiza = ativo;
	}
	
	/**
	 * Retorna o {@link TipoGameObject} do game object.
	 * @return O tipo do game object.
	 */
	public final TipoGameObject GetTipo()
	{
		return _tipo;
	}
	
	/**
	 * Função que valida colisão entre este {@link GameObject} e outro. Caso positivo, chama a função {@link GameObject#AoColidir(GameObject)} dos dois game objects.
	 * @param colidiu {@link GameObject} a validar colisão.
	 * @return True caso tenha colidido.
	 */
	public boolean ValidaColisao(GameObject colidiu)
	{
		if (colidiu == null || !_colidiveis.contains(colidiu.GetTipo()))
			return false;
		
		if (this._sprite.getBoundingRectangle().overlaps(colidiu.GetSprite().getBoundingRectangle()))
		{
			this.AoColidir(colidiu);
			colidiu.AoColidir(this);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Retorna se este {@link GameObject} é colidível.
	 * @return True caso seja colidível.
	 */
	public boolean GetColidivel()
	{
		if (_colidiveis != null)
			return !_colidiveis.isEmpty();
		else
			return false;
	}
	
	/**
	 * Retorna a lista de {@link GameObject} que podem colidir com este.
	 * @return {@link HashSet} de {@link GameObject.TipoGameObject} de {@link GameObject} que podem colidir com este. Ou nulo caso não exista colidíveis ({@link GameObject#GetColidivel()} == false) .
	 * @see {@link GameObject#GetColidivel()}.
	 */
	@SuppressWarnings("javadoc")
	public HashSet<GameObject.TipoGameObject> GetColidiveis()
	{
		return _colidiveis;
	}
	
	/**
	 * Libera os recursos deste game object.
	 */
	public void Encerra()
	{
		this.SetAtivo(false);
		_telaInserido.Remover(this);
		_colidiveis.clear();
	}
}

