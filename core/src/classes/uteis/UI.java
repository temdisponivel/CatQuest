//TODO: como toda a parte de UI desses cara s�o mt ruins, vo construi uma pr�pria. Talvez usar s� a parte de texto deles.

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
 * Classe que auxilia na cria��o de bot�es, labels, e outros componentes de UI.
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
