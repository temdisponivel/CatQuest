package classes.gameobjects.personagens.inimigos;

import catquest.CatQuest;
import classes.uteis.controle.Controle.Direcoes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe do inimigo Cachorro.
 * 
 * @author matheus
 *
 */
public class Cachorro extends Inimigo
{
	public Cachorro()
	{
		super();
		
		TextureRegion texturaAndando = CatQuest.instancia.GetTextura(Gdx.files.local("sprites/cachorrin"));
		TextureRegion[][] framesTemp = texturaAndando.split(texturaAndando.getRegionWidth()/16, texturaAndando.getRegionHeight()/1);
		TextureRegion[] frames = new TextureRegion[16];
		
		int indice = 0;
		for (int i = 0; i < framesTemp.length; i++)
			for (int j = 0; j < framesTemp[i].length; j++)
				frames[indice++] = framesTemp[i][j];
		
		AnimacaoInimigo[] animacoes = new AnimacaoInimigo[]{AnimacaoInimigo.MovimentoCima, AnimacaoInimigo.MovimentoBaixo, AnimacaoInimigo.MovimentoEsquerda, AnimacaoInimigo.MovimentoDireita, AnimacaoInimigo.Ativo};
		for (int i = 0, ii = 0; i < frames.length-1;)
		{
			Animation andando = new Animation(0.2f, frames[i++], frames[i++], frames[i++]);
			this.IncluirAnimacao(animacoes[ii++], andando);
		}
	}
	

	@Override
	protected Vector2 Movimenta(int direcao, float delta, boolean colidi)
	{
		AnimacaoInimigo animacao = AnimacaoInimigo.MovimentoCima;
		switch (direcao)
		{
		case Direcoes.BAIXO:
			animacao = AnimacaoInimigo.MovimentoBaixo;
			break;
		case Direcoes.ESQUERDA:
			animacao = AnimacaoInimigo.MovimentoEsquerda;
			break;
		case Direcoes.DIREITA:
			animacao = AnimacaoInimigo.MovimentoDireita;
			break;
		case Direcoes.NORDESTE:
		case Direcoes.NOROESTE:
			animacao = AnimacaoInimigo.MovimentoCima;
			break;
		case Direcoes.SUDESTE:
		case Direcoes.SUDOESTE:
			animacao = AnimacaoInimigo.MovimentoBaixo;
			break;
		default:
			break;
		}
		this.SetAnimacao(animacao);
		return super.Movimenta(direcao, delta, colidi);
	}

	@Override
	public String toString()
	{
		return "Cachorro";
	}

	@Override
	protected void Ataque()
	{
		
	}
}
