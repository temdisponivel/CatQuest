package util;

import com.badlogic.gdx.graphics.Color;

public class Camada
{
	Integer _idCamada;
	Color _cor;
	
	public Camada(int id)
	{
		_idCamada = new Integer(id);
		_cor = new Color(1, 1, 1, 1);
	}
	
	public Integer GetIdCamada()
	{
		return _idCamada;
	}
	
	public Color GetCor()
	{
		return _cor;
	}
	
	@Override
	public boolean equals(Object camada)
	{
		if (camada == null)
			return false;
		
		return camada.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode()
	{
		return _idCamada.hashCode();
	}
}
