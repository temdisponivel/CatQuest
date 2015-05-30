package classes.telas;

import catquest.CatQuest;
import classes.gameobjects.personagens.herois.Barbaro;
import classes.gameobjects.personagens.herois.Mago;
import classes.uteis.Player;
import classes.uteis.Player.TipoPlayer;
import classes.uteis.UI.Botao;
import classes.uteis.UI.BotaoTexto;
import classes.uteis.UI.Imagem;
import classes.uteis.UI.Botao.EscutadorBotao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import classes.uteis.UI.Menu;
import classes.uteis.sons.CarregarMusica;
import classes.uteis.sons.CarregarMusicaListner;

public class Titulo extends Tela implements EscutadorBotao, CarregarMusicaListner
{
	private Botao jogar, configurar, sair;
	private Menu menu;
	private Music _musica = null;
	
	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.MENU;
		
		this.InserirGameObject(new Imagem("sprites//titulo"));
		
		menu = new Menu(new Vector2(CatQuest.instancia.GetLarguraMundo() / 2 - 60, 60));
		menu.AdicionaFilho(sair = new BotaoTexto("SAIR", new Rectangle(0, 0, 220, 30), new Vector2(), this));
		menu.AdicionaFilho(configurar = new BotaoTexto("CONFIGURAR", new Rectangle(0, 0, 220, 30), new Vector2(), this));
		menu.AdicionaFilho(jogar = new BotaoTexto("JOGAR", new Rectangle(0, 0, 220, 30), new Vector2(), this));
		
		this.InserirGameObject(menu);
		
		CarregarMusica.instancia.Carrega(Gdx.files.local("audio/musica/tela_inicial.mp3"), 0, true, true, this);
		
		_corFundo = Color.WHITE;
	}
	
	@Override
	public void Atualiza(float deltaTime)
	{
		// TODO Auto-generated method stub
		super.Atualiza(deltaTime);
	}
	
	@Override
	public void Click(Botao botaoClicado, BotoesMouse botaoMouse)
	{
		if (botaoMouse == BotoesMouse.Esquerdo)
		{
			if (botaoClicado == jogar)
			{
<<<<<<< HEAD
				Barbaro a, b;
				a = new Barbaro();
				b = new Barbaro();
				a.SetPlayer(new Player(TipoPlayer.Primario));
				b.SetPlayer(new Player(TipoPlayer.Secundario));
				CatQuest.instancia.AdicionaTela(new GamePlay(a, b), false, false);
				_musica.dispose();
=======
				Barbaro b;
				Mago a;
				b = new Barbaro();
				a = new Mago();
				a.SetPlayer(new Player(TipoPlayer.Primario));
				b.SetPlayer(new Player(TipoPlayer.Secundario));
				CatQuest.instancia.AdicionaTela(new GamePlay(a, b), false, false);
>>>>>>> origin/classes
			}
			else if (botaoClicado == configurar)
			{
				CatQuest.instancia.AdicionaTela(new Configuracoes(), false, false);
			}
			else if (botaoClicado == sair)
			{
				CatQuest.instancia.EncerraJogo();
			}
		}
	}

	@Override
	public void AoCarregar(Music somCarregada)
	{
		_musica = somCarregada;		
	}
}