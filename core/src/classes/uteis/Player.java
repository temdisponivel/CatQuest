package classes.uteis;

import classes.gameobjects.personagens.herois.Heroi;
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
	private Heroi _heroiControlado;
	private TipoPlayer _tipo;
	
	/**
	 * Contr�i os players do jogo.
	 */
	public static void ControiPlayers()
	{
		playerPrimario = new Player(TipoPlayer.Primario);
		playerSecundario = new Player(TipoPlayer.Secundario);
	}
	
	/**
	 * Contr�i um player espec�fico.
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
	 * Define um novo {@link Controle} para este jogador.
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

	/**
	 * Retorna o {@link Heroi} do jogador.
	 * @return Heroi controlado por esse player.
	 */
	public Heroi GetHeroi()
	{
		return _heroiControlado;
	}

	/**
	 * Define o {@link Heroi} do jogador
	 * @param _heroiControlado heroi que sera controlado
	 */
	public void SetHeroi(Heroi _heroiControlado)
	{
		this._heroiControlado = _heroiControlado;
	}
}
