package classes.uteis;

import java.util.LinkedList;
import com.badlogic.gdx.math.Vector2;
import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.ObjetoQuebravel;
import classes.gameobjects.personagens.herois.Heroi;
import classes.gameobjects.personagens.inimigos.Cachorro;
import classes.gameobjects.personagens.inimigos.Inimigo;
import classes.uteis.reciclador.Reciclador;

/**
 * Classe que gerencia a cria��o e reciclagem de inimigos.
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
	 * Cria uma nova f�brica.
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
		_pontosLancamento.add(new Vector2(50, 0));
		_pontosLancamento.add(new Vector2((_telaInserido.GetLarguraMapa() / 2) - 50, 50));
		_pontosLancamento.add(new Vector2(_telaInserido.GetLarguraMapa() - 50, 50));
		_pontosLancamento.add(new Vector2(50, (_telaInserido.GetAlturaMapa() / 2) - 50));
		_pontosLancamento.add(new Vector2(50, _telaInserido.GetAlturaMapa() - 50));
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
			novo.SetAlvo(Heroi.GetHeroiMaisProximo(novo.GetPosicao()));
			_ultimoTempoHunter = CatQuest.instancia.GetTempoJogo();
		}

		if (CatQuest.instancia.GetTempoJogo() - _ultimoTempoSiege > _intervaloSiege)
		{
			novo = this.CriaInimigo(Cachorro.class, _pontosLancamento.get((int) (_ultimoLancamentoSiege++ % _pontosLancamento.size())));
			novo.SetAlvo(ObjetoQuebravel.GetObjetoQuebravelMaisProximo(novo.GetPosicao()));
			_ultimoTempoSiege = CatQuest.instancia.GetTempoJogo();
		}
	}

	/**
	 * Recicla este inimigo e o torna dispon�vel para f�brica novamente.
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
	 *            {@link Vector2 Posi��o} para criar.
	 * @return Inst�ncia do inimigo criado.
	 */
	public Inimigo CriaInimigo(Class<? extends Inimigo> classe, Vector2 posicao)
	{
		Inimigo inimigo = _reciclador.GetInstancia(classe);
		inimigo.SetPosicao(posicao.cpy());
		inimigo.SetFabrica(this);
		
		_telaInserido.InserirGameObject(inimigo);

		return inimigo;
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
	};
}
