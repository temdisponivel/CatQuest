//TODO: TERMINAR DE IMPLEMENTAR CONTROLES. JOGADOR DEVE ESCOLHER COM QUAL VAI JOGAR. IMPLEMENTAR GETMOUSE, GETTECLA E ETC.

package classes.uteis;

import catquest.CatQuest;
import classes.telas.Menu;
import classes.uteis.Player.TipoPlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 * Classe que gerencia os controles do jogo. Aqui ficam os botões apertados, mouse, etc.
 * @author Matheus
 *
 */
public class Controle implements ControllerListener
{
	/**
	 * Direções possíveis de movimentação dos controles.
	 * @author Matheus
	 *
	 */
	public class Direcoes
	{
		static public final int CENTRO = 0;
		static public final int ESQUERDA = 7;
		static public final int DIREITA = 11;
		static public final int CIMA = 23;
		static public final int BAIXO = 31;
		static public final int NORDESTE = 34;
		static public final int SUDESTE = 44;
		static public final int NOROESTE = 30;
		static public final int SUDOESTE = 38;
	};
	
	/**
	 * Enumerador para os tipos de controle.
	 * @author Matheus
	 *
	 */
	public enum TipoControle
	{
		TECLADO,
		CONTROLE,
	}
	
	private Controller _controle = null;
	private ConjuntoComandos _conjunto = null;
	
	/**
	 * Contrói um novo controle baseado no {@link TipoPlayer} que vai utilizar.
	 * @param tipoPlayer {@link TipoPlayer} que vai utilizar o controle.
	 */
	public Controle(TipoPlayer tipoPlayer)
	{
		Controllers.addListener(this);
		
		for (Controller controle : Controllers.getControllers())
		{
			if (controle.getName().toLowerCase().contains("xbox") && controle.getName().toLowerCase().contains("360"))
			{
				if (controle != CatQuest.instancia.GetPlayer(tipoPlayer == TipoPlayer.UM ? tipoPlayer : TipoPlayer.DOIS).GetControle())
				{
					_controle = controle;
					break;
				}
			}
		}
		
		_conjunto = new ConjuntoComandos(tipoPlayer, this.GetTipoControle());
	}
	
	/**
	 * Retorna a direção informada pelo usuário através do teclado ou controle.
	 * @return {@link Direcoes} Que o usuário informou via teclado ou controle. {@link Direcoes#CENTRO} quando nada informado.
	 */
	public int GetDirecao()
	{
		int direcao = Direcoes.CENTRO;
		
		if (_controle == null)
		{
			if (Gdx.input.isKeyPressed(_conjunto.ESQUERDA))
				direcao += Direcoes.ESQUERDA;
			else if (Gdx.input.isKeyPressed(_conjunto.DIREITA))
				direcao += Direcoes.DIREITA;
			if (Gdx.input.isKeyPressed(_conjunto.CIMA))
				direcao += Direcoes.CIMA;
			else if (Gdx.input.isKeyPressed(_conjunto.BAIXO))
				direcao += Direcoes.BAIXO;
		}
		else
		{
			if (_controle.getAxis(_conjunto.ESQUERDA) <= -1/3)
				direcao += Direcoes.ESQUERDA;
			else if (_controle.getAxis(_conjunto.DIREITA) >= 1/3)
				direcao += Direcoes.DIREITA;
			if (_controle.getAxis(_conjunto.CIMA) >= 1/3)
				direcao += Direcoes.CIMA;
			else if (_controle.getAxis(_conjunto.BAIXO)  <= -1/3)
				direcao += Direcoes.BAIXO;
		}
		
		return direcao;
	}

	@Override
	public void connected(Controller controller)
	{
		//valida se o controle é de xbox 360
		if (!(controller.getName().toLowerCase().contains("xbox") && controller.getName().toLowerCase().contains("360")))
			return;
		
		if (_controle == null)
		{
			_controle = controller;
			_controle.addListener(this);
		}
	}

	@Override
	public void disconnected(Controller controller)
	{
		if (_controle == controller)
		{
			_controle = null;
			_conjunto.SetConjuntoTeclado();
			CatQuest.instancia.AdicionaTela(new Menu(), false, true);
		}
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode)
	{
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode)
	{
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value)
	{
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value)
	{
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value)
	{
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value)
	{
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value)
	{
		return false;
	}
	
	/**
	 * @return O {@link TipoControle} que o player está jogando.
	 */
	public TipoControle GetTipoControle()
	{
		if (_controle != null)
			return TipoControle.CONTROLE;
		else
			return TipoControle.TECLADO;
	}
	
	/**
	 * Retorna o {@link ConjuntoComandos} do controle.
	 * @return {@link ConjuntoComandos} do controle.
	 */
	public ConjuntoComandos GetConjunto()
	{
		return _conjunto;
	}
	
	/**
	 * Define um novo {@link ConjuntoComandos} conjunto de comandos do controle.
	 * @param conjunto {@link ConjuntoComandos} de comandos do controle.
	 */
	public void SetConjunto(ConjuntoComandos conjunto)
	{
		_conjunto = conjunto;
	}
}
