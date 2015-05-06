package classes.telas;

import java.util.Stack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import catquest.CatQuest;
import classes.uteis.UI.Botao;
import classes.uteis.UI.Botao.EscutadorBotao;
import classes.uteis.UI.BotaoTexto;
import classes.uteis.UI.Menu;
import classes.uteis.controle.Controle;

/**
 * Tela de configurações. Onde o usuário configura volume, resolução e controles.
 * @author matheus
 */
public class Configuracoes extends Tela implements EscutadorBotao
{
	private Botao _grafico = null, _audio = null, _controles = null;
	private Stack<Menu> menus = null;
	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.INTRODUCAO;
		
		_grafico = new BotaoTexto("Grafico", new Rectangle(0, 0, 200, 30), new Vector2(), this);
		_audio = new BotaoTexto("Audio", new Rectangle(0, 0, 200, 30), new Vector2(), this);
		_controles = new BotaoTexto("Controles", new Rectangle(0, 0, 200, 30), new Vector2(), this);
		
		Menu _categoriaConfig = new Menu(new Vector2(), _controles, _audio, _grafico);
		_categoriaConfig.SetPosicao(10, (CatQuest.instancia.GetAlturaTela() / 2) - (_categoriaConfig.GetAltura() / 2));
		
		menus = new Stack<Menu>();
		
		menus.add(_categoriaConfig);
		
		this.InserirGameObject(_categoriaConfig);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (Controle.GetQualquerPause())
		{
			if (menus.size() == 1)
				CatQuest.instancia.RetiraTela();
			else
				menus.pop();
		}
	}
	
	@Override
	public void Click(Botao botaoClicado, BotoesMouse botaoMouse)
	{
				
	}
}
