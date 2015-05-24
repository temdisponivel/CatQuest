package classes.gameobjects.personagens.ataque;

import classes.uteis.controle.Controle.Direcoes;

/**
 * Classe de BolaDeFogo (Mago)
 * @author Victor
 *
 * Se move em linha reta e ao colidir coloca fogo no chão.
 */
public class BolaDeFogo extends Ataque
{
	
	public BolaDeFogo()
	{
		
	}
	
	@Override
	protected void Movimenta()
	{
		if(_direcao != Direcoes.CENTRO){
			if(_direcao == Direcoes.CIMA)
			{
				
			}
		}
	}
	
}
