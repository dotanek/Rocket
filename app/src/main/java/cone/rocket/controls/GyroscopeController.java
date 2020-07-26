package cone.rocket.controls;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import cone.rocket.objects.Rocket;

public class GyroscopeController extends Controller implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    public GyroscopeController(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (rocket == null) {
            return;
        }

        float xPosition = event.values[1];
        float velX = 0;

        if (Math.abs(xPosition) > 0.05 && Math.abs(xPosition) < 0.70) {
            velX = xPosition * 100;

        } else if (Math.abs(xPosition) >= 0.70) {
            velX =  70 * ((xPosition > 0) ? 1 : -1);
        }

        rocket.setVelX(velX);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void close() {
        sensorManager.unregisterListener(this);
    }
}
