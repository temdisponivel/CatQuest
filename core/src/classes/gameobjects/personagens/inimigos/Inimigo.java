package classes.gameobjects.personagens.inimigos;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import catquest.CatQuest.Dificuldade;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.Personagem;
import classes.uteis.Camada;
import classes.uteis.reciclador.Reciclador;
import classes.uteis.reciclador.Reciclavel;

/**
 * Classe base para todos os inimigos do jogo.
 * @author matheus
 *
 */
public abstract class Inimigo extends Personagem implements Reciclavel
{
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
	
	/**
	 * Enumerados para os possíveis estados dos inimigos.
	 * Utilizado pela IA para o controle de comportamento.
	 * @author matheus
	 *
	 */
	protected enum EstadosInimigo
	{
		Perambula,
		Segue,
		Ataca,
	}
	
	/**
	 * Valores fuzzyficados para a distancia, para poder definir segundo o inimigo.
	 * @author matheus
	 *
	 */
	protected enum DistanciaFuzzyficada
	{
		Perto,
		Proximo,
		Distante,
	}
	
	/**
	 * Valores para a fuzzyficação da própria vida e do inimigo.
	 * @author matheus
	 *
	 */
	protected enum VidaFuzzyficada
	{
		Baixa,
		Media,
		Alta,
	}
	
	/**
	 * Triangulo para a lógica fuzzy.
	 * @author matheus
	 *
	 */
	protected class TrianguloFuzzy
	{
		float esquerda, centro, direita;
		
		/**
		 * Cria um triangulo para lógica fuzzy com um valor para o ponto esquerdo e direito. O valor do centro é calculado a partir destes.
		 * @param esquerda Ponto esquerdo.
		 * @param direita Ponto direito.
		 */
		public TrianguloFuzzy(float esquerda, float direita)
		{
			this.esquerda = esquerda;
			this.direita = direita;
			this.centro = (direita + esquerda) / 2;
		}
	}
	
	/**
	 * Classe que representa uma fuzificação, contém três triangulos para fuzificar valores.
	 * @author matheus
	 *
	 */
	protected class Fuzzyficacao
	{
		TrianguloFuzzy esquerda, centro, direita;
		
		/**
		 * Cria uma nova fuzificação.
		 * @param esquerda {@link TrianguloFuzzy Triangulo} da esquerda. 
		 * @param centro {@link TrianguloFuzzy Triangulo} do centro.
		 * @param direita {@link TrianguloFuzzy Triangulo} da direita.
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
	static protected Fuzzyficacao _fuzzyVidaAlvo = null;
	static protected Fuzzyficacao _fuzzyVidaPropria = null;
	static protected Fuzzyficacao _fuzzyDistancia = null;
	static protected int _quantiColunasBaseConhecimento = 4; //quatro colunas porque vamos considerar 3 dados e 1 resultado
	static protected int _quantiLinhasBaseConhecimento = 27; //27 linhas porque são 3 possíveis valores em cada coluna, portanto 3 a quarta
	static protected Object[][] _baseConhecimento = new Object[_quantiLinhasBaseConhecimento][_quantiColunasBaseConhecimento];
	
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
	}
	
	@Override
	public void Inicia()
	{
		super.Inicia();
		_colidiveis.put(GameObjects.Heroi, Colisoes.Passavel);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		this.DecideOQueFazer();
		
		//se devemos seguir o alvo, segue
		if (_estado == EstadosInimigo.Segue)
		{
			if (_alvo != null && (_caminho.isEmpty() || CatQuest.instancia.GetDificuldade() == Dificuldade.Dificil))
			{
				_destino.set(_alvo.GetPosicao());
				this.GetCaminho();
			}
			
			this.MovimentaCaminho(deltaTime);
		}
		else if (_estado == EstadosInimigo.Ataca)
		{
			this.Ataque();
		}
		else if (_estado == EstadosInimigo.Perambula)
		{
			if (_caminho.isEmpty())
			{
				if (_telaInserido != null)
					_destino.set(_telaInserido.GetPosicaoAleatoria(this.GetPosicao(), 300));
				
				this.GetCaminho();
			}
			
			this.MovimentaCaminho(deltaTime);
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
			this.GetCaminho();
		
	}
	
	/**
	 * Função que executa a lógica fuzzy e decide o que o fazer. O resultado é representado por estados definidos em {@link #_estado estado}.
	 */
	protected void DecideOQueFazer()
	{
		if (_alvo == null || _fuzzyDistancia == null || _fuzzyVidaAlvo == null || _fuzzyVidaPropria == null)
		{
			_estado = EstadosInimigo.Perambula;
			return;
		}
		
		Dificuldade dificuldade = CatQuest.instancia.GetDificuldade();
		float vidaAlvo = _alvo.GetVida();
		float distanciaAlvo = _alvo.GetPosicao().dst(this.GetPosicao());
		float vidaAlvoFuzzyficadaAlta = 0;
		float vidaAlvoFuzzyficadaMeida = 0;
		float vidaAlvoFuzzyficadaBaixa = 0;
		float distanciaFuzzyficadaDistante = 0;
		float distanciaFuzzyficadaProximo = 0;
		float distanciaFuzzyficadaPerto = 0;
		float[] valoresPertinencia = null;
		
		//valor pertinencia vida do alvo
		valoresPertinencia = this.GetValorPertinencia(vidaAlvo, _fuzzyVidaAlvo);
		vidaAlvoFuzzyficadaBaixa = valoresPertinencia[0];
		vidaAlvoFuzzyficadaMeida = valoresPertinencia[1];
		vidaAlvoFuzzyficadaAlta = valoresPertinencia[2];

		//valor pertinencia distancia do alvo
		valoresPertinencia = this.GetValorPertinencia(distanciaAlvo, _fuzzyDistancia);
		distanciaFuzzyficadaPerto = valoresPertinencia[0];
		distanciaFuzzyficadaProximo = valoresPertinencia[1];
		distanciaFuzzyficadaDistante = valoresPertinencia[2];
		
		
	}
	
	/**
	 * Retorna o valor de pertinencia.
	 * @param valor Valor real.
	 * @param fuzzy Triangulos fuzzy para fuzzyficação.
	 * @return Os 3 valores fuzzyficados. [0] = esquerda, [1] = centro, [2] = direita.
	 */
	private float[] GetValorPertinencia(float valor, Fuzzyficacao fuzzy)
	{
		float[] valores = new float[3];
		
		//pega os valores de pertinencia dos tres triangulos do campo de fuzzyficação
		valores[0] = this.GetPertinencia(valor, fuzzy.esquerda);
		valores[1] = this.GetPertinencia(valor, fuzzy.centro);
		valores[2] = this.GetPertinencia(valor, fuzzy.direita);
		
		return valores;
	}
	
	/**
	 * Valor de pertinência do valor real no triangulo de fuzzyficação.
	 * @param valor Valor real.
	 * @param triangulo Triangulo com os valores de pertinencia.
	 * @return Valor de pertinencia.
	 */
	private float GetPertinencia(float valor, TrianguloFuzzy triangulo)
	{
		if (valor < triangulo.esquerda || valor > triangulo.direita)
			return 0;
		
		if (valor <= triangulo.centro)
			return (valor - triangulo.esquerda) / (triangulo.centro - triangulo.esquerda);
		else if (valor <= triangulo.esquerda)
			return (triangulo.direita - valor) / (triangulo.esquerda - triangulo.centro);
		else
			return 0;
	}
	
	/**
	 * Função que contrói a base de conhecimento para o fuzzy. Não define os valores para cada combinação.
	 */
	protected void ControiBaseConhecimento()
	{
		DistanciaFuzzyficada[] distancia = new DistanciaFuzzyficada[]{DistanciaFuzzyficada.Perto, DistanciaFuzzyficada.Proximo, DistanciaFuzzyficada.Distante};
		VidaFuzzyficada[] vida = new VidaFuzzyficada[]{VidaFuzzyficada.Baixa, VidaFuzzyficada.Media, VidaFuzzyficada.Alta};
		Dificuldade[] dificuldade = new Dificuldade[]{Dificuldade.Facil, Dificuldade.Normal, Dificuldade.Dificil};
		
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
	}
	
	/**
	 * Define os valores para cada combinação da base de conhecimento. Esta sendo construída manualmente segundo o arquivo contendo a base de conhecimento.
	 * Arquivo na pasta do jogo.
	 * @see {@link #ControiBaseConhecimento()}.
	 */
	public void DefineValoresBaseConhecimento()
	{
		_baseConhecimento[0][4] = EstadosInimigo.Ataca;
		_baseConhecimento[1][4] = EstadosInimigo.Ataca;
		_baseConhecimento[2][4] = EstadosInimigo.Ataca;
		_baseConhecimento[3][4] = EstadosInimigo.Ataca;
		_baseConhecimento[4][4] = EstadosInimigo.Ataca;
		_baseConhecimento[5][4] = EstadosInimigo.Ataca;
		_baseConhecimento[6][4] = EstadosInimigo.Ataca;
		_baseConhecimento[7][4] = EstadosInimigo.Ataca;
		_baseConhecimento[8][4] = EstadosInimigo.Ataca;
		_baseConhecimento[9][4] = EstadosInimigo.Perambula;
		_baseConhecimento[10][4] = EstadosInimigo.Segue;
		_baseConhecimento[11][4] = EstadosInimigo.Segue;
		_baseConhecimento[12][4] = EstadosInimigo.Segue;
		_baseConhecimento[13][4] = EstadosInimigo.Segue;
		_baseConhecimento[14][4] = EstadosInimigo.Segue;
		_baseConhecimento[15][4] = EstadosInimigo.Segue;
		_baseConhecimento[16][4] = EstadosInimigo.Segue;
		_baseConhecimento[17][4] = EstadosInimigo.Segue;
		_baseConhecimento[18][4] = EstadosInimigo.Perambula;
		_baseConhecimento[19][4] = EstadosInimigo.Perambula;
		_baseConhecimento[20][4] = EstadosInimigo.Segue;
		_baseConhecimento[21][4] = EstadosInimigo.Perambula;
		_baseConhecimento[22][4] = EstadosInimigo.Perambula;
		_baseConhecimento[23][4] = EstadosInimigo.Segue;
		_baseConhecimento[24][4] = EstadosInimigo.Perambula;
		_baseConhecimento[25][4] = EstadosInimigo.Perambula;
		_baseConhecimento[26][4] = EstadosInimigo.Segue;
	}
	
	/**
	 * Função chamada quando devemos atacar o {@link #_alvo alvo}.
	 */
	protected abstract void Ataque();
}
