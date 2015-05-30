package classes.gameobjects.personagens;

import classes.gameobjects.GameObject;

/**
 * 
 * @author matheus
 */
public class ObjetoQuebravel extends Personagem
{
	static float _vidaTotal = 0;
	static int _quantInstancia = 0;
	
	public ObjetoQuebravel()
	{
		super();
		_quantInstancia++;		
	}
	
	@Override
	public void Inicia()
	{
		super.Inicia();
		_vidaTotal = (_vidaTotal + _vida) / _quantInstancia;
	}
	
	@Override
	public void RecebeDano(float dano)
	{
		super.RecebeDano(dano);
		_vidaTotal -= Math.abs(dano - _defesa);

		if (_vidaTotal <= 0);
			//TODO: perde o jogo;
	}

	@Override
	public void AoColidir(GameObject colidiu){};
	
	@Override
	public String toString()
	{
		return "ObjetoQuebravel";
	}

	@Override
	public void Morre()
	{
		
		this.Encerra();
	}
}
