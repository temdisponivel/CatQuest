package classes.gameobjects.personagens;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.telas.FimJogo;

/**
 * 
 * @author matheus
 */
public class ObjetoQuebravel extends Personagem
{
	static float _vidaTotal = 0;
	static int _quantInstancia = 0;
	static public HashMap<Integer, ObjetoQuebravel> objetosquebraveis = new HashMap<Integer, ObjetoQuebravel>();
	
	public ObjetoQuebravel()
	{
		super();
		_quantInstancia++;
		objetosquebraveis.put(this.GetId(), this);
		_tipo = GameObjects.Cenario;
	}
	
	@Override
	public void Inicia()
	{
		super.Inicia();
		_vidaTotal = (_vidaTotal + _vida) / _quantInstancia;
	}
	
	@Override
	public void RecebeDano(float dano)
	{
		super.RecebeDano(dano);
		_vidaTotal -= Math.abs(dano - _defesa);

		if (_vidaTotal <= 0)
		{
			CatQuest.instancia.RetiraTela();
			CatQuest.instancia.AdicionaTela(new FimJogo(), false, false);
		}
	}

	@Override
	public void AoColidir(GameObject colidiu){};
	
	@Override
	public String toString()
	{
		return "ObjetoQuebravel";
	}

	@Override
	public void Morre()
	{
		this.Encerra();
	}
	
	/**
	 * Retorna o {@link ObjetoQuebravel objeto quebrável} mais próximo da posição informada.
	 * @param posicao {@link Vector2 Posição} para procurar pelo mais próximo.
	 * @return Objeto quebravel mais próximo.
	 */
	static public ObjetoQuebravel GetObjetoQuebravelMaisProximo(Vector2 posicao)
	{
		Iterator<ObjetoQuebravel> objetos = objetosquebraveis.values().iterator();
		ObjetoQuebravel retorno = null, aux = null;
		
		float menorDistancia = Integer.MAX_VALUE;
		while (objetos.hasNext())
		{
			aux = objetos.next();
			
			if (posicao.dst(aux.GetPosicao()) < menorDistancia)
			{
				retorno = aux;
				menorDistancia = posicao.dst(aux.GetPosicao());
			}
		}
		
		return retorno;
	}
}
