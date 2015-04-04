package classes.uteis;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class Controle implements ControllerListener, Controller
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
	 * Retorna a direção informada pelo usuário através do teclado ou controle.
	 * @return {@link Controle.DIRECOES} Que o usuário informou via teclado ou controle. {@link Controle.DIRECOES#CENTRO} quando nada informado.
	 */
	public Controle.DIRECOES GetDirecao()
	{
		
		return Controle.DIRECOES.CENTRO;
	}

	@Override
	public boolean getButton(int buttonCode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getAxis(int axisCode)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PovDirection getPov(int povCode)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getSliderX(int sliderCode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getSliderY(int sliderCode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector3 getAccelerometer(int accelerometerCode)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAccelerometerSensitivity(float sensitivity)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(ControllerListener listener)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(ControllerListener listener)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connected(Controller controller)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected(Controller controller)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode)
	{
		// TODO Auto-generated method stub
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
