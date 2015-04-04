package classes.telas;

import java.util.HashMap;
import java.util.Map.Entry;
import classes.gameobjects.GameObject;
import classes.uteis.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classe base para todas as telas. Tela � o que guarda e gerencia todos os gameobjects que est�o sendo manipulados pelo jogo.
 * @author Matheus
 *
 */
public class Tela
{
	protected HashMap<Camada, ListaGameObject> _listasGameObject = null;
	protected boolean _desenha = true, _atualiza = true;
	
	/**
	 * Fun��o que inicia as propriedades da tela.
	 */
	public void Iniciar()
	{
		_listasGameObject = new HashMap<Camada, ListaGameObject>();
	}
	
	/**
	 * Fun��o que roda a rotina de atualizar os gameobjects da tela, colis�es, etc.
	 * @param deltaTime Diferen�a dos tempos dos dois ultimos gameloops.
	 */
	public void Atualiza(final float deltaTime)
	{
		/* ------------ atualiza -------------------- */
		
		for (Entry<Camada, ListaGameObject> entrada : _listasGameObject.entrySet())
		{
			if (!entrada.getKey().GetAtualiza() || !_atualiza || !entrada.getValue().GetAtiva())
				continue;
			
			for (Entry<Integer, GameObject> entrada2 : entrada.getValue().entrySet())
			{
				//caso o meu gameobject esteja adicionado a uma camada que n�o � a dela
				//ou seja, caso este gameobject tenha atualizado a camada, removemos da cadamada errada e readicionamos na nova
				//s� atualizamos ele no proximo loop
				if (entrada2.getValue().GetCamada() != entrada.getKey())
				{
					entrada.getValue().Remover(entrada2.getValue());
					this.InserirGameObject(entrada2.getValue());
				}
					
				entrada2.getValue().Atualiza(deltaTime);
			}
		}
		
		//TODO: implementar colisoes. Pensar se � melhor crar uma interface de colidiveis com a lista de colidiveis e pa
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
	 * Fun��o que insere um novo gameobject na tela.
	 * @param gameObject {@link GameObject} a ser inserido.
	 */
	public void InserirGameObject(GameObject gameObject)
	{
		if (gameObject.GetCamada().hashCode() > _listasGameObject.size())
			_listasGameObject.put(gameObject.GetCamada(), new ListaGameObject());
		
		_listasGameObject.get(gameObject.GetCamada()).Adicionar(gameObject);
		gameObject.SetTela(this);
	}
	
	/**
	 * Fun��o que remove um {@link GameObject} da tela.
	 * @param remover {@link GameObject} a ser removido.
	 * @return O objeto removido.
	 */
	public GameObject Remover(GameObject remover)
	{
		ListaGameObject listaTemp;
		if ((listaTemp = _listasGameObject.get(remover.GetCamada())) != null)
			listaTemp.Remover(remover);
		else
			remover = null;
		
		return remover;
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
	}
	
	/**
	 * Fun��o que reinicia a {@link Tela} do inicio.
	 */
	public void ReiniciaTela()
	{
		this.Encerrar();
		this.Iniciar();
	}
}