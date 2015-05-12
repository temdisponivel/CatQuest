//TODO: TERMINAR DE IMPLEMENTAR CONTROLES. IMPLEMENTAR GETMOUSE. FAZER PAUSAR QUANDO DESCONECTAR CONTROLE

package classes.uteis.controle;

import java.util.ArrayList;
import classes.uteis.Configuracoes;
import classes.uteis.Player.TipoPlayer;
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
	
	static public ArrayList<Controle> _controlesEmUso = null;
	private Controller _controle = null;
	private ConjuntoComandos _conjunto = null;
	private float _sensibilidade = 1/3f;
	private TipoPlayer _tipoPlayer = TipoPlayer.Primario;
	
	/**
	 * Contrï¿½i um novo controle baseado no {@link TipoPlayer} que vai utilizar.
	 * @param tipoPlayer {@link TipoPlayer} que vai utilizar o controle.
	 */
	public Controle(TipoPlayer tipo)
	{		
		//PERCORRE TODOS OS CONTROLES
		for (Controller controle : Controllers.getControllers())
		{
			//VALIDA SE É UM CONTROLE DE XBOX
			if (controle.getName().toLowerCase().contains("xbox") && controle.getName().toLowerCase().contains("360"))
			{
				if (_controlesEmUso == null)
					_controlesEmUso = new ArrayList<Controle>();
				
				//SE O CONTROLE JÁ ESTÁ EM USO, TENTA O PROXIMO
				if (_controlesEmUso.contains(controle))
					continue;
				
				//SE NAO ESTÁ EM USO, VAI ENTRAR EM USO AGORA
				_controle = controle;
			}
		}
		
		_tipoPlayer = tipo;
		
		this.GerenciaConjunto();
		
		if (_controlesEmUso == null)
			_controlesEmUso = new ArrayList<Controle>();
		
		_controlesEmUso.add(this);
	}
	
	/**
	 * Gerencia os conjuntos de comandos segundo o tipo do player e o tipo de controle.
	 */
	public void GerenciaConjunto()
	{
		if (_controle != null)
			this.SetConjunto(new ConjuntoComandos(_tipoPlayer, TipoControle.CONTROLE));
		else if (_tipoPlayer == TipoPlayer.Primario)
			this.SetConjunto(Configuracoes.instancia.GetComandoPlayerPrimario());
		else
			this.SetConjunto(Configuracoes.instancia.GetComandoPlayerSecundario());
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
			if (_controle.getAxis(_conjunto.ATAQUE_CIMA) >= 1/3f)
				direcao += Direcoes.CIMA;
			else if (_controle.getAxis(_conjunto.ATAQUE_BAIXO)  <= -_sensibilidade)
				direcao += Direcoes.BAIXO;
		}
		
		return direcao;
	}
	
	/**
	 * 
	 * @return True se o botï¿½o de aï¿½ï¿½o foi ativado.
	 */
	public boolean GetAcao()
	{
		if (this.GetTipoControle() == TipoControle.TECLADO)
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
		if (this.GetTipoControle() == TipoControle.TECLADO)
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
		if (this.GetTipoControle() == TipoControle.TECLADO)
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
	
	/**
	 * Valida se o controle ainda está conectado. Caso não, troca os comandos para teclado.
	 * @return True caso estaja.
	 */
	private boolean ValidaControle()
	{
		if (!Controllers.getControllers().contains(_controle, true))
		{
			_controle = null;
			this.GerenciaConjunto();
			return false;
		}

		return true;
	}
}
