package com.litlabproductions.litlands.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.litlabproductions.litlands.LitLands;
import com.litlabproductions.litlands.Screens.PlayScreen;
import com.litlabproductions.litlands.Sprites.Brick;
import com.litlabproductions.litlands.Sprites.Coin;
import com.litlabproductions.litlands.Sprites.Enemy;
import com.litlabproductions.litlands.Sprites.Goomba;
import com.litlabproductions.litlands.Sprites.Portal;
import com.litlabproductions.litlands.Sprites.ZombieOne;

/**
 * Created by David on 9/23/2017.
 */

public class B2WorldCreator
{
    private Array<Goomba> goombas;
    private Array<ZombieOne> zombieOnes;
    private Array<Portal> portals;

    public B2WorldCreator(PlayScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
            // ~ Define fixture to add to body. Then add body to World la.
        FixtureDef fdef = new FixtureDef();
        Body body;



            // ~ Create a body and fixture at every corresponding GROUND tile within tile map layers.
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

                //Body Types:
                // ~ Dynamic - Forces can move around on the screen
                // ~ Static - Can move forcfull but they are not affected by defauly forces
                // ~ Kinematic - Isn't affected by forces like graviy, but can be manipulated with velocity.
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / LitLands.PPM, (rect.getY() + rect.getHeight() / 2) / LitLands.PPM);
                // ~ Add body to Box2D World.
            body = world.createBody(bdef);

                // ~ Define poly shape.
            shape.setAsBox(rect.getWidth() / 2 / LitLands.PPM , rect.getHeight() / 2 / LitLands.PPM);
            fdef.shape = shape;

                // ~ Add fixture to body.
            body.createFixture(fdef);
        }


        /*


            // ~ Create a body and fixture at every corresponding PIPE tile within tile map layers.
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / LitLands.PPM, (rect.getY() + rect.getHeight() / 2) / LitLands.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / LitLands.PPM , rect.getHeight() / 2 / LitLands.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = LitLands.OBJECT_BIT;

            body.createFixture(fdef);
        }


         */

      //  portals = new Array<Portal>();
     //   for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
        //{
           // Rectangle rect = ((RectangleMapObject) object).getRectangle();

          //  portals.add(new Portal(screen, rect.getX() / LitLands.PPM, rect.getY() / LitLands.PPM));
        //}

            // ~ Create a body and fixture at every corresponding BRICK tile within tile map layers.
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class))
        {
            new Brick(screen, object);
        }

            // ~ Create a body and fixture at every corresponding COIN tile within tile map layers.
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class))
        {
            new Coin(screen, object);
        }




        goombas = new Array<Goomba>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            goombas.add(new Goomba(screen, rect.getX() / LitLands.PPM, rect.getY() / LitLands.PPM));
        }

        zombieOnes = new Array<ZombieOne>();
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            zombieOnes.add(new ZombieOne(screen, rect.getX() / LitLands.PPM, rect.getY() / LitLands.PPM));
        }
    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }

    public Array<Enemy> getEnemies()
    {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(zombieOnes);
        return enemies;
    }
}
