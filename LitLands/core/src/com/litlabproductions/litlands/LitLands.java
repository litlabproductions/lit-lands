package com.litlabproductions.litlands;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.litlabproductions.litlands.Screens.MenuScreen;
import com.litlabproductions.litlands.Screens.PlayScreen;

public final class LitLands extends Game implements ApplicationListener
{
	public static final int V_WIDTH = 1080;
	public static final int V_HEIGHT = 562;

	public static final float PPM = 125;
	public static final short NOTHING_BIT = 0;
	public static final short PLAYER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short GROUND_BIT = 1;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short PLAYER_HEAD_BIT = 512;
	public static final short PORTAL_BIT = 1024;



	FPSLogger fpsLogger;

	public static SpriteBatch batch;

	public AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/sounds/jump_sound_1.mp3", Sound.class);
		manager.load("audio/sounds/walk_sound.wav", Sound.class);
		manager.load("audio/sounds/sword_attack_1.wav", Sound.class);
		manager.load("audio/music/song_1_1.ogg", Music.class);
		manager.finishLoading();

		fpsLogger = new FPSLogger();
		Gdx.app.log("LitLands", "Creating game" );
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render ()
	{
		super.render();
		fpsLogger.log();
		// ~ Call manager to continue to load updates.
		// ~ Instead we say finish loading ^^^
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		manager.dispose();
	}
}