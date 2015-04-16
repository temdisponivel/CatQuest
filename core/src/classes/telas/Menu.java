package classes.telas;

import catquest.CatQuest;
import classes.uteis.Log;
import classes.uteis.UI.Botao;
import classes.uteis.UI.Botao.EscutadorBotao;
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
		
		this.InserirGameObject(new Botao(new Vector2(50, 50), new Rectangle(0, 0, 100, 50), this));
		this.InserirGameObject(new Etiqueta("Texto teste", new Vector2(100, 100)));
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
		Log.instancia.Logar("FUNCIONA!!!!" + botaoMouse.toString());
		CatQuest.instancia.AdicionaTela(new GamePlay(), true, true);
	}
}

