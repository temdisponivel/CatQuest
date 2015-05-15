package classes.gameobjects.personagens;

import java.util.HashMap;
import java.util.LinkedList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import classes.gameobjects.GameObject;
import classes.uteis.Serializador;

/**
 * Classe que representa as classes do jogo. Tem propriedades como agilidade, defesa, ataque, vida, chanse de dano crítico, etc.
 * @author matheus
 *
 */
public abstract class Personagem extends GameObject implements Serializador
{
	/**
	 * Classe que representa uma célular para calcular o A* e guardar valores de custo de movimentação.
	 * @author matheus
	 *
	 */
	static private class CelulaCaminho
	{
		int custoTotal = 0;
		int custoMovimento = 0;
		int custoHeuristica = 0;
		CelulaCaminho parente = null;
		Vector2 posicao = null;
		static HashMap<Vector2, CelulaCaminho> celulas = new HashMap<Vector2, CelulaCaminho>();
		
		/**
		 * Cria uma nova célula para o a*
		 * @param custoMovimento Custo de movimento.
		 * @param custoHeuristica Custo da heuristica.
		 * @param parente Parente desta célula.
		 * @param posicao Posição desta célula.
		 */
		public CelulaCaminho(int custoMovimento, int custoHeuristica, CelulaCaminho parente, Vector2 posicao)
		{
			this.custoMovimento = custoMovimento;
			this.custoHeuristica = custoHeuristica;
			this.parente = parente;
			this.posicao = posicao;
			this.custoTotal = this.custoHeuristica + this.custoMovimento;
			celulas.put(posicao, this);
		}
		
		@Override
		public boolean equals(Object obj)
		{
			return obj == this; 
		}
	}
	
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
	 * Enumerador para as {@link Animation animações} dos {@link Personagem personagens}.
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
	protected LinkedList<Vector2> _caminho = null;
	
	/**
	 * Cria um novo personagem.
	 */
	public Personagem()
	{
		super();
		personagens.put(this.GetId(), this);
		_colidiveis.add(GameObjects.Cenario);
		_caminho = new LinkedList<Vector2>();
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
	
	
	//TODO: fazer movimentação segundo deltatime
	/**
	 * Movimenta o {@link Personagem personagem} para o destino desejado.
	 * @param destino {@link Vector2 Destino} do personagem.
	 * @param deltaTime Delta time do frame para multriplar como alpha.
	 * @return True caso tenha alcaçado o destino neste frame.
	 * @see {@link Vector2#lerp(Vector2, float)}
	 */
	public boolean Movimenta(Vector2 destino, float deltaTime)
	{
		boolean retorno = false;
		this.TocaSom(SomPersonagem.Movimenta);
		this.SetPosicao(_posicaoTela.lerp(destino, (retorno = (_agilidade * deltaTime) >= 1) ? 1 : _agilidade * deltaTime));
		return retorno;
	}
	
	/**
	 * Movimenta o personagem pelo caminho definido em {@link #_caminho}. Caso o caminho esteja vazio ou nulo, não faz nada.
	 * Ele vai se movimentar da posição atual, até a próxima da pilha de caminho segundo este deltatime.
	 * @param deltaTime Deltatime do frame para realizar a movimentação.
	 * @return True se alcançou o próximo ponto do caminho, falso caso contrário e quando não há caminho.
	 */
	public boolean MovimentaCaminho(float deltaTime)
	{
		if (_caminho == null || _caminho.isEmpty())
			return false;
		
		if (this.Movimenta(_caminho.element(), deltaTime))
		{
			_caminho.pop();
			return true;
		}
		
		return false;
	}
		
	/**
	 * Inflinge dano ao {@link Ator ator}.
	 * @param dano Pontos de dano a inflingir. Repare que destes pontos serão subtraídos a defesa do ator.
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
	 * Função que retorna um caminho até o destino. Calculado com A*.
	 * @param destino {@link Vector2 Destino} do objeto.
	 * @return {@link LinkedList<Vector2> Fila} da posição atual até o destino. Ou nulo caso não haja caminho. 
	 * Este caminho também é defino em {@link #_caminho}, mas quando não há caminho {@link #_caminho} fica limpa.
	 */
	protected LinkedList<Vector2> GetCaminho(Vector2 destino)
	{		
		LinkedList<CelulaCaminho> listaAberta = new LinkedList<CelulaCaminho>();
		LinkedList<CelulaCaminho> listaFechada = new LinkedList<CelulaCaminho>();
		LinkedList<CelulaCaminho> adjacentes = new LinkedList<CelulaCaminho>();
		Rectangle aux = new Rectangle(_posicaoTela.x, _posicaoTela.y, _caixaColisao.width, _caixaColisao.height);
		CelulaCaminho atual = new CelulaCaminho(0, Manhattan(_posicaoTela, destino), null, _posicaoTela);
		CelulaCaminho temp = null;
		listaAberta.add(atual);
		
		//enquanto não cheguei no meu destino
		while (!listaAberta.isEmpty())
		{
			atual = listaAberta.get(0);
			//percorre todos os da lista aberta e paga o que tem o menor valor total
			for (int i= 0; i < listaAberta.size(); i++)
			{
				temp = listaAberta.get(i);
				
				if (temp.custoTotal < atual.custoTotal)
					atual = temp;
			}
			
			//retira da lista aberta e coloca na lista fechada
			listaAberta.remove(atual);
			listaFechada.add(atual);
			
			//se achamos
			if (aux.setPosition(atual.posicao).contains(destino) || atual.posicao.dst(destino) < _agilidade)
			{
				_caminho.addFirst(destino);
				
				//cria a pilha do caminho a percorrer e encerra o loop
				while (atual.parente != null)
				{
					_caminho.addFirst(atual.posicao);
					atual = atual.parente;
				}
				
				break;
			}
			
			//pega os 8 adjacentes a posicao atual
			adjacentes = this.GetAdjacentesAndaveis(atual, destino);
			
			//para cada adjacente
			for (int i = 0; i < adjacentes.size(); i++)
			{
				CelulaCaminho adjacente = adjacentes.get(i);
				
				//se ja vimos, retorna
				if (listaFechada.contains(adjacente))
					continue;

				//se os adjacentes nao estão na lista aberta, adiciona
				if (!listaAberta.contains(adjacente))
				{
					listaAberta.add(adjacente);
				}
				//se já contém, valida a distancia entre o adjacente e o pai dele e verifica se há um melhor caminho
				else
				{
					//se estamos lidando com um adjacente em algulo reto
					if ((atual.posicao.x == adjacente.posicao.x) || (atual.posicao.y == adjacente.posicao.y))
					{
						if (atual.custoMovimento + 10 < adjacente.custoMovimento)
						{
							adjacente.custoMovimento = atual.custoMovimento + 10;
							adjacente.custoTotal = adjacente.custoHeuristica + adjacente.custoMovimento;
							adjacente.parente = atual;
						}
					}
					//se estamos lidando com um adjacente na diagonal
					else
					{
						if (atual.custoMovimento + 14 < adjacente.custoMovimento)
						{
							adjacente.custoMovimento = atual.custoMovimento + 14;
							adjacente.custoTotal = adjacente.custoHeuristica + adjacente.custoMovimento;
							adjacente.parente = atual;
						}
					}
				}
			}
		}
		
		return _caminho;
	}
	
	/**
	 * Retorna um custo heurístico entre a posição e o destino.
	 * @param posicao {@link Vector2 Posicao} atual.
	 * @param destino {@link Vector2 Posicao} do destino.
	 */
	protected int Manhattan(Vector2 posicao, Vector2 destino)
	{
		int x = (int) Math.abs(posicao.x - destino.x);
		int y = (int) Math.abs(posicao.y - destino.y);
		return 1 * (x + y);
	}
	
	/**
	 * @param atual {@link CelulaCaminho Celula} para pegar os adjacentes.
	 * @return {@link LinkedList<CelulaCaminho> Lista} com os adjacentes andaveis.
	 */
	protected LinkedList<CelulaCaminho> GetAdjacentesAndaveis(CelulaCaminho atual, Vector2 destino)
	{
		LinkedList<CelulaCaminho> adjacentes = new LinkedList<CelulaCaminho>();
		Rectangle auxCampo = new Rectangle(0, 0, _caixaColisao.width, _caixaColisao.height);;
		Vector2 auxPosicao = new Vector2();
		
		//DIREITA
		auxPosicao.x = atual.posicao.x + _agilidade;
		auxPosicao.y = atual.posicao.y;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//CIMA
		auxPosicao.x = atual.posicao.x;
		auxPosicao.y = atual.posicao.y + _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//ESQUERDA
		auxPosicao.x = atual.posicao.x - _agilidade;
		auxPosicao.y = atual.posicao.y;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//BAIXO
		auxPosicao.x = atual.posicao.x;
		auxPosicao.y = atual.posicao.y - _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//NORDESTE
		auxPosicao.x = atual.posicao.x + _agilidade;
		auxPosicao.y = atual.posicao.y + _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//NOROESTE
		auxPosicao.x = atual.posicao.x - _agilidade;
		auxPosicao.y = atual.posicao.y + _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//SUDOESTE
		auxPosicao.x = atual.posicao.x - _agilidade;
		auxPosicao.y = atual.posicao.y - _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//SUDESTE
		auxPosicao.x = atual.posicao.x + _agilidade;
		auxPosicao.y = atual.posicao.y - _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}

		return adjacentes;
	}
	
	/**
	 * Função para quando o personagem morrer. Ou seja, quando seu {@link #_vida =< 0}.
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
