package cone.rocket;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Rocket {

    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public void setX(float x) {

        if (x - (sizeX/2) >= (screenWidth - sizeX)) {
            this.x = (screenWidth - sizeX);
        } else if (x - (sizeX/2) <= 0) {
            this.x = 0;
        } else {
            this.x = x - (sizeX/2);
        }
    }

    public void setY(int y) {
        this.y = y;
    }

    public void changeX(float changeX) {
        float velocity = (changeX - (x + (sizeX/2))) / 5;
        x += velocity;
    }

    public void changeY(float changeY) {
        x += changeY;
    }

    private float x, y;
    private float velX, velY;
    private float accX, accY;
    private boolean dX,dY;

    private int sizeX, sizeY;

    private Bitmap rocket_model;

    public Rocket(Context context) {
        sizeX = (screenWidth/3);
        sizeY = (screenWidth/2);
        x = (screenWidth/2) - (sizeX/2);
        y = screenHeight - sizeY - (sizeY/4);
        rocket_model = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocket_model_rgb);
        rocket_model = Bitmap.createScaledBitmap(rocket_model, sizeX, sizeY, false);
        velX = 10;
        velY = 10;
        accX = 0;
        accY = 0;
        dX = false;
        dY = false;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(rocket_model, x, y, null);


        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 255, 255));
        //canvas.drawOval(changeX, 200, 100+changeX, 300, paint);
    }

    public void update() {


    }
}
