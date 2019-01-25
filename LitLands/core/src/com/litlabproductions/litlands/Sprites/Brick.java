package com.litlabproductions.litlands.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.litlabproductions.litlands.LitLands;
import com.litlabproductions.litlands.Scenes.Hud;
import com.litlabproductions.litlands.Screens.PlayScreen;

/**
 * Created by David on 9/23/2017.
 */

public class Brick extends InteractiveTileObject
{
    public Brick(PlayScreen screen, MapObject object)
    {
        super(screen, object);
            // ~ Need access to the specific obj that user came into contact with.
        fixture.setUserData(this);
        setCategoryFilter(LitLands.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Player player)
    {
            //LitLands.manager.get("audio/sounds/bump.wav", Sound.class).play();
    }
}