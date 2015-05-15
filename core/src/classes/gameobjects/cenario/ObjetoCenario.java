package classes.gameobjects.cenario;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.uteis.Camada;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ObjetoCenario extends GameObject
{
	/**
	 * Cria um novo objeto de cen�rio baseado em imagem.
	 * @param posicao {@link Vectoe2 Posi��o} do objeto na tela.
	 * @param textura Caminho da textura do objeto. Pode ser string branca para carregar sem imagem. Note que, caso n�o haja imagem, nem tamanho, o objeto ser� in�til.
	 * @param tamanho {@link Rectangle Tamanho} da textura. Pode ser nulo para pegar o tamanho da imagem. 
	 * Se n�o for nulo e houver textura, o tamanho da textura ser� definido como este.
	 */
	public ObjetoCenario(Vector2 posicao, String textura, Rectangle tamanho, float rotacao)
	{
		super();
		_tipo = GameObjects.Cenario;
		_camada = Camada.ObjetosEstaticos;
		
		if (textura.equals(""))
			_sprite = new Sprite();
		else
			_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local(textura)));
		
		this.SetPosicao(posicao);
		
		if (tamanho != null)
			_sprite.setSize(tamanho.width, tamanho.height);

		_caixaColisao.setSize(_sprite.getWidth(), _sprite.getHeight());
		
		if (textura.equals(""))
			_sprite = null;
	}
	
	/**
	 * Cria um novo objeto de cen�rio baseado em imagem.
	 * @param posicao {@link Vectoe2 Posi��o} do objeto na tela.
	 * @param textura Caminho da textura do objeto.
	 * @param tamanho {@link Rectangle Tamanho} da textura. Pode ser nulo para pegar o tamanho da imagem.
	 */
	public ObjetoCenario(Vector2 posicao, Rectangle tamanho, String textura)
	{
		this(posicao, textura, null, 0);
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
	}
}
