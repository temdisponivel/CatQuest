package classes.uteis.UI;

import catquest.CatQuest;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe para criação de botões com textos.
 * @author Matheus
 *
 */
public class BotaoTexto extends Botao
{
	private Etiqueta _etiqueta = null;
	private boolean _textoBaixo = false;
	
	/**
	 * Cria um novo botão com um texto centralizado. O tamanho do botão é igual ao tamanho do texto mais bordas de 5px em cada lado.
	 * @param texto Texto para aparecer no botão.
	 * @param posicao {@link Vector2 Posição} do botão na tela.
	 * @param escutador {@link EscutadorBotao Escutador} para os eventos do botão.
	 */
	public BotaoTexto(String texto, Vector2 posicao, EscutadorBotao escutador)
	{
		super(posicao, new Rectangle(0, 0, CatQuest.instancia.GetFonte().getBounds(texto).width + 10, CatQuest.instancia.GetFonte().getBounds(texto).height + 10), escutador);
		_etiqueta = new Etiqueta(texto, new Vector2(5, -30));
		_etiqueta.Inicia();
		this.AdicionaFilho(_etiqueta);
	}
	
	/**
	 * Cria um novo botão com um texto centralizado e um tamanho específico.
	 * @param texto Texto para aparecer no botão.
	 * @param posicao {@link Vector2 Posição} do botão na tela.
	 * @param tamanho {@link Rectangle Tamanho} do botão.
	 * @param escutador {@link EscutadorBotao Escutador} para os eventos do botão.
	 */
	public BotaoTexto(String texto, Rectangle tamanho, Vector2 posicao, EscutadorBotao escutador)
	{
		super(posicao, tamanho, escutador);
		Rectangle tamanhoTexto = CatQuest.instancia.GetTamanhoTexto(texto, 10);
		_etiqueta = new Etiqueta(texto, new Vector2((tamanho.width / 2 - ((tamanhoTexto.width / 2) - 5)), -((tamanho.height / 2) - (-tamanhoTexto.height / 2))));
		_etiqueta.Inicia();
		this.AdicionaFilho(_etiqueta);
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		super.Atualiza(deltaTime);
		
		if (_apertado && !_textoBaixo)
		{
			_etiqueta.GetPosicaoAbsoluta().x += 3;
			_etiqueta.GetPosicaoAbsoluta().y += 3;
			_textoBaixo = true;
		}
		else if (_textoBaixo && !_apertado)
		{
			_etiqueta.GetPosicaoAbsoluta().x -= 3;
			_etiqueta.GetPosicaoAbsoluta().y -= 3;
			_textoBaixo = false;
		}
	}
	
	/**
	 * @return {@link Etiqueta} do botão. Usar para mapilar cor, tamanho, fonte, etc. Cuidado ao alterar posição.
	 */
	public Etiqueta GetEtiqueta()
	{
		return _etiqueta;
	}
}
