package com.litlabproductions.litlands.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.litlabproductions.litlands.LitLands;

/**
 * Created by David on 9/25/2017.
 */

public class GameOverScreen implements Screen
{
    private Viewport viewport;
    private Stage stage;
    final Game game;

    public GameOverScreen(final Game game)
    {
        this.game = game;
        viewport = new FitViewport(LitLands.V_HEIGHT, LitLands.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((LitLands) game).batch);

        Gdx.input.setInputProcessor(stage);

            // ~ Create font for game over screen.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/old_font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!



        //countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font12, Color.FIREBRICK));
        // ~ Create font for game over screen.
        Label.LabelStyle font = new Label.LabelStyle(font12, Color.WHITE);

        Table table = new Table();
        table.center();

            // ~ Allow table to take up entire stage.
        table.setFillParent(true);

        Label gameOverLabel = new Label("PLAY   AGAIN", font);
        Label mainMenuLabel = new Label("MAIN   MENU", font);

        gameOverLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen((LitLands) game, "1_1.tmx"));
                dispose();
            }
        });


        mainMenuLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MenuScreen(game));
                dispose();
            }
        });


            // ~ Allow to expand the distance of the newly created row.
        table.add(gameOverLabel).expandX().expandY();
        table.row();
        table.add(mainMenuLabel).expandX().expandY().padTop(10f);
        table.setColor(Color.BLUE);
        stage.addActor(table);
    }

    public void setPlayScreen()
    {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
      //  if (Gdx.input.justTouched())
       // {
           // game.setScreen(new PlayScreen((LitLands) game));
           // game.setScreen(new MenuScreen((LitLands) game));
           // dispose();
       // }
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
