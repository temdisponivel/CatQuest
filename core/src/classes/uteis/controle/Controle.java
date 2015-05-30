//TODO: TERMINAR DE IMPLEMENTAR CONTROLES. IMPLEMENTAR GETMOUSE. FAZER PAUSAR QUANDO DESCONECTAR CONTROLE

package classes.uteis.controle;

import java.util.ArrayList;
import classes.uteis.Player.TipoPlayer;
import classes.uteis.controle.ConjuntoComandos.TipoConjunto;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

/**
 * Classe que gerencia os controles do jogo. Aqui ficam os botï¿½es apertados, mouse, etc.
 * @author Matheus
 *
 */
public class Controle
{
	/**
	 * Direï¿½ï¿½es possï¿½veis de movimentaï¿½ï¿½o dos controles.
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
		static public final int SUDESTE = 42;
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
		Teclado,
		Controle,
	}
	
	static public ArrayList<Controle> _controlesEmUso = new ArrayList<Controle>();
	static private ArrayList<Controller> _joystickEmUso = new ArrayList<Controller>();
	private Controller _controle = null;
	private ConjuntoComandos _conjunto = null;
	private float _sensibilidade = 1/3f;
	
	/**
	 * Contrï¿½i um novo controle baseado no {@link TipoPlayer} que vai utilizar.
	 * @param comandos {@link ConjuntoComandos Comandos} para o controle.
	 * Caso haja joystick disponível, o conjunto será trocado para {@link TipoConjunto#Controle}
	 */
	public Controle(ConjuntoComandos comandos)
	{
		//PERCORRE TODOS OS CONTROLES
		for (Controller controle : Controllers.getControllers())
		{
			//VALIDA SE É UM CONTROLE DE XBOX
			if (controle.getName().toLowerCase().contains("xbox") && controle.getName().toLowerCase().contains("360"))
			{				
				//SE O CONTROLE JÁ ESTÁ EM USO, TENTA O PROXIMO
				if (_joystickEmUso.contains(controle))
					continue;
				else
					_joystickEmUso.add(controle);
				
				//SE NAO ESTÁ EM USO, VAI ENTRAR EM USO AGORA
				_controle = controle;
				comandos = ConjuntoComandos.controle;
				break;
			}
		}
		
		this.SetConjunto(comandos);
		
		_controlesEmUso.add(this);
	}
	
	/**
	 * @return True caso exista um joystick de Xbox livre.
	 */
	static public boolean ExisteJoystickLivre()
	{
		//PERCORRE TODOS OS CONTROLES
		for (Controller controle : Controllers.getControllers())
		{
			//VALIDA SE É UM CONTROLE DE XBOX
			if (controle.getName().toLowerCase().contains("xbox") && controle.getName().toLowerCase().contains("360"))
			{				
				//SE O CONTROLE JÁ ESTÁ EM USO, TENTA O PROXIMO
				if (_joystickEmUso.contains(controle))
					continue;
				else
					return true;
			}
		}
		
		return false;
	}

	/**
	 * Retorna a direï¿½ï¿½o informada pelo usuï¿½rio atravï¿½s do teclado ou controle.
	 * @return {@link Direcoes} Que o usuï¿½rio informou via teclado ou controle. {@link Direcoes#CENTRO} quando nada informado.
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
			if (!this.ValidaControle())
				return Direcoes.CENTRO;
				
			if (_controle.getAxis(_conjunto.ESQUERDA) <= -_sensibilidade)
				direcao += Direcoes.ESQUERDA;
			else if (_controle.getAxis(_conjunto.DIREITA) >= _sensibilidade)
				direcao += Direcoes.DIREITA;

			if (_controle.getAxis(_conjunto.CIMA) <= -_sensibilidade)
				direcao += Direcoes.CIMA;
			else if (_controle.getAxis(_conjunto.BAIXO)  >= _sensibilidade)
				direcao += Direcoes.BAIXO;
		}
		
		return direcao;
	}
	
	/**
	 * Retorna a direï¿½ï¿½o de ataque informada pelo usuï¿½rio atravï¿½s do teclado ou controle.
	 * @return {@link Direcoes} Que o usuï¿½rio informou via teclado ou controle. {@link Direcoes#CENTRO} quando nada informado.
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
			if (!this.ValidaControle())
				return Direcoes.CENTRO;
			 
			if (_controle.getAxis(_conjunto.ATAQUE_ESQUERDA) > -_sensibilidade && _controle.getAxis(_conjunto.ATAQUE_ESQUERDA) < _sensibilidade)
				return Direcoes.CENTRO;
			
			if (_controle.getAxis(_conjunto.ATAQUE_ESQUERDA) <= -_sensibilidade)
				direcao += Direcoes.ESQUERDA;
			else if (_controle.getAxis(_conjunto.ATAQUE_DIREITA) >= _sensibilidade)
				direcao += Direcoes.DIREITA;
			if (_controle.getAxis(_conjunto.ATAQUE_CIMA) >= _sensibilidade)
				direcao += Direcoes.BAIXO;
			else if (_controle.getAxis(_conjunto.ATAQUE_BAIXO)  <= -_sensibilidade)
				direcao += Direcoes.CIMA;
		}
		
		return direcao;
	}
	
	/**
	 * 
	 * @return True se o botï¿½o de aï¿½ï¿½o foi ativado.
	 */
	public boolean GetAcao()
	{
		if (this.GetTipoControle() == TipoControle.Teclado)
		{
			return Gdx.input.isKeyPressed(_conjunto.ACAO);
		}
		else
		{
			if (!this.ValidaControle())
				return false;
			
			return _controle.getButton(_conjunto.ACAO);
		}
	}
	
	/**
	 * @return True se o botï¿½o de habilidade foi ativado. 
	 */
	public boolean GetHabilidade()
	{
		if (this.GetTipoControle() == TipoControle.Teclado)
		{
			return Gdx.input.isKeyPressed(_conjunto.HABILIDADE);
		}
		else
		{
			if (!this.ValidaControle())
				return false;
			
			return _controle.getAxis(_conjunto.HABILIDADE) <= -_sensibilidade;
		}
	}
	
	/**
	 * 
	 * @return True caso o botï¿½o de pause seja ativado.
	 */
	public boolean GetPause()
	{
		if (this.GetTipoControle() == TipoControle.Teclado)
		{
			return Gdx.input.isKeyJustPressed(_conjunto.PAUSE);
		}
		else
		{
			return _controle.getButton(_conjunto.PAUSE);
		}
	}
	
	/**
	 * @return True caso qualquer controle tenha apertado a tecla de pause.
	 */
	static public boolean GetQualquerPause()
	{
		for (int i = 0; i < _controlesEmUso.size(); i++)
		{
			if (_controlesEmUso.get(i).GetPause())
				return true;
		}
		
		return false;
	}
	
	/**
	 * @return O {@link TipoControle} que o player estï¿½ jogando.
	 */
	public TipoControle GetTipoControle()
	{
		if (_controle != null)
			return TipoControle.Controle;
		else
			return TipoControle.Teclado;
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
	
	/**
	 * Valida se o controle ainda está conectado. Caso não, troca os comandos para teclado.
	 * @return True caso estaja.
	 */
	private boolean ValidaControle()
	{
		/*
		if (!Controllers.getControllers().contains(_controle, true))
		{
			_controle = null;
			this.GerenciaConjunto();
			return false;
		}
		 */
		return true;
	}
}
