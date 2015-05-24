//TODO: validar desempenho com esse monte de for each do java

package classes.telas;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import classes.gameobjects.GameObject;
import classes.gameobjects.cenario.ObjetoCenario;
import classes.uteis.*;
import classes.uteis.UI.Imagem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
	protected float _precisaoMapaX = 8f;
	protected float _precisaoMapaY = 8f;
	protected float _alturaMapa = 0;
	protected float _larguraMapa = 0;
	protected FileHandle _arquivoMapa = null;
	
	/**
	 * Cria uma nova tela.
	 */
	public Tela(){};
	
	/**
	 * Cria uma tela com um arquivo de mapa e carrega o mapa.
	 * @param arquivo {@link FileHandle Arquivo} do mapa.
	 */
	public Tela(FileHandle arquivo)
	{
		_arquivoMapa = arquivo;
	}
	
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
		this.CarregaMapa();
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
				gameObject.SetTela(this);
				gameObject.Inicia();
				
				if (_matrizMapa != null)
				{
					this.InserirNaMatriz(gameObject, null);
				}
			}
			
			_gameObjectsIncluir.clear();
		}
		
		if (_removeGameObject)
		{
			_removeGameObject = false;
			
			for (GameObject remover : _gameObjectsExcluir)
			{
				ListaGameObject listaTemp;
				
				if ((listaTemp = _listasGameObject.get(remover.GetCamada())) != null)
				{
					listaTemp.Remover(remover);
					
					this.RemoverDaMatriz(remover, null);
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
	 * Fun��o que insere v�rios novos gameobjects na tela.
	 * @param gameObject Vetor de {@link GameObject} a ser inserido.
	 */
	public void InserirGameObject(GameObject... gameObject)
	{
		for (int i = 0; i < gameObject.length; i++)
			_gameObjectsIncluir.add(gameObject[i]);
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
	 * @param campo {@link Rectangle Tamanho} do campo para pegar os {@link GameObject game objects} que est�o dentro.  X e Y do Rectangle � utilizado como zero zero - canto inferior esquerdo.
	 * @return {@link LinkedList<GameObject> Lista} com os objetos que est�o nesta regi�o. Ou nulo caso n�o haja objetos.
	 */
	public LinkedList<GameObject> GetObjetosRegiao(Rectangle campo)
	{
		if (_matrizMapa == null)
			return null;
		
		if ((campo.x < 0 || campo.y < 0) || (campo.x + campo.width > _larguraMapa || campo.y + campo.height > _alturaMapa))
			return null;
		
		LinkedList<GameObject> objetos = new LinkedList<GameObject>();
		int quantidadeX = (int) (campo.width / _precisaoMapaX);
		int quantidadeY = (int) (campo.height / _precisaoMapaY);		
		
		for (int x = 0, i = 0; x <= quantidadeX; x++)
		{
			for (int y = 0, j = 0; y <= quantidadeY; y++)
			{
				i = Math.abs((int) (campo.x / _precisaoMapaX) + x);
				j = Math.abs((int) (campo.y / _precisaoMapaY) + y);
				
				if (i < _matrizMapa.length && j < _matrizMapa[i].length)
					objetos.addAll(_matrizMapa[i][j]);
				else
					return null;
			}
		}
		
		return objetos;
	}
	
	/**
	 * Atualiza a representa��o do {@link GameObject objeto} na matriz representativa do mapa.
	 * @param objeto {@link GameObject Objeto} a atualizar a posi��o.
	 * @param novaPosicao {@link Vector2 Posi��o} onde game object ficar� agora.
	 * @param antigaPosicao {@link Vector2 Posi��o} antiga do game object.
	 */
	public void AtualizaPosicaoMatriz(GameObject objeto, Vector2 novaPosicao, Vector2 antigaPosicao)
	{
		if (_matrizMapa == null) 
			return;
		
		this.RemoverDaMatriz(objeto, antigaPosicao);
		this.InserirNaMatriz(objeto, novaPosicao);
	}
	
	/**
	 * Insere um {@link GameObject objeto} na matriz representativa do mapa.
	 * @param objeto {@link GameObject GameObject} para inserir.
	 * @param posicao {@link Vector2 Posi��o} dele no mapa. Pode ser nulo para {@link GameObject#GetPosicao() pegar} a posi��o do objeto.
	 * @return True caso tenha inserido. Falso caso contr�rio.
	 */
	public boolean InserirNaMatriz(GameObject objeto, Vector2 posicao)
	{
		if (_matrizMapa == null) 
			return false;
		
		int quantidadeX = (int) Math.abs((objeto.GetLargura() / _precisaoMapaX));
		int quantidadeY = (int) Math.abs((objeto.GetAltura() / _precisaoMapaY));
		
		if (posicao == null)
			posicao = objeto.GetPosicao();
		
		for (int x = 0, i = 0; x <= quantidadeX; x++)
		{
			for (int y = 0, j = 0; y <= quantidadeY; y++)
			{
				i = Math.abs((int) (posicao.x / _precisaoMapaX) + x);
				j = Math.abs((int) (posicao.y / _precisaoMapaY) + y);
				
				if (i < _matrizMapa.length && j < _matrizMapa[i].length)
					_matrizMapa[i][j].add(objeto);
			}
		}
		
		return true;
	}
	
	/**
	 * Remove um {@link GameObject objeto} da matriz representativa do mapa.
	 * @param objeto {@link GameObject GameObject} para remover.
	 * @param posicao {@link Vector2 Posi��o} dele no mapa. Pode ser nulo para {@link GameObject#GetPosicao() pegar} a posi��o do objeto.
	 * @return True caso tenha removido. Falso caso contr�rio.
	 */
	public boolean RemoverDaMatriz(GameObject objeto, Vector2 posicao)
	{
		if (_matrizMapa == null) 
			return false;
		
		int quantidadeX = (int) Math.abs((objeto.GetLargura() / _precisaoMapaX));
		int quantidadeY = (int) Math.abs((objeto.GetAltura() / _precisaoMapaY));
		
		if (posicao == null)
			posicao = objeto.GetPosicao();
		
		for (int x = 0, i = 0; x <= quantidadeX; x++)
		{
			for (int y = 0, j = 0; y <= quantidadeY; y++)
			{
				i = Math.abs((int) (posicao.x / _precisaoMapaX) + x);
				j = Math.abs((int) (posicao.y / _precisaoMapaY) + y);
				
				if (i < _matrizMapa.length && j < _matrizMapa[i].length)
					_matrizMapa[i][j].remove(objeto);
			}
		}
		
		return true;
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
	
	//TODO: terminar implementa��o
	/**
	 * Carrega um mapa .TMX e coloca os objetos nas suas posi��es.
	 */
	public void CarregaMapa()
	{
		if (_arquivoMapa == null)
			return;
		
		TiledMap mapa = new TmxMapLoader(new LocalFileHandleResolver()).load(_arquivoMapa.toString());
		MapLayers camadas = mapa.getLayers();
		MapObjects objetos = null;
		Imagem fundo = null;
		
		if (mapa.getProperties().containsKey("Textura"))
		{
			this.InserirGameObject(fundo = new Imagem(Gdx.files.local(mapa.getProperties().get("Textura").toString())));
			_larguraMapa = fundo.GetLargura();
			_alturaMapa = fundo.GetAltura();
		}
		
		if (mapa.getProperties().containsKey("largura"))
			_larguraMapa = mapa.getProperties().get("largura", float.class);
		
		if (mapa.getProperties().containsKey("altura"))
			_alturaMapa = mapa.getProperties().get("altura", float.class);
		
		this.CriaMatriz();
		
		//para cada camada do mapa
		for (int i = 0; i < camadas.getCount(); i++)
		{
			//pega a lista de objetos
			objetos = camadas.get(i).getObjects();
			
			//para cada lista de objetos
			for (int j = 0; j < objetos.getCount(); j++)
			{
				MapObject obj = objetos.get(j);
				MapProperties prop = obj.getProperties();
				
				//se tem uma classe definida
				if (prop.containsKey("Classe"))
				{
					try
					{
						//pega a classe
						Class<?> classe = Class.forName((String) prop.get("Classe"));
						
						//valida se � subclasse de gameobject
						Class<? extends GameObject> subclasse = classe.asSubclass(GameObject.class);
						
						GameObject objeto = subclasse.newInstance();
						objeto.SetPosicao(new Vector2(prop.get("x", Float.class), prop.get("y", Float.class)));
						this.InserirGameObject(objeto);
						continue;
					}
					catch (Exception e)
					{
						Log.instancia.Logar("Tentar carregar objeto do mapa sem sucesso!", e, false);
					}
				}
				
				//Se n�o existe as propriedades necess�rias, cria padr�o
				if (!prop.containsKey("rotation"))
					prop.put("rotation", 0f);
				
				if (!prop.containsKey("Textura"))
					prop.put("Textura", "");
				
				if (obj instanceof RectangleMapObject)
					this.InserirGameObject(new ObjetoCenario(new Vector2(prop.get("x", Float.class), prop.get("y", Float.class)), 
							prop.get("Textura").toString(), ((RectangleMapObject) obj).getRectangle(), prop.get("rotation", Float.class)));
				else
					this.InserirGameObject(new ObjetoCenario(new Vector2(prop.get("x", Float.class), prop.get("y", Float.class)), 
							prop.get("Textura").toString(), null, prop.get("rotation", Float.class)));
			}
		}
		
		mapa.dispose();
	}
	
	/**
	 * Cria a matriz de representa��o do mapa.
	 */
	@SuppressWarnings("unchecked")
	protected void CriaMatriz()
	{
		_matrizMapa = new LinkedList[(int)(_larguraMapa / _precisaoMapaX)][(int)(_alturaMapa / _precisaoMapaY)];
		
		for (int i = 0; i < _matrizMapa.length; i++)
		{
			for (int j = 0; j < _matrizMapa[i].length; j++)
			{
				_matrizMapa[i][j] = new LinkedList<GameObject>();
			}
		}
	}
	
	/** 
	 * @return Largura do mapa da tela.
	 */
	public float GetAlturaMapa()
	{
		return _alturaMapa;
	}
	
	/** 
	 * @return Largura do mapa da tela.
	 */
	public float GetLarguraMapa()
	{
		return _larguraMapa;
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
	
	/**
	 * A precis�o com que a matriz representativa do mapa est� utilizando no eixo X.
	 * @return Valor da precis�o.
	 */
	public float GetPrecisaoMapaX()
	{
		return _precisaoMapaX;
	}
	
	/**
	 * A precis�o com que a matriz representativa do mapa est� utilizando no eixo Y.
	 * @return Valor da precis�o.
	 */
	public float GetPrecisaoMapaY()
	{
		return _precisaoMapaY;
	}
	
	@Override
	public int hashCode()
	{
		return _tipo.ordinal();
	}
}