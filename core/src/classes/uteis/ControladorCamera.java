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
		GameObject objA = null, objB = null;
		float distancia = -1;
		float distanciaAux = 0;
		float coeficienteZoom = 1;//(_telaInserido.GetHipotenusaMapa() / CatQuest.instancia.GetHipotenusaMundo());
		float catetoOposto = 0;
		float catetoAdjacente = 0;
		
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
					
					//se a maior distancia é a mesma que a calculada no ultimo frame, nao faz nada
					if (_maiorDistancia == (distanciaAux = posicao.dst(posicaoAux)))
						continue;
					
					if (distancia < distanciaAux)
					{
						distancia = distanciaAux;
						posicaoA = posicao.cpy();
						posicaoB = posicaoAux.cpy();
						
						//pega os catetos do triangulo retangulo formado pelos dois maiores objetos até agora
						catetoOposto = Math.abs(posicaoA.x - posicaoB.x);
						catetoAdjacente = Math.abs(posicaoA.y - posicaoB.y);								
						
						//compensa o ponto zero do objeto
						//como o ponto zero é no inferior esquerdo, devemos compensar com a largura do objeto que esta mais a direita
						catetoOposto += objA.GetLargura() + objB.GetLargura();
						catetoAdjacente += objA.GetAltura() + objB.GetAltura(); 
						
						//compensa o centro do lerp entre os dois objetos por causa do ponto zero ser no inferior esquerdo
						if (posicaoA.x > posicaoB.x)
							posicaoB.x += objB.GetLargura();
						else
							posicaoA.x += objA.GetLargura();
						
						if (posicaoA.y > posicaoB.y)
							posicaoA.y += objA.GetAltura();
						else
							posicaoB.y += objB.GetAltura();
						
						/**
						 * pega a hipotenusa deste triangulo e divide pela hipotenusa do mundo
						 * essa relação existe porque quando o zoom é 1, o campo de visão é igual a hipotenusa do mundo
						 * portanto, para que o campo de visão seja igual a hipotenusa do nosso triangulo formado pelos objetos
						 * devemos definir o zoom igual à hipotenusa do triangulo dos objetos / hipotenusa do mundo (que é a padrão)
						 */
						coeficienteZoom = (float) (Math.hypot(catetoAdjacente, catetoOposto) / CatQuest.instancia.GetHipotenusaMundo());
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
		
		//se já movemos para está posição
		if (posicaoA == null)
			return;
		
		//tira zoom para mostrar mais da tela
		if (coeficienteZoom >= 1)
			_camera.zoom = coeficienteZoom;
		
		_maiorDistancia = distancia;
		
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
