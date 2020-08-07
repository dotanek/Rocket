package cone.rocket;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private static MediaPlayer mediaPlayer;
    private static Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(new GameView(this));

        mediaPlayer = MediaPlayer.create(this, R.raw.dupa);
        vibe = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

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
