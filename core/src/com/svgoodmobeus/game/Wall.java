package com.svgoodmobeus.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Svgood on 16.06.2017.
 */
public class Wall {
    static Texture img, img1;
    public Rectangle rect, upper_rect;

    public Wall(int x, int y){
        img = new Texture("wall2.png");
        img1 = new Texture("test.png");
        rect = new Rectangle(x, y, img.getWidth(), img.getHeight());
        upper_rect = new Rectangle(x, y + 32, img.getWidth(), img.getHeight());
    }

    public void update(SpriteBatch batch){
        batch.draw(img, rect.x, rect.y);
        //batch.draw(img1, upper_rect.x, upper_rect.y);
    }
}
