//TODO: TERMINAR DE IMPLEMENTAR CONTROLES. JOGADOR DEVE ESCOLHER COM QUAL VAI JOGAR. IMPLEMENTAR GETMOUSE, GETTECLA E ETC.

package classes.uteis.controle;

import java.util.ArrayList;

import catquest.CatQuest;
import classes.telas.Menu;
import classes.uteis.Configuracoes;
import classes.uteis.Player.TipoPlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 * Classe que gerencia os controles do jogo. Aqui ficam os bot�es apertados, mouse, etc.
 * @author Matheus
 *
 */
public class Controle implements ControllerListener
{
	/**
	 * Dire��es poss�veis de movimenta��o dos controles.
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
	static private ArrayList<Controller> _controlesEmUso = null;
	
	/**
	 * Contr�i um novo controle baseado no {@link TipoPlayer} que vai utilizar.
	 * @param tipoPlayer {@link TipoPlayer} que vai utilizar o controle.
	 */
	public Controle(TipoPlayer tipo)
	{
		Controllers.addListener(this);
		
		//PERCORRE TODOS OS CONTROLES
		for (Controller controle : Controllers.getControllers())
		{
			//VALIDA SE � UM CONTROLE DE XBOX
			if (controle.getName().toLowerCase().contains("xbox") && controle.getName().toLowerCase().contains("360"))
			{
				if (_controlesEmUso == null)
					_controlesEmUso = new ArrayList<Controller>();
				
				//SE O CONTROLE J� EST� EM USO, TENTA O PROXIMO
				if (_controlesEmUso.contains(controle))
					continue;
				
				//SE NAO EST� EM USO, VAI ENTRAR EM USO AGORA
				_controlesEmUso.add(controle);
				_controle = controle;
			}
		}

		if (tipo == TipoPlayer.Primario)
			this.SetConjunto(Configuracoes.instancia.GetComandoPlayerPrimario());
		else
			this.SetConjunto(Configuracoes.instancia.GetComandoPlayerSecundario());
	}
	
	/**
	 * Retorna a dire��o informada pelo usu�rio atrav�s do teclado ou controle.
	 * @return {@link Direcoes} Que o usu�rio informou via teclado ou controle. {@link Direcoes#CENTRO} quando nada informado.
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
	
	/**
	 * Retorna a dire��o de ataque informada pelo usu�rio atrav�s do teclado ou controle.
	 * @return {@link Direcoes} Que o usu�rio informou via teclado ou controle. {@link Direcoes#CENTRO} quando nada informado.
	 */
	public int GetDirecaoAtaque()
	{
		int direcao = Direcoes.CENTRO;
		
		if (_controle == null)
		{
			if (Gdx.input.isKeyPressed(_conjunto.ATAQUE_ESQUERDA))
				direcao += Direcoes.ESQUERDA;
			else if (Gdx.input.isKeyPressed(_conjunto.ATAQUE_DIREITA))
				direcao += Direcoes.DIREITA;
			if (Gdx.input.isKeyPressed(_conjunto.ATAQUE_CIMA))
				direcao += Direcoes.CIMA;
			else if (Gdx.input.isKeyPressed(_conjunto.ATAQUE_BAIXO))
				direcao += Direcoes.BAIXO;
		}
		else
		{
			if (_controle.getAxis(_conjunto.ATAQUE_ESQUERDA) <= -1/3)
				direcao += Direcoes.ESQUERDA;
			else if (_controle.getAxis(_conjunto.ATAQUE_DIREITA) >= 1/3)
				direcao += Direcoes.DIREITA;
			if (_controle.getAxis(_conjunto.ATAQUE_CIMA) >= 1/3)
				direcao += Direcoes.CIMA;
			else if (_controle.getAxis(_conjunto.ATAQUE_BAIXO)  <= -1/3)
				direcao += Direcoes.BAIXO;
		}
		
		return direcao;
	}
	
	/**
	 * 
	 * @return True se o bot�o de a��o foi ativado.
	 */
	public boolean GetAcao()
	{
		if (this.GetTipoControle() == TipoControle.TECLADO)
		{
			return Gdx.input.isKeyPressed(_conjunto.ACAO);
		}
		else
		{
			return _controle.getButton(_conjunto.ACAO);
		}
	}
	
	/**
	 * @return True se o bot�o de habilidade foi ativado. 
	 */
	public boolean GetHabilidade()
	{
		if (this.GetTipoControle() == TipoControle.TECLADO)
		{
			return Gdx.input.isKeyPressed(_conjunto.HABILIDADE);
		}
		else
		{
			return _controle.getButton(_conjunto.HABILIDADE);
		}
	}
	
	/**
	 * 
	 * @return True caso o bot�o de pause seja ativado.
	 */
	public boolean GetPause()
	{
		if (this.GetTipoControle() == TipoControle.TECLADO)
		{
			return Gdx.input.isKeyJustPressed(_conjunto.PAUSE);
		}
		else
		{
			return _controle.getButton(_conjunto.PAUSE);
		}
	}

	@Override
	public void connected(Controller controller)
	{
		//valida se o controle � de xbox 360
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
	 * @return O {@link TipoControle} que o player est� jogando.
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
