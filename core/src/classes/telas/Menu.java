package classes.telas;

import com.badlogic.gdx.graphics.Color;

/**
 * Classe do menu de pause.
 * @author Matheus
 *
 */
public class Menu extends Tela
{
	public Menu()
	{
		super();
		_tipo = Telas.MENU;
	}

	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_corFundo = Color.WHITE;
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
	}
}