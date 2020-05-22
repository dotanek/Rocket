package cone.rocket;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Background {

    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private int y;
    private int velY;

    private Bitmap image;

    private int fire = 0;

    public Background(Context context) {
        image = BitmapFactory.decodeResource(context.getResources(),R.drawable.background_night_acc);
        image = Bitmap.createScaledBitmap(image, screenWidth, screenHeight*2, false);
        velY = 20;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 0, y - screenHeight, null);
    }

    public void update() {
        y += velY;

        if (y >= screenHeight) {
            y = 0;
        }
    }
}
