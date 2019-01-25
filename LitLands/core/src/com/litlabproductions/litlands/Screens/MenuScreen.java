package com.litlabproductions.litlands.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.litlabproductions.litlands.LitLands;

/**
 * Created by David on 9/25/2017.
 */

public class MenuScreen implements Screen
{
    private Viewport viewport;
    private Stage stage;
    final Game game;

    public MenuScreen(final Game game)
    {
        this.game = game;
        viewport = new FitViewport(LitLands.V_WIDTH / LitLands.PPM, LitLands.V_HEIGHT / LitLands.PPM, new OrthographicCamera());
        stage = new Stage(viewport, ((LitLands) game).batch);




        Gdx.input.setInputProcessor(stage);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/old_font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 75;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!



        //countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font12, Color.FIREBRICK));
            // ~ Create font for game over screen.
        Label.LabelStyle font = new Label.LabelStyle(font12, Color.WHITE);



        Texture background = new Texture(Gdx.files.internal("Menu/map_bg.png"));

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable((new TextureRegion(background)));

       // Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        //pixmap.setColor(Color.WHITE);
        //pixmap.fill();

        //skin.add("white", new Texture(pixmap));
        Table table = new Table();


        table.top();
        table.setBackground(textureRegionDrawable);

            // ~ Allow table to take up entire stage.
        table.setFillParent(true);



        Image litLandsHeader = new Image(new Texture("Menu/lit_lands_header.png"));
        //litLandsHeader.setSize(900, 264);
       // litLandsHeader.scaleBy(-.05f);
      litLandsHeader.setScaling(Scaling.fit);

        //litLandsHeader.setDrawable(headerRegion);
        //litLandsHeader.setScaling(Scaling.fit);




        Image optionsImage = new Image(new Texture("Menu/options.png"));
        //optionsImage.setFillParent(true);
       //optionsImage.setSize(200, 30);
       // optionsImage.setScaling(Scaling.fill);





        Image landsMap = new Image(new Texture("Menu/land_display.png"));
    //landsMap.setSize(600, 309  );
        //landsMap.setScaling(Scaling.fill);
        //landsMap.setScaling(Scaling.fit);
        //litLabel.setColor(Color.FIREBRICK);

        //Label landsLabel = new Label("L A N D S", font);
        //landsLabel.setColor(Color.FOREST);

        Image playImage = new Image(new Texture("Menu/play.png"));

        //playImage.setSize(175 , 35);
       // playImage.setScaling(Scaling.fit);


   // playImage.scaleBy(.5f);
        //Label playLabel = new Label("PLAY", font);
       // playLabel.setColor(Color.RED);

       // playLabel.setFontScale(.42f);


        playImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new WorldScreen(game));
                dispose();
            }
        });

        //litLabel.setFontScale(1.5f);
    //landsLabel.setFontScale(.47f);
        //table.row();




        //table.align(1);

       //table.add(optionsImage);

        table.add(litLandsHeader).size(playImage.getWidth() / LitLands.PPM, playImage.getHeight()/ LitLands.PPM);

        table.row();

              //  .pad(.1f, .1f, .1f, .1f);
      table.add(playImage).size(playImage.getWidth() / LitLands.PPM, playImage.getHeight()/ LitLands.PPM);
       table.row();
        table.add(optionsImage).size(optionsImage.getWidth() / LitLands.PPM, optionsImage.getHeight()/ LitLands.PPM);
       // table.row();
        //table.add(landsMap).size(landsMap.getWidth() / LitLands.PPM, landsMap.getHeight()/ LitLands.PPM);
        //table.row();

       // table.padBottom(.5f);


        table.pad(0, 0, 0, 0);
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {


        //if (Gdx.input.justTouched())
        //{
         //   game.setScreen(new PlayScreen((LitLands) game));
        //    dispose();
     //   }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
    public void dispose() {

    }
}
