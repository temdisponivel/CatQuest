package classes.gameobjects;

import java.util.HashSet;

import classes.uteis.Camada;
import classes.telas.Tela;
import catquest.CatQuest;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe base para todos os objetos do que aparecem no jogo.
 * @author Matheus
 *
 */
public abstract class GameObject
{
	/**
	 * Enumerador para os tipos de GameObject. Utilizado para evitar casts dinamicos e lista de colidiveis.
	 * @author Matheus
	 *
	 */
	static public enum TipoGameObject
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
	private boolean _atualiza = true, _desenha = true;
	private HashSet<GameObject.TipoGameObject> _colidiveis = null;
	
	public GameObject()
	{
		_id = CatQuest.instancia.GetNovoId();
	}
	
	/**
	 * Fun��o chamada a todo frame. N�o esquecer de chamar nas classes filhas, ao final da fun��o reescrita.
	 * @param deltaTime Diren�a do tempo entre os dois �ltimos loops.
	 */
	public void Atualiza(float deltaTime)
	{
		if (!_atualiza)
			return;
		
		_sprite = (Sprite) _animacao.getKeyFrame(CatQuest.instancia.GetStateTime());
	}
	
	/**
	 * Fun��o que desenha a sprite atual - atualizada a todo frame pela {@link #Atualiza(float)} baseada na {@link Animation}.
	 * @param bash {@link SpriteBatch} utilizada para desenhar objetos na tela.
	 */
	public void Desenha(SpriteBatch bash)
	{
		if (_desenha)
			bash.draw(_sprite, _posicaoTela.x, _posicaoTela.y);
	}
	
	/**
	 * Fun��o chamada sempre que este objeto colidi com um objeto da lista {@link #_colidiveis}
	 * @param colidiu {@link GameObject} que colidiu com este.
	 */
	public abstract <T extends GameObject> void AoColidir(T colidiu);
	
	/**
	 * Fun��o com toda a rotina de inicia��o das propriedades do objeto. Com excess�o do {@link #_id}, porque o ID j� � definido no contrutor desta classe.
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
	 * Seta uma nova camada. <b>Esta fun��o deve ser usada somente por {@link Tela#AtualizaCamadaGameObject(GameObject, Camada)}.
	 * Caso contr�rio, o objeto ainda ser� desenha e atualizado na camada anterior.</b> 
	 * @param novaCamada {@link Camada} nova camada do objeto.
	 */
	public void SetCamada(Camada novaCamada)
	{
		_camada = novaCamada;
	}
	
	/**
	 * Retorna a posi��o deste game object na tela;
	 * @return {@link Vector2}
	 */
	public final Vector2 GetPosicao()
	{
		return _posicaoTela;
	}
	
	/**
	 * Seta a nova posi��o do game object.
	 * @param posicao {@link Vector2} com a nova posi��o do game object. 
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
	 * Retorna a {@link Tela} que este game object est� inserido.
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
	 * Retorna se objeto ser� atualizado ou n�o. Se n�o (false), n�o roda a rotina de {@link #Atualiza(float)}. 
	 * @return True caso atualize.
	 */
	public final boolean GetSeAtualiza()
	{
		return _atualiza;
	}
	
	/**
	 * Retorna se objeto ser� desenhado ou n�o. Se n�o (false), n�o roda a rotina de {@link #Desenha(SpriteBatch)}.  
	 * @return True caso desenhe.
	 */
	public final boolean GetSeDesenha()
	{
		return _desenha;
	}
	
	/**
	 * Define se o game object ser� atualiza. Se false, n�o roda a rotina de {@link #Atualiza(float)}. 
	 * @param ativo True para rodar a rotina de {@link #Atualiza(float)}. 
	 */
	public void SetSeAtualiza(boolean atualza)
	{
		_atualiza = atualza;
	}
	
	/**
	 * Define se o game object ser� desenhado. Se false, n�o roda a rotina de {@link #Desenha(SpriteBatch)}. 
	 * @param ativo True para rodar a rotina de {@link #Desenha(SpriteBatch)}. 
	 */
	public void SetSeDesenha(boolean desenha)
	{
		_desenha = desenha;
	}
	
	/**
	 * 
	 * @return False caso n�o esteja rodando a rotina de {@link #Atualiza(float)} <b>e</b> {@link #Desenha(SpriteBatch)}. True caso esteja rodando qualquer uma - ou ambas - as duas.
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
	 * @return
	 */
	public final TipoGameObject GetTipo()
	{
		return _tipo;
	}
	
	/**
	 * Fun��o que valida colis�o entre este {@link GameObject} e outro. Caso positivo, chama a fun��o {@link GameObject#AoColidir(GameObject)} dos dois game objects.
	 * @param colidiu {@link GameObject} a validar colis�o.
	 * @return True caso tenha colidido.
	 */
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

