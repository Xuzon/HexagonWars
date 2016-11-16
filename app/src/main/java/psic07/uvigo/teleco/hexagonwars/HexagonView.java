package psic07.uvigo.teleco.hexagonwars;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;
import android.view.View;
import android.graphics.Typeface;

import java.util.Random;

/**
 * Created by danie on 15/11/2016.
 */

public class HexagonView extends View implements View.OnClickListener{
    public HexagonDrawable hexagon;
    Vector2 coords;
    String score="";

    public HexagonView(Context context,Vector2 coords, Vector2 dim, int color, String score) {
        super(context);
        this.coords = coords;
        this.score=score;
        hexagon = new HexagonDrawable(0xff00FF84);
        if(color==0) {
            Random random = new Random();
            int r = random.nextInt(3);
            color = (r == 0) ? hexagon.blueColor : ((r == 1) ? hexagon.redColor : hexagon.transparent);
         }
        hexagon.centerColor = color;
        hexagon.setBounds(coords.x, coords.y, coords.x + dim.x, coords.y + dim.y);
        this.setOnClickListener(this);
    }

    protected void onDraw(Canvas canvas) {
        hexagon.draw(canvas);

        //Pintamos el número de score en los hexágonos de puntuación.
        if(score!="") {
            Paint textPaint = new Paint();
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(54);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(score, coords.x + GameActivity.SIZE / 2, coords.y + GameActivity.SIZE / 2 + 16, textPaint);
        }
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
