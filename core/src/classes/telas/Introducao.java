package classes.telas;

import classes.uteis.Camada.Camadas;
import classes.uteis.ListaGameObject;
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
