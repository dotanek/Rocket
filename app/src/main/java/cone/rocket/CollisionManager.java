package cone.rocket;

import android.util.Log;

import cone.rocket.objects.Obstacle;
import cone.rocket.objects.Rocket;

public class CollisionManager {
    public static boolean orCollision(Obstacle o, Rocket r) {
        float or = o.getDiameter() / 2;
        float ocx = o.getX() + or;
        float ocy = o.getY() + or;

        float[][] centerArray = r.getHitBoxCenters();
        float[] radiusArray = r.getHitBoxRanges();
        float rx = r.getX();
        float ry = r.getY();

        for (int i = 0; i < 3; i++) {
            float rcx = rx + centerArray[i][0];
            float rcy = ry + centerArray[i][1];
            float rr = radiusArray[i];

            float distance = (float) Math.sqrt(Math.pow(ocx - rcx, 2) + Math.pow(ocy - rcy, 2));

            if (distance <= or + rr) {
                return true;
            }
        }

        return false;
    }
}
