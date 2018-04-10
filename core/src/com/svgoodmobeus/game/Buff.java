package com.svgoodmobeus.game;

/**
 * Created by Svgood on 14.07.2017.
 */
public class Buff {
    String type;
    int duration;
    Character character;
    Boolean just_buffed;
    int value;

    public Buff(String type, int duration, Character charcter, int value){
        this.type = type;
        this.duration = duration;
        this.character = charcter;
        just_buffed = false;
        this.value = value;
        for(int i = 0; i < charcter.buffs.size; i++)
            if (charcter.buffs.get(i).type.equals(type)){
                charcter.buffs.removeIndex(i);
            }

    }

    public void proc_buff(){
        if (duration > 0) {
            if (type.equals("BonusDamage")) {
                if (!just_buffed){
                    character.attack_dmg += value;
                    just_buffed = true;
                }
                duration -= 1;
                if (duration == 0) character.attack_dmg -= 7;
            }
            if (type.equals("HpRestore")) {
                character.hp_current += value;
                duration -= 1;
            }
            if (type.equals("AddWeaponResist")){
                if (!just_buffed){
                    character.weapon_resist += value;
                    just_buffed = true;
                }
                duration -= 1;
                if (duration == 0) character.weapon_resist -= value;
            }
            if (type.equals("AddMagicResist")){
                if (!just_buffed){
                    character.magic_resist += value;
                    just_buffed = true;
                }
                duration -= 1;
                if (duration == 0) character.magic_resist -= value;
            }
            if (type.equals("AddHealth")){
                if (!just_buffed){
                    character.hp_max += value;
                    character.hp_current += value;
                    just_buffed = true;
                }
                duration -= 1;
                if (duration == 0) {
                    character.hp_max -= value;
                    if (character.hp_current > character.hp_max) character.hp_current = character.hp_max;
                }
            }
        }
        else {
            character.buffs.removeValue(this, true);
        }
    }
}
