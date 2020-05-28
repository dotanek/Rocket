package cone.rocket.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class MenuOption {

    private int left;
    private int top;
    private int right;
    private int bottom;
    private int textX, textY;
    private String text;
    private int textSize;
    private Paint paint;

    public MenuOption(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.text = "MENU";
        textX = (left + right) / 2;
        textY = bottom - 20;
        textSize = 40;
        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        //canvas.drawRect(left, top, right, bottom, paint); // hitbox
        paint.setColor(Color.WHITE);
        canvas.drawText(text, textX, textY, paint);
    }

    public boolean checkClick(float x, float y) {

        if (x > left && x < right) {
            if (y > top && y < bottom) {
                return true;
            }
        }

        return false;
    }
}
