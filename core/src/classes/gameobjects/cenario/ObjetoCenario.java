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
	 * Cria um novo objeto de cenário baseado em imagem.
	 * @param posicao {@link Vectoe2 Posição} do objeto na tela.
	 * @param textura Caminho da textura do objeto.
	 */
	public ObjetoCenario(Vector2 posicao, String textura, float rotacao)
	{
		super();
		_tipo = GameObjects.Cenario;
		_camada = Camada.ObjetosEstaticos;
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local(textura)));
		this.SetPosicao(posicao);
		_sprite.setPosition(_posicaoTela.x, _posicaoTela.y);
		_sprite.setRotation(rotacao);
		_caixaColisao.set(_sprite.getBoundingRectangle());
	}
	
	/**
	 * Cria um novo objeto de cenário baseado em imagem.
	 * @param posicao {@link Vectoe2 Posição} do objeto na tela.
	 * @param textura Caminho da textura do objeto.
	 */
	public ObjetoCenario(Vector2 posicao, String textura)
	{
		this(posicao, textura, 0);
	}
	
	/**
	 * Cria um novo objeto de cenário sem imagem.
	 * @param posicao {@link Vector2 Posição} do objeto.
	 * @param tamanho {@link Rectangle Tamanho} do objeto.
	 */
	public ObjetoCenario(Vector2 posicao, Rectangle tamanho)
	{
		_caixaColisao = tamanho;
		this.SetPosicao(posicao);
	}
	
	@Override
	public Colisoes AoColidir(GameObject colidiu)
	{
		return Colisoes.NaoPassavel;
	}
}
