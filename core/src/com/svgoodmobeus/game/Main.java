package com.svgoodmobeus.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svgoodmobeus.game.AI.AICharacter;
import com.svgoodmobeus.game.Characters.Mage;
import com.svgoodmobeus.game.Characters.Rogue;
import com.svgoodmobeus.game.Characters.Warrior;

public class Main extends ApplicationAdapter {
	public BitmapFont debugFont;

	public Viewport viewport;
	public static OrthographicCamera camera;
	public static Texture priorityImage;
	public static float prior_x, prior_y;

	public static SpriteBatch batch;
    UI ui;

	public static Array<Character> characters;
    //static Array<AICharacter> aiCharacter;
	public static Array<Wall> walls;
	public static Array<DeadCharacters> deadCharacterses;
	float wait;

    Texture bg;
    //Stage
    Stage stage;
    Skin skin;

	@Override
	public void create() {
		debugFont = new BitmapFont(Gdx.files.internal("font48_white.fnt"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		camera.update();
        viewport = new ScreenViewport(camera);


		wait = 0;
		walls = new Array<Wall>();
        bg = new Texture("bg.jpg");

        characters = new Array<Character>();
		characters.add(new Mage(160,512));
        characters.add(new Rogue(544, 512));
        characters.add(new Warrior(512, 512));
        characters.add(new AICharacter(800, 512));
        characters.add(new AICharacter(800, 256));
        characters.add(new AICharacter(512, 256));
        characters.add(new AICharacter(480, 512));
        Controller.currentChar = characters.get(0);

        //aiCharacter = new Array<AICharacter>();
        //aiCharacter.add(new AICharacter(800, 512));

		deadCharacterses = new Array<DeadCharacters>();

        ui = new UI();
		batch = new SpriteBatch();
		LevelParser.parseFile(walls);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
        draw();
		for(int i = 0; i < characters.size; i++)
		    characters.get(i).update(batch);
		//levelEditor();
        Controller.events(batch);
        ui.draw(batch);
		priorityDraw();
		debug();
		batch.end();
	}

	public void debug(){
		debugFont.draw(batch, "" + Controller.getTouchX() + " " + Controller.getTouchY(), 50, 680 );
		debugFont.draw(batch, "" + Controller.getTouchX() + " " + Controller.getTouchY(), 50, 680 );
		debugFont.draw(batch, "" + camera.position.x + " " + camera.position.y, 50, 640 );
		System.out.println(camera.zoom);
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && camera.zoom < 1.5f) {
			camera.zoom += 0.05;
			camera.update();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)  && camera.zoom > 0.5f) {
			camera.zoom -= 0.05;
			camera.update();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.position.x -= 4;
			camera.update();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.position.x += 4;
			camera.update();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.position.y += 4;
			camera.update();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.position.y -= 4;
			camera.update();
		}
	}

	public void priorityDraw(){
		if (priorityImage != null){
			batch.draw(priorityImage, prior_x, prior_y);
		}
	}

	public void draw(){
        batch.draw(bg, 0, 0);
		for (int i = 0; i < deadCharacterses.size; i++){
			deadCharacterses.get(i).draw();
		}
		for(int i = 0; i < walls.size; i++)
			walls.get(i).update(batch);
	}

	public void levelEditor(){
		wait += Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.F5) && wait > 0.2f) {
            walls = new Array<Wall>();
            wait = 0;
        }
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && wait > 0.2f ) {
            LevelParser.saveLevel();
            wait = 0;
        }
		if (Gdx.input.isTouched() && wait > 0.2) {
			walls.add(new Wall(((int)(Gdx.input.getX()/32))*32, 720 - 32 - ((int)(Gdx.input.getY()/32))*32));
			wait = 0;
		}
	}

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
