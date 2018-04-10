package com.svgoodmobeus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Svgood on 29.09.2017.
 */
public class Animations {

    int current_frame;
    boolean last_stop, stop_anim;
    int frame_count;
    float changer, wait;
    Array<Texture> img_holder;

    public Animations(String name, int size){
        img_holder = new Array<Texture>();
        for(int i = 1; i < size; i++){
            img_holder.add(new Texture(name + i + ".png"));
        }
        frame_count = img_holder.size;
        changer = 60/img_holder.size;
        wait = 0;
        current_frame = 0;
        changer = 0;
        last_stop = false;
        stop_anim = false;
    }

    public Animations(String name, int size, float speed){
        img_holder = new Array<Texture>();
        for(int i = 1; i < size; i++){
            img_holder.add(new Texture(name + i + ".png"));
        }
        frame_count = img_holder.size;
        changer = 60/img_holder.size;
        wait = 0;
        current_frame = 0;
        changer = 1*speed;
        last_stop = false;
        stop_anim = false;
    }

    public Animations(String name, int size, float speed, boolean last){
        img_holder = new Array<Texture>();
        for(int i = 1; i < size; i++){
            img_holder.add(new Texture(name + i + ".png"));
        }
        frame_count = img_holder.size;
        changer = 60/img_holder.size;
        wait = 0;
        current_frame = 0;
        changer = 1*speed;
        last_stop = last;
        stop_anim = false;
    }

    public void start_animation(){
        wait = 0;
        current_frame = 0;
    }

    public void play_animation(){
        if (!stop_anim) {
            wait += 1;
            if (wait >= changer) {
                wait = 0;
                current_frame += 1;
                if (current_frame == img_holder.size && last_stop) {
                    current_frame -= 1;
                    stop_anim = true;
                }
                if (current_frame == img_holder.size) {
                    current_frame = 0;
                }
            }
        }
    }

    public Texture current_frame(){
        return img_holder.get(current_frame);
    }
}
