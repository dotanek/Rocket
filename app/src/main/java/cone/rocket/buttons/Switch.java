package cone.rocket.buttons;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class Switch {

    private int x, y;
    private int leftX, rightX;
    private int textSize;
    private int range;
    private String textLeft;
    private String textRight;
    private boolean active = false;
    private int height, width;
    private int left, top, right, bottom;
    private Paint paint;

    public Switch(int x, int y, String text, int textSize, int range) {
        this.x = x;
        this.y = y;
        this.textLeft = text;
        this.textRight = "OFF";
        this.textSize = textSize;
        this.range = range;

        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);

        Rect boundsLeft = new Rect();
        Rect boundsRight = new Rect();

        paint.getTextBounds(textLeft, 0, textLeft.length(), boundsLeft);
        paint.getTextBounds(textRight, 0, textRight.length(), boundsRight);

        leftX = x - boundsRight.width()/2 - 30;
        rightX = x + boundsLeft.width()/2 + 30;

        height = boundsRight.height();
        width = boundsRight.width();

        left = rightX-width/2 - range;
        top = y - height - range;
        right = rightX + width/2 + range;
        bottom = y + range;
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        //canvas.drawRect(left,top,right,bottom, paint); // Uncomment to see the hitbox
        paint.setColor(Color.WHITE);
        canvas.drawText(textLeft, leftX, y, paint);

        if (!active) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.GREEN);
        }

        canvas.drawText(textRight, rightX, y, paint);
        paint.setColor(Color.WHITE);
    }

    public boolean checkClick(float x, float y) {

        if (x > left && x < right) {
            if (y > top && y < bottom) {
                return true;
            }
        }

        return false;
    }

    public void changeActive() {
        if (active) {
            active = false;
            textRight = "OFF";
        } else {
            active = true;
            textRight = "ON";
        }
    }
}
