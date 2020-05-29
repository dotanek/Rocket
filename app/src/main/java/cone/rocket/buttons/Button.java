package cone.rocket.buttons;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import cone.rocket.GameView;

public class Button {

    private int x, y;
    private int textSize;
    private int range;
    private String text;
    private int height, width;
    private int left, top, right, bottom;
    private Paint paint;

    public Button(int x, int y, String text, int textSize, int range) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = textSize;
        this.range = range;

        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        height = bounds.height();
        width = bounds.width();
        left = x-width/2 - range;
        top = y - height - range;
        right = x + width/2 + range;
        bottom = y + range;
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        //canvas.drawRect(left,top,right,bottom, paint); // Uncomment to see the hitbox
        paint.setColor(Color.WHITE);
        canvas.drawText(text, x, y, paint);
    }

    public boolean checkClick(float x, float y) {

        if (x > left && x < right) {
            if (y > top && y < bottom) {
                return true;
            }
        }

        return false;
    }

    public Paint getPaint() {
        return paint;
    }
}
