package cone.rocket;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import cone.rocket.objects.Asteroid;
import cone.rocket.objects.Obstacle;
import cone.rocket.objects.Rocket;

public class ObstacleManager {

    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private ArrayList<Obstacle> obstacles;
    private Obstacle freshObstacle;
    private Context context;
    private float level;

    ObstacleManager(Context context) {
        level = 1;
        this.context = context;;
        obstacles = new ArrayList<Obstacle>();
    }

    public Obstacle getObstacle(int index) {
        return obstacles.get(index);
    }

    public void init() {
        freshObstacle = new Asteroid(context,200,1);
        obstacles.add(freshObstacle);
    }

    public void checkObstaclesBelow() {
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).isBelowVision()) {
                obstacles.remove(i);
            }
        }
    }

    public void checkObstaclesSpawn() {
        if (freshObstacle.getY() >= screenHeight/(2)) {
            freshObstacle = new Asteroid(context,200, 1 + level/10);
            obstacles.add(freshObstacle);
        }
        level += 0.005;
    }

    public boolean checkGameOver(Rocket r) {
        for (Obstacle obstacle : obstacles) {
            if (CollisionManager.orCollision(obstacle,r)) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        for(Obstacle obstacle : obstacles) {
            obstacle.update();
        }
    }

    public void draw(Canvas canvas) {
        for( Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }
    }

    public float getLevel() {
        return level;
    }
}
