package classes.gameobjects;

/**
 * Classe que representa as classes do jogo. Tem propriedades como agilidade, defesa, ataque, vida, chanse de dano crítico, etc.
 * @author matheus
 *
 */
public abstract class Classe extends GameObject
{
	/**
	 * Enumerador para os tipos de {link {@link Classe classes} possíveis.
	 * @author matheus
	 *
	 */
	public enum Classes
	{
		Barbaro,
		Lutador,
		Gatuno,
		Mago,
		Arqueiro,
		Bardo,
	}
	
	/**
	 * Enumerador para o estado do {@link GameObject game object} que herda desta {@link Classe classe}.
	 * @author matheus
	 *
	 */
	public enum Estado
	{
		Atacando,
		Andando,
		Parado,
	}
	
	protected float _agilidade = 0;
	protected float _defesa = 0;
	protected float _ataque = 0;
	protected float _vida = 0;
	protected float _chanseCritico = 0;
	protected Classes _classe = null;
	protected float _coeficienteCritico = 0;
	protected Estado _estado = Estado.Parado;
	protected boolean _colidido = false;
	protected String _descricaoClasse = "";
	
	
	/**
	 * @return Agilidade com que o {@link Ator ator} se move e interage no ambiente.
	 */
	public float GetAgilidade()
	{
		return _agilidade;
	}
	
	/**
	 * @return Quantidade de pontos de defesa do {@link Ator ator}. Pontos de defesa são subtraídos do ataque inflingido a este ator.
	 */
	public float GetDefesa()
	{
		return _defesa;
	}
	
	/**
	 * @return Quantidade de pontos de ataque que este {@link Ator ator} inflige à outro ator.
	 */
	public float GetAtaque()
	{
		return _ataque;
	}
	
	/**
	 * @return Quantidade de pontos de vida deste {@link Ator ator}.
	 */
	public float GetVida()
	{
		return _vida;
	}
	
	/**
	 * @return {@link Estado Estado} atual do {@link Ator ator}.
	 */
	public Estado GetEstado()
	{
		return _estado;
	}
	
	/**
	 * @return {@link String Descrição} em nível humano da classe. 
	 */
	public String GetDescricao()
	{
		return _descricaoClasse;
	}
	
	/**
	 * @return Chanse de acertar um dano crítico - em percentagem.
	 */
	public float GetChanseCritico()
	{
		return _chanseCritico;
	}
	
	/**
	 * @return Quantas vezes o dano crítico é mais forte que o comum.
	 */
	public float GetCoeficienteCritico()
	{
		return _coeficienteCritico;
	}
	
	/**
	 * @return {@link Classes Classe} do personagem.
	 */
	public Classes GetClasse()
	{
		return _classe;
	}
		
	/**
	 * Inflinge dano ao {@link Ator ator}.
	 * @param dano Pontos de dano a inflingir. Repare que destes pontos serão subtraídos a defesa do ator.
	 */
	public void RecebeDano(float dano)
	{
		_vida -= Math.abs(dano - _defesa);
	}
	
	/**
	 * Inflige um dano a um {@link GameObject game object}.
	 * @param inflige {@link Classe Objeto} a infligir o dano.
	 */
	public <T extends Classe> void InfligeDano(T inflige)
	{
		inflige.RecebeDano(_ataque);
	}
}
