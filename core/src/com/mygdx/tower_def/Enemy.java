package com.mygdx.tower_def;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
    Rectangle rect;
    int speed;
    int damage;

    public Enemy(float startposx, float startposy, int width, int height, int spd, int dmg){
        speed = spd;
        damage = dmg;
        rect = new Rectangle(startposx, startposy, width, height);
    }

    public void move(){
        rect.y += speed;
    }
}
