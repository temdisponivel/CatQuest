package classes.telas;

import classes.uteis.UI.Imagem;
import catquest.CatQuest;

public class Introducao extends Tela
{
	@Override
	public void Iniciar() 
	{
		super.Iniciar();
		_tipo = Telas.INTRODUCAO;
		Imagem i = new Imagem("sprites//intro");		
		this.InserirGameObject(i);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (CatQuest.instancia.GetTempoJogo() > 5)
		{
			CatQuest.instancia.RetiraTela();
			CatQuest.instancia.AdicionaTela(new Titulo(), false, false);
		}
	}
}
