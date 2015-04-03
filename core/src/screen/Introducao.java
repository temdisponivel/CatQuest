package screen;

import classe.GameObject;
import classe.Tela;

public class Introducao extends Tela
{
	GameObject _chatuba = null;
	@Override
	public void Iniciar() 
	{
		//_chatuba = new GameObject(Gdx.files.internal("imagens/chatuba.jpg"), catquest.CatQuest.Camadas.FUNDO);
		super.Iniciar();
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		//CatQuest.instancia.SetPosicaoCamera(_chatuba.GetPosicao());
	}

	@Override
	public void Encerrar()
	{
		// TODO Auto-generated method stub
		
	}

}
