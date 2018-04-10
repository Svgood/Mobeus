package com.svgoodmobeus.game;

/**
 * Created by Svgood on 07.07.2017.
 */
public class Debuff {
    String type;
    int duration;
    Character character;
    Boolean just_buffed;
    int value;


    public Debuff(String t, int d, Character c, int v){
        type = t;
        duration = d;
        character = c;
        just_buffed = false;
        value = v;
        for(int i = 0; i < c.debuffs.size; i++)
            if (c.debuffs.get(i).type.equals(t))
                c.debuffs.removeIndex(i);
    }

    public void proc_debuff(){
        if (duration > 0) {
            if (type.equals("Burns")) {
                character.hp_current -= value;
                duration -= 1;
            }
            if (type.equals("Bleeding")) {
                character.hp_current -= value;
                duration -= 1;
            }
            if (type.equals("Stun")){
                character.stunned = true;
                duration -= 1;
            }
            if (type.equals("Disarm")){
                character.disarmed = true;
                duration -= 1;
            }
        }
        else {
            character.debuffs.removeValue(this, true);
        }
    }

}
