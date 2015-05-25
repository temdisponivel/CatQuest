package classes.uteis;

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
	private float _maiorDistancia = -1;
	
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
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (_objetos == null || _camera == null)
			return;
		
		Vector2 posicao = null;
		Vector2 posicaoAux = null;
		Vector2 posicaoA = null;
		Vector2 posicaoB = null;
		float distancia = -1;
		float distanciaAux = 0;
		
		//se temos pelo menos 2 objetos
		if (_objetos.length > 1)
		{
			//compara todos com todos e pega a maior distancia entre dois objetos
			for (int i = 0; i < _objetos.length; i++)
			{
				posicao = _objetos[i].GetPosicao();
				posicaoAux = _objetos[++i].GetPosicao();
				
				if (_maiorDistancia != (distanciaAux = posicao.dst(posicaoAux)))
				{					
					if (distancia < distanciaAux)
					{
						distancia = distanciaAux;
						posicaoA = posicao;
						posicaoB = posicaoAux;
					}
				}
			}
		}
		else if (_objetos.length == 1)
		{
			posicaoA = _objetos[0].GetPosicao();
		}
		else
		{
			return;
		}
		
		//se já movemos para está posição
		if (posicaoA == null)
			return;
		
		if (distancia > CatQuest.instancia.GetHipotenusaMundo()/10 && distancia > _maiorDistancia)
		{
			//tira zoom para mostrar mais da tela
			_camera.zoom += .01f;
		}
		else
		{
			//coloca zoom para mostrar menos da tela
			if (_camera.zoom - .1f >= 1)
				_camera.zoom -= .01f;
		}
		
		_maiorDistancia = distancia;
		
		//se temos dois objetos
		if (posicaoB != null)
		{
			_camera.position.set(Math.abs(posicaoA.x - posicaoB.x), Math.abs(posicaoA.y - posicaoB.y), 0);
		}
		//se temos somente um objeto 
		else
		{
			_camera.position.add(posicaoA.x, posicaoA.y, 0);
		}
	}
	
	@Override
	public void AoColidir(GameObject colidiu){}
}
