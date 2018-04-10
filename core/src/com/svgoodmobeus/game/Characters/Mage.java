package com.svgoodmobeus.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Spells.MageSpells;

/**
 * Created by Svgood on 13.07.2017.
 */
public class Mage extends Character {

    public Mage(int x, int y){
        super(x, y);
        spells = new MageSpells(this);
    }
}
