package cone.rocket;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;

import cone.rocket.controls.Magnetic;
import cone.rocket.controls.Proximity;

public class MainActivity extends Activity {

    private static MediaPlayer mediaPlayer;
    private static Vibrator vibe;
    private static Proximity proximity;
    private static Magnetic magnetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        vibe = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        proximity = new Proximity(getApplicationContext());
        magnetic = new Magnetic(getApplicationContext());

        setContentView(new GameView(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        GameView.getMediaPlayer().stop();
        GameView.getLight().close();
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static Vibrator getVibe() {
        return vibe;
    }
}
