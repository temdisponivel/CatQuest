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
	 * Enumerador para as {@link Animation anima��es} dos {@link Inimigo inimigos}.
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
	 * Enumerados para os poss�veis estados dos inimigos.
	 * Utilizado pela IA para o controle de comportamento.
	 * @author matheus
	 *
	 */
	protected enum EstadosInimigo
	{
		Vigia,
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
	 * Valores para a fuzzyfica��o da pr�pria vida e do inimigo.
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
	 * Triangulo para a l�gica fuzzy.
	 * @author matheus
	 *
	 */
	protected class TrianguloFuzzy
	{
		float esquerda, centro, direita;
		
		/**
		 * Cria um triangulo para l�gica fuzzy com um valor para o ponto esquerdo e direito. O valor do centro � calculado a partir destes.
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
	 * Classe que representa uma fuzifica��o, cont�m tr�s triangulos para fuzificar valores.
	 * @author matheus
	 *
	 */
	protected class Fuzzyficacao
	{
		TrianguloFuzzy esquerda, centro, direita;
		
		/**
		 * Cria uma nova fuzifica��o.
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
	protected EstadosInimigo _estado = EstadosInimigo.Vigia;
	protected Personagem _alvo = null;
	protected HashMap<Float, DistanciaFuzzyficada> _valorFuzzyDistancia = new HashMap<Float, DistanciaFuzzyficada>();
	protected Fuzzyficacao _fuzzyVidaAlvo = null;
	protected Fuzzyficacao _fuzzyVidaPropria = null;
	protected Fuzzyficacao _fuzzyDistancia = null;
	
	/**
	 * Cria um novo inimigo.
	 */
	public Inimigo()
	{
		super();
		_camada = Camada.Personagens;
		_tipo = GameObjects.Inimigo;
		inimigos.put(this.GetId(), this);
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
	 * Fun��o que executa a l�gica fuzzy e decide o que o fazer. O resultado � representado por estados definidos em {@link #_estado estado}.
	 */
	protected void DecideOQueFazer()
	{
		if (_alvo == null || _fuzzyDistancia == null || _fuzzyVidaAlvo == null || _fuzzyVidaPropria == null)
			return;
		
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
	 * @param fuzzy Triangulos fuzzy para fuzzyfica��o.
	 * @return Os 3 valores fuzzyficados. [0] = esquerda, [1] = centro, [2] = direita.
	 */
	private float[] GetValorPertinencia(float valor, Fuzzyficacao fuzzy)
	{
		float[] valores = new float[3];
		
		//pega os valores de pertinencia dos tres triangulos do campo de fuzzyfica��o
		valores[0] = this.GetPertinencia(valor, fuzzy.esquerda);
		valores[1] = this.GetPertinencia(valor, fuzzy.centro);
		valores[2] = this.GetPertinencia(valor, fuzzy.direita);
		
		return valores;
	}
	
	/**
	 * Valor de pertin�ncia do valor real no triangulo de fuzzyfica��o.
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
}
