package psic07.uvigo.teleco.hexagonwars;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.FloatMath;

import java.util.HashMap;
import java.util.Map;

public class HexagonDrawable extends Drawable {

    public int centerColor = 0x0;
    public static int blueColor = 0xff11D5F7;
    public static int redColor = 0xffF72A86;
    public static int transparent = 0x0;
    public static int defaultBorderColor = 0xFF00FF84;
    public static final float FILL_PERCENTAGE = 0.9f;
    public Vector2 dim;
    private Path hexagon = new Path();
    private Path centerHexagon = new Path();
    private Path temporal = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public HexagonDrawable(int color) {
        paint.setColor(color);
        hexagon.setFillType(Path.FillType.EVEN_ODD);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(hexagon, paint);
        centerPaint.setColor(centerColor);
        canvas.drawPath(centerHexagon,centerPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        int toRet = paint.getAlpha();
        if(toRet == 0){
            return PixelFormat.TRANSPARENT;
        }
        if(toRet == 255){
            return  PixelFormat.OPAQUE;
        }
        if(toRet < 255 && toRet > 0){
            return  PixelFormat.TRANSLUCENT;
        }
        return PixelFormat.UNKNOWN;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        computeHex();
        invalidateSelf();
    }

    public void computeHex() {

        final int width = dim.x;
        final int height = dim.y;
        final int size = Math.min(width, height);
        //centerX and Y is directly width / 2 and height / 2 because it is local canvas
        final int centerX =  (width / 2);
        final int centerY = (height / 2);

        hexagon.reset();
        hexagon.addPath(createHexagon(size, centerX, centerY));
        hexagon.addPath(createHexagon((int) (size * FILL_PERCENTAGE), centerX, centerY));
        centerHexagon.reset();
        centerHexagon.addPath(createHexagon((int) (size * FILL_PERCENTAGE), centerX, centerY));
        centerHexagon.addPath(createHexagon(0,centerX ,centerY));
    }

    public void setBorderColor(int Color) {
        paint.setColor(Color);
    }

    private Path createHexagon(int size, int centerX, int centerY) {
        int radius = size / 2;
        Path hex = temporal;
        hex.reset();
        hex.moveTo(centerX, centerY + radius);
        hex.lineTo(centerX + radius, centerY + radius / 2);
        hex.lineTo(centerX + radius, centerY - radius / 2);
        hex.lineTo(centerX, centerY - radius);
        hex.lineTo(centerX - radius, centerY - radius / 2);
        hex.lineTo(centerX - radius, centerY + radius / 2);
        hex.lineTo(centerX , centerY + radius);

        hex.close();
        return hex;
    }
}
