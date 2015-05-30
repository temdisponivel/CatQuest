package classes.uteis;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.ObjetoQuebravel;
import classes.gameobjects.personagens.herois.Heroi;
import classes.gameobjects.personagens.inimigos.Cachorro;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.reciclador.Reciclador;

/**
 * Classe que gerencia a criação e reciclagem de inimigos.
 * 
 * @author matheus
 *
 */
public class FabricaInimigo extends GameObject
{
	private Reciclador<Inimigo> _reciclador = null;
	private float _ultimoLancamentoHunter = 0;
	private float _ultimoLancamentoSiege = 0;
	private float _ultimoTempoHunter = 0;
	private float _ultimoTempoSiege = 0;
	private float _intervaloHunter = (float) Math.log(10 * (CatQuest.instancia.GetDificuldade() > 0.0f ? CatQuest.instancia.GetDificuldade() : 0.1f));
	private float _intervaloSiege = (float) Math.log(20 * (CatQuest.instancia.GetDificuldade() > 0.0f ? CatQuest.instancia.GetDificuldade() : 0.1f));
	private LinkedList<Vector2> _pontosLancamento = null;

	/**
	 * Cria uma nova fábrica.
	 */
	public FabricaInimigo()
	{
		super();
	}
	
	@Override
	public void Inicia()
	{
		super.Inicia();
		_reciclador = new Reciclador<Inimigo>();
		_pontosLancamento = new LinkedList<Vector2>();
		_pontosLancamento.add(new Vector2(0, 0));
		_pontosLancamento.add(new Vector2((_telaInserido.GetLarguraMapa() / 2) - 50, 0));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa() - 50, 0));
		_pontosLancamento.add(new Vector2(0, (_telaInserido.GetAlturaMapa() / 2) - 50));
		_pontosLancamento.add(new Vector2(0, _telaInserido.GetAlturaMapa() - 50));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa() - 50, _telaInserido.GetLarguraMapa() - 50));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa() - 50, (_telaInserido.GetLarguraMapa() / 2) - 50));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa() - 50, _telaInserido.GetLarguraMapa() - 50));
		_pontosLancamento.add(new Vector2((_telaInserido.GetLarguraMapa() / 2) - 50, _telaInserido.GetLarguraMapa() - 50));
	}

	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		this.CriaInimigo();
	}

	/**
	 * Cria um novo inimigo.
	 */
	private void CriaInimigo()
	{
		Inimigo novo = null;
		
		if (CatQuest.instancia.GetTempoJogo() - _ultimoTempoHunter > _intervaloHunter)
		{
			novo = this.CriaInimigo(Cachorro.class, _pontosLancamento.get((int) (_ultimoLancamentoHunter++ % _pontosLancamento.size())));
			novo.SetAlvo(this.GetHeroiMaisProximo(novo.GetPosicao()));
			_ultimoTempoHunter = CatQuest.instancia.GetTempoJogo();
		}

		if (CatQuest.instancia.GetTempoJogo() - _ultimoTempoSiege > _intervaloSiege)
		{
			novo = this.CriaInimigo(Cachorro.class, _pontosLancamento.get((int) (_ultimoLancamentoSiege++ % _pontosLancamento.size())));
			novo.SetAlvo(this.GetObjetoQuebravelMaisProximo(novo.GetPosicao()));
			_ultimoTempoSiege = CatQuest.instancia.GetTempoJogo();
		}
	}

	/**
	 * Recicla este inimigo e o torna disponível para fábrica novamente.
	 * 
	 * @param inimigo
	 *            {@link Inimigo} para reciclar.
	 */
	public void Recicla(Inimigo inimigo)
	{
		_reciclador.Recicla(inimigo);
	}

	/**
	 * Cria um novo {@link Inimigo inimigo}.
	 * 
	 * @param classe
	 *            Classe do inimigo para instanciar.
	 * @param posicao
	 *            {@link Vector2 Posição} para criar.
	 * @return Instância do inimigo criado.
	 */
	public Inimigo CriaInimigo(Class<? extends Inimigo> classe, Vector2 posicao)
	{
		Inimigo inimigo = _reciclador.GetInstancia(classe);
		inimigo.SetPosicao(posicao.cpy());
		inimigo.SetFabrica(this);
		
		_telaInserido.InserirGameObject(inimigo);

		return inimigo;
	}

	/**
	 * Retorna o ponto mais próximo dentre os {@link #_pontosLancamento pontos de lançamento} do ponto parametrizado.
	 * @param ponto {@link Vector2 Ponto} para validar distancia.
	 * @return Ponto mais próximo.
	 */
	private Vector2 GetPontoMaisProximo(Vector2 ponto)
	{
		Vector2 posicao = null;
		float distancia = _telaInserido.GetHipotenusaMapa();
		for (int i = 0; i < _pontosLancamento.size(); i++)
		{
			if (_pontosLancamento.get(i).dst(ponto) < distancia)
			{
				posicao = _pontosLancamento.get(i);
			}
		}
		
		return posicao.cpy();
	}
	
	/**
	 * Retorna o {@link Heroi herói} mais próximo da posição informada.
	 * @param posicao {@link Vector2 Posição} para procurar pelo mais próximo.
	 * @return Herói mais próximo.
	 */
	private Heroi GetHeroiMaisProximo(Vector2 posicao)
	{
		Iterator<Heroi> herois = Heroi.GetHeroisAtivos().iterator();
		Heroi retorno = null, aux = null;
		
		float menorDistancia = _telaInserido.GetHipotenusaMapa() + 10;
		while (herois.hasNext())
		{
			aux = herois.next();
			
			if (posicao.dst(aux.GetPosicao()) < menorDistancia)
			{
				retorno = aux;
			}
		}
		
		return retorno;
	}
	
	/**
	 * Retorna o {@link ObjetoQuebravel objeto quebrável} mais próximo da posição informada.
	 * @param posicao {@link Vector2 Posição} para procurar pelo mais próximo.
	 * @return Objeto quebravel mais próximo.
	 */
	private ObjetoQuebravel GetObjetoQuebravelMaisProximo(Vector2 posicao)
	{
		Iterator<ObjetoQuebravel> objetos = ObjetoQuebravel.objetosquebraveis.values().iterator();
		ObjetoQuebravel retorno = null, aux = null;
		
		float menorDistancia = _telaInserido.GetHipotenusaMapa() + 10;
		while (objetos.hasNext())
		{
			aux = objetos.next();
			
			if (posicao.dst(aux.GetPosicao()) < menorDistancia)
			{
				retorno = aux;
			}
		}
		
		return retorno;
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
	};
}
