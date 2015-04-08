//TODO: implementar

package classes.gameobjects;

import classes.uteis.Player;

public class Heroi extends GameObject
{
	Player _player = null;
	
	public Heroi(Player player)
	{
		super();
		_player = player;
	}
	
	@Override
	public <T extends GameObject> void AoColidir(T colidiu)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Inicia()
	{
		// TODO Auto-generated method stub
		
	}

}
