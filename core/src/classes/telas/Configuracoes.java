package classes.telas;

import classes.uteis.UI.Botao;
import classes.uteis.UI.Botao.EscutadorBotao;
import classes.uteis.UI.Menu;

/**
 * Tela de configurações. Onde o usuário configura volume, resolução e controles.
 * @author matheus
 */
public class Configuracoes extends Tela implements EscutadorBotao
{
	Botao _grafico = null, _audio = null, _controles = null;
	Menu _categoriaConfig = null;
	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.INTRODUCAO;
		
		
	}
	
	@Override
	public void Click(Botao botaoClicado, BotoesMouse botaoMouse)
	{
				
	}
}
