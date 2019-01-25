package com.litlabproductions.litlands.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.litlabproductions.litlands.LitLands;
import com.litlabproductions.litlands.Screens.PlayScreen;

/**
 * Created by David on 9/23/2017.
 */

public class Coin extends InteractiveTileObject
{
    private static TiledMapTileSet tileSet;
        // ~ New variable. Tiled Map editor starts counting ID from 0 and LibGDX starts at 1.
        // ~ Index in Tiled is 27 for coin
    private final int BLANK_COIN = 28;


    public Coin(PlayScreen screen, MapObject object)
    {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(LitLands.COIN_BIT);
    }

    @Override
    public void onHeadHit(Player player)
    {
        //if(getCell().getTile().getId() == BLANK_COIN)
            //LitLands.manager.get("audio/sounds/bump.wav", Sound.class).play();
       // else
       // {
           // if(object.getProperties().containsKey("mushroom")) {
            //    screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / LitLands.PPM), Mushroom.class));
                //LitLands.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
           // }
           // else
                //manager.get("audio/sounds/coin.wav", Sound.class).play();
        //}
        //getCell().setTile(tileSet.getTile(BLANK_COIN));
       // Hud.addScore(100);
    }
}
