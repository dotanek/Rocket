package cone.rocket;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import cone.rocket.buttons.Button;
import cone.rocket.buttons.Switch;
import cone.rocket.objects.Rocket;

import static cone.rocket.Constraints.SCREEN_HEIGHT;
import static cone.rocket.Constraints.SCREEN_WIDTH;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private Rocket rocket;
    private Background background;
    private ObstacleManager obstacleManager;
    private CollisionManager collisionManager;
    private boolean touch = false;
    private boolean gameOver = false;

    private int highScore = 0;


    // ------------------- MENU ---------------------- //

    private Button buttonBanner;
    private Button buttonPlay;
    private Button buttonSettings;
    private Button buttonExit;

    // ------------------- SETTINGS ---------------------- //

    private Switch switchSound;
    private Switch switchVibrations;
    private Switch switchAccelerometer;
    private Switch switchGyroscope;
    private Switch switchLights;
    private Button buttonBack;

    private Button buttonMenu;

    private enum STATE {
        MENU,
        SETTINGS,
        GAME
    };

    private STATE state;

    private float lastX, lastY;

    public GameView(Context context) {
        super(context);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        getHolder().addCallback(this);

        background = new Background(getContext(),false);
        state = STATE.MENU;


        // ------------------- MENU ---------------------- //
        buttonBanner =  new Button(SCREEN_WIDTH/2, SCREEN_HEIGHT/2 - 180, "ROCKET", 150, 0);
        buttonPlay =  new Button(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, "PLAY", 70, 30);
        buttonSettings =  new Button(SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + 150, "SETTINGS", 70, 30);
        buttonExit =  new Button(SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + 300, "EXIT", 70, 30);

        // ------------------- SETTINGS ---------------------- //

        switchSound = new Switch(SCREEN_WIDTH/2,SCREEN_HEIGHT/2 - 300,"SOUND", 70,30);
        switchVibrations = new Switch(SCREEN_WIDTH/2,SCREEN_HEIGHT/2 - 150,"VIBRATIONS", 70,30);
        switchAccelerometer = new Switch(SCREEN_WIDTH/2,SCREEN_HEIGHT/2,"ACCELERPMETER", 70,30);
        switchGyroscope = new Switch(SCREEN_WIDTH/2,SCREEN_HEIGHT/2 + 150,"GYROSCOPE", 70,30);
        switchLights = new Switch(SCREEN_WIDTH/2,SCREEN_HEIGHT/2 + 300,"SMART LIGHTS", 70,30);
        buttonBack = new Button(SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + 450, "BACK", 70, 30);

        buttonMenu =  new Button(110, 80, "MENU", 40, 20);
        buttonMenu.getPaint().setTypeface(Typeface.create("Arial", Typeface.NORMAL));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!thread.isAlive()) {
            thread.setRunning(true);
            thread.start();
        }
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

    public void update() {

        switch (state) {

            case MENU:
                updateMenu();
                break;

            case SETTINGS:
                updateSettings();
                break;

            case GAME:
                updateGame();
                break;
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {

            switch (state) {

                case MENU:
                    drawMenu(canvas);
                    break;

                case SETTINGS:
                    drawSettings(canvas);
                    break;

                case GAME:
                    drawGame(canvas);
                    break;
            }
        }
    }

    private void updateMenu() {
        background.update();
    }

    private void drawMenu(Canvas canvas) {
        background.draw(canvas);
        buttonBanner.draw(canvas);
        buttonPlay.draw(canvas);
        buttonSettings.draw(canvas);
        buttonExit.draw(canvas);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);
        canvas.drawText("Highest level: " + Integer.toString(highScore),SCREEN_WIDTH/2,SCREEN_HEIGHT - 50, textPaint);
    }

    private void updateSettings() {
        background.update();
    }

    private void drawSettings(Canvas canvas) {
        background.draw(canvas);
        switchSound.draw(canvas);
        switchVibrations.draw(canvas);
        switchAccelerometer.draw(canvas);
        switchGyroscope.draw(canvas);
        switchLights.draw(canvas);
        buttonBack.draw(canvas);
    }

    private void updateGame() {

        if (touch) {
            rocket.move(lastX,lastY);
        }

        if (obstacleManager.checkGameOver(rocket)) {
            if(obstacleManager.getLevel() > highScore) {
                highScore = (int) obstacleManager.getLevel();
            }

            background = new Background(getContext(),false);
            state = STATE.MENU;
        }

        background.update();
        obstacleManager.update();
        obstacleManager.checkObstaclesBelow();
        obstacleManager.checkObstaclesSpawn();
    }

    private void drawGame(Canvas canvas) {
        background.draw(canvas);
        rocket.draw(canvas);
        obstacleManager.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText("LEVEL " + Integer.toString((int) obstacleManager.getLevel()), SCREEN_WIDTH - 200, 80, paint);

        if (touch) {
            canvas.drawOval(lastX-50,lastY-50,lastX+50,lastY+50, paint);
        }

        buttonMenu.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent e) {

        switch (state) {

            case MENU:
                menuTouch(e);
                break;

            case SETTINGS:
                settingsTouch(e);
                break;

            case GAME:
                gameTouch(e);
                break;
        }

        return true;
    }

    public void menuTouch(MotionEvent e) {

        boolean isReleased = e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL;

        float x = e.getX();
        float y = e.getY();

        if (isReleased) {
            if (buttonPlay.checkClick(x,y)) {
                background = new Background(getContext(),true);
                rocket = new Rocket(getContext());
                collisionManager = new CollisionManager();
                obstacleManager = new ObstacleManager(getContext());
                obstacleManager.init();
                lastX = 0;
                lastY = 0;
                state = STATE.GAME;
            } else if (buttonSettings.checkClick(x,y)) {
                state = STATE.SETTINGS;
            } else if (buttonExit.checkClick(x,y)) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }


    }

    public void gameTouch(MotionEvent e) {

        boolean isReleased = e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL;
        boolean isPressed = e.getAction() == MotionEvent.ACTION_DOWN;

        lastX = e.getX();
        lastY = e.getY();

        if (isReleased && buttonMenu.checkClick(lastX,lastY)) {
            background = new Background(getContext(),false);
            state = STATE.MENU;
        }

        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 255, 255));

        if (isReleased) {
            touch = false;
        } else if (isPressed) {
            touch = true;
        }
    }

    public void settingsTouch(MotionEvent e) {

        boolean isReleased = e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL;

        float x = e.getX();
        float y = e.getY();

        if (isReleased) {
            if (switchSound.checkClick(x,y)) {
                switchSound.changeActive();
            } else if (switchVibrations.checkClick(x,y)) {
                switchVibrations.changeActive();
            } else if (switchSound.checkClick(x,y)) {
                switchSound.changeActive();
            } else if (switchAccelerometer.checkClick(x,y)) {
                switchAccelerometer.changeActive();
            } else if (switchGyroscope.checkClick(x,y)) {
                switchGyroscope.changeActive();
            } else if (switchLights.checkClick(x,y)) {
                switchLights.changeActive();
            } else if (buttonBack.checkClick(x,y)) {
                state = STATE.MENU;
            }
        }
    }


}
