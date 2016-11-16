package psic07.uvigo.teleco.hexagonwars;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;

/**
 * Created by danie on 15/11/2016.
 */

public class HexagonView extends View implements View.OnClickListener{
    public HexagonDrawable hexagon;
    Vector2 coords;
    Vector2 dim;
    public HexagonView(Context context,Vector2 coords, Vector2 dim) {
        super(context);
        this.coords = coords;
        this.dim = dim;
        hexagon = new HexagonDrawable(0xff00FF84);
        Random random = new Random();
        int r = random.nextInt(2);
        int color = (r == 0) ? hexagon.blueColor : hexagon.redColor;
        hexagon.centerColor = color;
        /*Rect rect = new Rect();
        rect.left = coords.x;
        rect.right = coords.x + dim.x;
        rect.top = coords.y;
        rect.bottom = coords.y + dim.y;
        this.setClipBounds(rect);*/
        hexagon.setBounds(dim.x / 2, dim.y / 2,dim.x,dim.y);
        this.setOnClickListener(this);
    }

    public void ConquerMe(){
        hexagon.centerColor = hexagon.centerColor == HexagonDrawable.blueColor ?
                HexagonDrawable.redColor : HexagonDrawable.blueColor;
    }

    protected void onDraw(Canvas canvas) {
        hexagon.draw(canvas);
    }

    @Override
    public void onClick(View v) {
        System.out.println("HOLA DESDE (" + coords.x + "," + coords.y+ ")");
        if(v instanceof HexagonView){
            HexagonView hex = (HexagonView) v;
            hex.ConquerMe();
        }
    }
}
