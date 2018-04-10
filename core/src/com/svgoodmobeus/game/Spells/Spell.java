package com.svgoodmobeus.game.Spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.svgoodmobeus.game.*;
import com.svgoodmobeus.game.Character;

/**
 * Created by Svgood on 28.09.2017.
 */
public class Spell {
    public Animations animation;
    public Rectangle checkRect;
    public Texture img, icon, precast_img;
    public Sprite sprite;
    public Character caster, touched, target;
    public Boolean startDrawing;

    public String name;
    public String type;
    public String description;

    public float spell_timer;
    public float end_timer;

    public int length;
    public int rotation_koef;
    public int charges;
    public int slot_num;

    public Spell(Character caster){
        this.caster = caster;
        checkRect = new Rectangle(0, 0, 32, 32);
        startDrawing = false;
        spell_timer = 0;
        end_timer = 0;
        length = 0;
        charges = 0;
        sprite = new Sprite(new Texture("fireball.png"));

    }

    public void precastDraw(){

    }

    public void draw(){

    }

    public void use(){

    }

    public void update(){

    }

    public void clickSpell(){
        for (int i = 0; i < Main.characters.size; i++)
            if(Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY())){
                caster.spell_cd = 0;
                target = Main.characters.get(i);
                action();
                startDrawing = true;
                end_timer = 1.5f;
                caster.action_points_current -= 1;
                spell_timer = 0;
                break;
            }
    }

    public void clickDraw(Texture img_custom){
        if (startDrawing){
            spell_timer += Gdx.graphics.getDeltaTime();
            //Main.batch.draw(img_custom, target.rect.x, target.rect.y + 16);
            Main.priorityImage = img_custom;
            Main.prior_x = target.rect.x;
            Main.prior_y = target.rect.y + 16;

            if (spell_timer > end_timer){
                Main.priorityImage = null;
                startDrawing = false;
                caster.spellcasting = false;
                caster.deactivate_slots(slot_num);
            }
        }

    }

    public void raySpell(float value){
        if (Controller.getTouchX() > caster.rect.x){
            caster.rotate(1);
            rotation_koef = 1;
        }
        else {
            caster.rotate(-1);
            rotation_koef = -1;
        }
        caster.spell_cd = 0;
        length = 0;
        checkRect.x = caster.rect.x;
        checkRect.y = caster.rect.y + 16;
        caster.spellcasting = true;
        spell_timer = 0;
        end_timer = 1.5f;
        for (int i = 0; i < 50; i++){
            checkRect.x += 32*rotation_koef;
            for (int k = 0; k < Main.characters.size; k++){
                if (Main.characters.get(k).rect.overlaps(checkRect)){
                    Main.characters.get(k).hp_current -= value;
                    action();
                }
            }

            for (int k = 0; k < Main.walls.size; k++) {
                if (Main.walls.get(k).rect.overlaps(checkRect)) {
                    length = i-1;
                    startDrawing = true;
                    caster.action_points_current -= 1;
                    break;
                }
            }
            if (length != 0) break;
        }
        startDrawing = true;
    }

    public void rayDraw(Texture img_custom){
        if (startDrawing){
            spell_timer += Gdx.graphics.getDeltaTime();
            if (length == 0)
                length = 50;
            for(int i = 0; i <= length; i++)
                Main.batch.draw(img_custom, caster.rect.x + (32 + i*32)*rotation_koef, caster.rect.y);
            if (spell_timer > end_timer){
                startDrawing = false;
                caster.spellcasting = false;
                caster.deactivate_slots(slot_num);
            }
        }
    }

    public void projectileSpell(){
        if (Controller.getTouchX() > caster.rect.x){
            caster.rotate(1);
            rotation_koef = 1;
            sprite.setFlip(false,false);
        }
        else {
            caster.rotate(-1);
            rotation_koef = -1;
            sprite.setFlip(true, false);
        }
        caster.spell_cd = 0;
        checkRect.x = caster.rect.x;
        checkRect.y = caster.rect.y + 16;
        caster.spellcasting = true;
        startDrawing = true;
    }

    public void projectileDraw(Texture img_custom, float value){
        if (startDrawing){
            sprite.setTexture(img_custom);
            sprite.setPosition(checkRect.x+(16*rotation_koef), checkRect.y);
            //Main.batch.draw(img_custom,checkRect.x+(16*rotation_koef) ,checkRect.y);
            sprite.draw(Main.batch);
            checkRect.x += 6*rotation_koef;
            for (int i = 0; i < Main.walls.size; i++)
                if (Main.walls.get(i).rect.overlaps(checkRect)){
                    startDrawing = false;
                    caster.spellcasting = false;
                    checkRect.x = 0;
                    checkRect.y = 0;
                    caster.action_points_current -= 1;
                    caster.deactivate_slots(slot_num);
                }
            for (int i = 0; i < Main.characters.size; i++)
                if (Main.characters.get(i).rect.overlaps(checkRect) && Main.characters.get(i) != caster){
                    Main.characters.get(i).hp_current -= value;
                    target = Main.characters.get(i);
                    action();
                    startDrawing = false;
                    caster.spellcasting = false;
                    checkRect.x = 0;
                    checkRect.y = 0;
                    caster.action_points_current -= 1;
                    caster.deactivate_slots(slot_num);
                }
            if (checkRect.x > Gdx.graphics.getWidth() || checkRect.x < -32){
                startDrawing = false;
                caster.spellcasting = false;
                checkRect.x = 0;
                checkRect.y = 0;
                caster.action_points_current -= 1;
                caster.deactivate_slots(slot_num);
            }

        }
    }

    public void aoeSpell(){

    }

    public void aoeDraw(){

    }

    public void customSpell(){

    }

    public void customDraw(){

    }

    public void action(){

    }


}
