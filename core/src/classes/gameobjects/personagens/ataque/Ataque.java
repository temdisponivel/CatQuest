package classes.gameobjects.personagens.ataque;

import com.badlogic.gdx.math.Vector2;

import classes.gameobjects.GameObject;
import classes.gameobjects.cenario.ObjetoCenario;
import classes.gameobjects.personagens.Personagem;
import classes.gameobjects.personagens.herois.Heroi;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.controle.Controle.Direcoes;

/**
 * Classe de ataques e projéteis.
 * 
 * @author Victor
 *
 */

public abstract class Ataque extends Personagem
{
	protected int _direcao;
	protected Vector2 _posicaoInicial;

	public Ataque()
	{
		super();
	}

	public Ataque(Vector2 posicao, int direcao)
	{
		this();
		_posicaoInicial = posicao.cpy();
		_direcao = direcao;
		_tipo = GameObjects.Ataque;

		// TODO Corrigir o posicionamento do ataque baseado na direcao do heroi.
		// >_>
		switch (_direcao)
		{
		case Direcoes.CIMA:
			_posicaoInicial.y += 30;
			break;
		case Direcoes.BAIXO:
			_posicaoInicial.y -= 30;
			break;
		case Direcoes.ESQUERDA:
			_posicaoInicial.x -= 30;
			break;
		case Direcoes.DIREITA:
			_posicaoInicial.x += 30;
		}

	}

	@Override
	public void Inicia()
	{
		super.Inicia();

		this.SetPosicao(_posicaoInicial);
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
