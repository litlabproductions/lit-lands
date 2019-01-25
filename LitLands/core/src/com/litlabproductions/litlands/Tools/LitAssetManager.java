package com.litlabproductions.litlands.Tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by David on 10/29/2017.
 */

public class LitAssetManager extends AssetManager
{
    public AssetManager assetManager;

    public LitAssetManager()
    {
        assetManager = new AssetManager();
        assetManager.load("audio/sounds/jump_sound_1.mp3", Sound.class);
        assetManager.load("audio/sounds/walk_sound.wav", Sound.class);
        assetManager.load("audio/sounds/run_sound.wav", Sound.class);
        assetManager.load("audio/sounds/sword_attack_1.wav", Sound.class);
        assetManager.load("audio/music/song_1_1.ogg", Music.class);
        assetManager.finishLoading();
    }


}
