package classes.telas;

import classes.gameobjects.personagens.herois.Heroi;
import classes.uteis.ControladorCamera;
import classes.uteis.FabricaInimigo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

/**
 * Classe da tela onde o gameplay acontece.
 * 
 * @author matheus
 *
 */
public class GamePlay extends Tela
{
	Heroi[] _herois = null;

	public GamePlay(Heroi... herois)
	{
		super(Gdx.files.local("arquivos//teste.tmx"));
		_herois = herois;
	}

	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.GAMEPLAY;
		_corFundo = Color.BLACK;
		this.InserirGameObject(new ControladorCamera(_herois));
		this.InserirGameObject(_herois);
		this.InserirGameObject(new FabricaInimigo());
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
	}
}
