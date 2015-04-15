//TODO: como toda a parte de UI desses cara são mt ruins, vo construi uma própria. Talvez usar só a parte de texto deles.

package classes.uteis;

import catquest.CatQuest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Classe que auxilia na criação de botões, labels, e outros componentes de UI.
 * @author Matheus
 *
 */
public class UI
{
	public static UI instancia = null;
	private TextButtonStyle _estiloTextButton = null;
	private LabelStyle _estiloLabel = null;
	
	public UI()
	{
		if (instancia == null)
		{
			instancia = this;
			this.IniciaEstilos();
		}
	}
	
	private void IniciaEstilos()
	{
		this.IniciaEstiloTextButton();
		this.IniciaEstiloLabel();
	}
	
	private void IniciaEstiloTextButton()
	{
		TextureRegionDrawable up, down, checked;
		Pixmap p = new Pixmap(100, 100, Format.RGB888);
		p.setColor(Color.WHITE);
		p.fill();
		
		up = new TextureRegionDrawable(new TextureRegion(new Texture(new Pixmap(100, 100, Format.RGB888))));
		down = new TextureRegionDrawable(new TextureRegion(new Texture(p)));
		checked = down;
		
		_estiloTextButton = new TextButtonStyle(up, down, checked, CatQuest.instancia.GetFonte());
	}
	
	private void IniciaEstiloLabel()
	{
		
	}
	
	/**
	 * Cria um novo {@link TextButton}.
	 * @param texto Texto para aparecer no botão.
	 * @param largura Largura do botão.
	 * @param altura Altura do botão.
	 * @return Um novo {@link TextButton}
	 */
	public TextButton getTextButton(String texto, int largura, int altura)
	{
		return new TextButton(texto, _estiloTextButton);
	}
}
