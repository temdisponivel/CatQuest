package classes.uteis.UI;

import com.badlogic.gdx.math.Vector2;

import classes.gameobjects.GameObject;
import classes.uteis.Camada;

/**
 * Um menu que pode suporta diversos bot�es e os posiciona um rela��o ao outro, com um espa�amento de 5px em cada bot�o, de cada lado. O menu � orientada verticalmente.
 * Note que n�o serve apenas para bot�es, mas para qualquer {@link GameObject game object}. Por�m, devido a forma de posicionamento, faz mais sentido utilizar para criar menus.
 * Para adicionar objetos, utilize o {@link GameObject#AdicionaFilho(GameObject)}. Note que os objetos adicionados por este m�todo ficaram abaixo de todos.
 * @author matheus
 *
 */
public class Menu extends GameObject
{
	private int _espacamento = 5;
	
	/**
	 * Cria um novo bot�o. Para adicionar bot�es nele, adicione via {@link GameObject#AdicionaFilho(GameObject)}
	 * @param posicao {@link Vector2 Posi��o} do menu. 0, 0 no canto inferior esquerdo.
	 */
	public Menu(Vector2 posicao)
	{
		_tipo = GameObjects.Ui;
		_camada = Camada.UI;
		this.SetPosicao(posicao);
	}
	
	/**
	 * Cria um novo menu com objetos inclu�dos. O �ltimo objeto ser� o superior.
	 * @param botoes {@link GameObject Bot�es} (ou qualquer game object) para adicionar.
	 * @param posicao {@link Vector2 Posi��o} do menu. 0, 0 no canto inferior esquerdo.
	 */
	public Menu(Vector2 posicao, GameObject... botoes)
	{
		this(posicao);
		
		for (int i = botoes.length; i >= 0 ; i--)
		{
			this.AdicionaFilho(botoes[i]);
		}
	}
	
	@Override
	public GameObject AdicionaFilho(GameObject filho)
	{
		super.AdicionaFilho(filho);
		
		filho.SetPosicao(_espacamento, (_espacamento + filho.GetAltura()) * _filhos.size());
		
		return filho;
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
	}
}
