package classes.uteis;

import java.util.HashMap;
import classes.gameobjects.GameObject;

/**
 * Um HashMap com chave sendo o ID do {@link GameObject} e o valor sendo o {@link GameObject}.
 * Usada nas listas de GameObject das telas. Insersão, busca e remoção tem custo O(1).
 * @author Matheus
 *
 */
public class ListaGameObject extends HashMap<Integer, GameObject>
{
	private static final long serialVersionUID = 7807392367834454439L;
	private static boolean _ativa = true;
	
	/**
	 * Cria uma nova lista vazia.
	 */
	public ListaGameObject(){};
	
	/**
	 * Cria uma lista e adiciona um objeto nela.
	 * @param gameObject GameObject a incluir na nova lista.
	 */
	public ListaGameObject(GameObject gameObject)
	{
		Adicionar(gameObject);
	}
	
	/**
	 * Adiciona um {@link GameObject} na lista.
	 * @param gameObject GameObject a inserir. 
	 * @return O próprio GameObject. Se for nulo, não é inserido.
	 */
	public GameObject Adicionar(GameObject gameObject)
	{
		if (gameObject != null)
			super.put(gameObject.GetId(), gameObject);
		
		return gameObject;
	}
	
	/**
	 * Remove um {@link GameObject} da lista.
	 * @param gameObject GameObject a remover. 
	 * @return O próprio GameObject. Se for nulo, não é removido.
	 */
	public GameObject Remover(GameObject gameObject)
	{
		if (gameObject != null)
			super.remove(gameObject.GetId());
		
		return gameObject;
	}
	
	/**
	 * Retorna se a lista está ativa.
	 * @return True se está ativa.
	 */
	public boolean GetAtiva()
	{
		return _ativa;
	}
	
	/**
	 * Define se a lista está ativa ou não. 
	 * @param ativa True para ativar.
	 */
	public void SetAtiva(boolean ativa)
	{
		_ativa = ativa;
	}
}
