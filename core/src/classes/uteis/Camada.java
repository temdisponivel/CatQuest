package classes.uteis;

import com.badlogic.gdx.graphics.Color;

public class Camada
{
	private Integer _idCamada;
	private Color _cor;
	private boolean _colidivel = false;
	private boolean _ativa;
	
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
	
	public boolean GetAtiva()
	{
		return _ativa;
	}
	
	public void SetAtiva(boolean ativa)
	{
		_ativa = ativa;
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
