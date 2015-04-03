package classes;

import java.util.HashMap;
import java.util.Map.Entry;

import util.Camada;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tela
{
	static private HashMap<Camada, ListaGameObject> _listasGameObject = null;
	static private boolean _desenha = true, _atualiza = true;
	
	public void Iniciar()
	{
		_listasGameObject = new HashMap<Camada, ListaGameObject>();
	}
	
	public void Atualiza(final float deltaTime)
	{
		if (!_atualiza)
			return;
		
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			if (!entrada.getKey().GetAtiva())
				continue;
			
			for (Entry<Integer, GameObject> entrada2 : entrada.getValue().entrySet())
			{
				entrada2.getValue().Atualiza(deltaTime);
			}
		}
	}
	
	public void Desenha(final SpriteBatch spriteBatch)
	{
		if (!_desenha)
			return;
		
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			if (!entrada.getKey().GetAtiva())
				continue;
			
			for (Entry<Integer, GameObject> entrada2 : entrada.getValue().entrySet())
			{
				entrada2.getValue().Desenha(spriteBatch);
			}
		}
	}
	
	public void InserirGameObject(GameObject gameObject)
	{
		if (gameObject.GetCamada().hashCode() > _listasGameObject.size())
			_listasGameObject.put(gameObject.GetCamada(), new ListaGameObject());
		
		_listasGameObject.get(gameObject.GetCamada()).Adicionar(gameObject);
		gameObject.SetTela(this);
	}
	
	public void AtualizaCamadaGameObject(GameObject gameObject)
	{
		_listasGameObject.get(gameObject.GetCamada()).Remover(gameObject);
		InserirGameObject(gameObject);
	}
	
	public boolean GetDesenha()
	{
		return _desenha;
	}
	
	public boolean GetAtualiza()
	{
		return _atualiza;
	}
	
	public void SetDesenha(boolean desenha)
	{
		_desenha = desenha;
	}
	
	public void SetAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}
	
	public abstract void Encerrar();
}