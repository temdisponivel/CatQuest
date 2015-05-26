package classes.gameobjects.personagens.herois;

import catquest.CatQuest;
import classes.gameobjects.personagens.ataque.Espadada;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Classe do herói Barbaro.
 * @author Victor
 *
 */
public class Barbaro extends Heroi
{

	public Barbaro()
	{
		super();
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local("sprites/heroi")));
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
		this.GetTela().InserirGameObject(new Espadada(this.GetPosicao(), _player.GetControle().GetDirecaoAtaque()));
	}
	
	@Override
	public String toString()
	{
		return "Barbaro";
	}
}
