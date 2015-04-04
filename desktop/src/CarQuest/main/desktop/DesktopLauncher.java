package CarQuest.main.desktop;

import catquest.CatQuest;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		config.fullscreen = false;
		config.addIcon("imagens\\icones\\128.png", FileType.Internal);
		config.addIcon("imagens\\icones\\32.png", FileType.Internal);
		config.addIcon("imagens\\icones\\16.png", FileType.Internal);
		new LwjglApplication(new CatQuest(), config);
	}
}
