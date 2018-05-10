package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.FarmGame;

public class MainMenu implements Screen{
    private Viewport viewPort;
    private Stage mainStage;
    private Stage tutorialStage;
    private FarmGame game;
    private Skin skin;
    private Texture background;
    private Table table;

    public MainMenu(final FarmGame game) {
        this.game = game;
        viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        mainStage = new Stage(viewPort, game.batch);
        tutorialStage = new Stage(viewPort, game.batch);
        Gdx.input.setInputProcessor(mainStage);
        background = new Texture("background.jpg");
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        final TextButton startGame = new TextButton("New Game", skin);
        final TextButton tutorialButton = new TextButton("Tutorial", skin);
        final TextButton backButton = new TextButton("Back", skin);
        startGame.getLabel().setFontScale(1.5f);
        tutorialButton.getLabel().setFontScale(1.5f);
        backButton.getLabel().setFontScale(1.5f);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        font.font.getData().setScale(2);
        final Table table = new Table();
        mainStage.addActor(table);
        table.center();
        table.setFillParent(true);

        table.add(startGame).padBottom(50f).padTop(100f).width(250).height(75);
        table.row();
        table.add(tutorialButton).width(250).height(75);

        final Table tutorial = new Table();
        tutorialStage.addActor(tutorial);
        tutorial.center();
        tutorial.setFillParent(true);
        final Label playAgainLabel = new Label("" +
                "You are competing with an evil corporate company over your land!\n" +
                "Raise more money than them in 30 days or they will buy out your land!\n" +
                "\nHow to play:\n" +
                "-Move around using arrow keys or WASD\n" +
                "-Cycle crop seeds and tools using the mouse wheel or number keys\n" +
                "-When seeds are equiped, click on any grass to plant crops (must have seeds)\n"+
                "-Crops need to be watered everday to grow. If they go two days without water they will die\n" +
                "-Click on full grown crops to harvest for cash\n" +
                "-Click on crop crates to buy more seeds\n" +
                "-Sleep in your bed to advance a day " +
                "", font);

        tutorial.add(playAgainLabel).padBottom(50);
        tutorial.row();
        tutorial.add(backButton).width(250).height(75);
        tutorial.setVisible(false);
        tutorialButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
              table.setVisible(false);
              tutorial.setVisible(true);
              Gdx.input.setInputProcessor(tutorialStage);
                return true;
            }
        });
        backButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                table.setVisible(true);
                tutorial.setVisible(false);
                Gdx.input.setInputProcessor(mainStage);

                return true;
            }
        });
        startGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new PlayScreen(game));
                dispose();
                return true;
            }

        });



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 1280, 720);
        game.batch.end();
        mainStage.act(delta);
        tutorialStage.act(delta);
        tutorialStage.draw();
        mainStage.draw();
        viewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



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
        background.dispose();
        skin.dispose();
        mainStage.dispose();
        tutorialStage.dispose();
    }

}

