//TODO: pensar num sistema de carregar as propriedades e habilidades via arquivo

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
		Bárbaro,
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
	
	@Override
	public void AoColidir(GameObject colidiu)
	{
		_colidido = true;
		
		//se somos um heroi
		if (this instanceof Heroi)
		{
			//se colidimos com um inimigo
			if (colidiu instanceof Inimigo)
			{
				//se estamos atacando
				if (_estado == Estado.Atacando)
				{
					this.InfligeDano((Inimigo) colidiu); //inflige dano ao inimigo
				}
			}
		}
		//se somos um inimigo
		else if (this instanceof Inimigo)
		{
			//se colidimos com um heroi
			if (colidiu instanceof Heroi)
			{
				//se o inimigo não está atacando
				if (((Heroi) colidiu).GetEstado() != Estado.Atacando)
				{
					this.InfligeDano((Heroi) colidiu); //inflige dano ao inimigo
				}	
			}
		}
	}
}
