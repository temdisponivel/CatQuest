package classes.telas;

import classes.gameobjects.personagens.herois.Heroi;
import classes.uteis.ControladorCamera;
import classes.uteis.FabricaInimigo;
import classes.uteis.sons.CarregarMusica;
import classes.uteis.sons.CarregarMusicaListner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classe da tela onde o gameplay acontece.
 * 
 * @author matheus
 *
 */
public class GamePlay extends Tela implements CarregarMusicaListner
{
	private Heroi[] _herois = null;
	private Music _musica = null;
	private boolean _encerrada = false;
	
	/**
	 * Tela onde ocorre o gameplay do jogo.
	 * @param herois {@link Heroi Heróis} que irão jogar. Os heróis já devem ter seus {@link Player players} definidos.
	 */
	public GamePlay(Heroi... herois)
	{
		super(Gdx.files.local("arquivos//teste.tmx"));
		_herois = herois;
	}

	@Override
	public void Iniciar()
	{
		super.Iniciar();
		_tipo = Telas.GAMEPLAY;
		_corFundo = Color.BLACK;
		this.InserirGameObject(new ControladorCamera(_herois));
		this.InserirGameObject(_herois);
		this.InserirGameObject(new FabricaInimigo());
		CarregarMusica.instancia.Carrega(Gdx.files.local("audio/musica/game_play.mp3"), 0, true, true, this);
	}
	
	@Override
	public void Desenha(SpriteBatch spriteBatch)
	{
		super.Desenha(spriteBatch);
		
		
	}
	
	@Override
	public void Encerrar()
	{
		_atualiza = false;
	}

	@Override
	public void AoCarregar(Music somCarregada)
	{
		_musica = somCarregada;
	}
}
