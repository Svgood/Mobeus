package com.svgoodmobeus.game.Spells.MageSpellTesting;

import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.Animations;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Debuff;
import com.svgoodmobeus.game.Main;
import com.svgoodmobeus.game.Spells.Spell;

/**
 * Created by Svgood on 28.09.2017.
 */
public class Fireball extends Spell{

    public Fireball(Character caster){
        super(caster);
        animation = new Animations("fireball", 9);
        name = "Fireball";
        description = "Hadouken";
        img = new Texture("fireball.png");
    }

    public void use(){
        projectileSpell();
    }

    public void draw(){
        animation.play_animation();
        projectileDraw(animation.current_frame(), 15);
    }

    public void action(){
        target.debuffs.add(new Debuff("Burns", 4, target, 5));
    }
}
