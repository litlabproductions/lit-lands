package com.litlabproductions.litlands.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.litlabproductions.litlands.LitLands;

/**
 * Created by David on 9/23/2017.
 */

public class Hud implements Disposable
{
    public Stage stage;

        // ~ Using new viewport and camera for HUD, so when game moves this viewport stays on HUD.
    private Viewport viewport;

    private Integer worldTimer;
            private float timeCount;
    private static Integer score;

    private Label countDownLabel;
    private static Label scoreLabel;
    private Label  timeLabel;
    private Label  levelLabel;
    private Label litLabel;
    private Label landsLabel;
    private Label fpsLogoLabel;

    private Label fpsLabel;

    public Hud(SpriteBatch sb)
    {
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(LitLands.V_WIDTH, LitLands.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/old_font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        fpsLogoLabel = new Label("FPS", new Label.LabelStyle(font12, Color.FOREST));
        fpsLabel = new Label(String.format("%03d", Gdx.graphics.getFramesPerSecond()), new Label.LabelStyle(font12, Color.FIREBRICK));
        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font12, Color.FIREBRICK));
        scoreLabel = new Label("", new Label.LabelStyle(font12, Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(font12, Color.FOREST));
        levelLabel = new Label("v.0.1.4", new Label.LabelStyle(font12, Color.FIREBRICK));
        litLabel = new Label("", new Label.LabelStyle(font12, Color.BLACK));
        landsLabel = new Label("", new Label.LabelStyle(font12, Color.BLACK));



//        fpsLogoLabel.setFontScale(1.2f, 1);
        //levelLabel.setFontScale(1.1f,.8f);

        //countDownLabel.setFontScale(.7f);
        fpsLogoLabel.setFontScale(1.1f);
        timeLabel.setFontScale(1.1f);


        //scoreLabel.setFontScale(.5f);
        //levelLabel.setFontScale(.7f);



            // ~ If we place 3 items inside the 'expand x', the space will be shared equally by the 3 items.

            // ~ Row 1:
        table.add(fpsLogoLabel).expandX().padTop(10);
        table.add(levelLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();

            // ~ Row 2:
        table.add(fpsLabel).expandX().padTop(10).padTop(-10);
        table.add(landsLabel).expandX().padTop(-20);;
        table.add(countDownLabel).expandX().padLeft(3).padTop(-10);


            // ~ Add table to stage.


        stage.addActor(table);

    }

    public void update(float dt)
    {
        timeCount += dt;
        if(timeCount >= 1)
        {
            worldTimer--;
            countDownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
            fpsLabel.setText(String.format("%03d", Gdx.graphics.getFramesPerSecond()));
        }
    }

        // ~ Static so we can access without obj.
    public static void addScore(int value)
    {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
