package com.svgoodmobeus.game.Spells.MageSpellTesting;

import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.Animations;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Controller;
import com.svgoodmobeus.game.Main;
import com.svgoodmobeus.game.Spells.Spell;

/**
 * Created by Svgood on 28.09.2017.
 */
public class Heal extends Spell {

    public Heal(Character caster){
        super(caster);
        animation = new Animations("heal_crest", 35, 4, true);
        name = "Heal";
        description = "Heal yourself, tard!";
    }

    public void use(){
        clickSpell();
    }

    public void draw(){
        animation.play_animation();
        clickDraw(animation.current_frame());
    }

    public void action(){
        target.hp_current += 50;
    }
}
