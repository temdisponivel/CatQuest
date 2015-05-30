package classes.telas;

import classes.gameobjects.personagens.herois.Heroi;
import classes.gameobjects.personagens.inimigos.Cachorro;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.AreaIndesejada;
import classes.uteis.ControladorCamera;
import classes.uteis.controle.Controle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
		super(Gdx.files.local("arquivos/map.tmx"));
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
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);

		if (Controle.GetQualquerPause())
			this.InserirGameObject(Inimigo._reciclador.GetInstancia(Cachorro.class));
	}
}
