package classes.uteis.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import catquest.CatQuest;
import classes.gameobjects.GameObject;
import classes.uteis.Camada;

/**
 * Classe para criação de etiqutas.
 * @author Matheus
 *
 */
public class Etiqueta extends GameObject
{
	private String _texto = null;
	private Color _cor = null;
	private Color _corOriginal = null;
	private BitmapFont _fonte = null;
	
	/**
	 * Cria uma nova etiqueta com um texto e uma determinada posição.
	 * @param texto Texto a ser mostrado na etiqueta.
	 * @param posicao {@link Vector2 Posição} da etiqueta na tela.
	 */
	public Etiqueta(String texto, Vector2 posicao)
	{
		super();
		this.Inicia();
		_texto = texto;
		_posicaoTela = posicao;
		_camada = Camada.UI;
		_cor = Color.BLACK;
	}
	
	/**
	 * Cria uma nova etiqueta com um texto, uma determinada posição e uma cor.
	 * @param texto Texto a ser mostrado na etiqueta.
	 * @param posicao {@link Vector2 Posição} da etiqueta na tela.
	 * @param corTexto {@link Color Cor} do texto a ser exibido.
	 */
	public Etiqueta(String texto, Vector2 posicao, Color corTexto)
	{
		this(texto, posicao);
		_cor = corTexto;
	}

	@Override
	public void AoColidir(GameObject colidiu) {}

	@Override
	public void Inicia()
	{
		_fonte = CatQuest.instancia.GetFonte(); 
		_corOriginal = _fonte.getColor();
	}
	
	@Override
	public void Atualiza(float deltaTime) 
	{
		super.Atualiza(deltaTime);
		
		//atualiza o tamanho do texto
		_caixaColisao.width = _fonte.getBounds(_texto).width;
		_caixaColisao.height = _fonte.getBounds(_texto).height;
	}
	
	@Override
	public void Desenha(SpriteBatch batch)
	{
		_corOriginal = _fonte.getColor();
		_fonte.setColor(_cor);
		_fonte.draw(batch, _texto, _posicaoTela.x, _posicaoTela.y);
		_fonte.setColor(_corOriginal);
		
		super.Desenha(batch);
	}
	
	/**
	 * @return {@link Color Cor} atual do texto.
	 */
	public Color GetCor()
	{
		return _cor;
	}
	
	/**
	 * Define uma nova cor para o texto da {@link Etiqueta etiqueta}
	 * @param cor {@link Color Cor} para desenhar o texto.
	 */
	public void SetCor(Color cor)
	{
		_cor = cor;
	}
	
	/**
	 * @return Texto atual da etiqueta.
	 */
	public String GetTexto()
	{
		return _texto;
	}
	
	/**
	 * Define um novo texto na etiqueta.
	 * @param texto Texto para etiqueta.
	 */
	public void SetTexto(String texto)
	{
		_texto = texto;
	}
}
