//TODO: fazer o esquema de texture packer

package catquest.desktop;

import catquest.CatQuest;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher {
	
	public static void main (String[] arg) 
	{
		Settings settings = new Settings();
        TexturePacker.process(settings, "imagens", "pack", "CatQuest");
        
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
