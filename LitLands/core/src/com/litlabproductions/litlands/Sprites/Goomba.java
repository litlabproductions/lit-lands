package com.litlabproductions.litlands.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.litlabproductions.litlands.LitLands;
import com.litlabproductions.litlands.Screens.PlayScreen;

/**
 * Created by David on 9/24/2017.
 */

public class Goomba extends Enemy
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy = false;
    private boolean destroyed = false;


    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();

        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("goomba"), i * 16, 0, 16, 16));

        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / LitLands.PPM, 16 / LitLands.PPM);
    }

    public void update(float dt)
    {
        stateTime += dt;
        if (setToDestroy && !destroyed)
        {
                // ~ Remove goomba from box2D world.
            world.destroyBody(b2body);
            destroyed = true;

                // set texture region to smashed goomba
            setRegion(new TextureRegion(screen.getCharacterAtlas().findRegion("goomba"), 32, 0, 16, 16));
            stateTime = 0; // reset
        }

        else if (!destroyed)
        {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    public void destroy()
    {
        setToDestroy = true;
    }


    public void killed()
    {
        //currentState = ZombieOne.State.DEAD;
        Filter filter = new Filter();
        filter.maskBits = LitLands.NOTHING_BIT;

        //for (Fixture fixture : b2body.getFixtureList())
          //  fixture.setFilterData(filter);
        world.destroyBody(b2body);


        b2body.applyLinearImpulse(new Vector2(0, 2), b2body.getWorldCenter(), true);
    }

    @Override
    protected void defineEnemy()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        // ~ Hitbox size around mario for hitting boxes / enemy .
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / LitLands.PPM);

        // ~ Create a fixture that is mario and has a category bit of mario.
        fdef.filter.categoryBits = LitLands.ENEMY_BIT;
        // ~ What can mario collide with?
        fdef.filter.maskBits = LitLands.GROUND_BIT | LitLands.COIN_BIT | LitLands.BRICK_BIT |
            LitLands.ENEMY_BIT | LitLands.OBJECT_BIT | LitLands.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

            // ~ Create head here.
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / LitLands.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / LitLands.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / LitLands.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / LitLands.PPM);
        head.set(vertice);

        fdef.shape = head;
            // bounce.
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = LitLands.ENEMY_HEAD_BIT;

        b2body.createFixture(fdef).setUserData(this);
    }



    public void onEnemyHit(Enemy enemy)
    {
        if(enemy instanceof ZombieOne && ((ZombieOne) enemy).currentState == ZombieOne.State.MOVING_SHELL)
            setToDestroy = true;
        else
            reverseVelocity(true, false);
    }

    public void draw(Batch batch)
    {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead(Player player)
    {
        setToDestroy = true;
        //LitLands.manager.get("audio/sounds/stomp.wav", Sound.class).play();
    }
}
