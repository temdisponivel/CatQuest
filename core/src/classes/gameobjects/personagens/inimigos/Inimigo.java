package classes.gameobjects.personagens.inimigos;

import java.util.HashMap;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.Personagem;
import classes.uteis.Camada;
import classes.uteis.controle.Controle.Direcoes;
import classes.uteis.reciclador.Reciclador;
import classes.uteis.reciclador.Reciclavel;

/**
 * Classe base para todos os inimigos do jogo.
 * 
 * @author matheus
 *
 */
public abstract class Inimigo extends Personagem implements Reciclavel
{
	/**
	 * Enumerador para os {@link Sound sons} dos {@link Inimigo inimigos}.
	 * 
	 * @author matheus
	 *
	 */
	protected enum SomInimigo
	{
		Movimenta, Morte, Dano, Ataque,
	}

	/**
	 * Enumerador para as {@link Animation anima��es} dos {@link Inimigo
	 * inimigos}.
	 * 
	 * @author matheus
	 *
	 */
	protected enum AnimacaoInimigo
	{
		Parado, Movimento, Morto, Dano, Ataque,
	}

	/**
	 * Enumerados para os poss�veis estados dos inimigos. Utilizado pela IA para
	 * o controle de comportamento.
	 * 
	 * @author matheus
	 *
	 */
	protected enum EstadosInimigo
	{
		Perambula, Segue, Ataca,
	}

	/**
	 * Valores fuzzyficados para a distancia, para poder definir segundo o
	 * inimigo.
	 * 
	 * @author matheus
	 *
	 */
	protected enum DistanciaFuzzyficada
	{
		Perto, Proximo, Distante,
	}

	/**
	 * Valores para a fuzzyfica��o da pr�pria vida e do inimigo.
	 * 
	 * @author matheus
	 *
	 */
	protected enum VidaFuzzyficada
	{
		Baixa, Media, Alta,
	}

	/**
	 * Valores para a fuzzyfica��o da dificuldade.
	 * 
	 * @author matheus
	 *
	 */
	public enum DificuldadeFuzzyficada
	{
		Facil, Normal, Dificil,
	}

	/**
	 * Triangulo para a l�gica fuzzy.
	 * 
	 * @author matheus
	 *
	 */
	protected class TrianguloFuzzy
	{
		float esquerda, centro, direita;

		/**
		 * Cria um triangulo para l�gica fuzzy com um valor para o ponto
		 * esquerdo e direito. O valor do centro � calculado a partir destes.
		 * 
		 * @param esquerda
		 *            Ponto esquerdo.
		 * @param direita
		 *            Ponto direito.
		 */
		public TrianguloFuzzy(float esquerda, float direita)
		{
			this.esquerda = esquerda;
			this.direita = direita;
			this.centro = (direita + esquerda) / 2;
		}
	}

	/**
	 * Classe que representa uma fuzifica��o, cont�m tr�s triangulos para
	 * fuzificar valores.
	 * 
	 * @author matheus
	 *
	 */
	protected class Fuzzyficacao
	{
		TrianguloFuzzy esquerda, centro, direita;

		/**
		 * Cria uma nova fuzifica��o.
		 * 
		 * @param esquerda
		 *            {@link TrianguloFuzzy Triangulo} da esquerda.
		 * @param centro
		 *            {@link TrianguloFuzzy Triangulo} do centro.
		 * @param direita
		 *            {@link TrianguloFuzzy Triangulo} da direita.
		 */
		public Fuzzyficacao(TrianguloFuzzy esquerda, TrianguloFuzzy centro, TrianguloFuzzy direita)
		{
			this.esquerda = esquerda;
			this.centro = centro;
			this.direita = direita;
		}
	}

	static public HashMap<Integer, Inimigo> inimigos = new HashMap<Integer, Inimigo>();
	static public Reciclador<Inimigo> _reciclador = new Reciclador<Inimigo>();
	protected EstadosInimigo _estado = EstadosInimigo.Perambula;
	protected Personagem _alvo = null;
	protected Fuzzyficacao _fuzzyVidaAlvo = null;
	protected Fuzzyficacao _fuzzyDistancia = null;
	protected Fuzzyficacao _fuzzyDificuldade = null;
	static protected int _quantiColunasBaseConhecimento = 4;
	static protected int _quantiLinhasBaseConhecimento = 27;
	static protected Object[][] _baseConhecimento = new Object[_quantiLinhasBaseConhecimento][_quantiColunasBaseConhecimento];
	int _direcaoPerambula = Direcoes.DIREITA;
	float _tempoEntreIA = MathUtils.random(1);
	float _ultimaIA = 0;
	float _quantValidacaoPerambula = 0;

	/**
	 * Cria um novo inimigo.
	 */
	public Inimigo()
	{
		super();
		_camada = Camada.Personagens;
		_tipo = GameObjects.Inimigo;
		inimigos.put(this.GetId(), this);
		this.ControiBaseConhecimento();
		_fuzzyDistancia = new Fuzzyficacao(new TrianguloFuzzy(-400, 400), new TrianguloFuzzy(200, 500), new TrianguloFuzzy(350,
				CatQuest.instancia.GetHipotenusaMundo()));
		_fuzzyVidaAlvo = new Fuzzyficacao(new TrianguloFuzzy(0, 50), new TrianguloFuzzy(25, 75), new TrianguloFuzzy(50, 150));
		_fuzzyDificuldade = new Fuzzyficacao(new TrianguloFuzzy(-.3f, 0.3f), new TrianguloFuzzy(0f, 0.7f), new TrianguloFuzzy(.35f, 1.35f));
	}

	@Override
	public void Inicia()
	{
		super.Inicia();
		_colidiveis.put(GameObjects.Heroi, Colisoes.Passavel);
		this.TrocaDirecaoPerambula();
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);

		Vector2 movimento = null;

		if (CatQuest.instancia.GetTempoJogo() - _ultimaIA > _tempoEntreIA)
		{
			this.DecideOQueFazer();
			_ultimaIA = CatQuest.instancia.GetTempoJogo();
		}

		// se devemos seguir o alvo, segue
		if (_estado == EstadosInimigo.Segue)
		{
			if (_alvo != null && _caminho.isEmpty())
			{
				_destino.set(_alvo.GetPosicao());
				if (this.GetCaminho() == null)
					_estado = EstadosInimigo.Perambula;
			}

			this.MovimentaCaminho(deltaTime);
		}
		else if (_estado == EstadosInimigo.Ataca)
		{
			this.Ataque();
			_caminho.clear();
		}
		else if (_estado == EstadosInimigo.Perambula)
		{
			this.ValidaPerambula();
			
			movimento = this.Movimenta(_direcaoPerambula, deltaTime, false);

			_caminho.clear();
		}
	}

	@Override
	public void Morre()
	{
		_reciclador.Recicla(this);

		if (_telaInserido != null)
			_telaInserido.Remover(this);
	}

	@Override
	public void Recicla()
	{
		this.SetPosicao(new Vector2());
		this.Encerra();
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
		if (_colidiveis.get(colidiu.GetTipo()) == Colisoes.NaoPassavel)
		{
			this.TrocaDirecaoPerambula();
		}
	}

	/**
	 * Troca a dire��o para o qual estamos perambulando.
	 */
	protected void TrocaDirecaoPerambula()
	{
		_direcaoPerambula = (Integer) CatQuest.instancia.GetAleatorio(Direcoes.CENTRO, Direcoes.CIMA, Direcoes.BAIXO, Direcoes.ESQUERDA, Direcoes.DIREITA,
				Direcoes.NORDESTE, Direcoes.NOROESTE, Direcoes.SUDESTE, Direcoes.SUDOESTE);
	}
	
	/**
	 * Valida se a dire��o que vai perambular � v�lida, se sim, n�o faz nada. Sen�o, troca a dire��o.
	 * @see {@link #TrocaDirecaoPerambula()}.
	 */
	protected void ValidaPerambula()
	{
		Vector2 posicao = this.GetPosicaoDirecao(_direcaoPerambula);
		if (posicao == null || this.GetValorCampo(posicao) == Colisoes.NaoPassavel)
		{
			if (_quantValidacaoPerambula < 1)
			{
				this.TrocaDirecaoPerambula();
				_quantValidacaoPerambula++;
				
				this.ValidaPerambula();
			}
			else
			{
				_direcaoPerambula = Direcoes.CENTRO;
				_quantValidacaoPerambula = 0;
			}
		}
	}

	/**
	 * Fun��o que executa a l�gica fuzzy e decide o que o fazer. O resultado �
	 * representado por estados definidos em {@link #_estado estado}.
	 */
	protected void DecideOQueFazer()
	{
		// define o estado padr�o como perambula
		_estado = EstadosInimigo.Perambula;

		if (_alvo == null || _fuzzyDistancia == null || _fuzzyVidaAlvo == null || _fuzzyDificuldade == null)
		{
			return;
		}

		float dificuldade = CatQuest.instancia.GetDificuldade();
		float vidaAlvo = _alvo.GetVida();
		float distanciaAlvo = _alvo.GetPosicao().dst(this.GetPosicao());
		HashMap<Object, Float> valoresFuzzyficados = new HashMap<Object, Float>();
		HashMap<EstadosInimigo, Float> valorRegra = new HashMap<EstadosInimigo, Float>();
		float[] valoresPertinencia = null;
		float aux = 0;

		// valor pertinencia vida do alvo
		valoresPertinencia = this.GetValorPertinencia(vidaAlvo, _fuzzyVidaAlvo);
		valoresFuzzyficados.put(VidaFuzzyficada.Baixa, valoresPertinencia[0]);
		valoresFuzzyficados.put(VidaFuzzyficada.Media, valoresPertinencia[1]);
		valoresFuzzyficados.put(VidaFuzzyficada.Alta, valoresPertinencia[2]);

		// valor pertinencia distancia do alvo
		valoresPertinencia = this.GetValorPertinencia(distanciaAlvo, _fuzzyDistancia);
		valoresFuzzyficados.put(DistanciaFuzzyficada.Perto, valoresPertinencia[0]);
		valoresFuzzyficados.put(DistanciaFuzzyficada.Proximo, valoresPertinencia[1]);
		valoresFuzzyficados.put(DistanciaFuzzyficada.Distante, valoresPertinencia[2]);

		// valores pertinencia para dificuldade
		valoresPertinencia = this.GetValorPertinencia(dificuldade, _fuzzyDificuldade);
		valoresFuzzyficados.put(DificuldadeFuzzyficada.Facil, valoresPertinencia[0]);
		valoresFuzzyficados.put(DificuldadeFuzzyficada.Normal, valoresPertinencia[1]);
		valoresFuzzyficados.put(DificuldadeFuzzyficada.Dificil, valoresPertinencia[2]);

		// para cada linha da base de conhecimento
		for (int i = 0; i < _quantiLinhasBaseConhecimento; i++)
		{
			// pra cada combina��o, monta a for�a de regra
			Object resultadoCombinacao = _baseConhecimento[i][3];

			// pega o menor valor para a fuzzyfica��o desta combina��o
			float minimo = Math.min(valoresFuzzyficados.get(_baseConhecimento[i][0]), valoresFuzzyficados.get(_baseConhecimento[i][2]));
			minimo = Math.min(valoresFuzzyficados.get(_baseConhecimento[i][1]), minimo);

			// se ainda n�o temos a regra final, adiciona
			if (!valorRegra.containsKey(resultadoCombinacao))
			{
				valorRegra.put((EstadosInimigo) resultadoCombinacao, minimo);

				if (minimo > aux)
				{
					_estado = (EstadosInimigo) resultadoCombinacao;
					aux = minimo;
				}

				continue;
			}

			// se j� temos, vamos se temos a maior, sen�o, sobrep�e
			if (valorRegra.get(resultadoCombinacao) < minimo)
			{
				valorRegra.replace((EstadosInimigo) resultadoCombinacao, minimo);

				if (minimo > aux)
				{
					_estado = (EstadosInimigo) resultadoCombinacao;
					aux = minimo;
				}
			}
		}
	}

	/**
	 * Retorna o valor de pertinencia.
	 * 
	 * @param valor
	 *            Valor real.
	 * @param fuzzy
	 *            Triangulos fuzzy para fuzzyfica��o.
	 * @return Os 3 valores fuzzyficados. [0] = esquerda, [1] = centro, [2] =
	 *         direita.
	 */
	private float[] GetValorPertinencia(float valor, Fuzzyficacao fuzzy)
	{
		float[] valores = new float[3];

		// pega os valores de pertinencia dos tres triangulos do campo de
		// fuzzyfica��o
		valores[0] = this.GetPertinencia(valor, fuzzy.esquerda);
		valores[1] = this.GetPertinencia(valor, fuzzy.centro);
		valores[2] = this.GetPertinencia(valor, fuzzy.direita);

		return valores;
	}

	/**
	 * Valor de pertin�ncia do valor real no triangulo de fuzzyfica��o.
	 * 
	 * @param valor
	 *            Valor real.
	 * @param triangulo
	 *            Triangulo com os valores de pertinencia.
	 * @return Valor de pertinencia.
	 */
	private float GetPertinencia(float valor, TrianguloFuzzy triangulo)
	{
		if (valor < triangulo.esquerda || valor > triangulo.direita)
			return 0;

		if (valor <= triangulo.centro)
			return (valor - triangulo.esquerda) / (triangulo.centro - triangulo.esquerda);
		else if (valor <= triangulo.direita)
			return (triangulo.direita - valor) / (triangulo.direita - triangulo.centro);
		else
			return 0;
	}

	/**
	 * Fun��o que contr�i a base de conhecimento para o fuzzy. N�o define os
	 * valores para cada combina��o.
	 */
	protected void ControiBaseConhecimento()
	{
		DistanciaFuzzyficada[] distancia = new DistanciaFuzzyficada[] { DistanciaFuzzyficada.Perto, DistanciaFuzzyficada.Proximo,
				DistanciaFuzzyficada.Distante };
		VidaFuzzyficada[] vida = new VidaFuzzyficada[] { VidaFuzzyficada.Baixa, VidaFuzzyficada.Media, VidaFuzzyficada.Alta };
		DificuldadeFuzzyficada[] dificuldade = new DificuldadeFuzzyficada[] { DificuldadeFuzzyficada.Facil, DificuldadeFuzzyficada.Normal,
				DificuldadeFuzzyficada.Dificil };

		int auxDistancia = 0;
		int auxVida = 0;
		int auxDificuldade = 0;
		for (int i = 0; i < _quantiLinhasBaseConhecimento; i++)
		{
			if (i % 9 == 0 && i != 0)
				auxDistancia = ++auxDistancia % 3;

			if (i % 3 == 0 && i != 0)
				auxVida = ++auxVida % 3;

			_baseConhecimento[i][0] = distancia[auxDistancia];
			_baseConhecimento[i][1] = vida[auxVida];
			_baseConhecimento[i][2] = dificuldade[auxDificuldade];

			auxDificuldade = ++auxDificuldade % 3;
		}

		this.DefineValoresBaseConhecimento();
	}

	/**
	 * Define os valores para cada combina��o da base de conhecimento. Esta
	 * sendo constru�da manualmente segundo o arquivo contendo a base de
	 * conhecimento. Arquivo na pasta do jogo.
	 * 
	 * @see {@link #ControiBaseConhecimento()}.
	 */
	public void DefineValoresBaseConhecimento()
	{
		_baseConhecimento[0][3] = EstadosInimigo.Ataca;
		_baseConhecimento[1][3] = EstadosInimigo.Ataca;
		_baseConhecimento[2][3] = EstadosInimigo.Ataca;
		_baseConhecimento[3][3] = EstadosInimigo.Ataca;
		_baseConhecimento[4][3] = EstadosInimigo.Ataca;
		_baseConhecimento[5][3] = EstadosInimigo.Ataca;
		_baseConhecimento[6][3] = EstadosInimigo.Ataca;
		_baseConhecimento[7][3] = EstadosInimigo.Ataca;
		_baseConhecimento[8][3] = EstadosInimigo.Ataca;
		_baseConhecimento[9][3] = EstadosInimigo.Perambula;
		_baseConhecimento[10][3] = EstadosInimigo.Segue;
		_baseConhecimento[11][3] = EstadosInimigo.Segue;
		_baseConhecimento[12][3] = EstadosInimigo.Segue;
		_baseConhecimento[13][3] = EstadosInimigo.Segue;
		_baseConhecimento[14][3] = EstadosInimigo.Segue;
		_baseConhecimento[15][3] = EstadosInimigo.Segue;
		_baseConhecimento[16][3] = EstadosInimigo.Segue;
		_baseConhecimento[17][3] = EstadosInimigo.Segue;
		_baseConhecimento[18][3] = EstadosInimigo.Perambula;
		_baseConhecimento[19][3] = EstadosInimigo.Perambula;
		_baseConhecimento[20][3] = EstadosInimigo.Perambula;
		_baseConhecimento[21][3] = EstadosInimigo.Perambula;
		_baseConhecimento[22][3] = EstadosInimigo.Perambula;
		_baseConhecimento[23][3] = EstadosInimigo.Perambula;
		_baseConhecimento[24][3] = EstadosInimigo.Perambula;
		_baseConhecimento[25][3] = EstadosInimigo.Perambula;
		_baseConhecimento[26][3] = EstadosInimigo.Segue;
	}

	/**
	 * Fun��o chamada quando devemos atacar o {@link #_alvo alvo}.
	 */
	protected abstract void Ataque();
}
