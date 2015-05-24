package classes.uteis;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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
		Vector2 posicaoLongeA = null, posicaoLongeB = null;
		float maiorDistancia = 0; 
		
		if (_objetos.length > 1)
		{
			//pega distancias e posicao dos objetos
			for (int i = 0; i < _objetos.length; i++)
			{
				posicao = _objetos[i].GetPosicao();
				
				//pega a maior distancia entre dois objetos
				if (posicaoLongeB != null)
				{
					if (maiorDistancia < posicao.dst(posicaoLongeB))
					{
						posicaoLongeA = posicao;
						maiorDistancia = posicao.dst(posicaoLongeB);
					}
				}
				
				posicaoLongeB = posicao;
			}
		}
		else
		{
			maiorDistancia = 0;
		}
		
		//se a distancia entre os dois objetos mais separados, é maior que a hiponusa da tela, dá zoom
		if (maiorDistancia > CatQuest.instancia.GetHipotenusaTela())
			_camera.zoom = 1 + (maiorDistancia / 1000);
		else
			_camera.zoom = 1 - (maiorDistancia / 1000) >= 1 ? 1 - (maiorDistancia / 1000) : 1;
		
		if (posicaoLongeA != null & posicaoLongeB != null)
			_camera.position.set(posicaoLongeA.lerp(posicaoLongeB, 0.5f), 0);
	}
	
	@Override
	public void SetTela(Tela tela)
	{
		super.SetTela(tela);		
	}
	
	@Override
	public void AoColidir(GameObject colidiu){}
	
}
