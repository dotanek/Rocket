package cone.rocket.controls;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import cone.rocket.objects.Rocket;

public class AccelerometerController extends Controller implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    private enum MOVEMENT_STAGE {
        STATIC,
        ACCELERATION,
        RECOIL,
        RECOVERY
    };

    MOVEMENT_STAGE stage;

    private float peak;
    private boolean sign;

    public AccelerometerController(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, sensor , SensorManager.SENSOR_DELAY_NORMAL);
        stage = MOVEMENT_STAGE.STATIC;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float acc = event.values[0];

        switch (stage) {

            case STATIC:
                if (acc > 10) {
                    stage = MOVEMENT_STAGE.ACCELERATION;
                    sign = false;
                    peak = 0;
                } else if (acc < -10) {
                    stage = MOVEMENT_STAGE.ACCELERATION;
                    sign = true;
                    peak = 0;
                }
                break;

            case ACCELERATION:
                if(!sign) {
                    if (acc > peak) {
                        peak = acc;
                        //change = acc
                        //this.update();
                    } else {
                        stage = MOVEMENT_STAGE.RECOIL;
                        rocket.addMomentum(peak*3);
                    }
                } else {
                    if (acc < peak) {
                        peak = acc;
                        //change = acc;
                        //this.update();
                    } else {
                        stage = MOVEMENT_STAGE.RECOIL;
                        rocket.addMomentum(peak*3);
                    }
                }
                break;

            case RECOIL:
                if(!sign) {
                    if (acc < peak) {
                        peak = acc;
                    } else {
                        stage = MOVEMENT_STAGE.RECOVERY;
                    }
                } else {
                    if (acc > peak) {
                        peak = acc;
                    } else {
                        stage = MOVEMENT_STAGE.RECOVERY;
                    }
                }
                break;

            case RECOVERY:
                if (Math.abs(acc) < 1) {
                    stage = MOVEMENT_STAGE.STATIC;
                }
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
