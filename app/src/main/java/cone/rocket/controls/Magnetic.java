package cone.rocket.controls;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import cone.rocket.GameView;

public class Magnetic extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private Context context;

    public Magnetic(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (Math.abs(event.values[0]) + Math.abs(event.values[1]) + Math.abs(event.values[2]) > 800) {
            if (GameView.getState() == GameView.STATE.MENU) {
                GameView.setState(GameView.STATE.SETTINGS);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void close() {
        sensorManager.unregisterListener(this);
    }
}
