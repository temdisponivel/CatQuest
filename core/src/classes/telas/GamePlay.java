package classes.telas;

import catquest.CatQuest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GamePlay extends Tela
{	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		// TODO Auto-generated method stub
		_tipo = Telas.GAMEPLAY;
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		if (Gdx.input.isButtonPressed(Buttons.LEFT))
		{
			CatQuest.instancia.RetiraTela();
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
