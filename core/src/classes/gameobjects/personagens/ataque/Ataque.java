package classes.gameobjects.personagens.ataque;


import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.gameobjects.cenario.ObjetoCenario;
import classes.gameobjects.personagens.Personagem;
import classes.gameobjects.personagens.herois.Heroi;
import classes.gameobjects.personagens.inimigos.Inimigo;

import classes.uteis.controle.Controle.Direcoes;


/**
 * Classe de ataques e proj�teis.
 * 
 * @author Victor
 *
 */

public abstract class Ataque extends Personagem
{
	protected int _direcao;
	protected float _alturaHeroi, _larguraHeroi;
	protected float _tempoAtaque;
	protected Vector2 _posicaoInicial;

	public Ataque()
	{
		super();
	}

	public Ataque(Vector2 posicao, int direcao, float alturaHeroi, float larguraHeroi)
	{
		this();
		_posicaoInicial = posicao.cpy();
		_alturaHeroi = alturaHeroi;
		_larguraHeroi = larguraHeroi;
		this.CorrecaoSpriteAtaque();
		
		_tempoAtaque = CatQuest.instancia.GetTempoJogo();
		
		_direcao = direcao;
		_tipo = GameObjects.Ataque;
		

		
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
		if (colidiu instanceof Inimigo)
		{
			// O que acontece quando colide com Inimigo.
			this.Morre();

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
	
	protected void CorrecaoSpriteAtaque(){
		switch (_direcao)
		{
		case Direcoes.CIMA:
			_posicaoInicial.y += _alturaHeroi;
			break;
		case Direcoes.BAIXO:
			_posicaoInicial.y -= _alturaHeroi;
			break;
		case Direcoes.ESQUERDA:
			_posicaoInicial.x -= _larguraHeroi;
			break;
		case Direcoes.DIREITA:
			_posicaoInicial.x += _larguraHeroi;
			break;
		case Direcoes.NORDESTE:
			_posicaoInicial.x += _larguraHeroi;
			_posicaoInicial.y += _alturaHeroi;
			break;
		case Direcoes.NOROESTE:
			_posicaoInicial.x -= _larguraHeroi;
			_posicaoInicial.y += _alturaHeroi;
			break;
		case Direcoes.SUDESTE:
			_posicaoInicial.x += _larguraHeroi;
			_posicaoInicial.y -= _alturaHeroi;
			break;
		case Direcoes.SUDOESTE:
			_posicaoInicial.x -= _larguraHeroi;
			_posicaoInicial.y -= _alturaHeroi;
			break;
			
		}
	}

	protected abstract void MovimentaAtaque(float deltaTime);

	protected void Atualiza()
	{


	}

}
