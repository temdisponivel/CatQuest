package classes.telas;

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
	Menu _categoriaConfig = null, _menuGrafico = null, _menuControles = null, _menuAudio = null;
	int fluxoMenu = 1;
	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.INTRODUCAO;
		
		_grafico = new BotaoTexto("Grafico", new Rectangle(0, 0, 200, 30), new Vector2(), this);
		_audio = new BotaoTexto("Audio", new Rectangle(0, 0, 200, 30), new Vector2(), this);
		_controles = new BotaoTexto("Controles", new Rectangle(0, 0, 200, 30), new Vector2(), this);
		
		_categoriaConfig = new Menu(new Vector2(), _controles, _audio, _grafico);
		_categoriaConfig.SetPosicao(10, (CatQuest.instancia.GetAlturaMundo() / 2) - (_categoriaConfig.GetAltura() / 2));
		
		//_menus = new Stack<Menu>();
		
		//_menus.add(_categoriaConfig);
		
		this.InserirGameObject(_categoriaConfig);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (Controle.GetQualquerPause())
		{
			if (fluxoMenu == 1)
				CatQuest.instancia.RetiraTela();
		}
	}
	
	@Override
	public void Click(Botao botaoClicado, BotoesMouse botaoMouse)
	{
		if (botaoClicado == _grafico)
		{
			_menuGrafico.SetAtivo(true);
			_menuAudio.SetAtivo(false);
			_menuControles.SetAtivo(false);
		}
		else if (botaoClicado ==  _audio)
		{
			_menuGrafico.SetAtivo(false);
			_menuAudio.SetAtivo(true);
			_menuControles.SetAtivo(false);
		}
		else if (botaoClicado == _controles)
		{
			_menuGrafico.SetAtivo(false);
			_menuAudio.SetAtivo(false);
			_menuControles.SetAtivo(true);
		}
	}
	
	@Override
	public void Encerrar()
	{
		super.Encerrar();
		/*
		for (int i = 0; i < _menus.size(); i++)
		{
			_menus.get(i).Encerra();
		}
		*/
	}
}
