package classes.telas;

import classes.gameobjects.personagens.herois.Barbaro;
import classes.gameobjects.personagens.inimigos.Cachorro;
import classes.uteis.controle.Controle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GamePlay extends Tela
{	
	public GamePlay()
	{
		super(Gdx.files.local("arquivos//teste.tmx"));
	}
	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.GAMEPLAY;
		
		this.InserirGameObject(new Cachorro());
		this.InserirGameObject(new Barbaro());
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (Controle.GetQualquerPause())
			this.InserirGameObject(new Cachorro());
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
