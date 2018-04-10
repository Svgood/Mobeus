package com.svgoodmobeus.game.Spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.svgoodmobeus.game.*;
import com.svgoodmobeus.game.Character;

/**
 * Created by svgood on 28.07.17.
 */
public class PriestSpells extends Spells {

    /*
    1 - purify +
    2 - healing touch +
    3 - HpBuff +
    4 - MagicResisBuff +
    5 - HealingDamagingRay +
    6 - Silence
    7 - Decrease Magic Resistance
    8 - Chain healing
    9 -
     */
    Texture img_purifyRay;

    public PriestSpells(Character caster){
        super(caster);
        img_purifyRay = new Texture("");

    }

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

    public void useSpell(int id, int slot_num) {
        if (id == 1) {

        }
        if (id == 2) {

        }
        if (id == 3) {

        }
        if (id == 4) {

        }
        if (id == 5) {

        }
        if (id == 6) {

        }
        if (id == 7) {

        }
        if (id == 8) {

        }
        if (id == 9) {

        }
        if (id == 10) {

        }
        this.slot_num = slot_num;
    }

    public void spellDraw(int id) {
        if (id == 1) {

        }
        if (id == 2) {

        }
        if (id == 3) {

        }
        if (id == 4) {

        }
        if (id == 5) {

        }
        if (id == 6) {

        }
        if (id == 7) {

        }
        if (id == 8) {

        }
        if (id == 9) {

        }
        if (id == 10) {

        }
    }

    public void purify(){
        if(Functions.checkDistance(256, caster, Controller.mouse_target) && Controller.mouse_target != null){
            caster.face_target(Controller.mouse_target);
            caster.spell_cd = 0;
            Controller.mouse_target.debuffs = new Array<Debuff>();
            caster.action_points_current -= 1;
            caster.deactivate_slots(slot_num);
        }
    }

    public void healingTouch(){
        if(Functions.checkDistance(48, caster, Controller.mouse_target)){
            caster.face_target(Controller.mouse_target);
            caster.spell_cd = 0;
            Controller.mouse_target.hp_current += 40;
            caster.action_points_current -= 1;
            caster.deactivate_slots(slot_num);
        }
    }

    public void blessing(){
        if(Functions.checkDistance(256, caster, Controller.mouse_target) && Controller.mouse_target != null){
            caster.face_target(Controller.mouse_target);
            caster.spell_cd = 0;
            Controller.mouse_target.buffs.add(new Buff("AddHealth", 5, Controller.mouse_target, 35));
            caster.action_points_current -= 1;
            caster.deactivate_slots(slot_num);
        }
    }

    public void MagicResist(){
        if(Functions.checkDistance(256, caster, Controller.mouse_target) && Controller.mouse_target != null){
            caster.face_target(Controller.mouse_target);
            caster.spell_cd = 0;
            Controller.mouse_target.buffs.add(new Buff("AddMagicResist", 5, Controller.mouse_target, 30));
            caster.action_points_current -= 1;
            caster.deactivate_slots(slot_num);
        }
    }

    public void purifyingRay() {
        if (Controller.getTouchX() > caster.rect.x) {
            caster.rotate(1);
            rotation_koef = 1;
        } else {
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
        for (int i = 0; i < 50; i++) {
            checkRect.x += 32 * rotation_koef;
            if (Controller.mouse_target.rect.overlaps(checkRect)) {
                if (Controller.mouse_target.type.equals(caster.type))
                    Controller.mouse_target.hp_current += 20;
                else
                    Controller.mouse_target.hp_current -= 20;
            }

            for (int k = 0; k < Main.walls.size; k++) {
                if (Main.walls.get(k).rect.overlaps(checkRect)) {
                    length = i - 1;
                    startDrawing = true;
                    caster.action_points_current -= 1;
                    break;
                }
            }
            if (length != 0) break;
        }
    }

    public void purifyingRayDraw(){
        if (startDrawing){
            spell_timer += Gdx.graphics.getDeltaTime();
            for(int i = 0; i <= length; i++)
                Main.batch.draw(img_purifyRay, caster.rect.x + (32 + i*32)*rotation_koef, caster.rect.y);
            if (spell_timer > end_timer){
                startDrawing = false;
                caster.spellcasting = false;
                caster.deactivate_slots(slot_num);
            }
        }
    }
}
