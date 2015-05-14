package classes.gameobjects.personagens.inimigos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import classes.gameobjects.personagens.Personagem;
import classes.uteis.Reciclador;
import classes.uteis.Reciclavel;

/**
 * Classe base para todos os inimigos do jogo.
 * @author matheus
 *
 */
public abstract class Inimigo extends Personagem implements Reciclavel
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
	 * Enumerador para os {@link Sound sons} dos {@link Inimigo inimigos}.
	 * @author matheus
	 *
	 */
	protected enum SomInimigo
	{
		Movimenta,
		Morte,
		Dano,
		Ataque,
	}
	
	/**
	 * Enumerador para as {@link Animation animações} dos {@link Inimigo inimigos}.
	 * @author matheus
	 *
	 */
	protected enum AnimacaoInimigo
	{
		Parado,
		Movimento,
		Morto,
		Dano,
		Ataque,
	}
	
	static public HashMap<Integer, Inimigo> inimigos = new HashMap<Integer, Inimigo>();
	static private Reciclador<Inimigo> _reciclador = new Reciclador<Inimigo>(); 
	protected Stack<Vector2> _caminho = null;
	
	/**
	 * Cria um novo inimigo.
	 */
	public Inimigo()
	{
		super();
		inimigos.put(this.GetId(), this);
		_caminho = new Stack<Vector2>();
	}
	
	@Override
	public void Morre()
	{
		_reciclador.Recicla(this);
	}
	
	@Override
	public void Recicla()
	{
		this.Redefine();
	}
	
	/**
	 * Função que retorna um caminho até o destino. Calculado com A*.
	 * @param destino {@link Vector2 Destino} do objeto.
	 * @return {@link Stack<Vector2 Caminho} da posição atual até o destino. Ou nulo caso não haja caminho. 
	 * Este caminho também é defino em {@link #_caminho}, mas quando não há caminho {@link #_caminho} fica limpa.
	 */
	protected Stack<Vector2> GetCaminho(Vector2 destino)
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
			if (aux.setPosition(atual.posicao).contains(destino))
			{
				//cria a pilha do caminho a percorrer e encerra o loop
				while (atual != null)
				{
					_caminho.push(atual.posicao);
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
		auxPosicao.x = atual.posicao.x + _caixaColisao.width + _agilidade;
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
		auxPosicao.y = atual.posicao.y + _caixaColisao.height + _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//ESQUERDA
		auxPosicao.x = atual.posicao.x - _caixaColisao.width - _agilidade;
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
		auxPosicao.y = atual.posicao.y - _caixaColisao.height - _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 10, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//NORDESTE
		auxPosicao.x = atual.posicao.x + _caixaColisao.width + _agilidade;
		auxPosicao.y = atual.posicao.y + _caixaColisao.height + _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//NOROESTE
		auxPosicao.x = atual.posicao.x - _caixaColisao.width - _agilidade;
		auxPosicao.y = atual.posicao.y + _caixaColisao.height + _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//SUDOESTE
		auxPosicao.x = atual.posicao.x - _caixaColisao.width - _agilidade;
		auxPosicao.y = atual.posicao.y - _caixaColisao.height - _agilidade;
		auxCampo.setPosition(auxPosicao);
		if (_telaInserido.GetCampoLivre(this, auxCampo))
		{
			if (!CelulaCaminho.celulas.containsKey(auxPosicao))
				adjacentes.add(new CelulaCaminho(atual.custoMovimento + 14, this.Manhattan(auxPosicao, destino), atual, new Vector2(auxPosicao)));
			else
				adjacentes.add(CelulaCaminho.celulas.get(auxPosicao));
		}
		
		//SUDESTE
		auxPosicao.x = atual.posicao.x + _caixaColisao.width + _agilidade;
		auxPosicao.y = atual.posicao.y - _caixaColisao.height - _agilidade;
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
}
