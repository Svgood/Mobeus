package com.svgoodmobeus.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Spells.RogueSpells;

/**
 * Created by Svgood on 06.07.2017.
 */
public class Rogue extends Character {


    public Rogue(int x, int y){
        super(x, y);
        spells = new RogueSpells(this);
        attack_dmg = 25;
        img = new Texture("rogue1.png");
        img1 = new Texture("rogue2.png");
        hp_max = 80;
        hp_current = hp_max;
        previous_hp = hp_current;
        energy_max = 100;
        energy_current = energy_max;
        id_spell4 = 6;
        id_spell1 = 7;
    }
}
