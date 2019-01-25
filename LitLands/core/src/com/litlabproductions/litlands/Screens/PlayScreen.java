package com.litlabproductions.litlands.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.litlabproductions.litlands.LitLands;

import com.litlabproductions.litlands.Scenes.Controller;
import com.litlabproductions.litlands.Scenes.FrameRate;
import com.litlabproductions.litlands.Scenes.Hud;
import com.litlabproductions.litlands.Sprites.Enemy;
import com.litlabproductions.litlands.Sprites.Player;
import com.litlabproductions.litlands.Sprites.Portal;
import com.litlabproductions.litlands.Tools.B2WorldCreator;
import com.litlabproductions.litlands.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingDeque;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by David on 8/16/2017.
 */

public class PlayScreen implements Screen, ContactListener
{
    private LitLands game;
    private TextureAtlas characterAtlas;
    private TextureAtlas mapAtlas;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Player player; // ~ Player Class Obj

    private Music music;

    private Sound weaponSound;
    private long wepSoundID;


   // private Array<Item> items;
   // private LinkedBlockingDeque<ItemDef> itemsToSpawn;

    private int jumpCount;
    private int attackCount;

    private float xSpeed;
    private float cameraVFollowTimer;


    private boolean playerMoved;

    Controller controller;

    private boolean attacking;

    private Portal portal;


    private long now;
    private int framesCount = 0;
    private int framesCountAvg=0;
    private long framesTimer=0;


    private ParallaxBackground rbg;

    TextureRegion[] layers;
    ParallaxCamera camera;
   // OrthoCamController controller;



FrameRate frameRate;

    private boolean isRunning;

    public PlayScreen(LitLands game, String level)
    {
        characterAtlas = new TextureAtlas("Jugg.pack");
        mapAtlas = new TextureAtlas("Jugg.pack");

        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(LitLands.V_WIDTH / LitLands.PPM, LitLands.V_HEIGHT / LitLands.PPM, gameCam);
        gamePort.apply();

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(level);   //"1_1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / LitLands.PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()  / 2 , 0);

            // ~ Param: (gravity, sleep obj that are at rest).
        world = new World(new Vector2(0, -10), true);

        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);

        player = new Player(this, game);
        portal = new Portal(this, 582 / LitLands.PPM, 1975 / LitLands.PPM);

        world.setContactListener(new WorldContactListener());

        //music = game.manager.get("audio/music/song_1_1.ogg", Music.class);
        //music.setVolume(.2f);
        // music.setLooping(true);
        //music.play();

        weaponSound = game.manager.get("audio/sounds/sword_attack_1.wav", Sound.class);
        wepSoundID = weaponSound.play();
        weaponSound.setVolume(wepSoundID, .5f);

        //items = new Array<Item>();

      //  itemsToSpawn = new LinkedBlockingDeque<ItemDef>();

        controller = new Controller();

        attacking = false;
        xSpeed = .2f;
        cameraVFollowTimer = 0;
        isRunning = true;
        playerMoved = false;



  /*
        Texture b1 = new Texture(Gdx.files.internal("Land_1/Backgrounds/bg1.png"));
        TextureRegionDrawable bRegion = new TextureRegionDrawable((new TextureRegion(b1)));
      Texture b2 = new Texture(Gdx.files.internal("Land_1/Backgrounds/bg2.png"));
        TextureRegionDrawable bRegion2 = new TextureRegionDrawable((new TextureRegion(b2)));
        Texture b3 = new Texture(Gdx.files.internal("Land_1/Backgrounds/bg3.png"));
        TextureRegionDrawable bRegion3 = new TextureRegionDrawable((new TextureRegion(b3)));
 */

/*


        rbg = new ParallaxBackground(new ParallaxLayer[]{
                //new ParallaxLayer(bRegion.getRegion(),new Vector2(),new Vector2(0, 0)),
                new ParallaxLayer(bRegion2.getRegion(),new Vector2(1.0f,1.0f),new Vector2(0, 500)),
                new ParallaxLayer(bRegion3.getRegion(),new Vector2(0.1f,0),new Vector2(0,0),new Vector2(0, 0)),
        }, 12800, 2560,new Vector2(150,0));


// OTHER
         layers[1] = bRegion2.getRegion();
        layers[2] = bRegion3.getRegion();


        layers = new TextureRegion[3];
        layers[0] = bRegion.getRegion();


        camera = new ParallaxCamera(gameCam.viewportWidth, gameCam.viewportHeight);

 */

    }

    public Game getGame()
    {
        return game;
    }
    public boolean isPlayerAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

   // public void spawnItem(ItemDef idef)
    //{
     //   itemsToSpawn.add(idef);
   // }

    public void handleSpawnItems()
    {
        //if(!itemsToSpawn.isEmpty())
        //{
         //   ItemDef idef = itemsToSpawn.poll();
           // if(idef.type == Mushroom.class)
           // {
           //     items.add(new Mushroom(this, idef.position.x, idef.position.y));
           // }
     //   }
    }

    public TextureAtlas getCharacterAtlas()
    {
        return characterAtlas;
    }

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public void show()
    {
        //Gdx.input.setInputProcessor(parallaxStage);    // ~ Parallax Background.
    }





    public void handleInput(float dt)
    {
            // ~ Control our player using immediate impulses.
        //if(player.currentState != Player.State.DEAD)
        //{
            //if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) // ~ Param: x, y force --> When in body to apply --> wake obj
             //   player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            //if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
             //   player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
           // if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
           //     player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        //}
        //float xSpeed = .9955f;



        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
        {
            if (input.isKeyPressed(Input.Keys.SPACE) && attackCount < 1)
            {
                attacking = true;
                game.manager.get("audio/sounds/sword_attack_1.wav", Sound.class).play(.2f);
                ++attackCount;
                // if(canAttack() && player.b2body.getLinearVelocity().x < .3)
                //canAttack = false;
            }

            if (!(input.isKeyPressed(Input.Keys.SPACE)))
            {
               attackCount = 0;

            }


        }
        if (Gdx.app.getType() == Application.ApplicationType.Android)
        {
            if (controller.isBPressed() && attackCount < 1)
            {
                attacking = true;
                ++attackCount;
                game.manager.get("audio/sounds/sword_attack_1.wav", Sound.class).play(.2f);

                //if(canAttack() && player.b2body.getLinearVelocity().x < .3)
                //player.swing();
            }
            if (!controller.isBPressed())
            {
                attackCount = 0;

                // attacking = false;
            }
        }


        if((controller.isRightPressed() &&  player.b2body.getLinearVelocity().x <= 3)|| (input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 3))
        {
            player.b2body.applyLinearImpulse(new Vector2(xSpeed, 0), player.b2body.getWorldCenter(), true);
            if(xSpeed < 2)
                xSpeed += 0.00001f;


        }
        if ((controller.isLeftPressed() &&  player.b2body.getLinearVelocity().x >= -3)|| (input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -3))
        {
            player.b2body.applyLinearImpulse(new Vector2(-xSpeed, 0), player.b2body.getWorldCenter(), true);
            if(xSpeed < 2)
                xSpeed += 0.00001f;
        }

        if(!controller.isRightPressed() && !input.isKeyPressed(Input.Keys.RIGHT) && !player.isFlipX() && player.b2body.getLinearVelocity().x > 0.1)
             player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x - .05f, player.b2body.getLinearVelocity().y);

        if (!controller.isLeftPressed() && !input.isKeyPressed(Input.Keys.LEFT) && player.isFlipX() && player.b2body.getLinearVelocity().x < -0.1)
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x + .05f, player.b2body.getLinearVelocity().y);


        switch(player.getState())
        {
            case RUNNING:
                jumpCount = 0;
                break;
            case STANDING:
                jumpCount = 0;
               // xSpeed = .5f;
                break;
            default:
                break;
        }


        if(player.b2body.getLinearVelocity().y > 6.5)
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, player.b2body.getLinearVelocity().y -= .02);

        if (input.isKeyJustPressed(Input.Keys.UP))
        {
            controller.setUpPressed(false);
            if (jumpCount < 2 )
            {
                if (jumpCount == 0)
                {
                    player.b2body.applyLinearImpulse(new Vector2(0, 6 - player.b2body.getLinearVelocity().y), player.b2body.getWorldCenter(), true);
                    game.manager.get("audio/sounds/jump_sound_1.mp3", Sound.class).play(.5f);
                }

                if (jumpCount == 1 )
                {
                    player.b2body.applyLinearImpulse(new Vector2(0, 6 - player.b2body.getLinearVelocity().y), player.b2body.getWorldCenter(), true);
                    game.manager.get("audio/sounds/jump_sound_1.mp3", Sound.class).play(.5f);
                }
                ++jumpCount;
            }
        }

        if (controller.isUpPressed())
        {
            controller.setUpPressed(false);
            if (jumpCount < 2 )
            {
                if (jumpCount == 0)
                {
                    player.b2body.applyLinearImpulse(new Vector2(0, 6 - player.b2body.getLinearVelocity().y), player.b2body.getWorldCenter(), true);
                    game.manager.get("audio/sounds/jump_sound_1.mp3", Sound.class).play(.5f);
                }

                if (jumpCount == 1 )
                {
                    player.b2body.applyLinearImpulse(new Vector2(0, 6 - player.b2body.getLinearVelocity().y), player.b2body.getWorldCenter(), true);
                    game.manager.get("audio/sounds/jump_sound_1.mp3", Sound.class).play(.5f);
                }
                ++jumpCount;
            }
        }
    }

    public void update(float dt)
    {
        handleInput(dt);
        handleSpawnItems();
            // ~ In order for Box2D to execute our physics simulation, we need to tell
            //   it how many times top calculate per second.
            // ~ Params : Have to do with collision calulation between two bodies.
            //*** LOOK INTO THIS BEFORE DESIGNING FINAL DRAFT CHARACTERS.
        world.step(1/60f, 6, 2);

        player.update(dt);
        portal.update(dt);


        if(player.b2body.getLinearVelocity().x != 0)
            playerMoved = true;

        for(Enemy enemy : creator.getEnemies())
        {
                    // Setting enemies active if player comes within a certain distance.
                    //if(enemy.getX() < player.getX() + 224 / LitLands.PPM)
                enemy.b2body.setActive(true);

                // ~ Running from the left to right. -> (Enemy to the right of player)
            if(player.b2body.getPosition().x < enemy.b2body.getPosition().x
                    && enemy.b2body.getPosition().x - player.b2body.getPosition().x < 1.3
                    && attacking && !player.isFlipX())
            {
                enemy.destroy();
            }

                // ~ Running from the right to left. -> (Enemy to the left of player)
            if(player.b2body.getPosition().x > enemy.b2body.getPosition().x
                    && player.b2body.getPosition().x - enemy.b2body.getPosition().x < 1.3
                    && attacking && player.isFlipX())
            {
                enemy.destroy();
            }

            enemy.update(dt);
        }

       // for(Item item : items)
      //  {
       //     item.update(dt);
      //  }

        hud.update(dt);

            // ~ Camera -> Character Follow.
        if(player.currentState != Player.State.DEAD)
            gameCam.position.x = player.b2body.getPosition().x;

        if (player.b2body.getPosition().y < gameCam.position.y)
            gameCam.position.y = player.b2body.getPosition().y;

        if (player.b2body.getPosition().y / LitLands.PPM  > gameCam.position.y / LitLands.PPM + ((gameCam.viewportHeight / 4) /  LitLands.PPM))
        gameCam.translate(0, gameCam.viewportHeight / LitLands.PPM, 0); // Moves the camera up.

        gameCam.update();
        renderer.setView(gameCam);


        //if(!playerMoved)
        //   gameCam.position.y = player.b2body.getPosition().y;
        // cameraVFollowTimer
        // gameCam.position.y = player.b2body.getPosition().y + .4F;
        //if (player.b2body.getLinearVelocity().y < 0)
                // gameCam.translate(0, (-(3 * gameCam.viewportHeight) / LitLands.PPM), 0); // Moves the camera up.
        // gameCam.position.y = player.b2body.getPosition().y;//gameCam.translate(0, -gameCam.viewportHeight / LitLands.PPM, 0); // Moves the camera down.
        //if (player.b2body.getPosition().y / LitLands.PPM < (gameCam.position.y  / LitLands.PPM - (gameCam.viewportHeight / 2 ) / LitLands.PPM))
      //  {
            //if (gameCam.position.y  / LitLands.PPM - player.b2body.getPosition().y / LitLands.PPM >= gameCam.viewportHeight / LitLands.PPM)
                //while (!(player.b2body.getPosition().y / LitLands.PPM >= (gameCam.viewportHeight / 2) / LitLands.PPM))
                    //gameCam.translate(0, (player.b2body.getLinearVelocity().y / LitLands.PPM), 0); // Moves the camera down.
       //     gameCam.position.y = player.b2body.getPosition().y + .4F;//gameCam.translate(0, -gameCam.viewportHeight / LitLands.PPM, 0); // Moves the camera down.
       // }
        //  gameCam.tr
       // if (player.b2body.getPosition().y  < gameCam.position.y + .1f)
          //  gameCam.position.y = player.b2body.getPosition().y ;
            // ~ Update camera every render.
            // ~ Only render what the game cam can see.

//To Measure FPS
//Main Game Loop
/*



        if(player.b2body.isActive())
            isRunning = true;

        if(!player.b2body.isActive())
            isRunning = false;

        while (isRunning)
        {
            //Record the time before update and draw
            long beforeTime = System.nanoTime();
            //... Update program & draw program...
            // DRAW FPS:
            now=System.currentTimeMillis();
            Gdx.(c, framesCountAvg);
            framesCount++;
            if(now-framesTimer>1000)
            {
                framesTimer=now;
                framesCountAvg=framesCount;
                framesCount=0;
            }} */
    }

    @Override
    public void render(float delta)
    {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        // ~ Parallax.
        // rbg.render(delta);

        b2dr.render(world, gameCam.combined);

        // ~ Main cam when were running around.
        game.batch.setProjectionMatrix(gameCam.combined);

        // ~ Open box to put all desired textures inside.
        game.batch.begin();

        player.draw(game.batch);
        portal.draw(game.batch);

        for(Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);


       // for(Item item : items)
        //    item.draw(game.batch);

        game.batch.end();

        // ~ What you want to be shown via the camera.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.stage.draw();

        if(gameOver())
        {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

/*


        boolean updateCamera = false;
        if (camera.position.x < -LitLands.V_WIDTH + camera.viewportWidth / 2) {
            camera.position.x = -LitLands.V_WIDTH + (int)(camera.viewportWidth / 2);
            updateCamera = true;
        }

        if (camera.position.x > LitLands.V_WIDTH - camera.viewportWidth / 2) {
            camera.position.x = LitLands.V_WIDTH - (int)(camera.viewportWidth / 2);
            updateCamera = true;
        }

        if (camera.position.y < 0) {
            camera.position.y = 0;
            updateCamera = true;
        }
        // arbitrary height of scene
        if (camera.position.y > LitLands.V_HEIGHT - camera.viewportHeight / 2) {
            camera.position.y = LitLands.V_HEIGHT - (int)(camera.viewportHeight / 2);
            updateCamera = true;
        }



        // background layer, no parallax, centered around origin
        game.batch.setProjectionMatrix(camera.calculateParallaxMatrix(0, 0));
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(layers[0], -(int)(layers[0].getRegionWidth() / 2), -(int)(layers[0].getRegionHeight() / 2));
        game.batch.end();
        game.batch.enableBlending();


        // midground layer, 0.5 parallax (move at half speed on x, full speed on y)
        // layer is 1024x320
        game.batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.5f, 1));
        game.batch.begin();
        game.batch.draw(layers[1], -6400 / LitLands.PPM, -1280 / LitLands.PPM);
        game.batch.end();

        // foreground layer, 1.0 parallax (move at full speed)
        // layer is 2048x320
        game.batch.setProjectionMatrix(camera.calculateParallaxMatrix(1f, 1));
        game.batch.begin();

        game.batch.draw(layers[2], layers[2].getRegionWidth() - 6400 / LitLands.PPM, -1280 / LitLands.PPM);

        game.batch.end();
 */



        // frameRate.update();
      //  frameRate.render();
    }

    public boolean gameOver()
    {
            // ~ If dead and 3 seconds has passed to allow for dead animation.
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 3)
            return true;
        else
            return false;
    }

    @Override
    public void resize(int width, int height)
    {
       // gameCam.setToOrtho(false, width/16, height/16);
        gamePort.update(width, height);
    }

    public int getJumpCount()
    {
        return jumpCount;
    }

    public TiledMap getMap()
    {
        return map;
    }

    public World getWorld()
    {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

       // layers[0].getTexture().dispose();
    }

    @Override
    public void beginContact(Contact contact) {


    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}