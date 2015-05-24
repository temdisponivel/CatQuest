package classes.gameobjects.personagens.herois;

import catquest.CatQuest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
		
		TextureRegion texturaAndando = CatQuest.instancia.GetTextura(Gdx.files.local("sprites/barbaro"));
		TextureRegion[][] framesTemp = texturaAndando.split(texturaAndando.getRegionWidth()/3, texturaAndando.getRegionHeight()/1);
		TextureRegion[] frames = new TextureRegion[3];
		
		int indice = 0;
		for (int i = 0; i < framesTemp.length; i++)
			for (int j = 0; j < framesTemp[i].length; j++)
				frames[indice++] = framesTemp[i][j];
		
		Animation andando = new Animation(0.5f, frames);
		this.IncluirAnimacao(AnimacaoHeroi.Movimento, andando);
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
	
	@Override
	public String toString()
	{
		return "Barbaro";
	}
}
