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
 * @author matheus
 *
 */
public class FabricaInimigo extends GameObject
{
	private Reciclador<Inimigo> _reciclador = null;
	private float _ultimoLancamento = 0;
	private float _quantidadePorHunter = (float) Math.log(10 * (CatQuest.instancia.GetDificuldade() > 0.0f ? CatQuest.instancia.GetDificuldade() : 0.1f));
	private float _quantidadePorSiege = (float) Math.log(10 * (CatQuest.instancia.GetDificuldade() > 0.0f ? CatQuest.instancia.GetDificuldade() : 0.1f));
	private LinkedList<Vector2> _pontosLancamento = null;
	
	/**
	 * Cria uma nova fábrica.
	 */
	public FabricaInimigo()
	{
		super();
		_reciclador = new Reciclador<Inimigo>();
		_pontosLancamento =  new LinkedList<Vector2>();
		CatQuest.instancia.SetDificuldade(0f);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (CatQuest.instancia.GetTempoJogo() - _ultimoLancamento >= _quantidadePorHunter)
			this.CriaInimigo();
	}
	
	@Override
	public void Desenha(SpriteBatch batch)
	{
		super.Desenha(batch);
		
		
	}
	
	/**
	 * Cria um novo inimigo.
	 */
	private void CriaInimigo()
	{
		_ultimoLancamento = CatQuest.instancia.GetTempoJogo();
		this.CriaInimigo(Cachorro.class, new Vector2());
	}
	
	/**
	 * Recicla este inimigo e o torna disponível para fábrica novamente.
	 * @param inimigo {@link Inimigo} para reciclar.
	 */
	public void Recicla(Inimigo inimigo)
	{
		_reciclador.Recicla(inimigo);
	}
	
	/**
	 * Cria um novo {@link Inimigo inimigo}. 
	 * @param classe Classe do inimigo para instanciar.
	 * @param posicao {@link Vector2 Posição} para criar. 
	 */
	public void CriaInimigo(Class<? extends Inimigo> classe, Vector2 posicao)
	{
		Inimigo inimigo = _reciclador.GetInstancia(classe);
		_telaInserido.InserirGameObject(inimigo);
		inimigo.SetPosicao(posicao);
		inimigo.SetFabrica(this);
	}

	@Override
	public void AoColidir(GameObject colidiu){};
}
