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
	public Cachorro()
	{
		super();
		_camada = Camada.Personagens;
		_tipo = GameObjects.Inimigo;
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local("sprites/inimigo")));
		_agilidade = 10;
	}
	
	@Override
	public void Inicia()
	{
		super.Inicia();
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
	}

	@Override
	public Colisoes AoColidir(GameObject colidiu)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString()
	{
		return "Cachorro";
	}
}
