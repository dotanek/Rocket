package cone.rocket;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import static cone.rocket.Constraints.SCREEN_HEIGHT;
import static cone.rocket.Constraints.SCREEN_WIDTH;

public class Background {

    private int y;
    private float velY;

    private Bitmap image;

    private boolean isPlaying;

    public Background(Context context, boolean isPlaying) {
        this.isPlaying = isPlaying;

        if (isPlaying) {
            image = BitmapFactory.decodeResource(context.getResources(),R.drawable.background_dynamic);
        } else {
            image = BitmapFactory.decodeResource(context.getResources(),R.drawable.background_static);
        }

        image = Bitmap.createScaledBitmap(image, SCREEN_WIDTH, SCREEN_HEIGHT*2, false);
        velY = 20;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 0, y - SCREEN_HEIGHT, null);
    }

    public void update() {

        if (isPlaying) {
            y += velY;
            velY += 0.005;
        } else {
            y += 2;
        }

        if (y >= SCREEN_HEIGHT) {
            y = 0;
        }
    }
}
