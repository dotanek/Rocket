package cone.rocket.objects;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

import static cone.rocket.Constraints.SCREEN_WIDTH;

public abstract class Obstacle {

    protected float x, y;
    protected float velX, velY;
    protected int diameter;
    protected boolean belowVision;
    protected boolean collided;
    protected int degrees;

    protected Bitmap model;

    public Obstacle(Context context, int diameter, float speedModifier) {
        this.diameter = diameter;

        Random rand = new Random();

        velX = rand.nextFloat() * 20 - 10;
        velY = (rand.nextFloat() * 5 + 10) * speedModifier;
        x = rand.nextFloat() * (SCREEN_WIDTH -  diameter);
        y =  0 - diameter;
        x = 300;
        belowVision = false;
        collided = false;
        degrees = 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public void changeDirectionX() {
        velX = velX * (-1);
    }

    public void draw(Canvas canvas) {
        Matrix rotation = new Matrix();
        rotation.postRotate(degrees, diameter/2, diameter/2);
        rotation.postTranslate(x,y);
        canvas.drawBitmap(model, rotation, null);
        degrees++;
    }

    public void update() {
    }

    public boolean isBelowVision() {
        return belowVision;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
