package classes.uteis;

import classes.gameobjects.personagens.Personagem;
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
	
	private Controle _controle;
	private Personagem _personagemControlado;
	private TipoPlayer _tipo;
	
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
		if (_tipo == TipoPlayer.Primario)
			_controle = new Controle(Configuracoes.instancia.GetConjuntoPrimario());
		else if (_tipo == TipoPlayer.Secundario)
			_controle = new Controle(Configuracoes.instancia.GetConjuntoSecundario());
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
	 * @param tipo Novo {@link TipoPlayer tipo} para o player. E {@link #IniciaControle() inicia o controle}.
	 */
	public void SetTipo(TipoPlayer tipo)
	{
		_tipo = tipo;
		this.IniciaControle();
	}

	/**
	 * Retorna o {@link Personagem} do jogador.
	 * @return Personagem controlado por esse player.
	 */
	public Personagem GetPersonagem()
	{
		return _personagemControlado;
	}

	/**
	 * Define o {@link Personagem} do jogador
	 * @param Personagem que sera controlado
	 */
	public void SetPersonagem(Personagem _heroiControlado)
	{
		this._personagemControlado = _heroiControlado;
	}
}
