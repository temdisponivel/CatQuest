package classe;

import java.util.TreeMap;

public class ListaGameObject extends TreeMap<Integer, GameObject>
{
	private static final long serialVersionUID = -4729550419524770454L;
	
	public ListaGameObject()
	{
	}
	
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
}
