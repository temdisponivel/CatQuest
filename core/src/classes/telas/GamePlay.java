package classes.telas;

import catquest.CatQuest;
import classes.uteis.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GamePlay extends Tela
{	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.GAMEPLAY;
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		if (Player.playerPrimario.GetControle().GetPause())
		{
			CatQuest.instancia.AdicionaTela(new Menu(), false, true);
		}
	}

	@Override
	public void Desenha(SpriteBatch spriteBash)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Encerrar()
	{
		// TODO Auto-generated method stub
		
	}
}
