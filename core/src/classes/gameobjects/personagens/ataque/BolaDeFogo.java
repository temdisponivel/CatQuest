package classes.gameobjects.personagens.ataque;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.gameobjects.cenario.ObjetoCenario;
import classes.gameobjects.personagens.herois.Heroi;
import classes.gameobjects.personagens.inimigos.Inimigo;


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
		
		this.CorrecaoSpriteAtaque();
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

	@Override
	public String toString()
	{
		return "BolaDeFogo";
	}




}
