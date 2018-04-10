package com.svgoodmobeus.game.Spells.MageSpellTesting;

import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Controller;
import com.svgoodmobeus.game.Debuff;
import com.svgoodmobeus.game.Main;
import com.svgoodmobeus.game.Spells.Spell;

/**
 * Created by Svgood on 28.09.2017.
 */
public class Blink extends Spell{

    public Blink(Character caster){
        super(caster);
        name = "Blink";
        description = "U can go anywhere!";
        img = new Texture("fireball.png");
    }

    public void use(){
        customSpell();
    }

    public void draw(){
        customDraw();
    }

    public void customSpell(){
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

    public void customDraw(){
        Main.batch.draw(caster.img_silouet, ((int)(Controller.getTouchX()/32))*32, -16 + ((int)(Controller.getTouchY()/32))*32);
    }
}
