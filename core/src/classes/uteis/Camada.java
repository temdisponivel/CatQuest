package classes.uteis;

import catquest.CatQuest;
import com.badlogic.gdx.graphics.Color;

/**
 * Classe utilizada para organizar os a ordem em que os {@link classes.gameobjects.GameObject GameObjects} ser�o atualizados e desenhados em cada {@link Tela}.
 * Tamb�m cont�m a cor em que todos eles ser�o desenhados e flags que simbolizam quando � pra atualizar e quando � pra desenhar.
 * @author Matheus
 *
 */
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
	
	/**
	 * 
	 * @return {@link Integer} contendo o ID da camada. Para compara��o entre camadas, usar {@link Integer#equals(Object)}.
	 */
	public Integer GetIdCamada()
	{
		return _idCamada;
	}
	
	/**
	 * 
	 * @return {@link Color} em que os objetos dessa {@link Camada} ser�o desenhados. Esta cor � definida na {@link com.badlogic.gdx.graphics.g2d.SpriteBatch SpriteBatch} 
	 * antes de desenhar os {@link classes.gameobjects.GameObject GameObjects} desta camada.
	 * @see {@link com.badlogic.gdx.graphics.g2d.SpriteBatch#setColor(Color) SpriteBatch}
	 */
	public Color GetCor()
	{
		return _cor;
	}
	
	/**
	 * Define uma {@link Color Cor} para que os objetos dessa cor sejam desenhados. Esta cor � definida na {@link com.badlogic.gdx.graphics.g2d.SpriteBatch SpriteBatch} 
	 * antes de desenhas os {@link classes.gameobjects.GameObject GameObjects} desta camada.
	 * @param cor
	 */
	public void SetCor(Color cor)
	{
		_cor = cor;
	}
	
	/**
	 * 
	 * @return True caso a l�gica de colis�o seja feita para os {@link classes.gameobjects.GameObject GameObjects} que est�o vinculados a esta {@link Camada}.
	 */
	public boolean GetColidivel()
	{
		return _colidivel;
	}
	
	/**
	 * Define se os {@link classes.gameobjects.GameObject GameObjects} vinculados a esta {@link Camada} v�o verificar colis�es.
	 * @param colidivel True para que seja rodada a logica de colis�o nas intera��es do game loop.
	 */
	public void SetColidivel(boolean colidivel)
	{
		_colidivel = colidivel;
	}
	
	/**
	 * 
	 * @return True caso os {@link classes.gameobjects.GameObject GameObjects} vinculados a esta {@link Camada} 
	 * estejam sendo {@link classes.gameobjects.GameObject#Atualiza(float) Atualizados} no gameloop. Caso contr�rio, false.
	 */
	public boolean GetAtualiza()
	{
		return _atualiza;
	}
	
	/**
	 * Define se os {@link classes.gameobjects.GameObject GameObjects} desta {@link Camada} ser�o {@link classes.gameobjects.GameObject#Atualiza(float) Atualizados} no gameloop.
	 * @param atualiza True para {@link classes.gameobjects.GameObject#Atualiza(float) Atualizar} os {@link GameObject}.
	 */
	public void SetAtualiza(boolean atualiza)
	{
		_atualiza = atualiza;
	}
	
	/**
	 * 
	 * @return True caso os {@link classes.gameobjects.GameObject GameObjects} vinculados a esta {@link Camada} 
	 * estejam sendo {@link classes.gameobjects.GameObject#Desenha(com.badlogic.gdx.graphics.g2d.SpriteBatch) Desenhados} no gameloop. Caso contr�rio, false.
	 */
	public boolean GetDesenha()
	{
		return _desenha;
	}
	
	/**
	 * Define se os {@link classes.gameobjects.GameObject GameObjects} desta {@link Camada} ser�o 
	 * {@link classes.gameobjects.GameObject#Desenha(com.badlogic.gdx.graphics.g2d.SpriteBatch) Desenhados} no gameloop.
	 * @param desenha True para {@link classes.gameobjects.GameObject#Desenha(com.badlogic.gdx.graphics.g2d.SpriteBatch) Desenhar} os {@link classes.gameobjects.GameObject GameObjects}.
	 */
	public void SetDesenha(boolean desenha)
	{
		_desenha = desenha;
	}
	
	/**
	 * 
	 * @return Meso que {@link Camada#GetAtualiza()} || {@link Camada#GetDesenha()}.
	 */
	public boolean GetAtiva()
	{
		return _atualiza && _desenha;
	}
	
	/**
	 * Seta se {@link classes.gameobjects.GameObject GameObjects} desta {@link Camada} ser�o 
	 * {@link classes.gameobjects.GameObject#Desenha(com.badlogic.gdx.graphics.g2d.SpriteBatch) Desenhado} e
	 * {@link classes.gameobjects.GameObject#Atualiza(float) Atualizado}. </br>
	 * O mesmo que: {@link Camada#SetAtualiza(ativa)} e {@link Camada#SetDesenha(ativa)}.
	 * @param ativa True para desenhar e atualizar, false para n�o desenhar e atualizar.
	 */
	public void SetAtiva(boolean ativa)
	{
		this.SetAtualiza(ativa);
		this.SetDesenha(ativa);
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
