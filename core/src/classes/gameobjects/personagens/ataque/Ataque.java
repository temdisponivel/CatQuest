package classes.gameobjects.personagens.ataque;

import com.badlogic.gdx.math.Vector2;

import classes.gameobjects.GameObject;
import classes.gameobjects.cenario.ObjetoCenario;
import classes.gameobjects.personagens.Personagem;
import classes.gameobjects.personagens.herois.Heroi;
import classes.gameobjects.personagens.inimigos.Inimigo;

/**
 * Classe de ataques e projéteis.
 * 
 * @author Victor
 *
 */

public abstract class Ataque extends Personagem
{
	
	protected Vector2 _posicao;
	protected int _direcao;
	
	public Ataque()
	{
		super();
	}

	public Ataque(Vector2 posicao, int direcao)
	{
		_posicao = posicao;
		_direcao = direcao;
	}
	
	
	@Override
	public void Morre()
	{
		this.Encerra();
	}
	
	@Override
	public void AoColidir(GameObject colidiu)
	{
		_colidido = true;

		if (colidiu instanceof Inimigo)
		{
			// O que acontece quando colide com Inimigo.

		}
		else if (colidiu instanceof Heroi)
		{
			// O que acontece quando colide com Heroi.

		}
		else if (colidiu instanceof ObjetoCenario)
		{
			this.Morre();
		}
	}
	
	protected abstract void MovimentaAtaque(float deltaTime);

	protected void Atualiza()
	{
		// TODO Auto-generated method stub
		
	}

}
