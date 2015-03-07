package classe;

import java.util.HashMap;
import java.util.function.Consumer;

import catquest.CatQuest;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tela
{
	static private HashMap<Integer, ListaGameObeject> _listasGameObject = null;
	
	public void Iniciar(CatQuest jogo)
	{
		_listasGameObject = new HashMap<Integer, ListaGameObeject>();
	}
	
	public void Atualiza(final float deltaTime)
	{
		//para cada lista das camadas
		_listasGameObject.values().forEach(new Consumer<ListaGameObeject>()
		{
			@Override
			public void accept(ListaGameObeject lista)
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
	}
	
	public void Desenha(final SpriteBatch spriteBash)
	{
		//para cada lista das camadas
		_listasGameObject.values().forEach(new Consumer<ListaGameObeject>()
		{
			@Override
			public void accept(ListaGameObeject lista)
			{
				//para cada gameobject de cada lista
				lista.values().forEach(new Consumer<GameObject>()
				{
					@Override
					public void accept(GameObject gameObject)
					{
						gameObject.Desenha(spriteBash);
					}
				});
			}
		});
	}
	
	public void InserirGameObject(GameObject gameObject)
	{
		if (gameObject.GetCamada() > _listasGameObject.size())
			_listasGameObject.put(gameObject.GetCamada(), new ListaGameObeject());
		
		_listasGameObject.get(gameObject.GetCamada()).Adicionar(gameObject);
	}
	
	public void AtualizaCamadaGameObject(GameObject gameObject)
	{
		_listasGameObject.get(gameObject.GetCamada()).Remover(gameObject);
		InserirGameObject(gameObject);
	}
	
	public abstract void Encerrar();
}