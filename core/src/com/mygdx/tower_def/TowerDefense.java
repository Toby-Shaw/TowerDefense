package com.mygdx.tower_def;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Intersector;

public class TowerDefense extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shape;
	OrthographicCamera camera;
	Cannon cannon;
	Enemy[] enemies;
	int enMax;
	int enSpeed = -2;
	BitmapFont font12;
	int score;
	int livesLeft;
	Rectangle foundation;
	
	@Override
	public void create () {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		parameter.color = Color.BLACK;
		font12 = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		camera = new OrthographicCamera();
		cannon = new Cannon();
		camera.setToOrtho(false, 800, 1200);
		enMax = 3;
		livesLeft = enMax;
		enemies = new Enemy[enMax];
		for(int i = 0; i<enMax; i++){
			newEnemy(i);
		}
		foundation = new Rectangle(300, 25, 200, 150);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.9f, 0.9f, 0.9f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font12.draw(batch, "Score: " + score, 50, 50);
		font12.draw(batch, "Lives Left: " + livesLeft, 650, 50);
		batch.end();
		shape.setProjectionMatrix(camera.combined);
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeType.Filled);
		shape.identity();
		shape.setColor(cannon.color);
		Rectangle d = cannon.cannonRect;
		shape.rect(d.x, d.y, 0, 25, d.width, d.height, 1, 1, cannon.degPos);
		shape.setColor(0.5f, 0.5f, 0.5f, 1);
		shape.rect(foundation.x, foundation.y, foundation.width, foundation.height);
		shape.setColor(1f, 0, 0, 1);
		for(CannonBall ball:cannon.cannonBalls){
		shape.circle(ball.coords[0], ball.coords[1], ball.radius);}
		for(Enemy enemy : enemies){
			shape.rect(enemy.rect.x, enemy.rect.y, enemy.rect.width, enemy.rect.height);
		}
		shape.end();

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			if(cannon.degPos<150) cannon.degPos += 2;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) 
			if(cannon.degPos>30) cannon.degPos -= 2;
		if(Gdx.input.isKeyJustPressed(Input.Keys.Q)){
			cannon.fire();
		}
		for(int i = 0; i<enMax; i++){
			enemies[i].move();
		}
		for(Iterator<CannonBall> it = cannon.cannonBalls.iterator(); it.hasNext();){
			CannonBall cannonBall = it.next();
			cannonBall.move();
			float x = cannonBall.coords[0];
			float y = cannonBall.coords[1];
			if(x<-cannonBall.radius||y<-cannonBall.radius||x>800+cannonBall.radius||y>1200+cannonBall.radius){
				it.remove();
				continue;
			}
			for(int i=0; i<enMax; i++){
				if(Intersector.overlaps(cannonBall.circle, enemies[i].rect)){
					newEnemy(i);
					it.remove();
					score ++;
					if(score%20 == 0){
						increaseSpeed();
					}
					break;
				}
			}}
		for(int j=0; j<enMax; j++){
			if(Intersector.overlaps(enemies[j].rect, foundation)){
				newEnemy(j);
				livesLeft --;
				checkLives();
			}
			if(enemies[j].rect.y < 0){
				newEnemy(j);
				livesLeft --;
			}
		}	
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public void newEnemy(int ind){
		enemies[ind] = new Enemy(Math.round(Math.random()*(800-50)), 1200, 50, 50, enSpeed, 10);
	}

	public void checkLives(){
		if(livesLeft<=0){
			dispose();
			System.exit(0);
		}
	}

	public void increaseSpeed(){
		enSpeed -= 1;
	}
}
