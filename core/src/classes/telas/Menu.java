package classes.telas;

import catquest.CatQuest;

<<<<<<< HEAD
=======


>>>>>>> origin/classes
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
		_corFundo = CatQuest.instancia.GetCorFundo();
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
	}
}