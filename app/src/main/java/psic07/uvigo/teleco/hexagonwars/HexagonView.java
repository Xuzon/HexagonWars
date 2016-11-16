package psic07.uvigo.teleco.hexagonwars;


import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * Created by danie on 15/11/2016.
 */

public class HexagonView extends View implements View.OnClickListener{
    public HexagonDrawable hexagon;
    Vector2 coords;

    public HexagonView(Context context,Vector2 coords, Vector2 dim, int color) {
        super(context);
        this.coords = coords;
        hexagon = new HexagonDrawable(0xff00FF84);
        if(color==0) {
            Random random = new Random();
            int r = random.nextInt(2);
            color = (r == 0) ? hexagon.blueColor : hexagon.redColor;
         }
        hexagon.centerColor = color;
        hexagon.setBounds(coords.x, coords.y, coords.x + dim.x, coords.y + dim.y);
        this.setOnClickListener(this);
    }

    protected void onDraw(Canvas canvas) {
        hexagon.draw(canvas);
    }

    public String debug(){
        return "HOLA DESDE (" + coords.x + "," + coords.y+ ")";
    }

    @Override
    public void onClick(View v) {
        HexagonView hex = (HexagonView) v;
        System.out.println(hex.debug());
    }
}
