package com.svgoodmobeus.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Spells.WarriorSpells;

/**
 * Created by Svgood on 07.07.2017.
 */
public class Warrior extends Character {
    public Warrior(int x, int y){
        super(x, y);
        attack_dmg = 30;
        img = new Texture("warrior1.png");
        img1 = new Texture("warrior2.png");
        hp_max = 120;
        hp_current = hp_max;
        previous_hp = hp_current;
        energy_max = 60;
        energy_current = energy_max;
        spells = new WarriorSpells(this);
        id_spell1 = 4;
    }
}
