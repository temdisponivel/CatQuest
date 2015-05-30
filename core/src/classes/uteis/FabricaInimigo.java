package classes.uteis;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
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
	private float _intervaloHunter = (float) Math.log(5 * (CatQuest.instancia.GetDificuldade() > 0.0f ? CatQuest.instancia.GetDificuldade() : 0.1f));
	private float _intervaloSiege = (float) Math.log(10 * (CatQuest.instancia.GetDificuldade() > 0.0f ? CatQuest.instancia.GetDificuldade() : 0.1f));
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
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa() / 2, 0));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa(), 0));
		_pontosLancamento.add(new Vector2(0, _telaInserido.GetAlturaMapa() / 2));
		_pontosLancamento.add(new Vector2(0, _telaInserido.GetAlturaMapa()));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa(), _telaInserido.GetLarguraMapa()));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa(), _telaInserido.GetLarguraMapa() / 2));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa(), _telaInserido.GetLarguraMapa()));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa() / 2, _telaInserido.GetLarguraMapa()));
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
		/*
		Inimigo novo = null;
		if (CatQuest.instancia.GetTempoJogo() - _ultimoTempoHunter > _intervaloHunter)
		{
			novo = this.CriaInimigo(Cachorro.class, _pontosLancamento.get((int) (_ultimoLancamentoHunter++ % _pontosLancamento.size())));
			//novo.SetAlvo(alvo);
			_ultimoTempoHunter = CatQuest.instancia.GetTempoJogo();
		}

		if (CatQuest.instancia.GetTempoJogo() - _ultimoTempoSiege > _intervaloSiege)
		{
			this.CriaInimigo(Cachorro.class, _pontosLancamento.get((int) (_ultimoLancamentoSiege++ % _pontosLancamento.size())));
			_ultimoLancamentoSiege = CatQuest.instancia.GetTempoJogo();
		}
		*/
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
		_telaInserido.InserirGameObject(inimigo);
		inimigo.SetPosicao(posicao);
		inimigo.SetFabrica(this);

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

	@Override
	public void AoColidir(GameObject colidiu)
	{
	};
}
