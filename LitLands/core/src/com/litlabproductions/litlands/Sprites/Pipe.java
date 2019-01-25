package com.litlabproductions.litlands.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.litlabproductions.litlands.Screens.PlayScreen;

/**
 * Created by David on 9/23/2017.
 */

public class Pipe extends InteractiveTileObject
{
    public Pipe(PlayScreen screen, MapObject object)
    {
        super(screen, object);
            // ~ Need access to the specific obj that user came into contact with.
        fixture.setUserData(this);

    }

    @Override
    public void onHeadHit(Player player)
    {
        Gdx.app.log("Pipe", "Collision");
        //setCategoryFilter(LitLands.DESTROYED_BIT);

    }
}
