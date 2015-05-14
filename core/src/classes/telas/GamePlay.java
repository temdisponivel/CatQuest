package classes.telas;

import java.util.LinkedList;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.inimigos.Cachorro;
import classes.uteis.controle.Controle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

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
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (Controle.GetQualquerPause())
		{
			CatQuest.instancia.AdicionaTela(new Menu(), false, true);
			
			LinkedList<GameObject> objetos = this.GetObjetosRegiao(new Rectangle(0, 0, 500, 500));
			
			for (int i = 0; i < objetos.size(); i++)
			{
				objetos.get(i).SetSeDesenha(false);
			}
		}
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
