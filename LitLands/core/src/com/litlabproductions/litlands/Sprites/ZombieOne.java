package com.litlabproductions.litlands.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.litlabproductions.litlands.LitLands;
import com.litlabproductions.litlands.Screens.PlayScreen;

/**
 * Created by David on 9/25/2017.
 */

public class ZombieOne extends Enemy
{
    public static final int KICK_LEFT_SPEED = -2;
    public static final int KICK_RIGHT_SPEED = 2;

    public enum State {WALKING, STANDING_SHELL, MOVING_SHELL, DEAD};
    public State currentState;
    public State previousState;

    private float stateTime;
    private Animation walkAnimation;
    private Animation deadAnimation;

    //private Array<TextureRegion> frames;


    private float deadRotationDegrees;

    private boolean setToDestroy = false;
    private boolean destroyed = false;


    public ZombieOne(PlayScreen screen, float x, float y)
    {
        super(screen, x, y);
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 6; i++)
            frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("zombie_walk"), i * 64, 0, 64, 64));
        walkAnimation = new Animation(.3f, frames);
        frames.clear();

        for(int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("zombie_die"), i * 64, 0, 64, 64));
        deadAnimation = new Animation(.3f, frames);
        frames.clear();

        //frames.clear();
        currentState = previousState = State.WALKING;

        setBounds(getX(), getY(), 100 / LitLands.PPM, 100 / LitLands.PPM);
        deadRotationDegrees = 0;


    }



    @Override
    protected void defineEnemy()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY() + .15f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        // ~ Hitbox size around player for hitting boxes / enemy .
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / LitLands.PPM);

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
        fdef.restitution = 1.5f;
        fdef.filter.categoryBits = LitLands.ENEMY_HEAD_BIT;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void onEnemyHit(Enemy enemy)
    {
        enemy.reverseVelocity(true, false);
        /*


        if(enemy instanceof  ZombieOne)
        {
            if(((ZombieOne) enemy).currentState == State.MOVING_SHELL && currentState != State.MOVING_SHELL)
            {
                destroy();
            }
            else if( currentState == State.MOVING_SHELL)
                return;
            else
                reverseVelocity(true, false);

        }
         */
        //else if
            //(currentState != State.MOVING_SHELL)
    }

    public TextureRegion getFrame(float dt)
    {
        TextureRegion region;

        switch(currentState)
        {
            case DEAD:
                 region = (TextureRegion) deadAnimation.getKeyFrame(stateTime, false);
                break;
           // case MOVING_SHELL:
               // region = shell;
              //  break;
            case WALKING:
            default:
                region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
                break;
        }
        // ~ By default the animation for walking turtle is facing tpo the left, so
        //   if he is facing right we need to flip animation frames.

       // if(velocity.x > 0 && region.isFlipX() == false)
      //   region.flip(true, false);

      //  if(velocity.x < 0 && region.isFlipX() == false)
         //   region.flip(true, false);


        if (velocity.x < 0  && !region.isFlipX()) {
            region.flip(true, false);
        }
        else if((velocity.x > 0  && region.isFlipX()))
        {
            region.flip(true, false);
        }

            // ~ Set state time to zero if the current state is not equal to the previous state.
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }

    public void draw(Batch batch)
    {
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void update(float dt)
    {
            // ~ 1.) What texture do we want to show?
            // ~ 2.) Has the turtle been out of the shell for more than 5 seconds.
            // ~ 3.) Position of sprite equal to the box 2d body
            // ~ 4.) Set velocity of box2d body between left and right.
        stateTime += dt;

/*
        if(currentState == State.STANDING_SHELL && stateTime > 5)
        {
            currentState = State.WALKING;
            velocity.x = 1;
        }
 */


        if (setToDestroy && !destroyed)
        {
            // ~ Remove goomba from box2D world.
            b2body.setActive(false);
            setRegion(getFrame(dt));
            if (deadAnimation.isAnimationFinished(stateTime))
            {
                world.destroyBody(b2body);
                destroyed = true;
            }
            // set texture region to smashed goomba

        }

        else if (!destroyed)
        {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y +.15f) - getHeight() / 2 );
            setRegion(getFrame(dt));
        }
        else
            setRegion(getFrame(dt));

       // if(deadAnimation.isAnimationFinished(stateTime))
         //   stateTime = 0;

        b2body.setLinearVelocity(velocity);


    }

    @Override
    public void hitOnHead(Player player)
    {
        killed();
        /*
        if ( currentState != State.STANDING_SHELL)

        {
            currentState = State.STANDING_SHELL;
            velocity.x = 0;
        }
        else
        {
            kick(player.getX() <= this.getX() ? KICK_RIGHT_SPEED : KICK_LEFT_SPEED);
        }
 */
        // ~ Check to see if turtle is standing still, what direction to kick shell

    }
    public void destroy()
    {
        currentState = State.DEAD;
        setToDestroy = true;
    }


    public State getCurrentState()
    {

        return currentState;
    }



    public void killed()
    {

        currentState = State.DEAD;

        Filter filter = new Filter();
        filter.maskBits = LitLands.NOTHING_BIT;
//
        for (Fixture fixture : b2body.getFixtureList())
            fixture.setFilterData(filter);


        world.destroyBody(b2body);
        b2body.applyLinearImpulse(new Vector2(0, 0), b2body.getWorldCenter(), true);
        update(stateTime);
    }
}
