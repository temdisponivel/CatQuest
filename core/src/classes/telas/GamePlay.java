package classes.telas;

import catquest.CatQuest;
import classes.uteis.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GamePlay extends Tela
{	
	public GamePlay()
	{
		super(Gdx.files.local("arquivos//teste.tmx"));
	}
	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.GAMEPLAY;
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (Player.playerPrimario.GetControle().GetPause())
		{
			CatQuest.instancia.AdicionaTela(new Menu(), false, true);
		}
	}

	@Override
	public void Desenha(SpriteBatch spriteBatch)
	{
		super.Desenha(spriteBatch);
	}

	@Override
	public void Encerrar()
	{
		// TODO Auto-generated method stub
		
	}
}
