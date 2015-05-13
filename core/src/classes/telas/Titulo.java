package classes.telas;

import catquest.CatQuest;
import classes.uteis.Player;
import classes.uteis.UI.Botao;
import classes.uteis.UI.BotaoTexto;
import classes.uteis.UI.Botao.EscutadorBotao;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import classes.uteis.UI.Menu;

public class Titulo extends Tela implements EscutadorBotao
{
	Botao jogar, configurar, sair;
	Menu menu;
	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.MENU;
		
		for (int i = 0; i < 2000; i++)
		{
			menu = new Menu(new Vector2(CatQuest.instancia.GetLarguraTela() - 280, 60));
			menu.AdicionaFilho(sair = new BotaoTexto("SAIR", new Rectangle(0, 0, 220, 30), new Vector2(), this));
			menu.AdicionaFilho(configurar = new BotaoTexto("CONFIGURAR", new Rectangle(0, 0, 220, 30), new Vector2(), this));
			menu.AdicionaFilho(jogar = new BotaoTexto("JOGAR", new Rectangle(0, 0, 220, 30), new Vector2(), this));
			
			this.InserirGameObject(menu);
		}
		
		_corFundo = Color.WHITE;
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (Player.playerPrimario.GetControle().GetHabilidade())
		{
			menu.SetPosicao(menu.GetPosicao().add(0, 10));
		}
	}

	@Override
	public void Desenha(SpriteBatch spriteBatch)
	{
		super.Desenha(spriteBatch);
	}

	@Override
	public void Click(Botao botaoClicado, BotoesMouse botaoMouse)
	{
		if (botaoMouse == BotoesMouse.Esquerdo)
		{
			if (botaoClicado == jogar)
			{
				CatQuest.instancia.AdicionaTela(new GamePlay(), false, false);
			}
			else if (botaoClicado == configurar)
			{
				CatQuest.instancia.AdicionaTela(new Configuracoes(), false, false);
			}
			else if (botaoClicado == sair)
			{
				CatQuest.instancia.EncerraJogo();
			}
		}
	}
}