package com.svgoodmobeus.game.AI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.svgoodmobeus.game.Character;
import com.svgoodmobeus.game.Controller;
import com.svgoodmobeus.game.Functions;
import com.svgoodmobeus.game.Main;

/**
 * Created by Svgood on 16.06.2017.
 */
public class AICharacter extends Character {
    float wait;
    boolean did_something;

    public AICharacter(int x, int y) {
        super(x, y);
        wait = 0;
        img = new Texture("skeleton.png");
        img1 = new Texture("skeleton1.png");
        type = "AI";
        attack_dmg = 15;
        hp_max = 80;
        hp_current = hp_max;
        previous_hp = hp_max;
        did_something = false;
        finished = false;
    }

    public void update(SpriteBatch batch) {
        if (!dead) {
            wait += Gdx.graphics.getDeltaTime();
            checking_stance();
            death();
            movement();
            gravity();
            draw(batch);
            if (Controller.currentTurn.equals("AI") && Controller.enemieTurnBegins){
                ai();
                if (!did_something){
                    finished = true;
                }
            }
        }
        else finished = true;
    }

    public void ai() {
            if (wait > 0.5f) {
                wait = 0;
                find_closest();
                take_action();
            }

    }

    public void take_action(){
        did_something = false;

        if (Functions.checkDistance(48, this, target)){
            attack_target();
        }
        else move_to_target();
    }

    public void find_closest(){
        float max = 1000;
        for(int i = 0; i < Main.characters.size; i++){
            if (Functions.getDistance(this, Main.characters.get(i)) < max
                    && !type.equals(Main.characters.get(i).type)){
                max = Functions.getDistance(this, Main.characters.get(i));
                target = Main.characters.get(i);
            }
        }
    }

    public void move_to_target(){
        if (move_points_current > 0) {
            if (target.rect.x > rect.x) move(1);
            else move(-1);
            did_something = true;
        }
    }

    public void attack_target(){
        if (action_points_current > 0) {
            target.hp_current -= attack_dmg;
            action_points_current -= 1;
            did_something = true;
        }
    }

    public void replenish(){
        move_points_current = move_points_max;
        action_points_current = action_points_max;
        finished = false;
    }
}