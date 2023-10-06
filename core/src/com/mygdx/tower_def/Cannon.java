package com.mygdx.tower_def;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public class Cannon {
    Rectangle cannonRect;
    float degPos;
    Color color;
    ArrayList<CannonBall> cannonBalls;
    int maxProj;

    public Cannon(int x, int y, int width, int height, Color color, float degPos){
        this.degPos = degPos;
        cannonRect = new Rectangle(x, y, width, height);
        this.color = color;
        cannonBalls = new ArrayList<CannonBall>();
        maxProj = 12;
    }
    public Cannon(){
        degPos = 90;
        cannonRect = new Rectangle(400f, 125f, 150, 50);
        this.color = new Color(0, 0, 0, 1);
        cannonBalls = new ArrayList<CannonBall>();
        maxProj = 12;
    }

    public void fire(){
        if(cannonBalls.size()<=maxProj-3){
        int offset = -5;
        for(int i = 0; i<3; i++){
            CannonBall newBall = new CannonBall(degPos, 10);
            cannonBalls.add(newBall);
            newBall.placeBall(degPos, cannonRect.width+5, (int) cannonRect.x, (int) cannonRect.y, offset);
            offset += 5;
    }}
    }
}
