package cone.rocket.objects;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cone.rocket.Constraints;
import cone.rocket.R;

import static cone.rocket.Constraints.SCREEN_HEIGHT;
import static cone.rocket.Constraints.SCREEN_WIDTH;

public class Rocket {
    private float x, y;

    private int sizeX, sizeY;
    private int fireSizeX, fireSizeY;
    private int glowSizeX, glowSizeY;

    private int fire = 0;

    private Bitmap rocket_model;
    private Bitmap glow_model;
    private Bitmap fire_model1, fire_model2;

    private float[][] hitBoxCenters;
    private float[] hitBoxRanges;

    public Rocket(Context context) {
        sizeX = (SCREEN_WIDTH/(6));
        sizeY = (SCREEN_WIDTH/4);
        fireSizeX = sizeX/2;
        fireSizeY = sizeY/2;
        glowSizeX = sizeX + 100;
        glowSizeY = sizeY*2;
        x = (SCREEN_WIDTH/2) - (sizeX/2);
        y = SCREEN_HEIGHT - sizeY - 200;

        hitBoxCenters = new float[3][2];
        hitBoxRanges = new float[3];

        for (int i = 0; i < 3; i++) {
            hitBoxCenters[i][0] = sizeX/2;
        }

        hitBoxCenters[0][1] = sizeY * 0.23f;
        hitBoxCenters[1][1] = sizeY * 0.40f;
        hitBoxCenters[2][1] = sizeY * 0.76f;

        hitBoxRanges[0] = 50;
        hitBoxRanges[1] = 65;
        hitBoxRanges[2] = 80;

        rocket_model = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket);
        rocket_model = Bitmap.createScaledBitmap(rocket_model, sizeX, sizeY, false);

        glow_model = BitmapFactory.decodeResource(context.getResources(), R.drawable.glow);
        glow_model = Bitmap.createScaledBitmap(glow_model, glowSizeX, glowSizeY, false);

        fire_model1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire1);
        fire_model2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire2);
        fire_model1 = Bitmap.createScaledBitmap(fire_model1, fireSizeX, fireSizeY, false);
        fire_model2 = Bitmap.createScaledBitmap(fire_model2, fireSizeX, fireSizeY, false);
    }

    public void move(float newX, float newY) {
        float velocityX = (newX - (x + (sizeX/2))) / 10;
        x += velocityX;
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
        canvas.drawBitmap(glow_model, x+(sizeX/2)-(glowSizeX/2), y+(sizeY/2)-(glowSizeY/2)+50, null);

        if (fire <= 6) {
            canvas.drawBitmap(fire_model1, x+(sizeX/2)-(fireSizeX/2), y+sizeY-10, null);
            fire++;
        } else {
            canvas.drawBitmap(fire_model2, x+(sizeX/2)-(fireSizeX/2), y+sizeY-10, null);
            fire++;

            if (fire >= 12) {
                fire = 0;
            }
        }

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(rocket_model, x, y, null);
        //Hitboxes (Ovals tbh)
        //canvas.drawOval(x+hitBoxCenters[0][0]-hitBoxRanges[0],y + hitBoxCenters[0][1] - hitBoxRanges[0],x+hitBoxCenters[0][0]+hitBoxRanges[0],y + hitBoxCenters[0][1] + hitBoxRanges[0],paint);
        //canvas.drawOval(x+hitBoxCenters[1][0]-hitBoxRanges[1],y + hitBoxCenters[1][1] - hitBoxRanges[1],x+hitBoxCenters[1][0]+hitBoxRanges[1],y + hitBoxCenters[1][1] + hitBoxRanges[1],paint);
        //canvas.drawOval(x+hitBoxCenters[2][0]-hitBoxRanges[2],y + hitBoxCenters[2][1] - hitBoxRanges[2],x+hitBoxCenters[2][0]+hitBoxRanges[2],y + hitBoxCenters[2][1] + hitBoxRanges[2],paint);
    }

    public float[][] getHitBoxCenters() {
        return hitBoxCenters;
    }

    public float[] getHitBoxRanges() {
        return hitBoxRanges;
    }
}
