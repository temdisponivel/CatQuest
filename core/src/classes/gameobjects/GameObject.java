package classes.gameobjects;

import java.util.HashSet;
import java.util.LinkedList;

import classes.uteis.Camada;
import classes.telas.Tela;
import catquest.CatQuest;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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
	public enum GameObjects
	{
		INIMIGO,
		HEROI,
		CENARIO,
	};
	
	protected TextureRegion _sprite = null;
	protected GameObjects _tipo;
	protected Vector2 _posicaoTela = null;
	protected Rectangle _caixaColisao = null;
	protected Animation _animacao = null;
	protected Camada _camada = null;
	protected Integer _id = null;
	protected Tela _telaInserido = null;
	protected boolean _atualiza = true, _desenha = true;
	protected HashSet<GameObject.GameObjects> _colidiveis = null;
	protected LinkedList<GameObject> _filhos = null;
	protected boolean _colidiPai = false;
	protected GameObject _pai = null;
	
	public GameObject()
	{
		_id = CatQuest.instancia.GetNovoId();
		_posicaoTela = new Vector2();
		_caixaColisao = new Rectangle();
	}
	
	/**
	 * Função chamada a todo frame. Não esquecer de chamar nas classes filhas, ao final da função reescrita.
	 * @param deltaTime Dirença do tempo entre os dois últimos loops.
	 */
	public void Atualiza(float deltaTime)
	{
		if (!_atualiza)
			return;
		
		if (_animacao != null)
		{
			_sprite = _animacao.getKeyFrame(CatQuest.instancia.GetStateTime());
			_caixaColisao.setPosition(_posicaoTela);
			_caixaColisao.setHeight(_sprite.getRegionHeight());
			_caixaColisao.setWidth(_sprite.getRegionWidth());
		}
		
		for (GameObject filho : _filhos)
		{
			filho.Atualiza(deltaTime);
			filho.SetPosicaoRelativa(_posicaoTela);
		}
	}
	
	/**
	 * Função que desenha a sprite atual - atualizada a todo frame pela {@link #Atualiza(float)} baseada na {@link Animation}.
	 * @param bash {@link SpriteBatch} utilizada para desenhar objetos na tela.
	 */
	public void Desenha(SpriteBatch batch)
	{
		if (_desenha && _sprite != null)
			batch.draw(_sprite, _posicaoTela.x, _posicaoTela.y);
		
		for (GameObject filho : _filhos)
		{
			filho.Desenha(batch);
		}
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
	 * Retorna a {@link TextureRegion} atual do game object.
	 * @return {@link TextureRegion} atual do object.
	 */
	public final TextureRegion GetSprite()
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
	 * Retorna o {@link GameObjects} do game object.
	 * @return O tipo do game object.
	 */
	public final GameObjects GetTipo()
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
		for (GameObject filho : _filhos)
		{
			filho.ValidaColisao(colidiu);
		}
		
		if (colidiu == null || !_colidiveis.contains(colidiu.GetTipo()) || (!_colidiPai && colidiu == _pai))
			return false;
		
		if (this.GetCaixaColisao().overlaps(colidiu.GetCaixaColisao()))
		{
			this.AoColidir(colidiu);
			colidiu.AoColidir(this);
			return true;
		}
		
		return false;
	}
	
	public Rectangle GetCaixaColisao()
	{
		return new Rectangle(_posicaoTela.x, _posicaoTela.y, _sprite.getRegionWidth(), _sprite.getRegionHeight());
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
	public HashSet<GameObject.GameObjects> GetColidiveis()
	{
		return _colidiveis;
	}
	
	/**
	 * Função que adiciona um filho a este {@link GameObject}. Todos os filhos são atualizados e desenhados logo após o pai.
	 * Ao adicionar um filho, a camada do filho passa a ser a camada do pai; ele só será atualizado e desenhado a partir do pai;
	 * Portante, se o pai não é atualizado, o filho também não. Se o pai não é desenhado, o filho também não.
	 * Entretanto, as colisões são realizadas independentes do pai. A colisão com o pai é desativada por padrão, mas pode ser mudada em {@link GameObject#SetColidiPai(boolean)}.
	 * @param filho {@link GameObject} filho.
	 */
	public void AdicionaFilho(GameObject filho)
	{
		if (_filhos == null)
			_filhos = new LinkedList<GameObject>();
		
		if (_filhos == null)
			return;
		
		filho.SetPai(this);
		filho.SetCamada(_camada);
		filho.SetPosicaoRelativa(_posicaoTela);
		
		if (_telaInserido != null && _telaInserido.ContemGameObject(filho))
			_telaInserido.Remover(filho);
			
		_filhos.add(filho);
	}
	
	/**
	 * Adiciona uma série de filhos a este gameobject. O mesmo que {@link GameObject#adicionaFilho(GameObject)}.
	 * @param filhos Filhos a serem adicionados.
	 * @see {@link GameObject#adicionaFilho(GameObject)}
	 */
	public void AdicionaFilhos(GameObject... filhos)
	{
		for (int i = 0; i < filhos.length; i++)
			AdicionaFilho(filhos[i]);
	}
	
	/**
	 * Define um pai para este {@link GameObject}. Deve ser utilizado quando este gameobject é adicionado como filho de outro. 
	 * @param pai {@link GameObject} que será referenciado como pai.
	 */
	public void SetPai(GameObject pai)
	{
		_pai = pai;
	}
	
	/**
	 * Define a posição atual deste {@link GameObject} em relação a do objeto parametrizado.
	 * Ou seja, toma como ponto ZERO o vetor parametrizado.
	 * @param posicaoRelativa Vetor a relacionar como ponto ZERO.
	 */
	public void SetPosicaoRelativa(Vector2 posicaoRelativa)
	{
		_posicaoTela.sub(posicaoRelativa);
	}
	
	/**
	 * Define quando este {@link GameObject} deve validar colisão com seu pai.
	 * @param colidiPai True se é pra validar colisão.
	 */
	public void SetColidiPai(boolean colidiPai)
	{
		_colidiPai = colidiPai;
	}
	
	/**
	 * @return True se a colisão deste {@link GameObject} é calculada também com o pai. Ou seja, se ele colidi com o pai.
	 */
	public boolean GetColidiPai()
	{
		return _colidiPai;
	}
	
	/**
	 * @return True se este {@link GameObject} tem algum filho.
	 */
	public boolean GetSePai()
	{
		if (_filhos != null)
			return !_filhos.isEmpty();
		else
			return false;
	}
	
	/**
	 * @return {@link LinkedList<T>} dos filhos deste objeto. Pode ser nulo caso não tenha filhos. Validar via {@link GameObject#GetSePai()}.
	 */
	public LinkedList<GameObject> GetFilhos()
	{
		return _filhos;
	}
	
	/**
	 * Retira um filho deste {@link GameObject}.
	 * @param filho {@link GameObject} filho a ser removido.
	 */
	public void RemoveFilho(GameObject filho)
	{
		if (!this.GetSePai())
			return;
		
		_filhos.remove(filho);
	}
	
	/**
	 * Libera os recursos deste game object.
	 */
	public void Encerra()
	{
		this.SetAtivo(false);
		
		if (_telaInserido != null)
			_telaInserido.Remover(this);
		
		if (_colidiveis != null)
			_colidiveis.clear();
	}
	
	@Override
	public int hashCode()
	{
		return _tipo.ordinal();
	}
}

