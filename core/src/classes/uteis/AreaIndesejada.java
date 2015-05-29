package classes.uteis;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import classes.gameobjects.GameObject;

/**
 * Classe que representa uma �rea do mapa que os inimigos devem evitar
 * @author matheus
 *
 */
public class AreaIndesejada extends GameObject
{
	/**
	 * Cria uma �rea que objetos devem evitar.
	 * @param posicao Posi��o da �rea.
	 * @param tamanho Tamanho da �rea.
	 * @param quem Lista de tipo de objetos que devem evitar.
	 */
	public AreaIndesejada(Vector2 posicao, Rectangle tamanho, GameObjects... quem)
	{
		super();
		_caixaColisao = tamanho;
		_sprite.setSize(tamanho.width, tamanho.height);
		this.SetPosicao(posicao);
		
		for (int i = 0; i < quem.length; i++)
			_colidiveis.put(quem[i], Colisoes.Evitavel);
	}
	
	/**
	 * Define o tamanho da �rea.
	 */
	public void SetTamanho(Rectangle tamanho)
	{
		_caixaColisao = tamanho;
	}
	
	@Override
	public void AoColidir(GameObject colidiu)
	{
		
	}
}
