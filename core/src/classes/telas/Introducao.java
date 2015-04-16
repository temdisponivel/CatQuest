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
		
		Imagem i = new Imagem("sprites\\intro");
		i.GetSprite().setSize(CatQuest.instancia.GetLarguraTela(), CatQuest.instancia.GetAlturaTela());
				
		this.InserirGameObject(i);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (CatQuest.instancia.GetTempoJogo() > (3 * 1000))
		{
			CatQuest.instancia.RetiraTela();
			CatQuest.instancia.AdicionaTela(new Titulo(), false, false);
		}
	}
}
