package classes.telas;

import catquest.CatQuest;
import classes.uteis.UI.Botao;
import classes.uteis.UI.Botao.EscutadorBotao;
import classes.uteis.UI.BotaoTexto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Menu extends Tela implements EscutadorBotao
{
	Botao jogar, configurar, sair;
	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.MENU;
		
		this.InserirGameObject(jogar = new BotaoTexto("JOGAR", new Vector2(), this));
		jogar.GetPosicao().add(CatQuest.instancia.GetLarguraTela() - jogar.GetLargura() - 10, (3 * jogar.GetAltura() + 75));
		
		this.InserirGameObject(configurar = new BotaoTexto("CONFIGURAR", new Vector2(), this));
		configurar.GetPosicao().add(CatQuest.instancia.GetLarguraTela() - configurar.GetLargura() - 10, (2 * configurar.GetAltura() + 50));
		
		this.InserirGameObject(sair = new BotaoTexto("SAIR", new Vector2(), this));
		sair.GetPosicao().add(CatQuest.instancia.GetLarguraTela() - sair.GetLargura() - 10, (1 * sair.GetAltura() + 25));
		
		_corFundo = Color.WHITE;
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
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

