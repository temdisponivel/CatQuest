package classe;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import util.Camada;
import catquest.CatQuest;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.swing.internal.plaf.basic.resources.basic;

public abstract class Tela
{
	static private HashMap<Camada, ListaGameObject> _listasGameObject = null;
	
	public void Iniciar()
	{
		_listasGameObject = new HashMap<Camada, ListaGameObject>();
	}
	
	public void Atualiza(final float deltaTime)
	{
		
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			for (Entry<Integer, GameObject> entradaGameObject : entrada.getValue().entrySet())
			{
				entradaGameObject.getValue().Atualiza(deltaTime);
			}
		}
		
		/*
		//para cada lista das camadas
		_listasGameObject.values().forEach(new Consumer<ListaGameObject>()
		{
			@Override
			public void accept(ListaGameObject lista)
			{
				//para cada gameobject de cada lista
				lista.values().forEach(new Consumer<GameObject>()
				{
					@Override
					public void accept(GameObject gameObject)
					{
						gameObject.Atualiza(deltaTime);
					}
				});
			}
		});
		*/
	}
	
	public void Desenha(final SpriteBatch spriteBatch)
	{
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			for (Entry<Integer, GameObject> entradaGameObject : entrada.getValue().entrySet())
			{
				spriteBatch.setColor(entradaGameObject.getValue().GetCamada().GetCor());
				entradaGameObject.getValue().Desenha(spriteBatch);
			}
		}
		
		/*
		//para cada lista das camadas
		_listasGameObject.forEach(new Consumer<ListaGameObject>()
		{
			@Override
			public void accept(ListaGameObject lista)
			{
				//para cada gameobject de cada lista
				lista.values().forEach(new Consumer<GameObject>()
				{
					@Override
					public void accept(GameObject gameObject)
					{
						spriteBatch.setColor(gameObject.GetCamada().GetCor());
						gameObject.Desenha(spriteBatch);
					}
				});
			}
		});
		*/
	}
	
	public void InserirGameObject(GameObject gameObject)
	{
		if (gameObject.GetCamada().hashCode() >= _listasGameObject.size())
			_listasGameObject.put(gameObject.GetCamada(), new ListaGameObject());
		
		_listasGameObject.get(gameObject.GetCamada()).Adicionar(gameObject);
	}
	
	public void AtualizaCamadaGameObject(GameObject gameObject)
	{
		_listasGameObject.get(gameObject.GetCamada()).Remover(gameObject);
		InserirGameObject(gameObject);
	}
	
	public abstract void Encerrar();
}