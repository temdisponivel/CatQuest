package screen;

import classe.GameObject;
import classe.Tela;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Introducao extends Tela
{
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		this.InserirGameObject(new GameObject(Gdx.files.absolute("C:\\Users\\Matheus\\Desktop\\chatuba.jpg")));
		this.InserirGameObject(new GameObject(Gdx.files.absolute("C:\\Users\\Matheus\\Desktop\\chatuba.jpg")));
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
	}

	@Override
	public void Desenha(SpriteBatch spriteBatch)
	{
		super.Desenha(spriteBatch);
	}

	@Override
	public void Encerrar()
	{
		// TODO Auto-generated method stub
		
	}

}
