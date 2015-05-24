package classes.uteis;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.telas.Tela;

/**
 * Classe que controla o zoom e movimentação da camera.
 * @author matheus
 */
public class ControladorCamera extends GameObject 
{
	private GameObject[] _objetos = null;
	private OrthographicCamera _camera = null;
	private Rectangle tamanho = null;
	
	/**
	 * Controi um controlador de camera com um ou mais {@link GameObject GameObject} para seguir.
	 */
	public ControladorCamera(GameObject... objetos)
	{
		this.SetSeDesenha(false);
		_objetos = objetos;
	}
	
	@Override
	public void Inicia()
	{
		super.Inicia();
		_camera = CatQuest.instancia.GetCamera();
		tamanho = new Rectangle(0, 0, CatQuest.instancia.GetLarguraTela(), CatQuest.instancia.GetAlturaTela());
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (_objetos == null || _camera == null)
			return;
		
		Vector2 posicao = null;
		for (int i = 0; i < _objetos.length; i++)
		{
			posicao = _objetos[i].GetPosicao();
			
			if (posicao.x > tamanho.getWidth() / 2)
			{
				tamanho.x = posicao.x - _camera.position.x;
			}
			else
			{
				
			}
			
			if (posicao.y > tamanho.getWidth() / 2)
			{
				tamanho.y = posicao.y - _camera.position.y;
			}
			else
			{
				
			}
		}
		
		if ((tamanho.x + tamanho.width) > _telaInserido.GetLarguraMapa())
			tamanho.x -= (tamanho.x + tamanho.width) - _telaInserido.GetLarguraMapa();
		
		if ((tamanho.y + tamanho.height) > _telaInserido.GetAlturaMapa())
			tamanho.y -= (tamanho.y + tamanho.height) - _telaInserido.GetAlturaMapa();
		
		_camera.translate(tamanho.x, tamanho.y, 0);
	}
	
	@Override
	public void SetTela(Tela tela)
	{
		super.SetTela(tela);		
	}
	
	@Override
	public void AoColidir(GameObject colidiu){}
	
}
