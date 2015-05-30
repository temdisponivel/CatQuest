package classes.gameobjects;

import java.util.HashMap;
import java.util.LinkedList;

import classes.uteis.Camada;
import classes.uteis.Configuracoes;
import classes.uteis.sons.CarregarSom;
import classes.uteis.sons.CarregarSomListner;
import classes.telas.Tela;
import catquest.CatQuest;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe base para todos os objetos do que aparecem no jogo.
 * 
 * @author Matheus
 *
 */
public abstract class GameObject
{
	/**
	 * Enumerador para os tipos de GameObject. Utilizado para evitar casts
	 * dinamicos e lista de colidiveis.
	 * 
	 * @author Matheus
	 *
	 */
	public enum GameObjects
	{
		Tile, Heroi, Inimigo, Cenario, GameObject, Ataque, Ui,
	};

	/**
	 * Enumerador para resultado de colis�es.
	 * 
	 * @author matheus
	 *
	 */
	public enum Colisoes
	{
		Livre, Passavel, Evitavel, NaoPassavel,
	}

	static public HashMap<Integer, GameObject> gameobjects = new HashMap<Integer, GameObject>();
	protected Sprite _sprite = null;
	protected GameObjects _tipo;
	protected Vector2 _posicaoTela = null;
	protected Vector2 _posicaoTelaAux = null;
	protected Rectangle _caixaColisao = null;
	protected HashMap<Integer, Animation> _animacoes = null;
	protected Animation _animacao = null;
	protected Camada _camada = null;
	protected Integer _id = null;
	protected Tela _telaInserido = null;
	protected boolean _atualiza = true, _desenha = true;
	protected HashMap<GameObject.GameObjects, Colisoes> _colidiveis = null;
	protected LinkedList<GameObject> _filhos = null;
	protected boolean _colidiPai = false;
	protected GameObject _pai = null;
	protected Color _cor = null;
	protected boolean _iniciado = false;
	protected HashMap<Integer, Sound> _sons = null;

	/**
	 * Cria o gameobject e inicia as propriedades prim�rias dele.
	 */
	public GameObject()
	{
		_id = CatQuest.instancia.GetNovoId();
		_posicaoTela = new Vector2();
		_caixaColisao = new Rectangle();
		_colidiveis = new HashMap<GameObject.GameObjects, Colisoes>();
		_filhos = new LinkedList<GameObject>();
		_tipo = GameObjects.GameObject;
		_camada = Camada.GameObject;
		gameobjects.put(this.GetId(), this);
	}

	/**
	 * Fun��o chamada a todo frame. N�o esquecer de chamar nas classes
	 * filhas, ao final da fun��o reescrita.
	 * 
	 * @param deltaTime
	 *            Diren�a do tempo entre os dois �ltimos loops.
	 */
	public void Atualiza(float deltaTime)
	{
		if (!_atualiza)
			return;

		if (_animacao != null)
		{
			if (_sprite == null)
				_sprite = new Sprite();

			_sprite.setRegion(_animacao.getKeyFrame(CatQuest.instancia.GetTempoJogo(), true));
			_sprite.setSize(_sprite.getRegionWidth(), _sprite.getRegionHeight());
		}

		if (_sprite != null)
		{
			_sprite.setColor(_cor);
			_sprite.setPosition(_posicaoTela.x, _posicaoTela.y);
			_caixaColisao.set(_posicaoTela.x, _posicaoTela.y, _sprite.getRegionWidth(), _sprite.getRegionHeight());
		}

		_caixaColisao.setPosition(_posicaoTela);

		if (this.GetSePai())
		{
			GameObject filho = null;

			for (int i = 0; i < _filhos.size(); i++)
			{
				filho = _filhos.get(i);

				filho.Atualiza(deltaTime);
				filho.SetPosicaoRelativa(_posicaoTela);
			}
		}
	}

	/**
	 * Fun��o que desenha a sprite atual - atualizada a todo frame pela
	 * {@link #Atualiza(float)} baseada na {@link Animation}.
	 * 
	 * @param bash
	 *            {@link SpriteBatch} utilizada para desenhar objetos na tela.
	 */
	public void Desenha(SpriteBatch batch)
	{
		if (_desenha && _sprite != null)
			_sprite.draw(batch);

		if (this.GetSePai() && _desenha)
		{
			GameObject filho = null;

			for (int i = 0; i < _filhos.size(); i++)
			{
				filho = _filhos.get(i);
				filho.Desenha(batch);
			}
		}
	}

	/**
	 * Fun��o chamada sempre que este objeto colidi com um objeto da lista
	 * objetos colidiveis.
	 * 
	 * @param colidiu
	 *            {@link GameObject} que colidiu com este.
	 * @return
	 */
	public abstract void AoColidir(GameObject colidiu);

	/**
	 * Fun��o com toda a rotina de inicia��o das propriedades do objeto.
	 * Com excess�o do ID, porque o ID j� � definido no contrutor desta
	 * classe.
	 */
	public void Inicia()
	{
		this.SetAtivo(true);
		_cor = Color.WHITE;
		_iniciado = true;
	}

	/**
	 * Redefine todas as propriedades do objeto. Encerra e inicia novamente.
	 */
	public void Redefine()
	{
		this.Encerra();
		this.Inicia();
	}

	/**
	 * Retorna o id do game object.
	 * 
	 * @return ID do game object.
	 */
	public final Integer GetId()
	{
		return _id;
	}

	/**
	 * Retorna a camada do game objeto.
	 * 
	 * @return Uma constante para a {@link Camada} deste game object.
	 */
	public final Camada GetCamada()
	{
		return _camada;
	}

	/**
	 * Seta uma nova camada.
	 * 
	 * @param novaCamada
	 *            {@link Camada} nova camada do objeto.
	 */
	public void SetCamada(Camada novaCamada)
	{
		if (this.GetTela() != null)
		{
			this.GetTela().Remover(this);
			_camada = novaCamada;
			this.GetTela().InserirGameObject(this);
		}
		else
			_camada = novaCamada;
	}

	/**
	 * Retorna a posi��o deste game object na tela;
	 * 
	 * @return {@link Vector2}
	 */
	public final Vector2 GetPosicao()
	{
		return _posicaoTela;
	}

	/**
	 * Caso este objeto tenha um pai, retorna a posi��o absoluta - sem
	 * rela��o com o pai;
	 * 
	 * @return {@link Vector2}
	 */
	public final Vector2 GetPosicaoAbsoluta()
	{
		return _posicaoTelaAux;
	}

	/**
	 * Seta a nova posi��o do game object.
	 * 
	 * @param posicao
	 *            {@link Vector2} com a nova posi��o do game object.
	 */
	public void SetPosicao(Vector2 posicao)
	{
		if (_telaInserido != null)
		{
			_telaInserido.RemoverDaMatriz(this, null);
		}

		_posicaoTela = posicao;
		_posicaoTelaAux = _posicaoTela.cpy();
		_caixaColisao.setPosition(_posicaoTela);

		if (_sprite != null)
			_sprite.setPosition(_posicaoTela.x, _posicaoTela.y);

		_caixaColisao.setPosition(_posicaoTela);

		if (this.GetSePai())
		{
			GameObject filho = null;

			for (int i = 0; i < _filhos.size(); i++)
			{
				filho = _filhos.get(i);
				filho.SetPosicaoRelativa(_posicaoTela);
			}
		}

		if (_telaInserido != null)
		{
			_telaInserido.InserirNaMatriz(this, null);
			_telaInserido.AdicionaColidir(this);
		}
	}

	/**
	 * Valida colis�o com os {@link GameObject objetos} que est�o na regi�o
	 * deste.
	 */
	public void ValidaColisaoRegiao()
	{
		if (this.GetColidivel())
		{
			if (_telaInserido != null)
			{
				LinkedList<GameObject> objetos = _telaInserido.GetObjetosRegiao(_caixaColisao);
				GameObject objeto = null;

				if (objetos == null)
					return;

				for (int i = 0; i < objetos.size(); i++)
				{
					objeto = objetos.get(i);

					this.ValidaColisao(objeto, true);
				}
			}
		}
	}

	/**
	 * Seta a nova posi��o do game object.
	 * 
	 * @param x
	 *            Novo x - canto inferior esquerdo.
	 * @param y
	 *            Novo y - canto inferior esquerdo.
	 */
	public void SetPosicao(float x, float y)
	{
		this.SetPosicao(new Vector2(x, y));
	}

	/**
	 * Retorna a {@link Sprite} atual do game object.
	 * 
	 * @return {@link Sprite} atual do object.
	 */
	public final Sprite GetSprite()
	{
		return _sprite;
	}

	/**
	 * Retorna a {@link Tela} que este game object est� inserido.
	 * 
	 * @return Referencia para {@link Tela} deste game object. Ou nulo caso n�o
	 *         esteja em nenhum tela.
	 * @see {@link #GetSeEmTela()}
	 */
	public final Tela GetTela()
	{
		return _telaInserido;
	}

	/**
	 * Seta uma nova {@link Tela} para este game object.
	 * 
	 * @param tela
	 *            Nova {@link Tela} para este game object.
	 */
	public void SetTela(Tela tela)
	{
		_telaInserido = tela;
	}

	/**
	 * Retorna se objeto ser� atualizado ou n�o. Se n�o (false), n�o
	 * roda a rotina de {@link #Atualiza(float)}.
	 * 
	 * @return True caso atualize.
	 */
	public final boolean GetSeAtualiza()
	{
		return _atualiza;
	}

	/**
	 * Retorna se objeto ser� desenhado ou n�o. Se n�o (false), n�o roda
	 * a rotina de {@link #Desenha(SpriteBatch)}.
	 * 
	 * @return True caso desenhe.
	 */
	public final boolean GetSeDesenha()
	{
		return _desenha;
	}

	/**
	 * Define se o game object ser� atualiza. Se false, n�o roda a rotina de
	 * {@link #Atualiza(float)}.
	 * 
	 * @param atualiza
	 *            True para rodar a rotina de {@link #Atualiza(float)}.
	 */
	public void SetSeAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}

	/**
	 * Define se o game object ser� desenhado. Se false, n�o roda a rotina
	 * de {@link #Desenha(SpriteBatch)}.
	 * 
	 * @param desenha
	 *            True para rodar a rotina de {@link #Desenha(SpriteBatch)}.
	 */
	public void SetSeDesenha(boolean desenha)
	{
		_desenha = desenha;
	}

	/**
	 * 
	 * @return False caso n�o esteja rodando a rotina de
	 *         {@link #Atualiza(float)} <b>e</b> {@link #Desenha(SpriteBatch)}.
	 *         True caso esteja rodando qualquer uma - ou ambas - as duas.
	 */
	public boolean GetSeAtivo()
	{
		return _desenha || _atualiza;
	}

	/**
	 * @param ativo
	 *            True para ativar a chamada das rotinas de
	 *            {@link #Atualiza(float)} e {@link #Desenha(SpriteBatch)}.
	 *            False para desabilitar ambas.
	 */
	public void SetAtivo(boolean ativo)
	{
		_desenha = _atualiza = ativo;
	}

	/**
	 * Retorna o {@link GameObjects} do game object.
	 * 
	 * @return O tipo do game object.
	 */
	public final GameObjects GetTipo()
	{
		return _tipo;
	}

	/**
	 * Fun��o que valida colis�o entre este {@link GameObject} e outro.
	 * 
	 * @param colidiu
	 *            {@link GameObject} a validar colis�o.
	 * @param colidi
	 *            Se, caso haja colis�o, deve chamar
	 *            {@link GameObject#AoColidir(GameObject)} dos objetos.
	 * @return True caso tenha colidido.
	 */
	public Colisoes ValidaColisao(GameObject colidiu, boolean colidi)
	{
		if (colidiu == this)
			return Colisoes.Passavel;

		if (!this.GetSeAtivo() || !colidiu.GetSeAtivo())
			return Colisoes.Passavel;

		GameObject filho = null;
		Colisoes outro = Colisoes.Livre;
		Colisoes este = Colisoes.Livre;
		Colisoes retorno = Colisoes.Livre;
		Colisoes temp;

		for (int i = 0; i < _filhos.size(); i++)
		{
			filho = _filhos.get(i);
			temp = filho.ValidaColisao(colidiu, colidi);

			if (temp.ordinal() > retorno.ordinal())
				retorno = temp;
		}

		if (colidiu == null || (!_colidiveis.containsKey(colidiu.GetTipo()) &&  !colidiu.GetColidiveis().containsKey(_tipo)) || !_camada.GetColidivel() || (!_colidiPai && colidiu == _pai))
			return retorno;

		if (this.GetCaixaColisao().overlaps(colidiu.GetCaixaColisao()))
		{
			if (colidiu.GetColidiveis().containsKey(_tipo))
				outro = colidiu.GetColidiveis().get(_tipo);

			if (retorno.ordinal() < outro.ordinal())
				retorno = outro;
			
			if (_colidiveis.containsKey(colidiu.GetTipo()))
			{
				if (retorno.ordinal() < (este = _colidiveis.get(colidiu.GetTipo())).ordinal())
					retorno = este;
			}

			if (colidi)
			{
				colidiu.AoColidir(this);
				this.AoColidir(colidiu);
			}
		}

		return retorno;
	}

	/**
	 * Valida se um campo � pass�vel, livre ou n�o pass�vel. Realiza colis�es
	 * deste objeto com os objetos dentro do campo formado pela caixa de colis�o
	 * no x e y do parametro. A caixa de colis�o deste objeto � temporatiamente
	 * movida para o x e y do campo.
	 * 
	 * @param campo
	 *            {@link Vector2 Posicao} em que a caixa de colis�o deve estar
	 *            para verificar.
	 * @return Se o campo � livre, pass�vel ou n�o pass�vel.
	 */
	public Colisoes GetValorCampo(Vector2 campo)
	{
		if (_telaInserido == null)
			return Colisoes.Passavel;

		GameObject outro = null;
		Colisoes colisao;
		Colisoes retorno = Colisoes.Livre;

		float x = _caixaColisao.x;
		float y = _caixaColisao.y;
		_caixaColisao.x = campo.x;
		_caixaColisao.y = campo.y;

		LinkedList<GameObject> lista = _telaInserido.GetObjetosRegiao(_caixaColisao);

		if (lista == null)
			return Colisoes.NaoPassavel;

		for (int i = 0; i < lista.size(); i++)
		{
			outro = lista.get(i);
			colisao = this.ValidaColisao(outro, false);

			if (retorno.ordinal() < colisao.ordinal())
				retorno = colisao; 

			if (retorno == Colisoes.NaoPassavel)
				break;
		}

		_caixaColisao.x = x;
		_caixaColisao.y = y;

		return retorno;
	}

	public Rectangle GetCaixaColisao()
	{
		return _caixaColisao;
	}

	/**
	 * Retorna se este {@link GameObject} � colid�vel.
	 * 
	 * @return True caso seja colid�vel.
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
	 * 
	 * @return {@link HashMap} de {@link GameObject.TipoGameObject} de
	 *         {@link GameObject} que podem colidir com este. Ou nulo caso n�o
	 *         exista colid�veis ({@link GameObject#GetColidivel()} == false).
	 * @see {@link GameObject#GetColidivel()}.
	 * @see O valor vinculado a chave do Hash � o resultado caso eles colid�o.
	 */
	@SuppressWarnings("javadoc")
	public HashMap<GameObject.GameObjects, Colisoes> GetColidiveis()
	{
		return _colidiveis;
	}

	/**
	 * Fun��o que adiciona um filho a este {@link GameObject}. Todos os
	 * filhos s�o atualizados e desenhados logo ap�s o pai. Ao adicionar um
	 * filho, a camada do filho passa a ser a camada do pai; ele s� ser�
	 * atualizado e desenhado a partir do pai; Portante, se o pai n�o �
	 * atualizado, o filho tamb�m n�o. Se o pai n�o � desenhado, o filho
	 * tamb�m n�o. Entretanto, as colis�es s�o realizadas independentes
	 * do pai. A colis�o com o pai � desativada por padr�o, mas pode ser
	 * mudada em {@link GameObject#SetColidiPai(boolean)}. O novo filho n�o �
	 * {@link #Inicia() iniciado} aqui. Portanto, caso ainda n�o tenha iniciado
	 * - o que ocorre ao {@link Tela#InserirGameObject(GameObject) adicionar a
	 * uma tela} - inicie antes dessa fun��o.
	 * 
	 * @param filho
	 *            {@link GameObject} filho.
	 * @return {@link GameObject Filho} adicionado.
	 */
	public GameObject AdicionaFilho(GameObject filho)
	{
		if (_filhos == null)
			_filhos = new LinkedList<GameObject>();

		filho.SetPai(this);
		filho.SetCamada(_camada);
		filho.SetPosicaoRelativa(_posicaoTela);

		if (filho.GetTela() != null && filho.GetTela().ContemGameObject(filho))
			filho.GetTela().Remover(filho);

		_filhos.add(filho);

		return filho;
	}

	/**
	 * Adiciona uma s�rie de filhos a este gameobject. O mesmo que
	 * {@link GameObject#adicionaFilho(GameObject)}.
	 * 
	 * @param filhos
	 *            Filhos a serem adicionados.
	 * @see {@link GameObject#adicionaFilho(GameObject)}
	 */
	public void AdicionaFilhos(GameObject... filhos)
	{
		for (int i = 0; i < filhos.length; i++)
			AdicionaFilho(filhos[i]);
	}

	/**
	 * Define um pai para este {@link GameObject}. Deve ser utilizado quando
	 * este gameobject � adicionado como filho de outro.
	 * 
	 * @param pai
	 *            {@link GameObject} que ser� referenciado como pai.
	 */
	public void SetPai(GameObject pai)
	{
		_pai = pai;
		_posicaoTelaAux = _posicaoTela.cpy();
	}

	/**
	 * Define a posi��o atual deste {@link GameObject} em rela��o a do
	 * objeto parametrizado. Ou seja, toma como ponto ZERO o vetor
	 * parametrizado.
	 * 
	 * @param posicaoRelativa
	 *            {@link Vector2 vetor} a relacionar como ponto ZERO.
	 */
	public void SetPosicaoRelativa(Vector2 posicaoRelativa)
	{
		_posicaoTela.x = posicaoRelativa.x + _posicaoTelaAux.x;
		_posicaoTela.y = posicaoRelativa.y + _posicaoTelaAux.y;
	}

	/**
	 * Define quando este {@link GameObject} deve validar colis�o com seu pai.
	 * 
	 * @param colidiPai
	 *            True se � pra validar colis�o.
	 */
	public void SetColidiPai(boolean colidiPai)
	{
		_colidiPai = colidiPai;
	}

	/**
	 * @return True se a colis�o deste {@link GameObject} � calculada
	 *         tamb�m com o pai. Ou seja, se ele colidi com o pai.
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
	 * @return {@link LinkedList<T>} dos filhos deste objeto. Pode ser nulo caso
	 *         n�o tenha filhos. Validar via {@link GameObject#GetSePai()}.
	 */
	public LinkedList<GameObject> GetFilhos()
	{
		return _filhos;
	}

	/**
	 * Retira um filho deste {@link GameObject}.
	 * 
	 * @param filho
	 *            {@link GameObject} filho a ser removido.
	 */
	public void RemoveFilho(GameObject filho)
	{
		if (!this.GetSePai())
			return;

		_filhos.remove(filho);
	}

	/**
	 * @return Largura atual do {@link GameObject objeto}.
	 */
	public float GetLargura()
	{
		return _caixaColisao.width;
	}

	/**
	 * @return Altura atual do {@link GameObject objeto}.
	 */
	public float GetAltura()
	{
		return _caixaColisao.height;
	}

	/**
	 * @return {@link Color Cor} com que as sprites deste objeto est�o sendo
	 *         desenhadas. {@link Color#WHITE} por padr�o.
	 */
	public Color GetColor()
	{
		return _cor;
	}

	/**
	 * Define uma nova {@link Color cor} para desenhar as sprites deste objeto.
	 * 
	 * @param cor
	 *            Nova cor.
	 */
	public void SetColor(Color cor)
	{
		_cor = cor;
	}

	/**
	 * Toma um som do {@link GameObject objeto}.
	 * 
	 * @param chave
	 *            Chave utilizada para inserir o som na {@link #_sons lista de
	 *            sons} atrav�s de {@link #IncluirSom(EnumSom, FileHandle)}.
	 * @return -1 caso o som n�o exista. {@link Sound#play(float) ID} caso
	 *         contr�rio.
	 */
	protected <T extends Enum<?>> long TocaSom(T chave)
	{
		Sound som = null;

		if (_sons == null)
			return -1;

		if (!_sons.containsKey(chave))
			return -1;

		som = _sons.get(chave);
		return som.play(Configuracoes.instancia.GetVolumeSom());
	}

	/**
	 * Inclui os {@link Sounds sons} na {@link #_sons lista de sons}.
	 * 
	 * @param som
	 *            {@link FileHandle Arquivos} do som para adicionar.
	 * @param chave
	 *            {@link EnumSom Chave} para vincular o som na lista de sons.
	 *            Utilizar uma enum que implemente {@link EnumSom} para indexar.
	 */
	protected <T extends Enum<?>> void IncluirSom(final T chave, FileHandle som)
	{
		if (_sons == null)
			_sons = new HashMap<Integer, Sound>();

		CarregarSom.instancia.Carrega(som, new CarregarSomListner()
		{
			@Override
			public void AoCarregar(Sound somCarregada)
			{
				_sons.put(chave.ordinal(), somCarregada);
			}
		});
	}

	/**
	 * Define uma {@link Animation anima��o} como a atual. A que ser� utilizada
	 * at� a pr�xima altera��o. Caso n�o haja anima��o vinculada a chave, nada
	 * acontece.
	 * 
	 * @param chave
	 *            {@link T Chave} vinculada a anima��o. A mesma utilizada para
	 *            {@link #IncluirAnimacao adicionar a anima��o}.
	 */
	protected <T extends Enum<?>> void SetAnimacao(T chave)
	{
		if (_animacoes == null)
			return;

		if (!_animacoes.containsKey(chave.ordinal()))
			return;

		if (_animacao != _animacoes.get(chave.ordinal()))
			_animacao = _animacoes.get(chave.ordinal());
	}

	/**
	 * Rotaciona a {@link Vector2 posi��o} e sprite deste objeto pelo angulo
	 * informado.
	 * 
	 * @param angulo
	 *            Angulo em radiano.
	 * @see {@link Vector2#rotate(float)}
	 * @see {@link Sprite#setRotation(float)
	 */
	public void Rotaciona(float angulo)
	{
		_posicaoTela.rotate(angulo);

		if (_sprite != null)
			_sprite.rotate(angulo);

		_caixaColisao.set(_sprite.getBoundingRectangle());
	}

	/**
	 * Inclui uma nova anima��o ao {@link GameObject game object}.
	 * 
	 * @param chave
	 *            {@link T Chave} a ser vinculada a nova anima��o.
	 * @param animacao
	 *            {@link Animation Anima��o} a ser inclu�da.
	 */
	protected <T extends Enum<?>> void IncluirAnimacao(T chave, Animation animacao)
	{
		if (_animacoes == null)
			_animacoes = new HashMap<Integer, Animation>();

		_animacoes.put(chave.ordinal(), animacao);
	}

	/**
	 * @return True caso este {@link GameObject objeto} esteja inserido em
	 *         alguma tela.
	 */
	public boolean GetSeEmTela()
	{
		return _telaInserido != null;
	}

	/**
	 * @return True caso este {@link GameObject objeto} seja filho de outro.
	 */
	public boolean GetSeFilho()
	{
		return _pai != null;
	}

	/**
	 * @return Instancia do pai deste {@link GameObject objeto}. Ou nulo caso
	 *         n�o tenha pai.
	 * @see {@link #GetSeFilho()}.
	 */
	public GameObject GetPai()
	{
		return _pai;
	}

	/**
	 * Remove este {@link GameObject objeto} do pai. Caso n�o seja filho, nada
	 * acontece.
	 */
	public void RemoverDoPai()
	{
		if (_pai != null)
			_pai.RemoveFilho(this);

		_pai = null;
	}

	/**
	 * Remove este {@link GameObject objeto} da {@link Tela tela}. Caso n�o
	 * esteja em nenhuma tela, nada acontece.
	 */
	public void RemoverDaTela()
	{
		if (_telaInserido != null)
			_telaInserido.Remover(this);

		_telaInserido = null;
	}

	/**
	 * Libera os recursos deste game object.
	 */
	public void Encerra()
	{
		this.SetAtivo(false);

		this.RemoverDaTela();
		this.RemoverDoPai();

		if (this.GetSePai())
		{
			GameObject filho = null;

			for (int i = 0; i < _filhos.size(); i++)
			{
				filho = _filhos.get(i);
				filho.Encerra();
			}
		}
		
		_iniciado = false;
	}

	/**
	 * Distancia entre este e outro {@link GameObject objeto}.
	 * 
	 * @param outro
	 *            {@link GameObject} objeto para ver a distancia.
	 * @return Distancia em pixels.
	 */
	public float GetDistancia(GameObject outro)
	{
		return this.GetPosicao().dst(outro.GetPosicao());
	}
	
	/**
	 * REtorna se este objeto est� iniciado.
	 */
	public boolean GetSeIniciado()
	{
		return _iniciado;
	}
	
	/**
	 * Define uma {@link Sprite sprite} para o {@link GameObject objeto}.
	 */
	public void SetSprite(Sprite sprite)
	{
		_sprite = sprite;
		_caixaColisao.set(_posicaoTela.x, _posicaoTela.y, _sprite.getRegionWidth(), _sprite.getRegionHeight());
	}

	@Override
	public int hashCode()
	{
		return _tipo.ordinal();
	}
}