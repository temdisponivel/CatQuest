package classes.telas;

import java.util.HashMap;
import java.util.Map.Entry;
import classes.gameobjects.GameObject;
import classes.uteis.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classe base para todas as telas. Tela é o que guarda e gerencia todos os gameobjects que estão sendo manipulados pelo jogo.
 * @author Matheus
 *
 */
public class Tela
{
	private HashMap<Camada, ListaGameObject> _listasGameObject = null;
	private boolean _desenha = true, _atualiza = true;
	
	/**
	 * Função que inicia as propriedades da tela.
	 */
	public void Iniciar()
	{
		_listasGameObject = new HashMap<Camada, ListaGameObject>();
	}
	
	public void Atualiza(final float deltaTime)
	{
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			if (!entrada.getKey().GetAtiva() || !_atualiza)
				continue;
			
			for (Entry<Integer, GameObject> entrada2 : entrada.getValue().entrySet())
			{
				entrada2.getValue().Atualiza(deltaTime);
			}
		}
	}
	
	public void Desenha(final SpriteBatch spriteBatch)
	{		
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			if (!entrada.getKey().GetAtiva() || !_desenha)
				continue;
			
			spriteBatch.setColor(entrada.getKey().GetCor());
			
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
		this.InserirGameObject(gameObject);
	}
	
	public boolean GetSeDesenha()
	{
		return _desenha;
	}
	
	public boolean GetSeAtualiza()
	{
		return _atualiza;
	}
	
	public void SetSeDesenha(boolean desenha)
	{
		_desenha = desenha;
	}
	
	public void SetSeAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}
	
	
	public void SetAtiva(boolean ativa)
	{
		_desenha = ativa;
		_atualiza = ativa;
	}
	
	public boolean GetAtiva()
	{
		return _desenha && _atualiza;
	}
	
	public void Encerrar()
	{
		_atualiza = false;
		_desenha = false;

		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			for (Entry<Integer, GameObject> entrada2 : entrada.getValue().entrySet())
			{
				entrada2.getValue().Encerra();
			}
		}
		
		_listasGameObject.clear();
	}
	
	public void ReiniciaTela()
	{
		this.Encerrar();
		this.Iniciar();
	}
}