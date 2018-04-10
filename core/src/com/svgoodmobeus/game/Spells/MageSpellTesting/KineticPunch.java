package com.svgoodmobeus.game.Spells.MageSpellTesting;

import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Debuff;
import com.svgoodmobeus.game.Main;
import com.svgoodmobeus.game.Spells.Spell;

/**
 * Created by Svgood on 28.09.2017.
 */
public class KineticPunch extends Spell{

    public KineticPunch(Character caster){
        super(caster);
        name = "Kinetic Punch";
        description = "Play with gravity, bitch";
        img = new Texture("kpunch.png");
    }

    public void use(){
        projectileSpell();
    }

    public void draw(){
        projectileDraw(img, 10);
    }

    public void action(){
        target.punch_trigger(3, rotation_koef);
    }
}
