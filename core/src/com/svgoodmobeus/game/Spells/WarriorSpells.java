package com.svgoodmobeus.game.Spells;

import com.svgoodmobeus.game.*;
import com.svgoodmobeus.game.Character;

/**
 * Created by Svgood on 14.07.2017.
 */
public class WarriorSpells extends Spells {

    /*
    1 - Charge
    2 - Protection +
    3 - Sword Strike +
    4 - Domination Strike +
    5 - Full Defense +
    6 - Whirlwind +
    7 - Born to protect
    8 - Stunning hit +
    9 - Disarm +
    10 -

     */
    int DMG_SWORDSTRIKE;
    int DMG_STUNNING_HIT;
    int DMG_WHIRLWIND;


    public WarriorSpells(Character caster){
        super(caster);
        DMG_SWORDSTRIKE = 35;
        DMG_STUNNING_HIT = 10;
        DMG_WHIRLWIND = 27;
    }

    public String getSpellName(int id){
        if (id == 1) {
            name = "Sword Strike";
        }
        if (id == 2) {
            name = "Stunning hit";
        }
        if (id == 3) {
            name = "Protection";
        }
        if (id == 4) {
            name = "Dominating Strike";
        }
        if (id == 5) {
            name = "Disarm";
        }
        if (id == 6) {
            name = "Whirlwind";
        }
        if (id == 7) {
            name = "Full Defense";
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
            swordStrike();
        }
        if (id == 2) {
            stunningHit();
        }
        if (id == 3) {
            protection();
        }
        if (id == 4) {
            dominationStrike();
        }
        if (id == 5) {
            disarm();
        }
        if (id == 6) {
            whirlwind();
        }
        if (id == 7) {
            fullDefense();
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

    public void swordStrike(){
            if(Controller.mouse_target != null && Functions.checkDistance(48, caster, Controller.mouse_target)
                    && Controller.mouse_target != caster){
                caster.face_target(Controller.mouse_target);
                caster.spell_cd = 0;
                Controller.mouse_target.hp_current -= DMG_SWORDSTRIKE;
                caster.action_points_current -= 1;
                caster.deactivate_slots(slot_num);
            }
    }

    public void swordStrikeDraw(){

    }

    public void stunningHit(){
        if(Controller.mouse_target != null && Functions.checkDistance(48, caster, Controller.mouse_target)
                && Controller.mouse_target != caster){
            caster.face_target(Controller.mouse_target);
            caster.spell_cd = 0;
            Controller.mouse_target.hp_current -= DMG_STUNNING_HIT;
            Controller.mouse_target.stunned = true;
            Controller.mouse_target.debuffs.add(new Debuff("Stun", 1, Controller.mouse_target, 0));
            caster.action_points_current -= 1;
            caster.deactivate_slots(slot_num);
        }
    }

    public void stunningHitDraw(){

    }

    public void protection(){
        if(Controller.mouse_target != null && Functions.checkDistance(256, caster, Controller.mouse_target)){
            caster.face_target(Controller.mouse_target);
            caster.spell_cd = 0;
            Controller.mouse_target.buffs.add(new Buff("AddWeaponResist", 5, Controller.mouse_target, 30));
            caster.action_points_current -= 1;
            caster.deactivate_slots(slot_num);
        }
    }


    public void dominationStrike(){
        if(Controller.mouse_target != null && Functions.checkDistance(256, caster, Controller.mouse_target) ){
            caster.face_target(Controller.mouse_target);
            caster.spell_cd = 0;
            Controller.mouse_target.hp_current -= (Controller.mouse_target.hp_max - Controller.mouse_target.hp_current)*0.77f;
            caster.action_points_current -= 1;
            caster.deactivate_slots(slot_num);
        }
    }

    public void disarm(){
        if(Controller.mouse_target != null && Functions.checkDistance(256, caster, Controller.mouse_target)){
            caster.face_target(Controller.mouse_target);
            caster.spell_cd = 0;
            Controller.mouse_target.disarmed = true;
            Controller.mouse_target.debuffs.add(new Debuff("Disarm", 4, Controller.mouse_target, 0));
            caster.action_points_current -= 1;
            caster.deactivate_slots(slot_num);
        }
    }

    public void whirlwind(){
        caster.spell_cd = 0;
        checkRect.x = caster.rect.x - 96;
        checkRect.y = caster.rect.y;
        for(int i = 0; i < 5; i++){
            checkRect.x += 32;
            for(int k = 0; k < Main.characters.size; k++){
                if (Main.characters.get(k).rect.overlaps(checkRect) && Main.characters.get(k) != caster){
                    Main.characters.get(k).hp_current -= DMG_WHIRLWIND;
                }
            }
        }
        caster.action_points_current -= 1;
        caster.deactivate_slots(slot_num);
    }

    public void fullDefense(){
        caster.spell_cd = 0;
        caster.buffs.add(new Buff("AddWeaponResist", 1, caster, 75));
        caster.buffs.add(new Buff("AddMagicResist", 1, caster, 75));
        caster.action_points_current -= 1;
        caster.deactivate_slots(slot_num);
    }
}
