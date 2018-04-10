package com.svgoodmobeus.game.Spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.*;
import com.svgoodmobeus.game.Character;

/**
 * Created by Svgood on 06.07.2017.
 */
public class RogueSpells extends Spells {

    /*
    1 - piercing cut +
    2 - arrow shot +
    3 - leap away +
    4 - poison weapon +
    5 - shadow leap +
    6 - cummulative arrow +
    7 - explosive shot
    8 - twin strike
    9 - bleeding cut +
    10 - backstab +
    11 - stunning hit
     */

    Texture img_arrow;


    public RogueSpells(Character caster) {
        super(caster);
        img_arrow = new Texture("arrow.png");
    }

    public String getSpellName(int id){
        if (id == 1) {
            name = "Arrow Shot";
        }
        if (id == 2) {
            name = "Backstab";
        }
        if (id == 3) {
            name = "leap away";
        }
        if (id == 4) {
            name = "cummulative Arrow";
        }
        if (id == 5) {
            name = "Piercing cut";
        }
        if (id == 6) {
            name = "Shadow Leap";
        }
        if (id == 7) {
            name = "Poison Weapon";
        }
        if (id == 8) {
            name = "Bleeding Cut";
        }
        if (id == 9) {

        }
        if (id == 10) {

        }
        return name;
    }

    public void useSpell(int id, int slot_num) {
        if (id == 1) {
            arrowShot();
        }
        if (id == 2) {
            backstab();
        }
        if (id == 3) {
            leapAway();
        }
        if (id == 4) {
            cummulativeArrow();
        }
        if (id == 5) {
            piercingCut();
        }
        if (id == 6) {
            shadowleap();
        }
        if (id == 7) {
            poisonWeapon();
        }
        if (id == 8) {
            bleedingCut();
        }
        if (id == 9) {

        }
        if (id == 10) {

        }
        this.slot_num = slot_num;
    }

    public void spellDraw(int id) {
        if (id == 1) {
            arrowShotDraw();
        }
        if (id == 2) {
            backstabDraw();
        }
        if (id == 3) {
            leapAwayDraw();
        }
        if (id == 4) {
            cummulativeArrowDraw();
        }
        if (id == 5) {
            piercingCutDraw();
        }
        if (id == 6) {
            shadowleapDraw();
        }
        if (id == 7) {
            poisonWeaponDraw();
        }
        if (id == 8) {
            bleedingCutDraw();
        }
        if (id == 9) {

        }
        if (id == 10) {

        }
    }


    public void arrowShot() {
        if (Controller.getTouchX() > caster.rect.x) {
            caster.rotate(1);
            rotation_koef = 1;
        } else {
            caster.rotate(-1);
            rotation_koef = -1;
        }
        caster.spell_cd = 0;
        checkRect.x = caster.rect.x;
        checkRect.y = caster.rect.y + 16;
        caster.spellcasting = true;
        startDrawing = true;
    }

    public void arrowShotDraw() {
        if (startDrawing) {
            Main.batch.draw(img_arrow, checkRect.x + (16 * rotation_koef), checkRect.y);
            checkRect.x += 7 * rotation_koef;
            for (int i = 0; i < Main.walls.size; i++)
                if (Main.walls.get(i).rect.overlaps(checkRect)) {
                    startDrawing = false;
                    caster.spellcasting = false;
                    checkRect.x = 0;
                    checkRect.y = 0;
                    caster.action_points_current -= 1;
                    caster.deactivate_slots(slot_num);
                }
            for (int i = 0; i < Main.characters.size; i++)
                if (Main.characters.get(i).rect.overlaps(checkRect) && Main.characters.get(i) != caster) {
                    Main.characters.get(i).hp_current -= 25;
                    startDrawing = false;
                    caster.spellcasting = false;
                    checkRect.x = 0;
                    checkRect.y = 0;
                    caster.action_points_current -= 1;
                    caster.deactivate_slots(slot_num);
                }
            if (checkRect.x > Gdx.graphics.getWidth() || checkRect.x < -32) {
                startDrawing = false;
                caster.spellcasting = false;
                checkRect.x = 0;
                checkRect.y = 0;
                caster.action_points_current -= 1;
                caster.deactivate_slots(slot_num);
            }

        }

    }

    public void piercingCut(){
        for (int i = 0; i < Main.characters.size; i++)
            if(Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY()) && Functions.checkDistance(48, caster, Main.characters.get(i))
                    && Main.characters.get(i) != caster){
                caster.spell_cd = 0;
                Main.characters.get(i).hp_current -= 35;
                caster.action_points_current -= 1;
                caster.deactivate_slots(slot_num);
                break;
            }
    }

    public void piercingCutDraw() {

    }

    public void leapAway(){
        for (int i = 0; i < Main.characters.size; i++)
            if(Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY()) && Functions.checkDistance(48, caster, Main.characters.get(i))
                    && Main.characters.get(i) != caster){
                    if (Main.characters.get(i).rect.x > caster.rect.x){
                        caster.spell_cd = 0;
                        caster.punch_trigger(4, -1);
                        caster.rotate(1);
                        caster.action_points_current -= 1;
                        caster.deactivate_slots(slot_num);
                        break;
                    }
                    else {
                        caster.spell_cd = 0;
                        caster.punch_trigger(4, 1);
                        caster.rotate(-1);
                        caster.action_points_current -= 1;
                        caster.deactivate_slots(slot_num);
                        break;
                    }

            }
    }

    public void leapAwayDraw() {

    }

    public void cummulativeArrow() {
        if (Controller.getTouchX() > caster.rect.x) {
            caster.rotate(1);
            rotation_koef = 1;
        } else {
            caster.rotate(-1);
            rotation_koef = -1;
        }
        charges = 2;
        caster.spell_cd = 0;
        checkRect.x = caster.rect.x;
        checkRect.y = caster.rect.y + 16;
        caster.spellcasting = true;
        startDrawing = true;
        touched = null;
    }

    public void cummulativeArrowDraw() {
        if (startDrawing) {
            Main.batch.draw(img_arrow, checkRect.x + (16 * rotation_koef), checkRect.y);
            checkRect.x += 7 * rotation_koef;
            for (int i = 0; i < Main.walls.size; i++)
                if (Main.walls.get(i).rect.overlaps(checkRect)) {
                    startDrawing = false;
                    caster.spellcasting = false;
                    checkRect.x = 0;
                    checkRect.y = 0;
                    caster.action_points_current -= 1;
                    caster.deactivate_slots(slot_num);
                }
            for (int i = 0; i < Main.characters.size; i++)
                if (Main.characters.get(i).rect.overlaps(checkRect) && Main.characters.get(i) != caster && Main.characters.get(i) != touched) {
                    Main.characters.get(i).hp_current -= 50/charges;
                    touched = Main.characters.get(i);
                    charges -= 1;
                    if (charges == 0) {
                        startDrawing = false;
                        caster.spellcasting = false;
                        checkRect.x = 0;
                        checkRect.y = 0;
                        caster.action_points_current -= 1;
                        caster.deactivate_slots(slot_num);
                    }
                }
            if (checkRect.x > Gdx.graphics.getWidth() || checkRect.x < -32) {
                startDrawing = false;
                caster.spellcasting = false;
                checkRect.x = 0;
                checkRect.y = 0;
                caster.action_points_current -= 1;
                caster.deactivate_slots(slot_num);
            }

        }
    }

    public void backstab(){
        for (int i = 0; i < Main.characters.size; i++)
            if(Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY()) && Functions.checkDistance(48, caster, Main.characters.get(i))
                    && Main.characters.get(i) != caster){
                if ((Main.characters.get(i).rect.x > caster.rect.x && Main.characters.get(i).facing_direction == 1) ||
                    (Main.characters.get(i).rect.x < caster.rect.x && Main.characters.get(i).facing_direction == -1)){
                    caster.spell_cd = 0;
                    Main.characters.get(i).hp_current -= 45;
                    caster.action_points_current -= 1;
                    caster.deactivate_slots(slot_num);
                    break;
                }
            }
    }

    public void backstabDraw() {

    }

    public void shadowleap(){
        for (int i = 0; i < Main.characters.size; i++)
            if(Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY()) && Functions.checkDistance(480, caster, Main.characters.get(i))
                    && Main.characters.get(i) != caster){
                    float previous_posx = caster.rect.x;
                    float previous_posy = caster.rect.y;
                    caster.teleport(Main.characters.get(i).rect.x + 32*Main.characters.get(i).facing_direction*(-1), Main.characters.get(i).rect.y);
                    for (int k = 0; k < Main.walls.size; k++){
                        if (Main.walls.get(k).rect.overlaps(caster.rect)){
                            caster.teleport(previous_posx, previous_posy);
                            return;
                        }
                    }
                    caster.rotate(Main.characters.get(i).facing_direction);
                    caster.spell_cd = 0;
                    Main.characters.get(i).hp_current -= 35;
                    caster.action_points_current -= 1;
                    caster.deactivate_slots(slot_num);
                    break;
            }
    }

    public void shadowleapDraw() {

    }

    public void poisonWeapon(){
        caster.buffs.add(new Buff("BonusDamage", 5, caster, 8));
        caster.action_points_current -= 1;
        caster.deactivate_slots(slot_num);
    }

    public void poisonWeaponDraw(){

    }

    public void bleedingCut(){
        for (int i = 0; i < Main.characters.size; i++)
            if(Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY()) && Functions.checkDistance(48, caster, Main.characters.get(i))
                    && Main.characters.get(i) != caster){
                caster.spell_cd = 0;
                Main.characters.get(i).hp_current -= 20;
                Main.characters.get(i).debuffs.add(new Debuff("Bleeding", 5, Main.characters.get(i), 3));
                caster.action_points_current -= 1;
                caster.deactivate_slots(slot_num);
                break;
            }
    }

    public void bleedingCutDraw() {

    }
}