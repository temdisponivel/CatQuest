package classes.uteis;

import classes.uteis.controle.Controle;
import catquest.CatQuest;

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
		UM,
		DOIS,
	}
	
	Controle _controle;
	TipoPlayer _tipo;
	
	public Player(TipoPlayer tipo)
	{
		_tipo = tipo;
		_controle = new Controle(this.GetTipo());
		
		if (this.GetTipo() == TipoPlayer.UM)
			_controle.SetConjunto(CatQuest.instancia.GetConfig().GetComandoPlayer1());
		else
			_controle.SetConjunto(CatQuest.instancia.GetConfig().GetComandoPlayer2());
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
