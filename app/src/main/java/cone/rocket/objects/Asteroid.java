package cone.rocket.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import cone.rocket.R;

import static cone.rocket.Constraints.SCREEN_HEIGHT;
import static cone.rocket.Constraints.SCREEN_WIDTH;

public class Asteroid extends Obstacle {

    public Asteroid(Context context, int diameter, float speedModifier) {
        super(context, diameter, speedModifier);

        Random rand = new Random();
        int whichModel = (rand.nextInt(100) + 1) % 3 + 1;

        switch (whichModel) {
            case 1:
                model = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle_asteroid1);
                break;

            case 2:
                model = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle_asteroid2);
                break;

            case 3:
                model = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle_asteroid3);
                break;
        }

        model = Bitmap.createScaledBitmap(model, diameter, diameter, false);
    }

    public void update() {
        x += velX;
        y += velY;

        if (x <= 0 || x >= (SCREEN_WIDTH - diameter)) {
            velX *= (-1);
        }

        if (y >= SCREEN_HEIGHT) {
            belowVision = true;
        }
    }
}
