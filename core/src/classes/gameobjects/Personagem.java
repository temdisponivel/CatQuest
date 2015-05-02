package classes.gameobjects;

/**
 * Classe que representa as classes do jogo. Tem propriedades como agilidade, defesa, ataque, vida, chanse de dano cr�tico, etc.
 * @author matheus
 *
 */
public abstract class Personagem extends GameObject
{
	
	/**
	 * Enumerador para o estado do {@link GameObject game object} que herda desta {@link Personagem classe}.
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
	protected float _coeficienteCritico = 0;
	protected Estado _estado = Estado.Parado;
	protected boolean _colidido = false;
	
	
	/**
	 * @return Agilidade com que o {@link Ator ator} se move e interage no ambiente.
	 */
	public float GetAgilidade()
	{
		return _agilidade;
	}
	
	/**
	 * @return Quantidade de pontos de defesa do {@link Ator ator}. Pontos de defesa s�o subtra�dos do ataque inflingido a este ator.
	 */
	public float GetDefesa()
	{
		return _defesa;
	}
	
	/**
	 * @return Quantidade de pontos de ataque que este {@link Ator ator} inflige � outro ator.
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
	 * @return Chanse de acertar um dano cr�tico - em percentagem.
	 */
	public float GetChanseCritico()
	{
		return _chanseCritico;
	}
	
	/**
	 * @return Quantas vezes o dano cr�tico � mais forte que o comum.
	 */
	public float GetCoeficienteCritico()
	{
		return _coeficienteCritico;
	}
		
	/**
	 * Inflinge dano ao {@link Ator ator}.
	 * @param dano Pontos de dano a inflingir. Repare que destes pontos ser�o subtra�dos a defesa do ator.
	 */
	public void RecebeDano(float dano)
	{
		_vida -= Math.abs(dano - _defesa);
	}
	
	/**
	 * Inflige um dano a um {@link GameObject game object}.
	 * @param inflige {@link Personagem Objeto} a infligir o dano.
	 */
	public <T extends Personagem> void InfligeDano(T inflige)
	{
		inflige.RecebeDano(_ataque);
	}
}
