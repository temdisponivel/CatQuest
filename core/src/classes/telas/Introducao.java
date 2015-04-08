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
		_listasGameObject.put(CatQuest.instancia.GetCamada(Camadas.OBJETOS_ESTATICOS), new ListaGameObject());
		_listasGameObject.put(CatQuest.instancia.GetCamada(Camadas.PERSONAGENS), new ListaGameObject());
		_listasGameObject.put(CatQuest.instancia.GetCamada(Camadas.UI), new ListaGameObject());
		
		CatQuest.instancia.AdicionaTela(new Menu(), false, false);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
	}
}
