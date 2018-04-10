package com.svgoodmobeus.game.Spells.MageSpellTesting;

import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Spells.Spell;

/**
 * Created by Svgood on 28.09.2017.
 */
public class Lasor extends Spell {

    public Lasor(Character caster){
        super(caster);
        name = "Lasor";
        description = "LASOOOOOR PSHHHH";
        img = new Texture("lasor.png");
    }

    public void use(){
        raySpell(20);
    }

    public void draw(){
        rayDraw(img);
    }
}
