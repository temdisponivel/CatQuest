package classes.telas;

import classes.gameobjects.personagens.herois.Barbaro;
import classes.gameobjects.personagens.inimigos.Cachorro;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.Player;
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
		Barbaro a, b = null;
		this.InserirGameObject(Inimigo._reciclador.GetInstancia(Cachorro.class));
		this.InserirGameObject(a = new Barbaro());
		this.InserirGameObject(b = new Barbaro());
		
		a.SetPlayer(Player.playerPrimario);
		b.SetPlayer(Player.playerSecundario);
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (Controle.GetQualquerPause())
			this.InserirGameObject(Inimigo._reciclador.GetInstancia(Cachorro.class));
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
