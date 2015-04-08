package classes.telas;

import catquest.CatQuest;
import classes.uteis.Controle.Direcoes;
import classes.uteis.Player.TipoPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu extends Tela
{
	@Override
	public void Iniciar()
	{
		// TODO Auto-generated method stub
		super.Iniciar();
		_tipo = Telas.MENU;
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		// TODO Auto-generated method stub
		super.Atualiza(deltaTime);
		
		if (CatQuest.instancia.GetPlayer(TipoPlayer.DOIS).GetControle().GetDirecao() == Direcoes.CIMA)
		{
			CatQuest.instancia.CriarNovoSom(Gdx.files.local("audio\\som\\notify.wav")).play();
		}
	}

	@Override
	public void Desenha(SpriteBatch spriteBatch)
	{
		// TODO Auto-generated method stub
		super.Desenha(spriteBatch);
	}

	@Override
	public void Encerrar()
	{
		// TODO Auto-generated method stub
	}

}

