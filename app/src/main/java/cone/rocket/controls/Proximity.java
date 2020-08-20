package cone.rocket.controls;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import cone.rocket.GameView;

public class Proximity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private Context context;

    public Proximity(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.e("Proximity:", String.valueOf(event.values[0]));
        if (event.values[0] < 5) {
            GameView.setState(GameView.STATE.MENU);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void close() {
        sensorManager.unregisterListener(this);
    }
}
