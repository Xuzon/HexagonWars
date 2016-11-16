package psic07.uvigo.teleco.hexagonwars;

import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.FloatMath;

public class HexagonDrawable extends Drawable {

    public int centerColor = 0xffff0000;
    public static int blueColor = 0xff11D5F7;
    public static int redColor = 0xffF72A86;
    public static final int SIDES = 6;
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
        computeHex(bounds);
        invalidateSelf();
    }

    public void computeHex(Rect bounds) {

        final int width = bounds.width();
        final int height = bounds.height();
        final int size = Math.min(width, height);
        final int centerX = bounds.left + (width / 2);
        final int centerY = bounds.top + (height / 2);

        hexagon.reset();
        hexagon.addPath(createHexagon(size, centerX, centerY));
        hexagon.addPath(createHexagon((int) (size * .8f), centerX, centerY));
        centerHexagon.reset();
        centerHexagon.addPath(createHexagon((int) (size * .7f), centerX, centerY));
        centerHexagon.addPath(createHexagon(0,centerX ,centerY));
    }

    private Path createHexagon(int size, int centerX, int centerY) {
        final float section = (float) (2.0 * Math.PI / SIDES);
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
