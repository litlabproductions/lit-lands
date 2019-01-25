package com.litlabproductions.litlands.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.litlabproductions.litlands.LitLands;
import com.litlabproductions.litlands.Screens.PlayScreen;

/**
 * Created by David on 9/23/2017.
 */

public abstract class InteractiveTileObject
{
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected PlayScreen screen;
    protected MapObject object;


    public InteractiveTileObject(PlayScreen screen, MapObject object) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.object = object;
        this.bounds = ((RectangleMapObject) object).getRectangle();

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        BodyDef bdef = new BodyDef();
        // Body Types:
        // ~ Dynamic - Forces can move around on the screen
        // ~ Static - Can move forcefully but they are not affected by default forces
        // ~ Kinematic - Isn't affected by forces like gravity, but can be manipulated with velocity.
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / LitLands.PPM, (bounds.getY() + bounds.getHeight() / 2) / LitLands.PPM);
        // ~ Add body to Box2D World.
        body = world.createBody(bdef);

        // ~ Define poly shape.
        shape.setAsBox(bounds.getWidth() / 2 / LitLands.PPM, bounds.getHeight() / 2 / LitLands.PPM);
        fdef.shape = shape;

        // ~ Add fixture to body.
        fixture = body.createFixture(fdef);

    }

    public abstract void onHeadHit(Player player);

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

            // ~ Return cell at x, y that corresponds to current Box2D Body.
            // ~ Box2D body is scaled down. if it looks like pos. 16 its really .16 so we * 100
        return layer.getCell((int)(body.getPosition().x * LitLands.PPM / 70),
                (int)(body.getPosition().y * LitLands.PPM / 70));
    }
}