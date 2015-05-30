package classes.gameobjects.personagens.herois;

import catquest.CatQuest;
import classes.gameobjects.personagens.ataque.Espadada;
import classes.uteis.controle.Controle.Direcoes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


/**
 * Classe do her�i Barbaro.
 * @author Victor
 *
 */
public class Barbaro extends Heroi
{

	public Barbaro()
	{
		super();
		
		TextureRegion texturaAndando = CatQuest.instancia.GetTextura(Gdx.files.local("sprites/herois/barbaro"));
		TextureRegion[][] framesTemp = texturaAndando.split(texturaAndando.getRegionWidth()/16, texturaAndando.getRegionHeight()/1);
		TextureRegion[] frames = new TextureRegion[16];
		
		int indice = 0;
		for (int i = 0; i < framesTemp.length; i++)
			for (int j = 0; j < framesTemp[i].length; j++)
				frames[indice++] = framesTemp[i][j];
		
		AnimacaoHeroi[] animacoes = new AnimacaoHeroi[]{AnimacaoHeroi.MovimentoCima, AnimacaoHeroi.MovimentoBaixo, AnimacaoHeroi.MovimentoEsquerda, AnimacaoHeroi.MovimentoDireita, AnimacaoHeroi.Ativo};
		for (int i = 0, ii = 0; i < frames.length-1;)
		{
			Animation andando = new Animation(0.2f, frames[i++], frames[i++], frames[i++]);
			this.IncluirAnimacao(animacoes[ii++], andando);
		}
	}
	
	@Override
	protected Vector2 Movimenta(int direcao, float delta, boolean colidi)
	{
		AnimacaoHeroi animacao = AnimacaoHeroi.MovimentoCima;
		switch (direcao)
		{
		case Direcoes.BAIXO:
			animacao = AnimacaoHeroi.MovimentoBaixo;
			break;
		case Direcoes.ESQUERDA:
			animacao = AnimacaoHeroi.MovimentoEsquerda;
			break;
		case Direcoes.DIREITA:
			animacao = AnimacaoHeroi.MovimentoDireita;
			break;
		case Direcoes.NORDESTE:
		case Direcoes.NOROESTE:
			animacao = AnimacaoHeroi.MovimentoCima;
			break;
		case Direcoes.SUDESTE:
		case Direcoes.SUDOESTE:
			animacao = AnimacaoHeroi.MovimentoBaixo;
			break;
		default:
			break;
		}
		this.SetAnimacao(animacao);
		return super.Movimenta(direcao, delta, colidi);
	}

	@Override
	protected void Acao()
	{
		// TODO Auto-generated method stub
	}

	@Override
	protected void HabilidadeAtiva()
	{
		// TODO Auto-generated method stub
	}

	@Override
	protected void AtaqueBasico()
	{
		this.GetTela().InserirGameObject(new Espadada(this.GetPosicao(), _player.GetControle().GetDirecaoAtaque(), _sprite.getHeight(), _sprite.getWidth()));
	}
	
	@Override
	public String toString()
	{
		return "Barbaro";
	}
}
