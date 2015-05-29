package classes.uteis;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import classes.gameobjects.GameObject;

/**
 * Classe que representa uma área do mapa que os inimigos devem evitar
 * @author matheus
 *
 */
public class AreaIndesejada extends GameObject
{
	/**
	 * Cria uma área indesejada na posição informada.
	 */
	public AreaIndesejada(Vector2 posicao, Rectangle tamanho)
	{
		super();
		_caixaColisao = tamanho;
		_colidiveis.put(GameObjects.Inimigo, Colisoes.Evitavel);
		_sprite.setSize(tamanho.width, tamanho.height);
		this.SetPosicao(posicao);
	}
	
	@Override
	public void AoColidir(GameObject colidiu)
	{
		
	}
}
