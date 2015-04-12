//TODO: fazer o esquema de texture packer

package catquest.desktop;

import catquest.CatQuest;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	
	public static void main (String[] arg) 
	{
		/*
		Settings settings = new Settings();
        settings.maxWidth = 8192;
        settings.maxHeight = 8192;
        TexturePacker.process(settings, "imagens\\pack\\pack2", "imagens\\pack\\pack2\\pack3", "game");
        */
        
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.forceExit = true;  
		config.vSyncEnabled = false;
		config.addIcon("imagens\\icones\\128.png", FileType.Local);
		config.addIcon("imagens\\icones\\32.png", FileType.Local);
		config.addIcon("imagens\\icones\\16.png", FileType.Local);
		new LwjglApplication(new CatQuest(), config);
	}
}
