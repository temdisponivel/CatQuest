package classes.gameobjects.personagens.ataque;

import catquest.CatQuest;
import classes.gameobjects.GameObject;

import classes.gameobjects.personagens.herois.Heroi;
import classes.gameobjects.personagens.inimigos.Inimigo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Espadada extends Ataque {

	public Espadada(Vector2 posicao, int direcao) {
		super(posicao, direcao);
		_sprite = new Sprite(CatQuest.instancia.GetTextura(Gdx.files
				.local("sprites/boladefogo")));
	}

	@Override
	protected void MovimentaAtaque(float deltaTime) {
		//N‹o realiza movimento!

	}

	@Override
	public void AoColidir(GameObject colidiu) {
		_colidido = true;

		if (colidiu instanceof Inimigo) {
			// O que acontece quando colide com Inimigo.

		} else if (colidiu instanceof Heroi) {
			// O que acontece quando colide com Heroi.

		}

	}

	@Override
	public String toString() {
		return "Espadada";
	}
}
