package com.litlabproductions.litlands.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.litlabproductions.litlands.LitLands;
import com.litlabproductions.litlands.Screens.PlayScreen;


/**
 * Created by David on 9/23/2017.
 */

public class Player extends Sprite
{
    public enum State
    {
        FALLING, JUMPING, STANDING, RUNNING, DEAD
    }

    public State currentState;
    public State previousState;

    public World world;
    public PlayScreen screen;

   private LitLands game;

    public Body b2body;

    private TextureRegion playerStand;
    private TextureRegion playerJump;
    private TextureRegion playerDead;
    private TextureRegion playerDoubleJump;
    private TextureRegion playerFall;

    private Animation playerRun;
    private Animation warriorAttack;
    private Animation warriorStand;
    private Animation warriorJump;
    private Animation playerWalk;

    private boolean playerIsDead;
    private boolean runningRight;

    private BodyDef bDef;
    private FixtureDef fDef;

    private BodyDef swingBDef;
    private FixtureDef swingFDef;

    float stateTimer;
    float attackTimer;

    float runSoundSpeed;

    Sound currentSound;
    Sound walkSound;
    Sound runSound;

    public long currentSoundID;
    public long walkSoundID;
    public float mTimeToNextStep;



    // ~ Test new swing animation / static b2body.

        // ~ Cannot do addition or subtraction of any box2d bodies while the physics simulation is
        //   under way. What we do instead is send a boolean trigger of what happened, allowing us
        //   to make the required changes next time we are out opf the simulation.
    public Player(PlayScreen screen, LitLands game)
    {
        this.game = game;
        this.world = screen.getWorld();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        this.screen = screen;


            // ~ Array of Player texture regions to pass the constructor for the animations.
        Array<TextureRegion> frames = new Array<TextureRegion>();

            // ~ Attack.
        for (int i = 0; i < 24; i++)
            frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("jugg_attack"), i * 256, 0, 256, 256));
        warriorAttack = new Animation(.016f, frames);
        frames.clear();

        // ~ Run.
        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("jugg_run2"), i * 256, 0, 256, 256));
        playerRun = new Animation(0.05f, frames);
        frames.clear();

        // ~ Walk.
        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("jugg_walk"), i * 256, 0, 256, 256));
        playerWalk = new Animation(.09f, frames);
        frames.clear();

        // ~ Stand ~ Not currently being used.

        for(int i = 0; i < 12; i++)
            frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("jugg_idle"), i * 256, 0, 256, 256));
        warriorStand = new Animation(.1f, frames);
        frames.clear();


        // ~ Jump.
        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getCharacterAtlas().findRegion("jugg_jump"), i * 256, 0, 256, 256));
        warriorJump = new Animation(.05f, frames);
        frames.clear();

        // ~ Single texture regions used for only one frame output.
        playerDoubleJump = new TextureRegion(screen.getCharacterAtlas().findRegion("jugg_double_jump"), 0, 0, 256, 256);

        playerFall = new TextureRegion(screen.getCharacterAtlas().findRegion("jugg_fall"), 0, 0, 256, 256);

        playerStand = new TextureRegion(screen.getCharacterAtlas().findRegion("jugg_idle"), 0, 0, 256, 256);

        playerDead = new TextureRegion(screen.getCharacterAtlas().findRegion("jugg_idle"), 0, 0, 256, 256);

        bDef = new BodyDef();
        fDef = new FixtureDef();

        swingBDef = new BodyDef();
        swingFDef = new FixtureDef();

        definePlayer();

        setBounds(0, 0, 256 / LitLands.PPM, 256 / LitLands.PPM);

        // ~ Set texture region associated with sprite.


        //setRegion(playerStand);
        setRegion((TextureRegion) warriorStand.getKeyFrame(stateTimer, true));

        stateTimer = 0;
        attackTimer = 0;

        runSoundSpeed = 1f;


        mTimeToNextStep = 0;


        //walkSoundID = walkSound.play();
        //runSoundID = runSound.play();

        //runSound.setPan(runSoundID, 2f, 1.5f);
       // walkSound.setPan(walkSoundID, 2f, 1.5f);

        //walkSound.setLooping(walkSoundID, true);

        //runSound.setLooping(runSoundID, true);

        //currentSound = walkSound;
        //currentSoundID = currentSound.play(1.2f, 2f, 1f);
        //currentSound.pause(currentSoundID);

        //runSound.setPitch(runSoundID, 5f);
        //walkSound.setPan(0, 2, 2);
        //runSound.setPan(0, 5, 2);
        //currentSound = runSound;
    }

    public void hit(Enemy enemy) {
        if (enemy instanceof ZombieOne && ((ZombieOne) enemy).getCurrentState() == ZombieOne.State.STANDING_SHELL) {
            //((ZombieOne) enemy).kick(this.getX() <= enemy.getX() ? ZombieOne.KICK_RIGHT_SPEED : ZombieOne.KICK_LEFT_SPEED);
        } else {
            // LitLands.manager.get("audio/music/mario_music.ogg", Music.class).stop();
            //  LitLands.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            playerIsDead = true;

            // ~ Attatch filter for mask bits(what a fixture can collide with) on every fixture
            Filter filter = new Filter();
            filter.maskBits = LitLands.NOTHING_BIT;

            // ~ Get all the fixtures for mario.
            // ~ for each fixture, set to filter above.
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);

            // ~ Death jump / fall
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);

        }
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void update(float dt) {

        attackTimer += dt;
/*


        if(b2body.getLinearVelocity().x > 2.5 && !isFlipX())
            b2body.setLinearVelocity(b2body.getLinearVelocity().x -= .02f, b2body.getLinearVelocity().y);

        if(b2body.getLinearVelocity().x < -2.5 && isFlipX())
            b2body.setLinearVelocity(b2body.getLinearVelocity().x += .02f, b2body.getLinearVelocity().y);
 */
        if(currentState == State.RUNNING)
        {
            mTimeToNextStep -= dt;

            if (Math.abs(b2body.getLinearVelocity().x) >= 1.85f)
            {
                if (mTimeToNextStep < 0)
                {
                    game.manager.get("audio/sounds/walk_sound.wav", Sound.class).play(.45f, 1f, 1f);

                    while (mTimeToNextStep < 0)
                    {                               // ~ In case of a really slow frame, make
                        mTimeToNextStep += .35f;     //   sure we don't fall too far behind.
                    }
                }
            }
            if (Math.abs(b2body.getLinearVelocity().x) < 1.85f)
            {
                if (mTimeToNextStep < 0)
                {
                    game.manager.get("audio/sounds/walk_sound.wav", Sound.class).play(.45f, 1f, 1f);

                    while (mTimeToNextStep < 0)
                    {                               // ~ In case of a really slow frame, make
                        mTimeToNextStep += .5f;     //   sure we don't fall too far behind.
                    }
                }
            }
        }
        else
        {
            mTimeToNextStep = 0;
        }
        //if(b2body.getLinearVelocity().y < 0)

        // b2body.getPosition().y + .3f will raise a little..
        setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y + .1f)  - getHeight() / 2);


        // ~ Return the correct frame that we need to display the texture region.


        setRegion(getFrame(dt));


        if(warriorAttack.isAnimationFinished(attackTimer)) {
            screen.setAttacking(false);
            attackTimer = 0;
            currentState = previousState;
            //swing = null;
        }


       //if(currentState != State.RUNNING)
           //currentSound.pause();
          // currentSound.stop();
       // if ((player.isFlipX() && player.b2body.getLinearVelocity().x >= 1.85f) || (!player.isFlipX() && player.b2body.getLinearVelocity().x <= -1.85f))
       // {
        //   runSound.play();
       // }


    }


    public State getCurrentState() {
        return currentState;
    }

    public void playSound(Sound toPlay, float dt)
    {

        mTimeToNextStep -= dt;

        if (mTimeToNextStep < 0)
        {
            toPlay.play();
            while (mTimeToNextStep < 0)
            {                               // ~ In case of a really slow frame, make
                mTimeToNextStep += 3;     //   sure we don't fall too far behind.
            }
        }


    }



    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        if (screen.isPlayerAttacking())
            region = (TextureRegion) warriorAttack.getKeyFrame(attackTimer, false);
        else if(b2body.getLinearVelocity().y < -4.5f) {
            region = playerFall;
            attackTimer = 0;
        }
        else
        {
            attackTimer = 0;

            switch (currentState)
            {
                case DEAD:
                    region = playerDead;
                    break;

                case FALLING:
                    region = playerFall;
                    break;

                case JUMPING:
                    if (screen.getJumpCount() > 1)
                        region = playerDoubleJump;

                    else
                        region = (TextureRegion) warriorJump.getKeyFrame(stateTimer, false);
                    break;

                case RUNNING:

                    if (screen.isPlayerAttacking())
                    {
                        region = (TextureRegion) warriorAttack.getKeyFrame(attackTimer, false);
                        b2body.setLinearVelocity(0, b2body.getLinearVelocity().y);
                    }
                    else if ((!isFlipX() && b2body.getLinearVelocity().x < 1.85f) || (isFlipX() && b2body.getLinearVelocity().x > -1.85f))
                    {
                        region = (TextureRegion) playerWalk.getKeyFrame(stateTimer, true);
                        currentSound = walkSound;
                    }
                    else {
                        region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                        currentSound = runSound;
                    }
                    break;

                case STANDING:

                    mTimeToNextStep = 0;

                default:
                    region = (TextureRegion) warriorStand.getKeyFrame(stateTimer, true);

                    break;
            }
        }
            // ~ If running in direction that
            if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
                region.flip(true, false);
                //bDef.position.set(currentPosition.add(-20, 0));
                runningRight = false;
            } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
                region.flip(true, false);
                runningRight = true;
            }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }





    public State getState() {
        // ~ Is currently jumping in the air.
        // ~ But, we want two different animations for falling and jumping/falling from ground.
        if (playerIsDead)
            return State.DEAD;
        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void definePlayer() {
        //bDef.position.set(32 / LitLands.PPM, 32 / LitLands.PPM);
        bDef.position.set(1300 / LitLands.PPM, 1000 / LitLands.PPM);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);


        // FixtureDef fDef = new FixtureDef();
        // ~ Hitbox size around mario for hitt6ing boxes / enemy .
        CircleShape shape = new CircleShape();
        shape.setRadius(22/ LitLands.PPM);

        // ~ Create a fixture that is mario and has a category bit of mario.
        fDef.filter.categoryBits = LitLands.PLAYER_BIT;
        // ~ What can player collide with?
        fDef.filter.maskBits = LitLands.GROUND_BIT |
                LitLands.COIN_BIT |
                LitLands.BRICK_BIT |
                LitLands.ENEMY_BIT |
                LitLands.OBJECT_BIT |
                LitLands.ENEMY_HEAD_BIT |
                LitLands.ITEM_BIT |
                LitLands.PORTAL_BIT;


        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);


        // ~ Line between two didderent points.
    }

    public void redefinePlayer() {       // ~ Create small mario at the same location big mario was at.
        Vector2 postition = b2body.getPosition();
        // ~ Destory big mario.
        world.destroyBody(b2body);
        // ~ Recreate little mario box2D body but at location of big mario above.

        // BodyDef bDef = new BodyDef();
        // bDef.position.set(32 / LitLands.PPM, 32 / LitLands.PPM);
        bDef.position.set(postition);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        //FixtureDef fDef = new FixtureDef();
        // ~ Hitbox size around mario for hitting boxes / enemy .
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / LitLands.PPM);

        // ~ Create a fixture that is mario and has a category bit of mario.
        fDef.filter.categoryBits = LitLands.PLAYER_BIT;
        // ~ What can mario collide with?
        fDef.filter.maskBits = LitLands.GROUND_BIT |
                LitLands.COIN_BIT |
                LitLands.BRICK_BIT |
                LitLands.ENEMY_BIT |
                LitLands.OBJECT_BIT |
                LitLands.ENEMY_HEAD_BIT |
                LitLands.ITEM_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);

        // ~ Line between two didderent points.
        EdgeShape head = new EdgeShape();
        // (x,y) of contact
        head.set(new Vector2(-2 / LitLands.PPM, 6 / LitLands.PPM), new Vector2(2 / LitLands.PPM, 6 / LitLands.PPM));
        fDef.filter.categoryBits = LitLands.PLAYER_HEAD_BIT;
        fDef.shape = head;

        // ~ Sensor doesnt colide, just used as sensor.
        fDef.isSensor = true;

        b2body.createFixture(fDef).setUserData(this);


    }


    public void draw(Batch batch) {
        super.draw(batch);
        //if(swing != null)
           // swing.draw(batch);
        // public void draw(Batch batch){
        //   super.draw(batch);
        // swing.draw(batch);

    }
}
// ~ Box2D Filter (2 Parts):
//  - Castegory - wjat is this fixture? mario brick ..
//  - mass - what can it collide with? ground, brick..