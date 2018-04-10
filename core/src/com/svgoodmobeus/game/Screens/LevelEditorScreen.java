package com.svgoodmobeus.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Svgood on 14.07.2017.
 */
public class LevelEditorScreen implements Screen {

    //final Drop game;

    OrthographicCamera camera;

    public LevelEditorScreen() {
        //this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
    }

    public void resume(){

    }

    public void pause(){

    }

    public void show(){

    }

    public void render(float Delta){

    }

    public void dispose(){

    }

    public void resize(int w, int h){

    }

    public void hide(){

    }

}
