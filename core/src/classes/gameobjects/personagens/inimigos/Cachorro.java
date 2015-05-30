package classes.gameobjects.personagens.inimigos;

import catquest.CatQuest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
		//_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local("sprites/inimigo")));
		
		TextureRegion texturaAndando = CatQuest.instancia.GetTextura(Gdx.files.local("sprites/cachorrin"));
		TextureRegion[][] framesTemp = texturaAndando.split(texturaAndando.getRegionWidth()/16, texturaAndando.getRegionHeight()/1);
		TextureRegion[] frames = new TextureRegion[16];
		
		int indice = 0;
		for (int i = 0; i < framesTemp.length; i++)
			for (int j = 0; j < framesTemp[i].length; j++)
				frames[indice++] = framesTemp[i][j];
		
		Animation andando = new Animation(0.2f, frames);
		this.IncluirAnimacao(AnimacaoInimigo.Movimento, andando);
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
