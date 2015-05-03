package classes.uteis;

import classes.uteis.controle.Controle;

/**
 * Classe que representa um player do jogo.
 * @author Matheus
 *
 */
public class Player
{
	/**
	 * Enumerador para o tipo do player.
	 * @author Matheus
	 *
	 */
	public enum TipoPlayer
	{
		Primario,
		Secundario,
	}
	
	static public Player playerPrimario = null;
	static public Player playerSecundario = null;
	
	private Controle _controle;
	private TipoPlayer _tipo;
	
	/**
	 * Contrói os players do jogo.
	 */
	public static void ControiPlayers()
	{
		playerPrimario = new Player(TipoPlayer.Primario);
		playerSecundario = new Player(TipoPlayer.Secundario);
	}
	
	/**
	 * Contrói um player específico.
	 * @param tipo
	 */
	public Player(TipoPlayer tipo)
	{
		_tipo = tipo;
		
		this.IniciaControle();
	}

	/**
	 * Inicia o controle do player.
	 */
	public void IniciaControle()
	{
		_controle = new Controle(_tipo);
	}
	
	/**
	 * Retorna o {@link Controle} deste jogador.
	 * @return Instancia do {@link Controle} deste jogador.
	 */
	public Controle GetControle()
	{
		return _controle;
	}

	/**
	 * Deine um novo {@link Controle} para este jogador.
	 * @param controle {@link Controle} deste jogador.
	 */
	public void SetControle(Controle controle)
	{
		this._controle = controle;
	}
	
	/**
	 * Retorna o {@link TipoPlayer} do jogador.
	 * @return Tipo do jogador.
	 */
	public TipoPlayer GetTipo()
	{
		return _tipo;
	}
}
