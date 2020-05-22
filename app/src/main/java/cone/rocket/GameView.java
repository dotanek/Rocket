package cone.rocket;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.telephony.mbms.MbmsErrors;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.lang.reflect.GenericArrayType;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Rocket rocket;
    private Background background;

    private float lastX, lastY;

    public GameView(Context context) {
        super(context);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        getHolder().addCallback(this);

        background = new Background(getContext());
        rocket = new Rocket(getContext());
        lastX = 0;
        lastY = 0;
    }

    public void update() {

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
            background.update();
            rocket.draw(canvas);
            rocket.update();
        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        rocket.changeX(e.getX());

        return true;
    }
}
