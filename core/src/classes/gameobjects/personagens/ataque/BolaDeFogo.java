package classes.gameobjects.personagens.ataque;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.gameobjects.cenario.ObjetoCenario;
import classes.gameobjects.personagens.Personagem;
import classes.gameobjects.personagens.herois.Heroi;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.controle.Controle.Direcoes;


/**
 * Classe de BolaDeFogo (Mago)
 * 
 * @author Victor
 *
 *         Se move em linha reta e ao colidir coloca fogo no ch�o.
 */
public class BolaDeFogo extends Ataque
{

	public BolaDeFogo(Vector2 posicao, int direcao, float alturaHeroi, float larguraHeroi)
	{
		super(posicao, direcao, alturaHeroi, larguraHeroi);
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local("sprites/boladefogo")));
		
		_sprite.setOrigin(_sprite.getWidth()/2, _sprite.getHeight()/2);
		switch (direcao)
		{
		case Direcoes.CIMA:
			_sprite.rotate90(false);
			break;
		case Direcoes.BAIXO:
			_sprite.rotate90(true);
			break;
		case Direcoes.ESQUERDA:
			_sprite.rotate90(true);
			_sprite.rotate90(true);
			break;
		case Direcoes.DIREITA:
			break;
		case Direcoes.NORDESTE:
			_sprite.rotate(-30);
		case Direcoes.NOROESTE:
			_sprite.rotate(-120);
			break;
		case Direcoes.SUDESTE:
			_sprite.rotate(-30);
		case Direcoes.SUDOESTE:
			_sprite.rotate90(true);
			_sprite.rotate(-30);
			break;
		default:
			break;
		}
		
		this.CorrecaoSpriteAtaque();
		
		_colidiveis.put(GameObjects.Inimigo, Colisoes.Passavel);
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		this.MovimentaAtaque(deltaTime);
	}

	@Override
	protected void MovimentaAtaque(float deltaTime)
	{
		// Movimenta em linha reta a partir da dire��o do heroi.
		this.Movimenta(_direcao, deltaTime, true);

	}

	@Override
	public void Morre()
	{
		// TODO Criar um campo de fogo aonde a bola morreu.
		this.Encerra();
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
		if (colidiu instanceof Inimigo)
		{
			this.InflingeDano((Personagem) colidiu);
		}
		else if (colidiu instanceof Heroi)
		{
			// O que acontece quando colide com Heroi.

		}
		else if (colidiu instanceof ObjetoCenario)
		{
			this.Encerra();
		}
	}

	@Override
	public String toString()
	{
		return "BolaDeFogo";
	}




}
