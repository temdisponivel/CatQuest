package classes.telas;

import classes.uteis.ListaGameObject;
import catquest.CatQuest;
import catquest.CatQuest.Camadas;

public class Introducao extends Tela
{
	@Override
	public void Iniciar() 
	{
		super.Iniciar();
		_listasGameObject.put(CatQuest.instancia.GetCamada(Camadas.OBJETOS_ESTATICOS), new ListaGameObject());
		_listasGameObject.put(CatQuest.instancia.GetCamada(Camadas.PERSONAGENS), new ListaGameObject());
		_listasGameObject.put(CatQuest.instancia.GetCamada(Camadas.UI), new ListaGameObject());
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
	}
}
