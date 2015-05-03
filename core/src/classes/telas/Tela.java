//TODO: validar desempenho com esse monte de for each do java

package classes.telas;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import classes.gameobjects.GameObject;
import classes.uteis.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe base para todas as telas. Tela é o que guarda e gerencia todos os gameobjects que estão sendo manipulados pelo jogo.
 * @author Matheus
 *
 */
public class Tela implements OnCompletionListener
{
	/**
	 * Enumerador para identificação de telas.
	 * @author Matheus
	 *
	 */
	public enum Telas
	{
		TITULO,
		MENU,
		INTRODUCAO,
		GAMEPLAY,
		FIMDOJOGO,		
	}
	
	protected HashMap<Camada, ListaGameObject> _listasGameObject = null;
	protected boolean _desenha = true, _atualiza = true;
	protected ListaGameObject _gameObjectsColisoes = null;
	protected Telas _tipo;
	protected Color _corFundo = null;
	protected boolean _inseriGameObject = false, _removeGameObject = false;
	protected LinkedList<GameObject> _gameObjectsIncluir = null;
	protected LinkedList<GameObject> _gameObjectsExcluir = null;
	
	/**
	 * Função que inicia as propriedades da tela.
	 */
	public void Iniciar()
	{
		_listasGameObject = new HashMap<Camada, ListaGameObject>();
		_gameObjectsColisoes = new ListaGameObject();
		_gameObjectsIncluir = new LinkedList<GameObject>();
		_gameObjectsExcluir = new LinkedList<GameObject>();
		_corFundo = Color.BLACK;
	}
	
	/**
	 * Função que roda a rotina de atualizar os gameobjects da tela, colisões, etc.
	 * @param deltaTime Diferença dos tempos dos dois ultimos gameloops.
	 */
	public void Atualiza(final float deltaTime)
	{
		this.GerenciaGameObject();
		
		/* ------------ atualiza -------------------- */
		
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			if (!entrada.getKey().GetAtualiza() || !_atualiza || !entrada.getValue().GetAtiva())
				continue;
			
			for (Entry<Integer, GameObject> entrada2 : entrada.getValue().entrySet())
			{					
				entrada2.getValue().Atualiza(deltaTime);
			}
		}
		
		/* ------------------------ COLISOES ------------------------------------*/
		
		//cria uma lista com todos os gameobjects colidiveis de todas as camadas
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			//se nao for pra atualizar, se a lista nao estiver ativa, se a camada nao for de colidiveis
			if (!_atualiza || !entrada.getValue().GetAtiva() || !entrada.getKey().GetColidivel())
				continue;
			
			_gameObjectsColisoes.putAll(entrada.getValue());
		}
		
		//percorre a lista de todos os gameobjects
		int i = 0, ii = 0;
		for (Entry<Integer, GameObject> entrada : _gameObjectsColisoes.entrySet())
		{
			ii = 0;
			for (Entry<Integer, GameObject> entrada2 : _gameObjectsColisoes.entrySet())
			{
				if (ii++ <= i++)
					continue;
				
				entrada.getValue().ValidaColisao(entrada2.getValue());
			}
		}
	}
	
	/**
	 * Função que roda a rotina de desenho dos gameobjects da tela.
	 * @param spriteBatch {@link SpriteBatch} para desenha as sprites.
	 */
	public void Desenha(final SpriteBatch spriteBatch)
	{		
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			if (!entrada.getKey().GetDesenha() || !_desenha || !entrada.getValue().GetAtiva())
				continue;
			
			spriteBatch.setColor(entrada.getKey().GetCor());
			
			for (Entry<Integer, GameObject> entrada2 : entrada.getValue().entrySet())
			{
				entrada2.getValue().Desenha(spriteBatch);
			}
		}
	}
	
	/**
	 * Função que gerencia os {@link GameObject} que devem ser inseridos ou removidos.
	 */
	private void GerenciaGameObject()
	{
		if (_inseriGameObject)
		{
			_inseriGameObject = false;
			
			for (GameObject gameObject : _gameObjectsIncluir)
			{
				if (!_listasGameObject.containsKey(gameObject.GetCamada()))
				{
					_listasGameObject.put(gameObject.GetCamada(), new ListaGameObject());
				}
				
				_listasGameObject.get(gameObject.GetCamada()).Adicionar(gameObject);
				gameObject.SetTela(this);
			}
			
			_gameObjectsIncluir.clear();
		}
		
		if (_removeGameObject)
		{
			_removeGameObject = false;
			
			for (GameObject remover : _gameObjectsIncluir)
			{
				ListaGameObject listaTemp;
				if ((listaTemp = _listasGameObject.get(remover.GetCamada())) != null)
					listaTemp.Remover(remover);
			}
			
			_gameObjectsExcluir.clear();
		}
	}
	
	/**
	 * Função que insere um novo gameobject na tela.
	 * @param gameObject {@link GameObject} a ser inserido.
	 */
	public void InserirGameObject(GameObject gameObject)
	{
		_inseriGameObject = true;
		_gameObjectsIncluir.add(gameObject);
	}
	
	/**
	 * Função que remove um {@link GameObject} da tela.
	 * @param remover {@link GameObject} a ser removido.
	 */
	public void Remover(GameObject remover)
	{
		_removeGameObject = true;
		_gameObjectsExcluir.add(remover);		
	}
	
	/**
	 * 
	 * @param contem {@link GameObject} a verificar se existe na tela.
	 * @return True caso exista na tela, falso caso contrário.
	 */
	public boolean ContemGameObject(GameObject contem)
	{
		return _listasGameObject.get(contem.GetCamada()) != null && _listasGameObject.get(contem.GetCamada()).containsValue(contem);
	}
	
	/**
	 *
	 * @return True caso a rotina de {@link Tela#Desenha(SpriteBatch)} seja chamada nas iterações do gameloop.
	 */
	public boolean GetSeDesenha()
	{
		return _desenha;
	}
	
	/**
	 * 
	 * @return True caso a rotina de {@link #Atualiza(float)} seja chamada nas iterações do gameloop.
	 */
	public boolean GetSeAtualiza()
	{
		return _atualiza;
	}
	
	/**
	 * 
	 * @param desenha True para rodar a rotina de {@link #Desenha(SpriteBatch)}, false caso contrário.
	 */
	public void SetSeDesenha(boolean desenha)
	{
		_desenha = desenha;
	}
	
	/**
	 * @param atualiza True para rodar a rotina de {@link #Atualiza(float)}, false caso contrário.
	 */
	public void SetSeAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}
	
	/**
	 * 
	 * @param ativa True para que ambas rotinas de {@link #Atualiza(float)} e {@link #Desenha(SpriteBatch)} sejam chamadas nas iterações do gameloop.
	 */
	public void SetAtiva(boolean ativa)
	{
		_desenha = _atualiza = ativa;
	}
	
	/**
	 * 
	 * @return ativa True qualquer uma das rotinas de {@link #Atualiza(float)} e {@link #Desenha(SpriteBatch)} são chamadas nas iterações do gameloop. False se nenhuma é chamada.
	 */
	public boolean GetSeAtiva()
	{
		return _desenha || _atualiza;
	}
	
	/**
	 * Valida se um campo na tela está livre para movimentação de um {@link GameObject objeto}.
	 * @param posicao {@link Vector2 Posição} do campo.
	 * @param campo  {@link Rectangle Tamanho} para validar se está livre. Por exemplo: valida se o campo de: (posicao(0, 0)) até (rectangle(150, 150) está livre.
	 * @param objeto {@link GameObject Objeto} para utilizado para validar campo livre. Deve ser o objeto que ocupara este lugar caso esteja livre.
	 * @return True caso livre. False caso contrário.
	 */
	public boolean GetCampoLivre(GameObject objeto, Vector2 posicao, Rectangle campo)
	{
		return true;
	}
	
	/**
	 * Função que libera os recursos da {@link Tela}
	 */
	public void Encerrar()
	{
		this.SetAtiva(false);

		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			for (Entry<Integer, GameObject> entrada2 : entrada.getValue().entrySet())
			{
				entrada2.getValue().Encerra();
			}
		}
		
		_listasGameObject.clear();
		_listasGameObject.clear();
	}
	
	/**
	 * Função que reinicia a {@link Tela} do inicio.
	 */
	public void ReiniciaTela()
	{
		this.Encerrar();
		this.Iniciar();
	}
	
	/**
	 * @return {@link Color Cor} do fundo. Cor que é utilizada para limpar a tela a todo frame que esta tela está no topo.
	 */
	public Color GetCorFundo()
	{
		return _corFundo;
	}
	
	/**
	 * 
	 * @return O {@link Telas Tipo} da tela.
	 */
	public Telas GetTipo()
	{
		return _tipo;
	}

	@Override
	public void onCompletion(Music music)
	{
		music.dispose();
	}
	
	@Override
	public int hashCode()
	{
		return _tipo.ordinal();
	}
}