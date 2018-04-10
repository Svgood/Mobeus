package com.svgoodmobeus.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Spells.PriestSpells;
import com.svgoodmobeus.game.Spells.RogueSpells;

/**
 * Created by svgood on 28.07.17.
 */
public class Priest extends Character {

    public Priest(int x, int y){
        super(x, y);
        spells = new PriestSpells(this);
        attack_dmg = 10;
        img = new Texture("rogue1.png");
        img1 = new Texture("rogue2.png");
        hp_max = 75;
        hp_current = hp_max;
        previous_hp = hp_current;
        energy_max = 120;
        energy_current = energy_max;
    }
}
