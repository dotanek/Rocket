package cone.rocket;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Rocket {

    private Bitmap image;

    public Rocket(Bitmap bmp) {

        image  = bmp;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 50, 50, null);
    }
}
