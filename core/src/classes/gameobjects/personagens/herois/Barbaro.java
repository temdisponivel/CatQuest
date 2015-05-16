package classes.gameobjects.personagens.herois;

import catquest.CatQuest;
import classes.gameobjects.GameObject.GameObjects;
import classes.uteis.Camada;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Classe do herói Barbaro.
 * @author victor
 *
 */
public class Barbaro extends Heroi
{

	public Barbaro()
	{
		super();
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local("sprites/heroi")));
		
		_vida = 150;
		_ataque = 10;
		_defesa = 15;
		_agilidade = 7;
		
	}

	@Override
	public String toString()
	{
		return "Barbaro";
	}

	@Override
	protected void Acao()
	{
		// TODO Auto-generated method stub
		
		
	}

	@Override
	protected void HabilidadeAtiva()
	{
		// TODO Auto-generated method stub
		
		
	}

	@Override
	protected void AtaqueBasico()
	{
		// TODO Auto-generated method stub
		
	}
	
}
