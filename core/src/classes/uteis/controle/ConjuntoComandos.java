//TODO: validar teclado numero
package classes.uteis.controle;

import com.badlogic.gdx.Input.Keys;

/**
 * Classe utilizada para configuração dos comandos dos dois players. Retorna o comando certo independente se é controle ou teclado.
 * @author Matheus
 */
public class ConjuntoComandos
{
	/**
	 * Enumerados para os tipos de conjuntos de comandos.
	 * @author matheus
	 *
	 */
	public enum TipoConjunto
	{
		Primario,
		Secundario,
		Controle,
	}
	
	static public ConjuntoComandos tecladoPrimario = new ConjuntoComandos(TipoConjunto.Primario);
	static public ConjuntoComandos tecladoSecundario = new ConjuntoComandos(TipoConjunto.Secundario);
	static public ConjuntoComandos controle = new ConjuntoComandos(TipoConjunto.Controle);
	private TipoConjunto _tipoConjunto;
	public int PAUSE = 0;
	public int ACAO = 0;
	public int HABILIDADE = 0;
	public int DIREITA = 0;
	public int ESQUERDA = 0;
	public int CIMA = 0;
	public int BAIXO = 0;
	public int ATAQUE_ESQUERDA = 0;
	public int ATAQUE_DIREITA = 0;
	public int ATAQUE_CIMA = 0;
	public int ATAQUE_BAIXO = 0;
	
	/**
	 * Contrói um {@link ConjuntoComandos conjunto de comandos} para teclado com o {@link TipoConjunto tipo} primário.
	 */
	public ConjuntoComandos() 
	{
		this(TipoConjunto.Primario);
	}
	
	public ConjuntoComandos(TipoConjunto conjunto)
	{
		_tipoConjunto = conjunto;
		
		if (_tipoConjunto == TipoConjunto.Controle)
			this.SetConjuntoControle();
		else
			this.SetConjuntoTeclado();
	}
	
	/**
	 * Define o conjunto de comandos como padrão de teclado.
	 */
	public void SetConjuntoTeclado()
	{
		if (_tipoConjunto == TipoConjunto.Primario)
		{
			PAUSE = Keys.ESCAPE;
			ACAO = Keys.E;
			DIREITA = Keys.D;
			ESQUERDA = Keys.A;
			CIMA = Keys.W;
			BAIXO = Keys.S;
			ATAQUE_ESQUERDA = Keys.J;
			ATAQUE_DIREITA = Keys.L;
			ATAQUE_CIMA = Keys.I;
			ATAQUE_BAIXO = Keys.K;
			HABILIDADE = Keys.SPACE;
		}
		else if (_tipoConjunto == TipoConjunto.Secundario) 
		{
			PAUSE = Keys.ESCAPE;
			ACAO = Keys.NUM_7;
			DIREITA = Keys.RIGHT;
			ESQUERDA = Keys.LEFT;
			CIMA = Keys.UP;
			BAIXO = Keys.DOWN;
			ATAQUE_ESQUERDA = Keys.NUM_4;
			ATAQUE_DIREITA = Keys.NUM_6;
			ATAQUE_CIMA = Keys.NUMPAD_8;
			ATAQUE_BAIXO = Keys.NUM_5;
			HABILIDADE = Keys.ENTER;
		}
	}
	
	/**
	 * Define o conjunto de comandos como o padrão de controle.
	 */
	public void SetConjuntoControle()
	{
		PAUSE = XboxControleMap.BUTTON_START;
		ACAO = XboxControleMap.BUTTON_A;
		DIREITA = XboxControleMap.AXIS_LEFT_X;
		ESQUERDA = XboxControleMap.AXIS_LEFT_X;
		CIMA = XboxControleMap.AXIS_LEFT_Y;
		BAIXO = XboxControleMap.AXIS_LEFT_Y;
		ATAQUE_ESQUERDA = XboxControleMap.AXIS_RIGHT_X;
		ATAQUE_DIREITA = XboxControleMap.AXIS_RIGHT_X;
		ATAQUE_CIMA = XboxControleMap.AXIS_RIGHT_Y;
		ATAQUE_BAIXO = XboxControleMap.AXIS_RIGHT_Y;
		HABILIDADE = XboxControleMap.AXIS_LEFT_TRIGGER;
	}
	
	/**
	 * @return {@link TipoConjunto Tipo} deste conjunto.
	 */
	public TipoConjunto GetTipoConjunto()
	{
		return _tipoConjunto;
	}
}
