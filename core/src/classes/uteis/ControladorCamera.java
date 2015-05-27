package classes.uteis;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import catquest.CatQuest;
import classes.gameobjects.GameObject;

/**
 * Classe que controla o zoom e movimentação da camera.
 * @author matheus
 */
public class ControladorCamera extends GameObject 
{
	private GameObject[] _objetos = null;
	private OrthographicCamera _camera = null;
	
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
		GameObject objA = null, objB = null;
		float maiorDistancia = -1;
		float distanciaAux = 0;
		float coeficienteZoom = 1;//(_telaInserido.GetHipotenusaMapa() / CatQuest.instancia.GetHipotenusaMundo());
		float diferencaX = 0;
		float diferencaY = 0;
		
		//se temos pelo menos 2 objetos
		if (_objetos.length > 1)
		{
			//compara todos com todos e pega a maior distancia entre dois objetos
			for (int i = 0; i < _objetos.length; i++)
			{
				for (int j = i + 1; j < _objetos.length; j++)
				{
					posicao = (objA = _objetos[i]).GetPosicao();
					posicaoAux = (objB = _objetos[j]).GetPosicao();
					
					distanciaAux = posicao.dst(posicaoAux);
					
					if (maiorDistancia < distanciaAux)
					{
						maiorDistancia = distanciaAux;
						posicaoA = posicao.cpy();
						posicaoB = posicaoAux.cpy();
						
						//pega os catetos do triangulo retangulo formado pelos dois maiores objetos até agora
						diferencaX = Math.abs(posicaoA.x - posicaoB.x);
						diferencaY = Math.abs(posicaoA.y - posicaoB.y);
						
						//adiciona um campo de visão para que a tela comece a abrir antes de que o gameobject encoste na borda
						diferencaX += (objA.GetLargura() + objB.GetLargura()) * 2;
						diferencaY += (objA.GetAltura() + objB.GetAltura()) * 2;
						
						coeficienteZoom = Math.max(coeficienteZoom, (diferencaY / CatQuest.instancia.GetAlturaMundo()));
						coeficienteZoom = Math.max(coeficienteZoom, (diferencaX / CatQuest.instancia.GetLarguraMundo()));
					}
				}
			}
		}
		else if (_objetos.length == 1)
		{
			posicaoA = _objetos[0].GetPosicao();
			coeficienteZoom = 1;
		}
		else
		{
			return;
		}
		
		if (posicaoA == null)
			return;
		
		//tira zoom para mostrar mais da tela
		if (coeficienteZoom >= 1)
			_camera.zoom = coeficienteZoom;
		
		//se temos dois objetos
		if (posicaoB != null)
		{
			_camera.position.set(posicaoA.lerp(posicaoB, .5f), 0);
		}
		//se temos somente um objeto 
		else
		{
			_camera.position.set(posicaoA, 0);
		}
	}
	
	@Override
	public void AoColidir(GameObject colidiu){}
}
