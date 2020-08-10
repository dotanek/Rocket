package cone.rocket.controls;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;

public class Light extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private Context context;

    public Light(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.d("Light: " , String.valueOf(event.values[0]));
        if (event.values[0] < 50) {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 50);
        } else if (event.values[0] < 100) {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 125);
        } else if (event.values[0] < 200) {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 190);
        } else {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void close() {
        sensorManager.unregisterListener(this);
    }

}
