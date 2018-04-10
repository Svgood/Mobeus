package com.svgoodmobeus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Svgood on 16.06.2017.
 */
public class UI {
    Texture img;
    Texture port_1, port_2, port_3;
    Texture spellbar, spellbar1, spellbar2, spellbar3, spellbar4;
    BitmapFont font48_white;
    BitmapFont font48_red;
    BitmapFont font48_blue;
    static Rectangle rect_portrait1, rect_portrait2, rect_portrait3, rect_spell1, rect_spell2, rect_spell3, rect_spell4,
    rect_endturn;
    static float start_x, start_y;

    public UI(){
        start_x = Main.camera.position.x;
        start_y = Main.camera.position.y;
        font48_white = new BitmapFont(Gdx.files.internal("font48_white.fnt"));
        font48_red = new BitmapFont(Gdx.files.internal("font48_red.fnt"));
        font48_blue = new BitmapFont(Gdx.files.internal("font48_blue.fnt"));
        port_1 = new Texture("port_1.png");
        port_2 = new Texture("port_2.png");
        port_3 = new Texture("port_3.png");
        img = new Texture("ui.png");
        spellbar = new Texture("spellbar.png");
        spellbar1 = new Texture("spellbar1.png");
        spellbar2 = new Texture("spellbar2.png");
        spellbar3 = new Texture("spellbar3.png");
        spellbar4 = new Texture("spellbar4.png");
        rect_portrait1 = new Rectangle(start_x + 0, start_y + 128, 64, 64);
        rect_portrait2 = new Rectangle(start_x +64, start_y +128, 64, 64);
        rect_portrait3 = new Rectangle(start_x +128, start_y +128, 64, 64);
        rect_spell1 = new Rectangle(start_x +596,start_y + 26, 76, 72);
        rect_spell2 = new Rectangle(start_x +685,start_y + 26, 76, 72);
        rect_spell3 = new Rectangle(start_x +777,start_y + 26, 76, 72);
        rect_spell4 = new Rectangle(start_x +866,start_y + 26, 76, 72);
        rect_endturn = new Rectangle(start_x +1134,start_y + 5, 138, 138);
    }

    public void update(){

    }

    public void draw(SpriteBatch batch){
        start_x = Main.camera.position.x - 640;
        start_y = Main.camera.position.y - 360;
        batch.draw(img, start_x, start_y);
        rect_portrait1.x = start_x + 0;
        rect_portrait2.x = start_x + 64;
        rect_portrait3.x = start_x + 128;
        rect_portrait1.y = start_y + 128;
        rect_portrait2.y = start_y + 128;
        rect_portrait3.y = start_y + 128;
        rect_spell1.x = start_x +596;
        rect_spell2.x = start_x +685;
        rect_spell3.x = start_x +777;
        rect_spell4.x = start_x +866;
        rect_endturn.x = start_x + 1134;
        rect_spell1.y = start_y + 26;
        rect_spell2.y = start_y + 26;
        rect_spell3.y = start_y + 26;
        rect_spell4.y = start_y + 26;
        rect_endturn.y = start_y + 5;


        font48_blue.draw(batch, ""+(int)Controller.currentChar.energy_current, start_x + 210, start_y + 50);
        font48_red.draw(batch, ""+(int)Controller.currentChar.hp_current, start_x + 290, start_y + 50);
        font48_white.draw(batch, ""+Controller.currentChar.action_points_current, start_x + 395, start_y +50);
        font48_white.draw(batch, ""+Controller.currentChar.move_points_current, start_x + 475, start_y + 50);

        if (Controller.currentCharacter == 1){
            batch.draw(port_1,start_x,start_y);
        }
        if (Controller.currentCharacter == 2){
            batch.draw(port_2,start_x,start_y);
        }
        if (Controller.currentCharacter == 3){
            batch.draw(port_3,start_x,start_y);
        }


        //spellbars
        if (Controller.currentChar.slot1_active){
            batch.draw(spellbar1,start_x,start_y);
        }
        else if (Controller.currentChar.slot2_active){
            batch.draw(spellbar2, start_x,start_y);
        }
        else if (Controller.currentChar.slot3_active){
            batch.draw(spellbar3, start_x,start_y);
        }
        else if (Controller.currentChar.slot4_active){
            batch.draw(spellbar4, start_x,start_y);
        }
        else {
            batch.draw(spellbar, start_x,start_y);
        }
    }
}
