package com.litlabproductions.litlands.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.litlabproductions.litlands.LitLands;

/**
 * Created by David on 11/2/2017.
 */



public class LoadingScreen implements Screen
{
    private Viewport viewport;
    private Stage stage;
    final Game game;

    private String levelName;

    public LoadingScreen(final Game game, String levelName)
    {
        this.game = game;
        viewport = new FitViewport(LitLands.V_HEIGHT, LitLands.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((LitLands) game).batch);

        this.levelName = levelName;

        game.setScreen(new PlayScreen((LitLands) game, levelName));

        dispose();
       // loadLevel();
    }

    public void loadLevel()
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
       // Gdx.gl.glClearColor(0, 0, 0, 1);
      //  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       // stage.draw();
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
