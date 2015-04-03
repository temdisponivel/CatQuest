package classe;

import java.util.HashMap;

public class ListaGameObject extends HashMap<Integer, GameObject>
{
	private static final long serialVersionUID = 7807392367834454439L;
	static boolean _ativa = true;
	
	public ListaGameObject(){};
	
	public ListaGameObject(GameObject gameObject)
	{
		Adicionar(gameObject);
	}
	
	public GameObject Adicionar(GameObject gameObject)
	{
		return super.put(gameObject.GetId(), gameObject);
	}
	
	public GameObject Remover(GameObject gameObject)
	{
		return super.remove(gameObject.GetId()); 
	}
	
	public boolean GetAtiva()
	{
		return _ativa;
	}
	
	public void SetAtiva(boolean ativa)
	{
		_ativa = ativa;
	}
}
