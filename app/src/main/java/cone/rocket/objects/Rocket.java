package cone.rocket.objects;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import cone.rocket.R;

public class Rocket {

    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private float x, y;

    private int sizeX, sizeY;

    private Bitmap rocket_model;

    public Rocket(Context context) {
        sizeX = (screenWidth/6);
        sizeY = (screenWidth/4);
        x = (screenWidth/2) - (sizeX/2);
        y = screenHeight - sizeY - (sizeY/2);
        rocket_model = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_model_rgb);
        rocket_model = Bitmap.createScaledBitmap(rocket_model, sizeX, sizeY, false);
    }

    public void move(float newX, float newY) {
        float velocityX = (newX - (x + (sizeX/2))) / 10;
        //float velocityY = (newY - (y + (sizeY/2))) / 10;
        x += velocityX;
        //y += velocityY;
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
        canvas.drawBitmap(rocket_model, x, y, null);
    }

    public void update() {


    }
}
