package classes.telas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import classes.uteis.CarregarMusicaListner;
import classes.uteis.ListaGameObject;
import catquest.CatQuest;
import catquest.CatQuest.Camadas;

public class Introducao extends Tela implements CarregarMusicaListner
{
	@Override
	public void Iniciar() 
	{
		super.Iniciar();
		_listasGameObject.put(CatQuest.instancia.GetCamada(Camadas.OBJETOS_ESTATICOS), new ListaGameObject());
		_listasGameObject.put(CatQuest.instancia.GetCamada(Camadas.PERSONAGENS), new ListaGameObject());
		_listasGameObject.put(CatQuest.instancia.GetCamada(Camadas.UI), new ListaGameObject());
		
		CatQuest.instancia.CriarNovaMusica(Gdx.files.local("audio\\musica\\a-abe_mangger.mp3"));
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
	}

	@Override
	public void AoCarregar(Music musicaCarregada)
	{
		// TODO Auto-generated method stub
		
	}
}
