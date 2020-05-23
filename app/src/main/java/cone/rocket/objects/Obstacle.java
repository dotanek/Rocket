package cone.rocket.objects;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

import cone.rocket.R;

public abstract class Obstacle {

    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private float x, y;
    private float velX, velY;
    private int sizeX, sizeY;
    private boolean belowVision;

    protected Bitmap model;

    public Obstacle(Context context, int sizeX, int sizeY) {
        this.sizeX = sizeY;
        this.sizeY = sizeX;

        Random rand = new Random();

        velX = rand.nextFloat() * 20 - 10;
        velY = rand.nextFloat() * 20 + 5;
        x = rand.nextFloat() * (screenWidth -  sizeX);
        y = 0 - sizeY;
        belowVision = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(model, x, y, null);
    }

    public void update() {

        x += velX;
        y += velY;

        if (x <= 0 || x >= (screenWidth - sizeX)) {
            velX *= (-1);
        }

        if (y >= screenHeight) {
            belowVision = true;
        }
    }

    public boolean isBelowVision() {
        return belowVision;
    }
}
