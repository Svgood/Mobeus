package com.svgoodmobeus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.svgoodmobeus.game.Spells.MageSpellTesting.*;
import com.svgoodmobeus.game.Spells.Spell;
import com.svgoodmobeus.game.Spells.Spells;

/**
 * Created by Svgood on 15.06.2017.
 */
public class Character {

    //static String [] names_array = {"Richard", "Bethoven", "Leeroy","DIablo", "Leorik", "Richard", "Richard", "Richard"}
    public float hp_current, hp_max, previous_hp;
    public float energy_current, energy_max;
    public float attack_dmg;
    public float weapon_resist;
    public float magic_resist;
    public Rectangle rect;
    public Texture img, img_silouet, img_lasor;
    public Sprite sprite;
    public float dest_x, dest_y;

    public boolean slot1_active, slot2_active, slot3_active, slot4_active;
    public float sprite_speed, v_speed;
    public Boolean falling, moving;
    public String move_how;
    public int char_id;
    public  static int total_chars = 0;

    //stats
    public int move_points_current, action_points_current;
    public int move_points_max, action_points_max;

    //anim
    public Texture img1;
    public static float animSpeed = 0.3f;
    public float animCounter;
    public boolean animTrigger;

    public float action_wait;
    public int facing_direction;
    public String type;
    public boolean dead, punched;
    public boolean spellcasting;
    public boolean lost_hp_trigger;
    public boolean stunned;
    public boolean disarmed;
    public boolean finished;

    public float lost_hp_timer;
    public float lost_hp;
    public Spells spells;
    public Spell currentSpell;
    public Character target;
    public float spell_cd;
    public int id_spell1, id_spell2, id_spell3, id_spell4;
    public BitmapFont font16_red, font16_blue, font48_white;
    public Array<Debuff> debuffs;
    public Array<Buff> buffs;
    public Array<Integer> deck;
    public Array<Spell> newSpells;
    public String currentSpellName;

    public int stance;
    /*
    0 - move and attack
    1 - cast spells
     */

    public Character(int x, int y){
        move_how = "";
        total_chars += 1;
        char_id = total_chars;
        sprite_speed = 2;
        img = new Texture("char1.png");
        img1 = new Texture("char2.png");
        img_silouet = new Texture("char_silouet.png");
        sprite = new Sprite(img);
        type = "Player1";
        facing_direction = 1;
        font16_red = new BitmapFont(Gdx.files.internal("font16_red.fnt"));
        font16_blue = new BitmapFont(Gdx.files.internal("font16_blue.fnt"));
        font48_white = new BitmapFont(Gdx.files.internal("font48_white.fnt"));
        stance = 0;
        animCounter = 0;
        animTrigger = false;

        rect = new Rectangle(x, y, img.getWidth(), img.getHeight());

        attack_dmg = 10;
        hp_max = 50;
        hp_current = hp_max;
        previous_hp = hp_current;
        energy_max = 150;
        energy_current = energy_max;

        dest_x = rect.x;
        dest_y = rect.y;
        falling = true;
        moving = false;
        v_speed = 2f;
        move_points_max = 4;
        action_points_max = 2;
        move_points_current = move_points_max;
        action_points_current = action_points_max;

        slot1_active = false;
        slot2_active = false;
        slot3_active = false;
        slot4_active = false;

        spell_cd = 0;
        spells = new Spells(this);
        debuffs = new Array<Debuff>();
        buffs = new Array<Buff>();
        deck = new Array<Integer>();
        newSpells = new Array<Spell>();
        id_spell1 = 1;
        id_spell2 = 2;
        id_spell3 = 3;
        id_spell4 = 4;
        spellcasting = false;
        dead = false;
        punched = false;
        lost_hp_trigger = false;
        stunned = false;
        disarmed = false;

        weapon_resist = 0;
        magic_resist = 0;

        currentSpellName = "";

        fillDeck();
        id_spell1 = deck.pop();
        id_spell2 = deck.pop();
        id_spell3 = deck.pop();
        id_spell4 = deck.pop();

        newSpells.add(new Lasor(this));
        newSpells.add(new Fireball(this));
        newSpells.add(new Heal(this));
        newSpells.add(new LightingStrike(this));
    }

    public void update(SpriteBatch batch){
        draw(batch);
        if (stunned){
            move_points_current = 0;
            action_points_current = 0;
        }
        if (!dead) {
            if (Controller.currentCharacter == char_id && Controller.currentTurn.equals("Player")) {
                spells_controller();
                move_check();
            }
            checking_stance();
            movement();
            gravity();
            death();
            //System.out.println("Rect y:" + rect.y + " Dest y:" + dest_y);
            //System.out.println("Rect x:" + rect.x + " Dest x:" + dest_x);
        }
    }

    protected void checking_stance(){
        if (previous_hp != hp_current && hp_current != hp_max){
            if (hp_current > hp_max){
                lost_hp = previous_hp - hp_max;
                lost_hp_timer = 0;
                lost_hp_trigger = true;
            }
            else {
                lost_hp = previous_hp - hp_current;
                lost_hp_timer = 0;
                lost_hp_trigger = true;
            }
        }
        previous_hp = hp_current;
        if (hp_current > hp_max) hp_current = hp_max;
        show_lost_health();
        check_stance();
    }

    public void proc_debuff(){
        for(int i = 0; i < debuffs.size; i++){
            debuffs.get(i).proc_debuff();
        }
    }

    public void proc_buff(){
        for(int i = 0; i < buffs.size; i++){
            buffs.get(i).proc_buff();
        }
    }

    public void draw(SpriteBatch batch){
        font16_red.draw(batch, "" + (int)hp_current, rect.x - 8, rect.y + 80);
        font48_white.draw(batch, currentSpellName, 400, 200);


        sprite.setPosition(rect.x , rect.y);
        sprite.draw(batch);
        animCounter += Gdx.graphics.getDeltaTime();
        if (animCounter > animSpeed){
            animCounter = 0;
            if (animTrigger) animTrigger = false;
            else animTrigger = true;
        }
        if (animTrigger) {
            sprite.setTexture(img);
        } else sprite.setTexture(img1);

        if (lost_hp_trigger){
            if (lost_hp < 0) {
                lost_hp = lost_hp * -1;
                font16_red.draw(batch, "+" + (int) lost_hp, rect.x + 4, rect.y + 96);
            }
            else
                font16_red.draw(batch, "-" + (int)lost_hp, rect.x + 4, rect.y + 96);
        }
    }

    protected void check_stance(){
        if (slot1_active || slot2_active || slot3_active || slot4_active){
            stance = 1;
        }
        else stance = 0;
    }

    protected void death(){
        if (hp_current <= 0) {
            dead = true;
            Main.deadCharacterses.add(new DeadCharacters(rect.x, rect.y));
        }
    }

    protected void spells_controller(){
        /*
        spell_cd += Gdx.graphics.getDeltaTime();
        if (action_points_current == 0 && !spellcasting){
            deactivate_slots();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            deactivate_slots();
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_1)
                || (Gdx.input.isTouched() && UI.rect_spell1.contains(Controller.getTouchX(), Controller.getTouchY()))) && action_points_current > 0 && !spellcasting && !Controller.touched) {
            currentSpellName = spells.getSpellName(id_spell1);
            target = null;
            slot1_active = true;
            slot2_active = false;
            slot3_active = false;
            slot4_active = false;
            stance = 1;
            spell_cd = 0;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_2)
                || (Gdx.input.isTouched() && UI.rect_spell2.contains(Controller.getTouchX(), Controller.getTouchY()))) && action_points_current > 0 && !spellcasting && !Controller.touched) {
            currentSpellName = spells.getSpellName(id_spell2);
            target = null;
            slot2_active = true;
            slot1_active = false;
            slot3_active = false;
            slot4_active = false;
            stance = 1;
            spell_cd = 0;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_3)
                || (Gdx.input.isTouched() && UI.rect_spell3.contains(Controller.getTouchX(), Controller.getTouchY()))) && action_points_current > 0 && !spellcasting && !Controller.touched) {
            currentSpellName = spells.getSpellName(id_spell3);
            target = null;
            slot2_active = false;
            slot1_active = false;
            slot3_active = true;
            slot4_active = false;
            stance = 1;
            spell_cd = 0;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_4)
                || (Gdx.input.isTouched() && UI.rect_spell4.contains(Controller.getTouchX(), Controller.getTouchY()))) && action_points_current > 0 && !spellcasting && !Controller.touched) {
            currentSpellName = spells.getSpellName(id_spell4);
            target = null;
            slot2_active = false;
            slot1_active = false;
            slot4_active = true;
            slot3_active = false;
            stance = 1;
            spell_cd = 0;
        }

        if (slot1_active) {
            spells.spellDraw(id_spell1);
            if (Gdx.input.isTouched() && action_points_current > 0 && !spellcasting && spell_cd > 0.5f && !Controller.touched) {
                spells.useSpell(id_spell1, 1);
                Controller.touched = true;
            }
        }
        if (slot2_active) {
            spells.spellDraw(id_spell2);
            if (Gdx.input.isTouched() && action_points_current > 0 && !spellcasting && spell_cd > 0.5f && !Controller.touched) {
                spells.useSpell(id_spell2, 2);
                Controller.touched = true;
            }
        }
        if (slot3_active){
            spells.spellDraw(id_spell3);
            if (Gdx.input.isTouched() && action_points_current > 0 && !spellcasting && spell_cd > 0.5f && !Controller.touched) {
                spells.useSpell(id_spell3, 3);
                Controller.touched = true;
            }
        }
        if (slot4_active){
            spells.spellDraw(id_spell4);
            if (Gdx.input.isTouched() && action_points_current > 0 && !spellcasting && spell_cd > 0.5f && !Controller.touched) {
                spells.useSpell(id_spell4, 4);
                Controller.touched = true;
            }
        }
        */
        spell_cd += Gdx.graphics.getDeltaTime();
        if (action_points_current == 0 && !spellcasting){
            deactivate_slots();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            deactivate_slots();
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_1)
                || (Gdx.input.isTouched() && UI.rect_spell1.contains(Controller.getTouchX(), Controller.getTouchY()))) && action_points_current > 0 && !spellcasting && !Controller.touched) {
            deactivate_slots();
            currentSpell = newSpells.get(0);
            currentSpellName = currentSpell.name;
            target = null;
            slot1_active = true;
            stance = 1;
            spell_cd = 0;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_2)
                || (Gdx.input.isTouched() && UI.rect_spell2.contains(Controller.getTouchX(), Controller.getTouchY()))) && action_points_current > 0 && !spellcasting && !Controller.touched) {
            deactivate_slots();
            currentSpell = newSpells.get(1);
            currentSpellName = currentSpell.name;
            target = null;
            slot2_active = true;
            stance = 1;
            spell_cd = 0;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_3)
                || (Gdx.input.isTouched() && UI.rect_spell3.contains(Controller.getTouchX(), Controller.getTouchY()))) && action_points_current > 0 && !spellcasting && !Controller.touched) {
            deactivate_slots();
            currentSpell = newSpells.get(2);
            currentSpellName = currentSpell.name;
            target = null;
            slot3_active = true;
            stance = 1;
            spell_cd = 0;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_4)
                || (Gdx.input.isTouched() && UI.rect_spell4.contains(Controller.getTouchX(), Controller.getTouchY()))) && action_points_current > 0 && !spellcasting && !Controller.touched) {
            deactivate_slots();
            currentSpell = newSpells.get(3);
            currentSpellName = currentSpell.name;
            target = null;
            slot4_active = true;
            stance = 1;
            spell_cd = 0;
        }

        if (stance == 1) {
            currentSpell.draw();
            if (Gdx.input.isTouched() && action_points_current > 0 && !spellcasting && spell_cd > 0.1 && !Controller.touched) {
                currentSpell.use();
                Controller.touched = true;
            }
        }
    }

    public void fillDeck(){
        for(int i = 1; i < 22; i++){
            deck.add(MathUtils.random(1, 7));
        }
    }

    public void deactivate_slots(int slot_num){
        currentSpellName = "";
        spell_cd = 0;
        if (slot_num == 1){
            id_spell1 = deck.pop();
        }
        if (slot_num == 2){
            id_spell2 = deck.pop();
        }
        if (slot_num == 3){
            id_spell3 = deck.pop();
        }
        if (slot_num == 4){
            id_spell4 = deck.pop();
        }
        slot1_active = false;
        slot2_active = false;
        slot3_active = false;
        slot4_active = false;
        stance = 0;
    }

    public void deactivate_slots(){
        currentSpellName = "";
        spell_cd = 0;
        slot1_active = false;
        slot2_active = false;
        slot3_active = false;
        slot4_active = false;
        stance = 0;
    }

    protected void move_check(){
        action_wait += Gdx.graphics.getDeltaTime();
        if ((Gdx.input.isKeyPressed(Input.Keys.D) || (Gdx.input.isTouched() && Controller.getTouchX() > rect.x))
                && rect.x == dest_x && rect.y == dest_y && stance == 0 && !Controller.touched) {
            for (int i = 0; i < Main.characters.size; i++){
                if (!Main.characters.get(i).type.equals(type) && Main.characters.get(i).rect.contains(rect.x + 48, rect.y)){
                    if (action_points_current > 0 && action_wait > 0.5) {
                        action_wait = 0;
                        if (!disarmed) {
                            Main.characters.get(i).hp_current -= attack_dmg;
                            action_points_current -= 1;
                        }
                        return;
                    }
                    else return;
                }
                if (Main.characters.get(i).type.equals(type) && Main.characters.get(i).rect.contains(rect.x + 48, rect.y) &&
                        Main.characters.get(i) != this && move_points_current > 0){
                    float temp;
                    temp =  Main.characters.get(i).dest_x;
                    Main.characters.get(i).dest_x = dest_x;
                    dest_x = temp;
                    move_points_current -= 1;
                    return;
                }
            }
            if (move_points_current > 0) move(1);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.A) || (Gdx.input.isTouched() && Controller.getTouchX() < rect.x))
                && rect.x == dest_x && rect.y == dest_y && stance == 0 && !Controller.touched) {
            for (int i = 0; i < Main.characters.size; i++){
                if (!Main.characters.get(i).type.equals(type) && Main.characters.get(i).rect.contains(rect.x - 16, rect.y)){
                    if (action_points_current > 0 && action_wait > 0.5) {
                        action_wait = 0;
                        if (!disarmed) {
                            Main.characters.get(i).hp_current -= attack_dmg;
                            action_points_current -= 1;
                        }
                        return;
                    }
                }
                if (Main.characters.get(i).type.equals(type) && Main.characters.get(i).rect.contains(rect.x - 16, rect.y) &&
                        Main.characters.get(i) != this && move_points_current > 0){
                    float temp;
                    temp =  Main.characters.get(i).dest_x;
                    Main.characters.get(i).dest_x = dest_x;
                    dest_x = temp;
                    move_points_current -= 1;
                    return;
                }
            }
            if (move_points_current > 0) move(-1);
        }
    }

    public void move(int koef){
        move_how = "";
        rotate(koef);
        rect.x += 0.1f*koef;
        for (int i = 0; i < Main.walls.size; i++) {
            if (Main.walls.get(i).rect.overlaps(rect)) {
                rect.y += 32;
                for (int k = 0; k < Main.walls.size; k++) {
                    if (Main.walls.get(k).rect.overlaps(rect)) {
                        move_how = "No";
                        break;
                    }
                    if (!move_how.equals("No"))
                        move_how = "Up";
                }
                rect.y -= 32;
            }
            if (move_how.equals("")) move_how = "Right";
        }
        System.out.println(move_how);
        rect.x -= 0.1f*koef;
        if (move_how.equals("Right")){
            move_points_current -= 1;
            dest_x += 32*koef;
        }
        if (move_how.equals("Up")){
            moving = true;
            move_points_current -= 1;
            dest_x += 32*koef;
            dest_y += 32;
        }
    }

    public void free_move(int koef){
        move_how = "";
        rect.x += 0.1f*koef;
        for (int i = 0; i < Main.walls.size; i++) {
            if (Main.walls.get(i).rect.overlaps(rect)) {
                rect.y += 35;
                for (int k = 0; k < Main.walls.size; k++) {
                    if (Main.walls.get(k).rect.overlaps(rect)) {
                        move_how = "No";
                        break;
                    }
                    if (!move_how.equals("No"))
                        move_how = "Up";
                }
                rect.y -= 35;
            }
            if (move_how.equals("")) move_how = "Right";
        }
        System.out.println(move_how);
        rect.x -= 0.1f*koef;
        if (move_how.equals("Right")){
            dest_x += 32*koef;
        }
        if (move_how.equals("Up")){
            moving = true;
            dest_x += 32*koef;
            dest_y += 32;
        }
    }

    public void punch_trigger(int range, int dir){
        float temp;
        for(int i = 0; i < range; i++){
            dest_x += 32*dir;
            temp = dest_x;
            for (int k = 0; k < Main.walls.size; k++)
                if (Main.walls.get(k).rect.contains(dest_x, dest_y+16)) {
                    dest_x -= 32 * dir;
                    break;
                }
            if (dest_x != temp) break;
        }
        punched = true;
    }

    public void rotate(int koef){
        if (koef == -1) {
            sprite.setFlip(true, false);
            facing_direction = -1;
        }
        else {
            sprite.setFlip(false, false);
            facing_direction = 1;
        }
    }

    public void teleport(float x, float y){
        rect.x = x;
        rect.y = y;
        dest_x = x;
        dest_y = y;
    }

    public void face_target(Character target){
        if (target.rect.x > rect.x)
            rotate(1);
        else rotate(-1);
    }

    protected void show_lost_health(){
        if (lost_hp_trigger){
            lost_hp_timer += Gdx.graphics.getDeltaTime();
            if (lost_hp_timer > 1.5f) lost_hp_trigger = false;
        }
    }

    protected void movement(){
        if (!punched) {
            if (rect.x < dest_x)
                rect.x += sprite_speed;
            if (rect.x > dest_x)
                rect.x -= sprite_speed;
            if (rect.y < dest_y)
                rect.y += sprite_speed;
            if (rect.y > dest_y)
                rect.y -= sprite_speed;
            if (rect.x == dest_x && rect.y == dest_y)
                moving = false;
        }
        else {
            dest_y = rect.y;
            if (rect.x < dest_x)
                rect.x += sprite_speed*4;
            if (rect.x > dest_x)
                rect.x -= sprite_speed*4;
            if (rect.x == dest_x && rect.y == dest_y)
                punched = false;
        }
    }

    protected void gravity(){
        if (!moving) {

            falling = true;
            rect.y -= 0.1f;
            for (int i = 0; i < Main.walls.size; i++) {
                if (Main.walls.get(i).rect.overlaps(rect)) {
                    falling = false;

                    //falling damage
                    if (v_speed > 10) {
                        hp_current -= v_speed * 1.5f;
                    }

                    rect.y = Main.walls.get(i).rect.y + 32;
                    dest_y = rect.y;
                }
                if (!falling) break;
                falling = true;
            }
            if (falling) rect.y += 0.1f;

            if (falling) {
                rect.y -= v_speed;
                dest_y -= v_speed;
                v_speed += 0.25f;
            } else v_speed = 2f;
        }
    }

    public void replenish(){
        move_points_current = move_points_max;
        action_points_current = action_points_max;
    }
}
