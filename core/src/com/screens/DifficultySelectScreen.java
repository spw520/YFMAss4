package com.screens;

// LibGDX imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
// Class imports
import com.Kroy;
import com.misc.SFX;

import static com.misc.Constants.DEBUG_ENABLED;

/**
 * Select a difficulty level
 */
public class DifficultySelectScreen implements Screen {

    // A constant variable to store the game
    final Kroy game;

    // objects used for visuals
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;
    private final Viewport viewport;

    /**
     * The constructor for the difficulty select screen
     *
     * @param game The game object.
     */
    public DifficultySelectScreen(final Kroy game) {
        this.game = game;

        skin = game.getSkin();

        // Create new sprite batch

        // Create an orthographic camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		/* tell the SpriteBatch to render in the
		   coordinate system specified by the camera. */
        game.spriteBatch.setProjectionMatrix(camera.combined);

        // Create a viewport
        viewport = new ScreenViewport(camera);
        viewport.apply(true);

        // Set camera to centre of viewport
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        // Create a stage for buttons
        stage = new Stage(viewport, game.spriteBatch);
        stage.setDebugAll(DEBUG_ENABLED);

        SFX.sfx_soundtrack_1.setLooping(true);
        SFX.playMenuMusic();
    }

    /**
     * Render function to display all elements in the main menu.
     *
     * @param delta The delta time of the game, updated every second rather than frame.
     */
    @Override
    public void render(float delta) {
        // MUST BE FIRST: Clear the screen each frame to stop textures blurring
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the button stage
        stage.draw();
    }

    // Below are all required methods of the screen class
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }

    /**
     * Create the button stage.
     */
    @Override
    public void show() {
        // Allow stage to control screen inputs.
        Gdx.input.setInputProcessor(stage);

        // Create table to arrange buttons.
        Table buttonTable = new Table();
        buttonTable.center();

        Image bcg = new Image(new Texture(Gdx.files.internal("menu_bg_2.png")));
        Stack bcgstack = new Stack();
        bcgstack.setFillParent(true);
        bcgstack.add(bcg);
        bcgstack.add(buttonTable);

        // Create buttons
        Label heading = new Label("Select your difficulty", new Label.LabelStyle(game.coolFont, Color.WHITE));
        heading.setFontScale(2);
        Label subHeading = new Label("Select Easy if this is your first time playing, otherwise choose more of a challenge!", new Label.LabelStyle(game.coolFont, Color.WHITE));
        TextButton easyButton = new TextButton("Easy", skin);
        TextButton mediumButton = new TextButton("Medium", skin);
        TextButton hardButton = new TextButton("Hard", skin);
        TextButton returnButton = new TextButton("Back to menu", skin);

        // Add buttons to table and style them
        buttonTable.add(heading).padBottom(10);
        buttonTable.row();
        buttonTable.add(subHeading).padBottom(20);
        buttonTable.row();
        buttonTable.add(easyButton).padBottom(20).width(200).height(40);
        buttonTable.row();
        buttonTable.add(mediumButton).padBottom(20).width(200).height(40);
        buttonTable.row();
        buttonTable.add(hardButton).width(200).height(40);
        buttonTable.row();
        buttonTable.add(returnButton).width(200).height(30);

        // Add listeners
        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SFX.sfx_button_click.play();
                game.setScreen(new StoryScreen(game,1));
                dispose();
            }
        });

        mediumButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SFX.sfx_button_click.play();
                game.setScreen(new StoryScreen(game,2));
                dispose();
            }
        });

        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SFX.sfx_button_click.play();
                game.setScreen(new StoryScreen(game,3));
                dispose();
            }
        });

        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Add table to stage
        stage.addActor(bcgstack);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Used to pass the main menu screen into the controls screen
     *
     * @return  main menu screen
     */
    public Screen getThis() {
        return this;
    }
}