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
	
	public UI()
	{
		if (instancia == null)
			instancia = this;
	}
}
