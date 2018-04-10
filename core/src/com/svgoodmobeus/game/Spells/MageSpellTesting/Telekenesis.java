package com.svgoodmobeus.game.Spells.MageSpellTesting;

import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Controller;
import com.svgoodmobeus.game.Main;
import com.svgoodmobeus.game.Spells.Spell;

/**
 * Created by Svgood on 28.09.2017.
 */
public class Telekenesis extends Spell {

    public Telekenesis(Character caster){
        super(caster);
        name = "Telekenesis";
        description = "Move character on 1 tile left or right";
    }

    public void use(){
        customSpell();
    }

    public void draw(){
        customDraw();
    }

    public void customSpell(){
        action();
    }

    public void action(){
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
}
