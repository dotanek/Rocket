package cone.rocket.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Random;

import cone.rocket.R;

public class Asteroid extends Obstacle {

    public Asteroid(Context context, int sizeX, int sizeY) {
        super(context, sizeX, sizeY);

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

        model = Bitmap.createScaledBitmap(model, sizeX, sizeY, false);
    }
}
