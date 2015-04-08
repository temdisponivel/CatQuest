package classes.telas;

import com.badlogic.gdx.Gdx;
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
		
		CatQuest.instancia.CriarNovaMusica(Gdx.files.local("audio\\musica\\James Brown - super bad.mp3"));
		CatQuest.instancia.RetiraTela();
		CatQuest.instancia.AdicionaTela(new Menu(), true, true);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
	}
}
