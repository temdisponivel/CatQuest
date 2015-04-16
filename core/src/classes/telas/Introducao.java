package classes.telas;

import catquest.CatQuest;

public class Introducao extends Tela
{
	@Override
	public void Iniciar() 
	{
		super.Iniciar();
		_tipo = Telas.INTRODUCAO;		
		CatQuest.instancia.AdicionaTela(new Menu(), false, false);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
	}
}
