package cone.rocket;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cone.rocket.objects.Asteroid;
import cone.rocket.objects.Obstacle;
import cone.rocket.objects.Rocket;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private Rocket rocket;
    private Background background;
    private ArrayList<Obstacle> obstacles;

    private boolean touch = false;
    private boolean gameOver = false;

    private float lastX, lastY;

    public GameView(Context context) {
        super(context);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        getHolder().addCallback(this);

        background = new Background(getContext());
        rocket = new Rocket(getContext());
        obstacles = new ArrayList<Obstacle>();
        obstacles.add(new Asteroid(getContext(),200,200));
        obstacles.add(new Asteroid(getContext(),200,200));
        obstacles.add(new Asteroid(getContext(),200,200));

        lastX = 0;
        lastY = 0;
    }

    public void collisionObstaclesControl() {

        for (int i = 0; i < obstacles.size(); i++) {
            for (int j = 0; j < obstacles.size(); j++) {
                if (i != j){
                    Obstacle first = obstacles.get(i);
                    Obstacle second = obstacles.get(j);

                    double distance = Math.sqrt(Math.pow(first.getX() - second.getX(),2) + Math.pow(first.getY() - second.getY(),2));

                    if (distance <= 200.0) {
                        obstacles.remove(first);
                        obstacles.remove(second);
                        obstacles.add(new Asteroid(getContext(),200,200));
                        obstacles.add(new Asteroid(getContext(),200,200));
                        i -= 1;
                        j -= 1;
                    }
                }
            }
        }
    }

    public boolean collisionRocketControl() {

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);

            float oLeftX = obstacle.getX();
            float oRightX = oLeftX + obstacle.getSizeX();
            float oTopY = obstacle.getY();
            float oBottomY = oTopY + obstacle.getSizeY();

            float rLeftX = rocket.getX() + 50;
            float rRightX = rLeftX + rocket.getSizeX() - 100;
            float rTopY = rocket.getY();
            float rBottomY = rTopY + rocket.getSizeY();

            if (oLeftX <= rRightX && oRightX >= rLeftX ) {
                if (oTopY <= rBottomY && oBottomY >= rTopY) {
                    return true;
                }
            }
        }

        return false;
    }

    public void update() {

        if (gameOver) {
            background = new Background(getContext());
            rocket = new Rocket(getContext());
            obstacles = new ArrayList<Obstacle>();
            obstacles.add(new Asteroid(getContext(),200,200));
            obstacles.add(new Asteroid(getContext(),200,200));
            obstacles.add(new Asteroid(getContext(),200,200));
            gameOver = false;
            return;
        }

        background.update();
        rocket.update();

        if (touch) {
            rocket.move(lastX,lastY);
        }

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle temp = obstacles.get(i);
            temp.update();
            if (temp.isBelowVision()) {
                obstacles.remove(temp);
                i -= 1;
                obstacles.add(new Asteroid(getContext(),200,200));
            }
        }

        //collisionObstaclesControl();
        gameOver = collisionRocketControl();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {

            background.draw(canvas);
            rocket.draw(canvas);

            if (touch) {
                Paint paint = new Paint();
                paint.setColor(Color.rgb(255, 255, 255));
                canvas.drawOval(lastX-50,lastY-50,lastX+50,lastY+50, paint);
            }

            for (int i = 0; i < obstacles.size(); i++) {
                Obstacle temp = obstacles.get(i);
                temp.draw(canvas);
            }

            if (gameOver) {
                Log.wtf("MyApp","Game Over!");
            }

        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        boolean isReleased = e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL;
        boolean isPressed = e.getAction() == MotionEvent.ACTION_DOWN;

        lastX = e.getX();
        lastY = e.getY();

        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 255, 255));

        if (isReleased) {
            touch = false;
        } else if (isPressed) {
            touch = true;
        }

        return true;
    }
}
