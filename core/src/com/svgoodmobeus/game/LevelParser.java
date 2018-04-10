package com.svgoodmobeus.game;

import com.badlogic.gdx.utils.Array;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Svgood on 16.06.2017.
 */
public class LevelParser {

    public static void parseFile(Array<Wall> walls){
        Array<String> lines = readFile("/level.txt");
        String[] separated;
        for (int i = 0; i < lines.size; i++){
            separated = lines.get(i).split(";");
            walls.add(new Wall(Integer.parseInt(separated[0]), Integer.parseInt(separated[1])));
            System.out.println("Wall Added at x:" + Integer.parseInt(separated[0]) + " and y:" + Integer.parseInt(separated[1]) );
        }
    }

    public static Array<String> readFile(String path){
        String filename = System.getProperty("user.dir") + path;
        Array<String> records = new Array<String>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.add(line);
            }
            reader.close();
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
        }
        return records;
    }

    public static void saveLevel(){
        String filename = System.getProperty("user.dir") + "/level.txt";
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < Main.walls.size; i++){
                writer.println((int)Main.walls.get(i).rect.x + ";" + (int)Main.walls.get(i).rect.y);
            }
            writer.close();
            } catch (IOException err) {
            System.out.println(err);
            }

    }
}
