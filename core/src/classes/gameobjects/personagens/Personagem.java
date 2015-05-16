package classes.gameobjects.personagens;

import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import classes.gameobjects.GameObject;
import classes.uteis.Camada;
import classes.uteis.Serializador;

/**
 * Classe que representa as classes do jogo. Tem propriedades como agilidade, defesa, ataque, vida, chanse de dano cr�tico, etc.
 * @author matheus
 *
 */
public abstract class Personagem extends GameObject implements Serializador
{
	/**
	 * Classe que representa uma c�lular para calcular o A* e guardar valores de custo de movimenta��o.
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
		 * Cria uma nova c�lula para o a*
		 * @param custoMovimento Custo de movimento.
		 * @param custoHeuristica Custo da heuristica.
		 * @param parente Parente desta c�lula.
		 * @param posicao Posi��o desta c�lula.
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
	protected LinkedList<Vector2> _caminho = null;
	protected Vector2 _destino = null;
	private float _coeficienteLerp = 0;
	
	/**
	 * Cria um novo personagem.
	 */
	public Personagem()
	{
		super();
		personagens.put(this.GetId(), this);
		_colidiveis.put(GameObjects.Cenario, Colisoes.NaoPassavel);
		_caminho = new LinkedList<Vector2>();
		_destino = new Vector2();
		_camada = Camada.Personagens;
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
	public float GetChanceCritico()
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
	
	
	//TODO: fazer movimenta��o segundo deltatime
	/**
	 * Movimenta o {@link Personagem personagem} para o destino desejado.
	 * @param destino {@link Vector2 Destino} do personagem.
	 * @param deltaTime Delta time do frame para multriplar como alpha.
	 * @return True caso tenha alca�ado o destino neste frame.
	 * @see {@link Vector2#lerp(Vector2, float)}
	 */
	public boolean Movimenta(Vector2 destino, float deltaTime)
	{
		this.TocaSom(SomPersonagem.Movimenta);
		this.SetPosicao(_posicaoTela.lerp(destino, _agilidade * _coeficienteLerp));
		this.Rotaciona(_posicaoTela.angle(destino));
		
		if (_agilidade * _coeficienteLerp == 1)
		{
			_coeficienteLerp = 0;
			return true;
		}
		
		//como o lerp tem por padr�o o smooth, ou seja, sempre anda uma parte do caminho
		//e conforme o caminho diminui, ele anda menos do caminho e portanto nunca chega,
		//devemos utilizar um coeficiente multiplicador para compensar isso
		//se chegamos no destino
		_coeficienteLerp = MathUtils.clamp(_coeficienteLerp + deltaTime, 0f, 1/_agilidade);
		
		return false;
	}
	
	/**
	 * Movimenta o personagem pelo caminho definido em {@link #_caminho}. Caso o caminho esteja vazio ou nulo, n�o faz nada.
	 * Ele vai se movimentar da posi��o atual, at� a pr�xima da pilha de caminho segundo este deltatime.
	 * @param deltaTime Deltatime do frame para realizar a movimenta��o.
	 * @return True se alcan�ou o pr�ximo ponto do caminho, falso caso contr�rio e quando n�o h� caminho.
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
	public void InflingeDano(Personagem inflige)
	{
		inflige.RecebeDano(_ataque);
	}
	
	/**
	 * Fun��o que retorna um caminho at� o {@link #_destino destino}. Calculado com A*.
	 * @return {@link LinkedList<Vector2> Fila} da posi��o atual at� o destino. Ou nulo caso n�o haja caminho. 
	 * Este caminho tamb�m � defino em {@link #_caminho}, mas quando n�o h� caminho {@link #_caminho} fica limpa.
	 */
	protected LinkedList<Vector2> GetCaminho()
	{	
		if (_telaInserido == null)
			return null;
					
		LinkedList<CelulaCaminho> listaAberta = new LinkedList<CelulaCaminho>();
		LinkedList<CelulaCaminho> listaFechada = new LinkedList<CelulaCaminho>();
		LinkedList<CelulaCaminho> adjacentes = new LinkedList<CelulaCaminho>();
		Rectangle aux = new Rectangle(_posicaoTela.x, _posicaoTela.y, _caixaColisao.width, _caixaColisao.height);
		CelulaCaminho atual = new CelulaCaminho(0, Manhattan(_posicaoTela, _destino), null, _posicaoTela);
		CelulaCaminho temp = null;
		listaAberta.add(atual);
				
		if (this.GetValorCampo(Rectangle.tmp.setPosition(_destino).setSize(_caixaColisao.width, _caixaColisao.height)) == Colisoes.NaoPassavel)
			return null;
		
		//enquanto n�o cheguei no meu destino
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
			if (aux.setPosition(atual.posicao).contains(_destino) || atual.posicao.dst(_destino) < _agilidade)
			{
				_caminho.addFirst(_destino);
				
				//cria a pilha do caminho a percorrer e encerra o loop
				while (atual.parente != null)
				{
					_caminho.addFirst(atual.posicao);
					atual = atual.parente;
				}
				
				break;
			}
			
			//pega os 8 adjacentes a posicao atual
			adjacentes = this.GetAdjacentesAndaveis(atual, _destino);
			
			//para cada adjacente
			for (int i = 0; i < adjacentes.size(); i++)
			{
				CelulaCaminho adjacente = adjacentes.get(i);
				
				//se ja vimos, retorna
				if (listaFechada.contains(adjacente))
					continue;

				//se os adjacentes nao est�o na lista aberta, adiciona
				if (!listaAberta.contains(adjacente))
				{
					listaAberta.add(adjacente);
				}
				//se j� cont�m, valida a distancia entre o adjacente e o pai dele e verifica se h� um melhor caminho
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
	 * Retorna um custo heur�stico entre a posi��o e o destino.
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
		Rectangle auxCampo = new Rectangle(0, 0, _caixaColisao.width, _caixaColisao.height);
		Vector2 auxPosicao = new Vector2();
		
		//DIREITA
		auxPosicao.x = atual.posicao.x + _telaInserido.GetPrecisarMapaX();
		auxPosicao.y = atual.posicao.y;
		auxCampo.setPosition(auxPosicao);
		Colisoes resultado = Colisoes.Livre;
		if ((resultado = this.GetValorCampo(auxCampo)) != Colisoes.NaoPassavel)
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10 + resultado.ordinal(), this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//CIMA
		auxPosicao.x = atual.posicao.x;
		auxPosicao.y = atual.posicao.y + _telaInserido.GetPrecisarMapaY();
		auxCampo.setPosition(auxPosicao);
		if ((resultado = this.GetValorCampo(auxCampo)) != Colisoes.NaoPassavel)
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10 + resultado.ordinal(), this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//ESQUERDA
		auxPosicao.x = atual.posicao.x - _telaInserido.GetPrecisarMapaX();
		auxPosicao.y = atual.posicao.y;
		auxCampo.setPosition(auxPosicao);
		if ((resultado = this.GetValorCampo(auxCampo)) != Colisoes.NaoPassavel)
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10 + resultado.ordinal(), this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//BAIXO
		auxPosicao.x = atual.posicao.x;
		auxPosicao.y = atual.posicao.y - _telaInserido.GetPrecisarMapaY();
		auxCampo.setPosition(auxPosicao);
		if ((resultado = this.GetValorCampo(auxCampo)) != Colisoes.NaoPassavel)
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10 + resultado.ordinal(), this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//NORDESTE
		auxPosicao.x = atual.posicao.x + _telaInserido.GetPrecisarMapaX();
		auxPosicao.y = atual.posicao.y + _telaInserido.GetPrecisarMapaY();
		auxCampo.setPosition(auxPosicao);
		if ((resultado = this.GetValorCampo(auxCampo)) != Colisoes.NaoPassavel)
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14 + resultado.ordinal(), this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//NOROESTE
		auxPosicao.x = atual.posicao.x - _telaInserido.GetPrecisarMapaX();
		auxPosicao.y = atual.posicao.y + _telaInserido.GetPrecisarMapaY();
		auxCampo.setPosition(auxPosicao);
		if ((resultado = this.GetValorCampo(auxCampo)) != Colisoes.NaoPassavel)
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14 + resultado.ordinal(), this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//SUDOESTE
		auxPosicao.x = atual.posicao.x - _telaInserido.GetPrecisarMapaX();
		auxPosicao.y = atual.posicao.y - _telaInserido.GetPrecisarMapaY();
		auxCampo.setPosition(auxPosicao);
		if ((resultado = this.GetValorCampo(auxCampo)) != Colisoes.NaoPassavel)
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14 + resultado.ordinal(), this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//SUDESTE
		auxPosicao.x = atual.posicao.x + _telaInserido.GetPrecisarMapaX();
		auxPosicao.y = atual.posicao.y - _telaInserido.GetPrecisarMapaY();
		auxCampo.setPosition(auxPosicao);
		if ((resultado = this.GetValorCampo(auxCampo)) != Colisoes.NaoPassavel)
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14 + resultado.ordinal(), this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}

		return adjacentes;
	}
	
	/**
	 * Valida se um campo � pass�vel, livre ou n�o pass�vel.
	 * Realiza colis�es deste objeto com os objetos dentro do campo informado. A caixa de colis�o deste objeto � temporatiamente movida para o x e y do campo.
	 * @param campo {@link Rectangle Campo} para verificar.
	 * @return Se o campo � livre, pass�vel ou n�o pass�vel.
	 */
	public Colisoes GetValorCampo(Rectangle campo)
	{
		if (_telaInserido == null)
			return Colisoes.Passavel;

		LinkedList<GameObject> lista = _telaInserido.GetObjetosRegiao(campo);
		
		if (lista == null)
			return Colisoes.NaoPassavel;
		
		GameObject outro = null;
		Colisoes colisaoOutro, colisaoEste;
		Colisoes retorno = Colisoes.Livre;
		
		float x = _caixaColisao.x;
		float y = _caixaColisao.y;
		_caixaColisao.x = campo.x;
		_caixaColisao.y = campo.y;
		
		for (int i = 0; i < lista.size(); i++)
		{
			outro = lista.get(i);
			colisaoOutro = outro.ValidaColisao(this, false);
			colisaoEste = this.ValidaColisao(outro, false);
			
			if (colisaoEste.ordinal() > colisaoOutro.ordinal())
				retorno = colisaoEste;
			else
				retorno = colisaoOutro;
			
			if (retorno == Colisoes.NaoPassavel)
				break;
		}
		
		_caixaColisao.x = x;
		_caixaColisao.y = y;
		
		return retorno;
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
		_chanseCritico = personagemTemp.GetChanceCritico();
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
	
	@Override
	public void AoColidir(GameObject colidiu)
	{
		if (_colidiveis.get(colidiu.GetTipo()) == Colisoes.NaoPassavel)
			this.GetCaminho();
	}
}
