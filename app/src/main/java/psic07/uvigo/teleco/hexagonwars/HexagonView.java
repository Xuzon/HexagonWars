package psic07.uvigo.teleco.hexagonwars;


import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * Created by danie on 15/11/2016.
 */

public class HexagonView extends View {
    public HexagonDrawable hexagon;

    public HexagonView(Context context,Vector2 coords, Vector2 dim) {
        super(context);
        hexagon = new HexagonDrawable(0xff00FF84);
        Random random = new Random();
        int r = random.nextInt(2);
        int color = (r == 0) ? hexagon.blueColor : hexagon.redColor;
        hexagon.centerColor = color;
        hexagon.setBounds(coords.x, coords.y, coords.x + dim.x, coords.y + dim.y);
    }

    protected void onDraw(Canvas canvas) {
        hexagon.draw(canvas);
    }
}
