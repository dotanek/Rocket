package cone.rocket.controls;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

import cone.rocket.objects.Rocket;

public class Controller {

    protected float change;
    private Rocket rocket;

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public void readPosition() {
    }

    public void update() {
        if (rocket != null) {
            rocket.updateX(change);
        }
    }
}
