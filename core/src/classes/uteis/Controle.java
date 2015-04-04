package classes.uteis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class Controle implements ControllerListener
{
	/**
	 * Direções possíveis de movimentação dos controles.
	 * @author Matheus
	 *
	 */
	public enum DIRECOES
	{
		CENTRO,
		ESQUERDA,
		DIREITA,
		CIMA,
		BAIXO,
		NORDESTE,
		SUDESTE,
		NOROESTE,
		SUDOESTE,
	};
	
	/**
	 * Players para requisição dos comandos.
	 * @author Matheus
	 *
	 */
	public enum PLAYER
	{
		PLAYER1,
		PLAYER2,
	}
	
	private Controller _controle1 = null;
	private Controller _controle2 = null;
	
	
	public Controle()
	{
		Controllers.addListener(this);
		
		if (Controllers.getControllers().size >= 1)
		{
			_controle1 = Controllers.getControllers().get(0);
		}
		
		if (Controllers.getControllers().size >= 2)
		{
			_controle2 = Controllers.getControllers().get(1);
		}
	}
	
	/**
	 * Retorna a direção informada pelo usuário através do teclado ou controle.
	 * @param player {@link PLAYER} o qual quer saber a direção.
	 * @return {@link DIRECOES} Que o usuário informou via teclado ou controle. {@link DIRECOES#CENTRO} quando nada informado.
	 */
	public DIRECOES GetDirecao(PLAYER player)
	{
		int direcao = 0;
		
		if (player == PLAYER.PLAYER1)
		{
			if (_controle1 == null)
			{
				if(Gdx.input.isButtonPressed(Keys.LEFT))
					direcao += direcao;
			}
		}
		
		return DIRECOES.values()[direcao];
	}

	@Override
	public void connected(Controller controller)
	{
		//valida se o controle é de xbox 360
		if (!(controller.getName().toLowerCase().contains("xbox") && controller.getName().toLowerCase().contains("360")))
			return;
		
		if (_controle1 == null)
		{
			_controle1 = controller;
			_controle1.addListener(this);
		}
		else if (_controle2 == null)
		{
			_controle2 = controller;
			_controle2.addListener(this);
		}
	}

	@Override
	public void disconnected(Controller controller)
	{
		if (_controle1 == controller)
		{
			_controle1 = null;
		}
		else if (_controle2 == controller)
		{
			_controle2 = null;
		}
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode)
	{
		if (controller != _controle1 && controller != _controle2)
			return false;
		
		if (controller == _controle1)
		{
			
		}
		else if (controller == _controle2)
		{
			
		}
		
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
