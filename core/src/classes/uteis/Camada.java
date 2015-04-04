package classes.uteis;

import catquest.CatQuest;

import com.badlogic.gdx.graphics.Color;

public class Camada
{
	private Integer _idCamada;
	private Color _cor;
	private boolean _colidivel = false;
	private boolean _atualiza = true, _desenha = true;
	
	public Camada(CatQuest.Camadas id)
	{
		_idCamada = new Integer(id.ordinal());
		
		switch (id)
		{
		case OBJETOS_ESTATICOS:
			_colidivel = false;
			_atualiza = false;
			break;
		case PERSONAGENS:
		case UI: 
			_colidivel = true;
			_atualiza = true;
			_desenha = true;
			break;
		default:
			break;
		}
		
		_cor = Color.WHITE;
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
	
	public boolean GetAtualiza()
	{
		return _atualiza;
	}
	
	public void SetAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}
	
	public boolean GetDesenha()
	{
		return _desenha;
	}
	
	public void SetDesenha(boolean desenha)
	{
		_desenha = desenha;
	}
	
	public boolean GetAtiva()
	{
		return _atualiza && _desenha;
	}
	
	public void SetAtiva(boolean ativa)
	{
		_atualiza = _desenha = ativa;
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
