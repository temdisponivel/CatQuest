package classes.gameobjects.personagens;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import classes.gameobjects.GameObject;
import classes.uteis.Log;
import classes.uteis.Serializador;

/**
 * Classe que representa as classes do jogo. Tem propriedades como agilidade, defesa, ataque, vida, chanse de dano cr�tico, etc.
 * @author matheus
 *
 */
public abstract class Personagem extends GameObject implements Serializador
{
	/**
	 * Enumarador para os sons do personagem.
	 * @author matheus
	 *
	 */
	protected enum SomPersonagem
	{
		Movimenta,
		Morte,
		Dano,
	}
	
	/**
	 * Enumerador para as {@link Animation anima��es} dos {@link Personagem personagens}.
	 * @author matheus
	 *
	 */
	protected enum AnimacaoPersonagem
	{
		Parado,
		Movimento,
		Morto,
		Dano,
	}
	
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
	
	static public HashMap<Integer, Personagem> personagens = new HashMap<Integer, Personagem>();
	protected float _agilidade = 0;
	protected float _defesa = 0;
	protected float _ataque = 0;
	protected float _vida = 0;
	protected float _chanseCritico = 0;
	protected float _coeficienteCritico = 0;
	protected Estado _estado = Estado.Parado;
	protected boolean _colidido = false;
	protected FileHandle _arquivo = Gdx.files.local("arquivos/personagens/" + this.toString());
	
	/**
	 * Cria um novo personagem.
	 */
	public Personagem()
	{
		super();
		personagens.put(this.GetId(), this);
		_colidiveis.add(GameObjects.Cenario);
	}
	
	@Override
	public void Inicia()
	{
		super.Inicia();
		//TODO: validar o carregamento via arquivo
		//this.Carrega();
	}
		
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
	 * Movimenta o {@link Personagem personagem} para o destino desejado.
	 * @param destino {@link Vector2 Destino} do personagem.
	 * @param deltaTime Delta time do frame para multriplar como alpha.
	 * @return True caso tenha alca�ado o destino neste frame.
	 * @see {@link Vector2#lerp(Vector2, float)}
	 */
	public boolean Movimenta(Vector2 destino, float deltaTime)
	{
		boolean retorno = false;
		this.TocaSom(SomPersonagem.Movimenta);
		Log.instancia.Logar(destino.toString(), String.valueOf(deltaTime));
		this.SetPosicao(this.GetPosicao().lerp(destino, (retorno = 1 * deltaTime > 1) ? 1 : 1 * deltaTime));
		return retorno;
	}
		
	/**
	 * Inflinge dano ao {@link Ator ator}.
	 * @param dano Pontos de dano a inflingir. Repare que destes pontos ser�o subtra�dos a defesa do ator.
	 */
	public void RecebeDano(float dano)
	{
		this.TocaSom(SomPersonagem.Dano);
		_vida -= Math.abs(dano - _defesa);
		
		if (_vida <= 0)
			this.Morre();
	}
	
	/**
	 * Inflige um dano a um {@link GameObject game object}.
	 * @param inflige {@link Personagem Objeto} a infligir o dano.
	 */
	public void InfligeDano(Personagem inflige)
	{
		inflige.RecebeDano(_ataque);
	}
	
	/**
	 * Fun��o para quando o personagem morrer. Ou seja, quando seu {@link #_vida =< 0}.
	 */
	abstract public void Morre();
	
	@Override
	public boolean Carrega()
	{
		//se o arquivo nao existe, cria para que possa ser alterado e retorna falso
		if (!_arquivo.exists())
		{
			this.Salva();
			return false;
		}
		
		Json json = new Json();
		String personagem = _arquivo.readString();
		
		//carrega do arquivo
		Personagem personagemTemp = json.fromJson(Personagem.class, personagem);
		
		_agilidade = personagemTemp.GetAgilidade();
		_defesa = personagemTemp.GetDefesa();
		_ataque = personagemTemp.GetAtaque();
		_vida = personagemTemp.GetVida();
		_chanseCritico = personagemTemp.GetChanseCritico();
		_coeficienteCritico = personagemTemp.GetCoeficienteCritico();
		
		return true;
	}
	
	@Override
	public void Salva()
	{
		Json json = new Json();
		json.setUsePrototypes(false);
		String personagem;
		personagem = json.toJson((Personagem)this);
		_arquivo.writeString(personagem, false);
	}
	
}
