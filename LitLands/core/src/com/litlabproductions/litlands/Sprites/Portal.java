package com.litlabproductions.litlands.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.litlabproductions.litlands.LitLands;
import com.litlabproductions.litlands.Screens.PlayScreen;

/**
 * Created by David on 9/24/2017.
 */

public class Portal extends Sprite
{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

   // public enum State {WALKING, STANDING_SHELL, MOVING_SHELL, DEAD};
  //  public ZombieOne.State currentState;
    //public ZombieOne.State previousState;
    private float stateTime;
    private Animation normalAnimation;
    private Animation enterAnimatmation;


    private float deadRotationDegrees;
    private boolean setToDestroy = false;
    private boolean destroyed = false;



    public Portal(PlayScreen screen, float x, float y)
    {

        this.world = screen.getWorld();
        this.screen = screen;

        setPosition(x, y);
        definePortal();

        velocity = new Vector2(0, 0);   // ~ Static, not moving,

        //b2body.setActive(true);

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 7; i++)
            frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("portal"), i * 256, 0, 256, 256));
        normalAnimation = new Animation(.1f, frames);
        frames.clear();

        //for(int i = 0; i < 8; i++)
       //     frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("zombie_die"), i * 64, 0, 64, 64));
      //  enterAnimatmation = new Animation(.3f, frames);
       // frames.clear();

        //frames.clear();
        //currentState = previousState = ZombieOne.State.WALKING;

        setBounds(0, 0, 256 / LitLands.PPM, 256 / LitLands.PPM);
        deadRotationDegrees = 0;

        stateTime = 0;
        setRegion((TextureRegion) normalAnimation.getKeyFrame(stateTime, true));
    }



    protected void definePortal()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        // ~ Hitbox size around player for hitting boxes / enemy .
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / LitLands.PPM);

            // ~ Create a fixture that is the Portal and has a category bit of the portal.
        fdef.filter.categoryBits = LitLands.PORTAL_BIT;


            // ~ What can Portal collide with?  ~~~>> Only ground .. for now. ******

        fdef.filter.maskBits = LitLands.GROUND_BIT |  LitLands.PLAYER_BIT;
               // | LitLands.COIN_BIT | LitLands.BRICK_BIT |
              //  LitLands.ENEMY_BIT | LitLands.OBJECT_BIT | LitLands.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }
     /*
    public void onEnemyHit(Enemy enemy)
    {

        enemy.reverseVelocity(true, false);



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

        //else if
        //(currentState != State.MOVING_SHELL)
    }
       public TextureRegion getFrame(float dt)
       {
           TextureRegion region
          /*
           switch(currentState)

           {
               case DEAD:
                   region = (TextureRegion) enterAnimatmation.getKeyFrame(stateTime, false);
                   break;
               // case MOVING_SHELL:
               // region = shell;
               //  break;
               case WALKING:
               default:
                   region = (TextureRegion) normalAnimation.getKeyFrame(stateTime, true);
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
        */
    public void draw(Batch batch)
    {
            super.draw(batch);
    }

    public void update(float dt)
    {
        stateTime += dt;
       // b2body.setActive(true);
        setRegion((TextureRegion) normalAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y + .7f)  - getHeight() / 2);
        // ~ 1.) What texture do we want to show?
        // ~ 2.) Has the turtle been out of the shell for more than 5 seconds.
        // ~ 3.) Position of sprite equal to the box 2d body
        // ~ 4.) Set velocity of box2d body between left and right.


       // if(currentState == State.STANDING_SHELL && stateTime > 5)
      //  {
     //       currentState = State.WALKING;
        //    velocity.x = 1;
       // }

     //   if (setToDestroy && !destroyed)
      //  {
            // ~ Remove goomba from box2D world.


            //if (enterAnimatmation.isAnimationFinished(stateTime))
           // {
            //    world.destroyBody(b2body);
           //     destroyed = true;
          //  }

       // }

        //else if (!destroyed)
        //{
           // b2body.setLinearVelocity(velocity);
           // setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y +.15f) - getHeight() / 2 );
          //  setRegion(getFrame(dt));
      //  }
        //else
          //  setRegion(getFrame(dt));

        // if(enterAnimatmation.isAnimationFinished(stateTime))
        //   stateTime = 0;

       // b2body.setLinearVelocity(velocity);


    }
/*


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

        // ~ Check to see if turtle is standing still, what direction to kick shell

    }

    public void destroy()
    {
        currentState = ZombieOne.State.DEAD;
        setToDestroy = true;
    }


    public ZombieOne.State getCurrentState()
    {

        return currentState;
    }



    public void killed()
    {

        currentState = ZombieOne.State.DEAD;

        Filter filter = new Filter();
        filter.maskBits = LitLands.NOTHING_BIT;
//
        for (Fixture fixture : b2body.getFixtureList())
            fixture.setFilterData(filter);


        world.destroyBody(b2body);
        b2body.applyLinearImpulse(new Vector2(0, 0), b2body.getWorldCenter(), true);
        update(stateTime);
    }
     */
}
