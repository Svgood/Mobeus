package com.svgoodmobeus.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Svgood on 29.06.2017.
 */
public class DeadCharacters {

    Texture img;
    float x, y;

    public DeadCharacters(float x, float y){
        img = new Texture("skeleton_corpse.png");
        this.x = x;
        this.y = y;
    }

    public void draw(){
        Main.batch.draw(img, x, y);
    }
}
