package classes.gameobjects.cenario;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.uteis.Camada;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ObjetoCenario extends GameObject
{
	/**
	 * Cria um novo objeto de cenário.
	 * @param posicao {@link Vectoe2 Posição} do objeto na tela.
	 * @param textura Caminho da textura do objeto.
	 */
	public ObjetoCenario(Vector2 posicao, String textura, float rotacao)
	{
		_tipo = GameObjects.Cenario;
		_camada = Camada.ObjetosEstaticos;
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local(textura)));
		_sprite.setPosition(_posicaoTela.x, _posicaoTela.y);
		_sprite.setRotation(rotacao);
	}
	
	/**
	 * Cria um novo objeto de cenário.
	 * @param posicao {@link Vectoe2 Posição} do objeto na tela.
	 * @param textura Caminho da textura do objeto.
	 */
	public ObjetoCenario(Vector2 posicao, String textura)
	{
		_tipo = GameObjects.Cenario;
		_camada = Camada.ObjetosEstaticos;
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files.local(textura)));
		this.SetPosicao(posicao);
		_sprite.setPosition(_posicaoTela.x, _posicaoTela.y);
	}
	
	@Override
	public void AoColidir(GameObject colidiu)
	{
	}
}
