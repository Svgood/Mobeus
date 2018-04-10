package com.svgoodmobeus.game;

/**
 * Created by Svgood on 07.07.2017.
 */
public class Functions {

    public static boolean checkDistance(float dist, Character char1, Character char2){
        if (dist >= Math.sqrt(Math.pow(char1.rect.x - char2.rect.x, 2) + Math.pow(char1.rect.y - char2.rect.y, 2)))
            return true;
        else return false;
    }

    public static float getDistance(Character char1, Character char2){
        return (float)Math.sqrt(Math.pow(char1.rect.x - char2.rect.x, 2) + Math.pow(char1.rect.y - char2.rect.y, 2));
    }
}
