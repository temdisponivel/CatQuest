package classes.gameobjects.personagens.inimigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import catquest.CatQuest;

/**
 * Classe do inimigo Cachorro.
 * 
 * @author matheus
 *
 */
public class Cachorro extends Inimigo
{
	public Cachorro()
	{
		super();
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local("sprites/inimigo")));
		_fuzzyDistancia = new Fuzzyficacao(new TrianguloFuzzy(-30, 30), new TrianguloFuzzy(0, 500), new TrianguloFuzzy(250, 1000));
	}

	@Override
	public String toString()
	{
		return "Cachorro";
	}

	@Override
	protected void Ataque()
	{
	}
}
