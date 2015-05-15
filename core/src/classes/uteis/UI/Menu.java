package classes.uteis.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import classes.gameobjects.GameObject;
import classes.uteis.Camada;

/**
 * Um menu que pode suporta diversos botões e os posiciona um relação ao outro, com um espaçamento de 5px em cada botão, de cada lado. O menu é orientada verticalmente.
 * Note que não serve apenas para botões, mas para qualquer {@link GameObject game object}. Porém, devido a forma de posicionamento, faz mais sentido utilizar para criar menus.
 * Para adicionar objetos, utilize o {@link GameObject#AdicionaFilho(GameObject)}. Note que os objetos adicionados por este método ficaram acima de todos.
 * @author matheus
 *
 */
public class Menu extends GameObject
{
	private int _espacamento = 5;
	private boolean _habilitado = true;
	
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
		
		for (int i = 0; i < botoes.length ; i++)
		{
			this.AdicionaFilho(botoes[i]);
		}
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		this.SetSeAtualiza(_habilitado);
	}
	
	@Override
	public GameObject AdicionaFilho(GameObject filho)
	{
		filho.SetPosicao(_espacamento, (_espacamento + filho.GetAltura()) * (_filhos == null ? 0 : _filhos.size()));
		
		super.AdicionaFilho(filho);
		
		_caixaColisao.height += filho.GetAltura();
		_caixaColisao.width += filho.GetLargura();
		
		return filho;
	}
	
	/**
	 * @param habilitato True para se devemos processar comando neste menu - e seus objetos - e mudar sua aparencia para simbolar desabilitado.
	 */
	public void SetHabilitado(boolean habilitato)
	{
		_habilitado = habilitato;
		
		GameObject filho = null;
		for (int i = 0; i < _filhos.size() ; i++)
		{
			filho = _filhos.get(i);
			filho.SetColor((_habilitado ? Color.WHITE : Color.LIGHT_GRAY));
		}
	}
	
	/**
	 * 
	 * @return True se o menu está habilitade.
	 * @see {@link #SetHabilitado(boolean)}
	 */
	public boolean GetSeHabilitado()
	{
		return _habilitado;
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
	}
}
