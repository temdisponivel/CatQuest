//TODO: colocar campo de visao pro A*

package classes.gameobjects.personagens.inimigos;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.gameobjects.personagens.Personagem;
import classes.uteis.Camada;
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
	
	static public HashMap<Integer, Inimigo> inimigos = new HashMap<Integer, Inimigo>();
	static private Reciclador<Inimigo> _reciclador = new Reciclador<Inimigo>();
	
	/**
	 * Cria um novo inimigo.
	 */
	public Inimigo()
	{
		super();
		_camada = Camada.Personagens;
		_tipo = GameObjects.Inimigo;
		inimigos.put(this.GetId(), this);
		_colidiveis.put(GameObjects.Heroi, Colisoes.Passavel);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (!_caminho.isEmpty())
		{
			this.MovimentaCaminho(deltaTime);
		}
		else
		{
			_destino.set(Gdx.input.getX(), CatQuest.instancia.GetAlturaTela()-Gdx.input.getY());
			this.GetCaminho();
		}
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
	
	@Override
	public void AoColidir(GameObject colidiu)
	{
		super.AoColidir(colidiu);
		
		if (_colidiveis.get(colidiu.GetTipo()) == Colisoes.NaoPassavel)
			this.GetCaminho();
	}
}
