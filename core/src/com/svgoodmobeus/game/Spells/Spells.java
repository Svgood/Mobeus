package com.svgoodmobeus.game.Spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Debuff;
import com.svgoodmobeus.game.Main;

/**
 * Created by Svgood on 25.06.2017.
 */
public class Spells {

    Rectangle checkRect;
    String name;
    int length;
    Character caster, touched;
    Boolean startDrawing;
    float spell_timer;
    float end_timer;
    int rotation_koef;
    int charges;

    int slot_num;

    public Spells(Character caster){
        this.caster = caster;
        checkRect = new Rectangle(0, 0, 32, 32);
        startDrawing = false;
        spell_timer = 0;
        end_timer = 0;
        length = 0;
        charges = 0;
    }

    public String getSpellName(int id){
        if (id == 1) {

        }
        if (id == 2) {

        }
        if (id == 3) {

        }
        if (id == 4) {

        }
        if (id == 5) {

        }
        if (id == 6) {

        }
        if (id == 7) {

        }
        if (id == 8) {

        }
        if (id == 9) {

        }
        if (id == 10) {

        }
        return name;
    }

    public void useSpell(int id, int slot_num) {
        if (id == 1) {

        }
        if (id == 2) {

        }
        if (id == 3) {

        }
        if (id == 4) {

        }
        if (id == 5) {

        }
        if (id == 6) {

        }
        if (id == 7) {

        }
        if (id == 8) {

        }
        if (id == 9) {

        }
        if (id == 10) {

        }
        this.slot_num = slot_num;
    }

    public void spellDraw(int id) {
        if (id == 1) {

        }
        if (id == 2) {

        }
        if (id == 3) {

        }
        if (id == 4) {

        }
        if (id == 5) {

        }
        if (id == 6) {

        }
        if (id == 7) {

        }
        if (id == 8) {

        }
        if (id == 9) {

        }
        if (id == 10) {

        }
    }

}
