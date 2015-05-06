package classes.uteis.UI;

import com.badlogic.gdx.math.Vector2;

import classes.gameobjects.GameObject;
import classes.uteis.Camada;

/**
 * Um menu que pode suporta diversos botões e os posiciona um relação ao outro, com um espaçamento de 5px em cada botão, de cada lado. O menu é orientada verticalmente.
 * Note que não serve apenas para botões, mas para qualquer {@link GameObject game object}. Porém, devido a forma de posicionamento, faz mais sentido utilizar para criar menus.
 * Para adicionar objetos, utilize o {@link GameObject#AdicionaFilho(GameObject)}. Note que os objetos adicionados por este método ficaram abaixo de todos.
 * @author matheus
 *
 */
public class Menu extends GameObject
{
	private int _espacamento = 5;
	
	/**
	 * Cria um novo botão. Para adicionar botões nele, adicione via {@link GameObject#AdicionaFilho(GameObject)}
	 * @param posicao {@link Vector2 Posição} do menu. 0, 0 no canto inferior esquerdo.
	 */
	public Menu(Vector2 posicao)
	{
		_tipo = GameObjects.Ui;
		_camada = Camada.UI;
		this.SetPosicao(posicao);
	}
	
	/**
	 * Cria um novo menu com objetos incluídos. O último objeto será o superior.
	 * @param botoes {@link GameObject Botões} (ou qualquer game object) para adicionar.
	 * @param posicao {@link Vector2 Posição} do menu. 0, 0 no canto inferior esquerdo.
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
