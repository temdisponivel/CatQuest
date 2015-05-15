package classes.uteis.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import classes.gameobjects.GameObject;
import classes.uteis.Camada;
import catquest.CatQuest;

/**
 * Classe para imagens.
 * @author Matheus
 *
 */
public class Imagem extends GameObject
{
	/**
	 * Cria uma nova imagem.
	 * @param caminho Caminho físico do arquivo. Será usado {@link CatQuest#GetTextura(String)} para carregar.
	 */
	public Imagem(String caminho)
	{
		this(Gdx.files.local(caminho));		
	}
	
	/**
	 * Cria uma nova imagem.
	 * @param caminho {@link FileHandle Caminho} físico do arquivo. Será usado {@link CatQuest#GetTextura(String)} para carregar.
	 */
	public Imagem(FileHandle caminho)
	{
		super();
		_camada = Camada.UI;
		_tipo = GameObjects.Ui;
		_sprite = new Sprite(CatQuest.instancia.GetTextura(caminho));
		_caixaColisao.set(_sprite.getBoundingRectangle());
	}

	@Override
	public void AoColidir(GameObject colidiu)
	{
	}

	@Override
	public void Inicia() 
	{
		super.Inicia();
	}
}
