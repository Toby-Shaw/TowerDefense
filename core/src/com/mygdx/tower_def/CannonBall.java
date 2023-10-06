package com.mygdx.tower_def;
import com.badlogic.gdx.math.Circle;

public class CannonBall {
    private float degPos;
    public float radius;
    public Circle circle;
    public float[] coords = {0, 0};
    public float[] velocity = {0, 0};
    private int topVel = 10;

    public CannonBall(){
        degPos = 0;
        radius = 10;
        circle = new Circle(0, 0, radius);
    }
    public CannonBall(float deg, float radius, int x, int y, float[] initVel){
        degPos = deg;
        this.radius = radius;
        coords[0] = x;
        coords[1] = y;
        velocity = initVel;
        circle = new Circle(coords[0], coords[1], radius);
    }
    public CannonBall(float deg, float radius){
        degPos = deg;
        this.radius = radius;
        circle = new Circle(coords[0], coords[1], radius);
    }

    public void placeBall(float deg, float distance, int ox, int oy, float degOffset){
        degPos = deg + degOffset;
        double horiz = Math.cos(degPos/180*Math.PI);
        double vert = Math.sin(degPos/180*Math.PI);
        coords[0] = (float) horiz*distance + ox;
        coords[1] = (float) vert*distance + oy + 25;
        velocity[0] = (float) horiz*topVel;
        velocity[1] = (float) vert*topVel;
    }
    public void move(){
        coords[0] += velocity[0];
        coords[1] += velocity[1];
        circle.x = coords[0];
        circle.y = coords[1];
    }
}
