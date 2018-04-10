package com.svgoodmobeus.game.Spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Controller;
import com.svgoodmobeus.game.Debuff;
import com.svgoodmobeus.game.Main;

/**
 * Created by svgood on 27.07.17.
 */
public class MageSpells extends Spells {

    Texture img, img_fireball, img_lasor, img_kpunch;
    Texture img_lighting_preview, img_lighting;

    public MageSpells(Character caster){
        super(caster);
        img = new Texture("test.png");
        img_fireball = new Texture("fireball.png");
        img_lasor = new Texture("lasor.png");
        img_lighting_preview = new Texture("lighting_preview.png");
        img_lighting = new Texture("lighting.png");
        img_kpunch = new Texture("kpunch.png");
    }

    //Spells total
    /*
    1 blink +
    2 fireball +
    3 telekenesis +
    4 lasor +
    5 heal +
    6 swap +
    7 lighting strike +
    8 create magic barier
    9 kinetic punch +
    10 meteor
    11 Clone
    * */

    public String getSpellName(int id){
        if (id == 1) {
            name = "blink";
        }
        if (id == 2) {
            name = "fireball";
        }
        if (id == 3) {
            name = "lighting strike";
        }
        if (id == 4) {
            name = "kinetic punch";
        }
        if (id == 5) {
            name = "lasor";
        }
        if (id == 6) {
            name = "heal";
        }
        if (id == 7) {
            name = "swap";
        }
        if (id == 8) {
            name = "telekenesis";
        }
        if (id == 9) {

        }
        if (id == 10) {

        }
        return name;
    }

    public void useSpell(int id, int slot_num){
        if (id == 1){
            blink();
        }
        if (id == 2){
            fireball();
        }
        if (id == 3){
            lightingStrike();
        }
        if (id == 4){
            kineticPunch();
        }
        if (id == 5){
            lasor();
        }
        if (id == 6){
            heal();
        }
        if (id == 7){
            swap();
        }
        if (id == 8){
            telekenesis();
        }
        if (id == 9){
            lightingStrike();
        }
        if (id == 10){
            telekenesis();
        }
        this.slot_num = slot_num;
    }

    public void spellDraw(int id){
        if (id == 1){
            blinkDraw();
        }
        if (id == 2){
            fireballDraw();
        }
        if (id == 3){
            lightingStrikeDraw();
        }
        if (id == 4){
            kineticPunchDraw();
        }
        if (id == 5){
            lasorDraw();
        }
        if (id == 6){
            healDraw();
        }
        if (id == 7){
            swapDraw();
        }
        if (id == 8){
            telekenesisDraw();
        }
        if (id == 9){
            lightingStrikeDraw();
        }
        if (id == 10){
            telekenesisDraw();
        }
    }

    //self target
    public void blink(){
        caster.spell_cd = 0;
        for (int i = 0; i < Main.walls.size; i++)
            if (Main.walls.get(i).upper_rect.contains(Controller.getTouchX(), Controller.getTouchY())) {
                caster.rect.x = Main.walls.get(i).upper_rect.x;
                caster.rect.y = Main.walls.get(i).upper_rect.y;
                caster.dest_x = caster.rect.x;
                caster.dest_y = caster.rect.y;
                caster.action_points_current -= 1;
                caster.deactivate_slots(slot_num);
                break;
            }
    }
    public void blinkDraw(){
        Main.batch.draw(caster.img_silouet, ((int)(Controller.getTouchX()/32))*32, -16 + ((int)(Controller.getTouchY()/32))*32);
    }

    //attacking lasorlike spell
    public void lasor(){
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
                    Main.characters.get(k).hp_current -= 20;
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
    }
    public void lasorDraw(){
        if (startDrawing){
            spell_timer += Gdx.graphics.getDeltaTime();
            for(int i = 0; i <= length; i++)
                Main.batch.draw(img_lasor, caster.rect.x + (32 + i*32)*rotation_koef, caster.rect.y);
            if (spell_timer > end_timer){
                startDrawing = false;
                caster.spellcasting = false;
                caster.deactivate_slots(slot_num);
            }
        }
    }

    //Target spell
    public void telekenesis(){
        if (caster.target != null && caster.spell_cd > 0.5f){
            if (Controller.getTouchX() < caster.target.rect.x){
                caster.target.free_move(-1);
                caster.action_points_current -= 1;
                caster.target = null;
                caster.spell_cd = 0;
                caster.deactivate_slots(slot_num);
                return;
            }
            if (Controller.getTouchX() > caster.target.rect.x){
                caster.target.free_move(1);
                caster.action_points_current -= 1;
                caster.target = null;
                caster.spell_cd = 0;
                caster.deactivate_slots(slot_num);
            }
        }
        for (int i = 0; i < Main.characters.size; i++)
            if(Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY())){
                caster.target = Main.characters.get(i);
                caster.spell_cd = 0;
            }
    }
    public void telekenesisDraw() {

    }


    public void fireball(){
        if (Controller.getTouchX() > caster.rect.x){
            caster.rotate(1);
            rotation_koef = 1;
        }
        else {
            caster.rotate(-1);
            rotation_koef = -1;
        }
        caster.spell_cd = 0;
        checkRect.x = caster.rect.x;
        checkRect.y = caster.rect.y + 16;
        caster.spellcasting = true;
        startDrawing = true;
    }

    public void fireballDraw(){
        if (startDrawing){
            Main.batch.draw(img_fireball, checkRect.x+(16*rotation_koef), checkRect.y);
            checkRect.x += 7*rotation_koef;
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
                    Main.characters.get(i).hp_current -= 15;
                    Main.characters.get(i).debuffs.add(new Debuff("Burns", 4, Main.characters.get(i), 5));
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

    public void heal(){
        for (int i = 0; i < Main.characters.size; i++)
            if(Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY())){
                caster.spell_cd = 0;
                Main.characters.get(i).hp_current += 50;
                caster.action_points_current -= 1;
                caster.deactivate_slots(slot_num);
                break;
            }
    }

    public void healDraw() {

    }

    public void swap(){
        float tempx, tempy;
        for (int i = 0; i < Main.characters.size; i++)
            if(Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY())){
                caster.spell_cd = 0;
                tempx = caster.rect.x;
                tempy = caster.rect.y;
                caster.rect.x = Main.characters.get(i).rect.x;
                caster.rect.y = Main.characters.get(i).rect.y;
                Main.characters.get(i).rect.x = tempx;
                Main.characters.get(i).rect.y = tempy;

                tempx = caster.dest_x;
                tempy = caster.dest_y;
                caster.dest_x =  Main.characters.get(i).dest_x;
                caster.dest_y =  Main.characters.get(i).dest_y;
                Main.characters.get(i).dest_x = tempx;
                Main.characters.get(i).dest_y = tempy;
                caster.action_points_current -= 1;
                caster.deactivate_slots(slot_num);
                break;
            }
    }

    public void swapDraw() {

    }

    public void lightingStrike(){
        checkRect.x = ((int)(Controller.getTouchX()/32))*32;
        checkRect.y = 720;

        caster.spell_cd = 0;
        caster.spellcasting = true;

        spell_timer = 0;
        end_timer = 0.8f;

        length = 0;
        for (int k = 0; k < 20; k++) {
            checkRect.y = 720 - 32*k;
            for (int i = 0; i < Main.characters.size; i++){
                if (Main.characters.get(i).rect.overlaps(checkRect)){
                    caster.action_points_current -= 1;
                    Main.characters.get(i).hp_current -= 40;
                    length = k+2;
                    break;
                }
            }

            for (int i = 0; i < Main.walls.size; i++) {
                if (Main.walls.get(i).rect.overlaps(checkRect)) {
                    caster.action_points_current -= 1;
                    length = k;
                    break;
                }
            }
            if (length != 0) break;
        }
        startDrawing = true;

    }
    public void lightingStrikeDraw(){
        if (!startDrawing){
            checkRect.x = ((int)(Controller.getTouchX()/32))*32;
            length = 0;
            for (int k = 0; k < 20; k++) {
                checkRect.y = 720 - 32*k;
                for (int i = 0; i < Main.walls.size; i++) {
                    if (Main.walls.get(i).rect.overlaps(checkRect)) {
                        length = k;
                        break;
                    }
                }
                if (length != 0) break;
            }
            for (int i = 0; i < length; i++){
                Main.batch.draw(img_lighting_preview, checkRect.x, 720 - 32*i);
            }
        }
        if (startDrawing){
            spell_timer += Gdx.graphics.getDeltaTime();
            if (spell_timer > end_timer){
                startDrawing = false;
                caster.spellcasting = false;
                caster.deactivate_slots(slot_num);
            }
            for (int i = 0; i < length; i++){
                Main.batch.draw(img_lighting, checkRect.x, 720 - 32*i);
            }
        }

    }


    public void kineticPunch(){
        if (Controller.getTouchX() > caster.rect.x){
            caster.rotate(1);
            rotation_koef = 1;
        }
        else {
            caster.rotate(-1);
            rotation_koef = -1;
        }
        caster.spell_cd = 0;
        checkRect.x = caster.rect.x;
        checkRect.y = caster.rect.y + 16;
        caster.spellcasting = true;
        startDrawing = true;
    }

    public void kineticPunchDraw(){
        if (startDrawing){
            Main.batch.draw(img_kpunch, checkRect.x+(16*rotation_koef), checkRect.y);
            checkRect.x += 8*rotation_koef;
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
                    Main.characters.get(i).hp_current -= 10;
                    Main.characters.get(i).punch_trigger(3, rotation_koef);
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
}
