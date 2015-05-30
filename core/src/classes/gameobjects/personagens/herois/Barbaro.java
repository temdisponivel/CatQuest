package classes.gameobjects.personagens.herois;

import catquest.CatQuest;
import classes.gameobjects.personagens.ataque.Espadada;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Classe do her�i Barbaro.
 * @author Victor
 *
 */
public class Barbaro extends Heroi
{

	public Barbaro()
	{
		super();
		
		TextureRegion texturaAndando = CatQuest.instancia.GetTextura(Gdx.files.local("sprites/herois/barbaro"));
		TextureRegion[][] framesTemp = texturaAndando.split(texturaAndando.getRegionWidth()/16, texturaAndando.getRegionHeight()/1);
		TextureRegion[] frames = new TextureRegion[16];
		
		int indice = 0;
		for (int i = 0; i < framesTemp.length; i++)
			for (int j = 0; j < framesTemp[i].length; j++)
				frames[indice++] = framesTemp[i][j];
		
		Animation andando = new Animation(0.2f, frames);
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
		
		this.GetTela().InserirGameObject(new Espadada(this.GetPosicao(), _player.GetControle().GetDirecaoAtaque(), _sprite.getHeight(), _sprite.getWidth()));
	}
	
	@Override
	public String toString()
	{
		return "Barbaro";
	}
}
