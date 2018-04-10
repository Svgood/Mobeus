package com.svgoodmobeus.game.Spells.MageSpellTesting;

import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Main;
import com.svgoodmobeus.game.Spells.Spell;

/**
 * Created by Svgood on 28.09.2017.
 */
public class Swap extends Spell{
    public Swap(Character caster){
        super(caster);
        name = "Swap";
        description = "Swap yourself with your ally";
    }

    public void use(){
        clickSpell();
    }

    public void draw(){
        //clickDraw();
    }

    public void action(){
        float tempx, tempy;
        caster.spell_cd = 0;
        tempx = caster.rect.x;
        tempy = caster.rect.y;
        caster.rect.x = target.rect.x;
        caster.rect.y = target.rect.y;
        target.rect.x = tempx;
        target.rect.y = tempy;

        tempx = caster.dest_x;
        tempy = caster.dest_y;
        caster.dest_x =  target.dest_x;
        caster.dest_y =  target.dest_y;
        target.dest_x = tempx;
        target.dest_y = tempy;
        caster.deactivate_slots(slot_num);
    }
}
