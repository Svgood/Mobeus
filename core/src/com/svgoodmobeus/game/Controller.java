package com.svgoodmobeus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Svgood on 16.06.2017.
 */
public class Controller {
    public static Texture your_turn = new Texture("your_turn.png");
    public static Texture enemies_turn = new Texture("enemies_turn.png");

    public static int currentCharacter = 1;
    public static Character currentChar;
    public static String currentTurn = "Player";

    public static float wait = 1.5f;
    public static float y = 0;
    public static float vspeed = 3;
    public static float cam_time;

    public static boolean enemieTurnBegins = true;
    public static boolean yourTurnBegins = true;
    public static boolean moveCamera = false;
    public static boolean touched = false;
    public static Character mouse_target;

    public static float camVecX, camVecY, camX, camY;

    public static void replenish(){
        wait = 1.5f;
        y = 0;
        vspeed = 3;
    }

    public static void events(SpriteBatch batch){
        turnControll();
        cameraMovement();
        enemiesTurnControll();
        showEnemiesTurn(batch);
        showYourTurn(batch);
        check_mouse_collision();
        touchControll();
    }

    public static void check_mouse_collision(){
        for(int i = 0; i < Main.characters.size; i++) {
            if (Main.characters.get(i).rect.contains(Controller.getTouchX(), Controller.getTouchY())) {
                mouse_target = Main.characters.get(i);
                break;
            }
            else mouse_target = null;
        }

    }

    public static void showEnemiesTurn(SpriteBatch batch){
        if (!enemieTurnBegins) {
            wait -= Gdx.graphics.getDeltaTime();
            batch.draw(enemies_turn, 0, y);
            if (wait < 0) {
                y -= vspeed;
                vspeed += 0.5;
            }
            if (y < -400) {
                enemieTurnBegins = true;
                replenish();
            }
        }
    }
    public static void showYourTurn(SpriteBatch batch){
        if (!yourTurnBegins) {
            wait -= Gdx.graphics.getDeltaTime();
            batch.draw(your_turn, 0, y);
            if (wait < 0) {
                y -= vspeed;
                vspeed += 0.5;
            }
            if (y < -400) {
                yourTurnBegins = true;
                replenish();
            }
        }
    }

    public static void enemiesTurn(){
        proc_buffs_and_debuffs();
        currentTurn = "AI";
        enemieTurnBegins = false;
    }
    public static void yourTurn(){
        proc_buffs_and_debuffs();
        currentTurn = "Player";
        yourTurnBegins = false;
    }

    public static void proc_buffs_and_debuffs(){
        for (int i = 0; i < Main.characters.size; i++){
            Main.characters.get(i).proc_debuff();
            Main.characters.get(i).proc_buff();
        }
    }

    public static void turnControll(){
        if (Gdx.input.isKeyPressed(Input.Keys.F1) || (Gdx.input.isTouched() && UI.rect_portrait1.contains(getTouchX(), getTouchY()))){
            Controller.currentCharacter = 1;
            Controller.currentChar = Main.characters.get(0);
            setCamVec();

        }
        if (Gdx.input.isKeyPressed(Input.Keys.F2) || (Gdx.input.isTouched() && UI.rect_portrait2.contains(getTouchX(), getTouchY()))){
            Controller.currentCharacter = 2;
            Controller.currentChar = Main.characters.get(1);
            setCamVec();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F3) || (Gdx.input.isTouched() && UI.rect_portrait3.contains(getTouchX(), getTouchY()))){
            Controller.currentCharacter = 3;
            Controller.currentChar = Main.characters.get(2);
            setCamVec();
        }

        if (Gdx.input.isTouched() && UI.rect_endturn.contains(getTouchX(), getTouchY()) && currentTurn.equals("Player") && yourTurnBegins){
            Main.characters.get(0).replenish();
            Main.characters.get(1).replenish();
            Main.characters.get(2).replenish();
            Controller.enemiesTurn();
        }
    }

    public static void enemiesTurnControll(){
        if (currentTurn.equals("AI")) {
            for (int i = 0; i < Main.characters.size; i++)
                if (Main.characters.get(i).type.equals("AI") && !Main.characters.get(i).finished)
                    return;
            for (int i = 0; i < Main.characters.size; i++){
                if (Main.characters.get(i).type.equals("AI"))
                    Main.characters.get(i).replenish();
            }
            Controller.yourTurn();
        }
    }

    public static void setCamVec(){
        if (!moveCamera) cam_time = 60;
        if (cam_time < 2) cam_time = 30;
        camVecX = Math.abs(currentChar.rect.x - Main.camera.position.x)/cam_time;
        camVecY = Math.abs(currentChar.rect.y - Main.camera.position.y)/cam_time;
        if (currentChar.rect.x < Main.camera.position.x)
            camVecX = -camVecX;
        if (currentChar.rect.y < Main.camera.position.y)
            camVecY = -camVecY;
        moveCamera = true;

    }

    public static void cameraMovement(){
        if (moveCamera){
            Main.camera.position.set(camX, camY, 0);
            camX += camVecX;
            camY += camVecY;
            Main.camera.update();
            cam_time -= 1;
            setCamVec();
            if (Math.abs(camX-currentChar.rect.x) < 1f && Math.abs(camY- currentChar.rect.y) < 1f) {
                Main.camera.position.set(currentChar.rect.x, currentChar.rect.y, 0);
                moveCamera = false;
            }
        }
    }

    public static float getTouchY(){
        Vector3 vec = Main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return (vec.y);
    }

    public static float getTouchX(){
        Vector3 vec = Main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return (vec.x);
    }

    public static void touchControll(){
        if (Gdx.input.isTouched()) touched = true;
        else touched = false;
    }
}
