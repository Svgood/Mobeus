package com.svgoodmobeus.game.Spells.MageSpellTesting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.svgoodmobeus.game.*;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Spells.Spell;

/**
 * Created by Svgood on 28.09.2017.
 */
public class LightingStrike extends Spell{

    public LightingStrike(Character caster){
        super(caster);
        name = "Lighting Strike";
        animation = new Animations("lighting", 4, 2);
        description = "By the power of Light!";
        img = new Texture("lighting.png");
        precast_img = new Texture("lighting_preview.png");
    }

    public void use(){
        customSpell();
    }

    public void draw(){
        customDraw();
    }

    public void customSpell(){
        checkRect.x = ((int)(Controller.getTouchX()/32))*32;
        checkRect.y = 720;

        caster.spell_cd = 0;
        caster.spellcasting = true;

        spell_timer = 0;
        end_timer = 0.8f;

        length = 0;
        for (int k = 0; k < 20; k++) {
            checkRect.y = 720 - 32*k;
            for (int i = 0; i < Main.characters.size; i++){
                if (Main.characters.get(i).rect.overlaps(checkRect)){
                    caster.action_points_current -= 1;
                    Main.characters.get(i).hp_current -= 40;
                    length = k+2;
                    break;
                }
            }

            for (int i = 0; i < Main.walls.size; i++) {
                if (Main.walls.get(i).rect.overlaps(checkRect)) {
                    caster.action_points_current -= 1;
                    length = k;
                    break;
                }
            }
            if (length != 0) break;
        }
        startDrawing = true;
    }

    public void customDraw(){
        if (!startDrawing){
            checkRect.x = ((int)(Controller.getTouchX()/32))*32;
            length = 0;
            for (int k = 0; k < 20; k++) {
                checkRect.y = 720 - 32*k;
                for (int i = 0; i < Main.walls.size; i++) {
                    if (Main.walls.get(i).rect.overlaps(checkRect)) {
                        length = k;
                        break;
                    }
                }
                if (length != 0) break;
            }
            for (int i = 0; i < length; i++){
                Main.batch.draw(precast_img, checkRect.x, 720 - 32*i);
            }
        }
        if (startDrawing){
            animation.play_animation();
            spell_timer += Gdx.graphics.getDeltaTime();
            if (spell_timer > end_timer){
                startDrawing = false;
                caster.spellcasting = false;
                caster.deactivate_slots(slot_num);
            }
            for (int i = 0; i < length; i++){
                Main.batch.draw(animation.current_frame(), checkRect.x, 720 - 32*i);
            }
        }
    }
}
