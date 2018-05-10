/*
	Classes for timer and day/night cycle were found here:
	http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=9557
	All art and sounds from
	https://opengameart.org/

 */


package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MainMenu;
import com.mygdx.game.screens.PlayScreen;

public class FarmGame extends Game {
	public static final float UNIT_SCALE = 1/32f;
	public SpriteBatch batch;
	public static AssetManager manager;

	@Override
	public void create () {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("music.mp3", Music.class);
        manager.load("water.mp3", Sound.class);
        manager.load("dirt.mp3", Sound.class);
        manager.finishLoading();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
