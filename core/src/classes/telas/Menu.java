package classes.telas;

import catquest.CatQuest;
import classes.uteis.Log;
import classes.uteis.UI.Botao;
import classes.uteis.UI.Botao.EscutadorBotao;
import classes.uteis.UI.BotaoTexto;
import classes.uteis.UI.Etiqueta;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Menu extends Tela implements EscutadorBotao
{
	Botao _botao = null;
	Etiqueta _etiqueta = null;
	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.MENU;
		
		this.InserirGameObject(_botao = new Botao(new Vector2((CatQuest.instancia.GetLarguraTela()/2)-100, (CatQuest.instancia.GetAlturaTela()/2)-25), new Rectangle(0, 0, 200, 50), this));
		this.InserirGameObject(new BotaoTexto("TEXTOOO", new Rectangle(0, 0, 250, 50), new Vector2((CatQuest.instancia.GetLarguraTela()/2)-100, (CatQuest.instancia.GetAlturaTela()/2) + 50), this));
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
		if (botaoClicado == _botao)
		{
			Log.instancia.Logar("FUNCIONA!!!!" + botaoMouse.toString());
			CatQuest.instancia.AdicionaTela(new GamePlay(), false, true);
		}
		else
		{
			//this.InserirGameObject(new BotaoTexto("TEXTOOO", new Vector2((CatQuest.instancia.GetLarguraTela()/2)-100, (CatQuest.instancia.GetAlturaTela()/2)-25), new Rectangle(0, 0, 200, 50), this));
		}
	}
}

