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
 * Classe base para todas as telas. Tela � o que guarda e gerencia todos os gameobjects que est�o sendo manipulados pelo jogo.
 * @author Matheus
 *
 */
public class Tela implements OnCompletionListener
{
	/**
	 * Enumerador para identifica��o de telas.
	 * @author Matheus
	 *
	 */
	public enum Telas
	{
		TITULO,
		MENU,
		CONFIGURACOES,
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
	protected LinkedList<GameObject>[][] _matrizMapa = null;
	protected float _precisaoMapaY = 32f;
	protected float _precisaoMapaX = 32f;
	
	/**
	 * Fun��o que inicia as propriedades da tela.
	 */
	public void Iniciar()
	{
		_listasGameObject = new HashMap<Camada, ListaGameObject>();
		_gameObjectsColisoes = new ListaGameObject();
		_gameObjectsIncluir = new LinkedList<GameObject>();
		_gameObjectsExcluir = new LinkedList<GameObject>();
		_corFundo = Color.WHITE;
	}
	
	/**
	 * Fun��o que roda a rotina de atualizar os gameobjects da tela, colis�es, etc.
	 * @param deltaTime Diferen�a dos tempos dos dois ultimos gameloops.
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
	}
	
	/**
	 * Fun��o que roda a rotina de desenho dos gameobjects da tela.
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
	 * Fun��o que gerencia os {@link GameObject} que devem ser inseridos ou removidos.
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
				_matrizMapa[Math.abs((int) (gameObject.GetPosicao().x % _precisaoMapaX))][Math.abs((int) (gameObject.GetPosicao().y % _precisaoMapaY))].add(gameObject);
				gameObject.SetTela(this);
				gameObject.Inicia();
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
				{
					listaTemp.Remover(remover);
					_matrizMapa[Math.abs((int) (remover.GetPosicao().x % _precisaoMapaX))][Math.abs((int) (remover.GetPosicao().y % _precisaoMapaY))].remove(remover);
				}
			}
			
			_gameObjectsExcluir.clear();
		}
	}
	
	/**
	 * Fun��o que insere um novo gameobject na tela.
	 * @param gameObject {@link GameObject} a ser inserido.
	 */
	public void InserirGameObject(GameObject gameObject)
	{
		_inseriGameObject = true;
		_gameObjectsIncluir.add(gameObject);
	}
	
	/**
	 * Fun��o que remove um {@link GameObject} da tela.
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
	 * @return True caso exista na tela, falso caso contr�rio.
	 */
	public boolean ContemGameObject(GameObject contem)
	{
		return _listasGameObject.get(contem.GetCamada()) != null && _listasGameObject.get(contem.GetCamada()).containsValue(contem);
	}
	
	/**
	 *
	 * @return True caso a rotina de {@link Tela#Desenha(SpriteBatch)} seja chamada nas itera��es do gameloop.
	 */
	public boolean GetSeDesenha()
	{
		return _desenha;
	}
	
	/**
	 * 
	 * @return True caso a rotina de {@link #Atualiza(float)} seja chamada nas itera��es do gameloop.
	 */
	public boolean GetSeAtualiza()
	{
		return _atualiza;
	}
	
	/**
	 * 
	 * @param desenha True para rodar a rotina de {@link #Desenha(SpriteBatch)}, false caso contr�rio.
	 */
	public void SetSeDesenha(boolean desenha)
	{
		_desenha = desenha;
	}
	
	/**
	 * @param atualiza True para rodar a rotina de {@link #Atualiza(float)}, false caso contr�rio.
	 */
	public void SetSeAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}
	
	/**
	 * 
	 * @param ativa True para que ambas rotinas de {@link #Atualiza(float)} e {@link #Desenha(SpriteBatch)} sejam chamadas nas itera��es do gameloop.
	 */
	public void SetAtiva(boolean ativa)
	{
		_desenha = _atualiza = ativa;
	}
	
	/**
	 * 
	 * @return ativa True qualquer uma das rotinas de {@link #Atualiza(float)} e {@link #Desenha(SpriteBatch)} s�o chamadas nas itera��es do gameloop. False se nenhuma � chamada.
	 */
	public boolean GetSeAtiva()
	{
		return _desenha || _atualiza;
	}
	
	/**
	 * Valida se um campo na tela est� livre para movimenta��o de um {@link GameObject objeto}.
	 * @param campo  {@link Rectangle Tamanho} para validar se est� livre. X e Y do Rectangle � utilizado como zero zero - canto inferior esquerdo.
	 * @param objeto {@link GameObject Objeto} para utilizado para validar campo livre. Deve ser o objeto que ocupara este lugar caso esteja livre.
	 * @return True caso livre. False caso contr�rio.
	 */
	public boolean GetCampoLivre(GameObject objeto, Rectangle campo)
	{
		if (_matrizMapa == null)
			return true;
		
		LinkedList<GameObject> lista = _matrizMapa[Math.abs((int) (campo.x % _precisaoMapaX))][Math.abs((int) (campo.y % _precisaoMapaY))];
		
		for (int i = 0; i < lista.size(); i++)
		{
			if (lista.get(i).ValidaColisao(objeto))
				return false;
		}
		
		return true;
	}
	
	/**
	 * @param campo {@link Rectangle Tamanho} do campo para pegar os {@link GameObject game objects} que est�o dentro.  X e Y do Rectangle � utilizado como zero zero - canto inferior esquerdo.
	 * @return {@link LinkedList<GameObject> Lista} com os objetos que est�o nesta regi�o. Ou nulo caso n�o haja objetos.
	 */
	public LinkedList<GameObject> GetObjetosRegiao(Rectangle campo)
	{
		if (_matrizMapa == null)
			return null;
		
		LinkedList<GameObject> objetos = new LinkedList<GameObject>();
		int quantidadeX = (int) Math.abs((campo.width / _precisaoMapaX));
		int quantidadeY = (int) Math.abs((campo.height / _precisaoMapaY));
		
		for (int x = 0; x < quantidadeX; x++)
		{
			for (int y = 0; y < quantidadeY; y++)
			{
				objetos.addAll(_matrizMapa[Math.abs((int) (campo.x % _precisaoMapaX) + x)][Math.abs((int) (campo.y % _precisaoMapaY) + y)]);
			}
		}
		
		return objetos;
	}
	
	/**
	 * Atualiza a representa��o do {@link GameObject objeto} na matriz representativa do mapa.
	 * @param objeto Objeto a atualizar a posi��o. Este game object j� deve estar com sua posi��o atulizada.
	 * @param antigaPosicao {@link Vector2 Posi��o} antiga do game object.
	 * @param novaPosicao {@link Vector2 Posi��o} onde game object ficar� agora.
	 */
	public void AtualizaPosicaoMatriz(Vector2 antigaPosicao, Vector2 novaPosicao, GameObject objeto)
	{
		if (_matrizMapa == null) 
			return;
		
		_matrizMapa[Math.abs((int) (antigaPosicao.x % _precisaoMapaX))][Math.abs((int) (antigaPosicao.y % _precisaoMapaY))].remove(objeto);
		_matrizMapa[Math.abs((int) (novaPosicao.x % _precisaoMapaX))][Math.abs((int) (novaPosicao.y % _precisaoMapaY))].add(objeto);
	}
	
	/**
	 * Fun��o que libera os recursos da {@link Tela}
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
	 * Fun��o que reinicia a {@link Tela} do inicio.
	 */
	public void ReiniciaTela()
	{
		this.Encerrar();
		this.Iniciar();
	}
	
	/**
	 * @return {@link Color Cor} do fundo. Cor que � utilizada para limpar a tela a todo frame que esta tela est� no topo.
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