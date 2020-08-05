package cone.rocket.controls;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.WindowManager;

public class Light extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private WindowManager.LayoutParams lp;

    public Light(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        lp = getWindow().getAttributes();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.e("Light: " , String.valueOf(event.values[0]));

        if (event.values[0] < 50) {
            lp.screenBrightness = 10;
            getWindow().setAttributes(lp);
        } else if (event.values[0] < 100) {
            lp.screenBrightness = 30;
            getWindow().setAttributes(lp);
        } else if (event.values[0] < 200) {
            lp.screenBrightness = 50;
            getWindow().setAttributes(lp);
        } else {
            lp.screenBrightness = 80;
            getWindow().setAttributes(lp);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    public void close() {
        sensorManager.unregisterListener(this);
    }

}
