package util;

public class PonteiroDe<TipoDado>
{
	TipoDado _ref;
	
	public PonteiroDe(){};
	
	public PonteiroDe(TipoDado ref)
	{
		_ref = ref;
	}
	
	public TipoDado GetRef()
	{
		return _ref;
	}
	
	public void SetRef(TipoDado ref)
	{
		_ref = ref;
	}
}
