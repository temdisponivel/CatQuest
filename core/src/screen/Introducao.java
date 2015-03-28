package screen;

import catquest.CatQuest;
import classe.Tela;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Introducao extends Tela
{
	Texture _chatuba = null;
	@Override
	public void Iniciar(CatQuest jogo) 
	{
		_chatuba = new Texture(Gdx.files.absolute("C:\\Users\\Matheus\\Desktop\\chatuba.jpg"));
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		
	}

	@Override
	public void Desenha(SpriteBatch spriteBash)
	{
		// TODO Auto-generated method stub
		spriteBash.draw(_chatuba, 0, 0);
	}

	@Override
	public void Encerrar()
	{
		// TODO Auto-generated method stub
		
	}

}
