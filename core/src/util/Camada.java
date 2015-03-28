package util;

import com.badlogic.gdx.graphics.Color;

public class Camada
{
	Integer _idCamada;
	Color _cor;
	boolean _colidivel = false;
	
	public Camada()
	{
	}
	
	public Camada(int id)
	{
		_idCamada = new Integer(id);
		_cor = Color.BLACK;
	}
	
	public Integer GetIdCamada()
	{
		return _idCamada;
	}
	
	public Color GetCor()
	{
		return _cor;
	}
	
	public void SetCor(Color cor)
	{
		_cor = cor;
	}
	
	public boolean GetColidivel()
	{
		return _colidivel;
	}
	
	public void SetColidivel(boolean colidivel)
	{
		_colidivel = colidivel;
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
