package classes.gameobjects.personagens.inimigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.uteis.Camada;

/**
 * Classe do inimigo Cachorro.
 * @author matheus
 *
 */
public class Cachorro extends Inimigo
{
	static int i = 0;
	
	public Cachorro()
	{
		super();
		_camada = Camada.Personagens;
		_tipo = GameObjects.Inimigo;
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local("sprites/inimigo")));
		_agilidade = 8;
	}
	
	@Override
	public String toString()
	{
		return "Cachorro";
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
	}
}
