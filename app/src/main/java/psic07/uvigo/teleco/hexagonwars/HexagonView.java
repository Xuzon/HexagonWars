package psic07.uvigo.teleco.hexagonwars;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;
import android.graphics.Rect;
import android.view.View;
import android.graphics.Typeface;

import java.util.Random;

/**
 * Created by danie on 15/11/2016.
 */

public class HexagonView extends View implements View.OnClickListener{
    public HexagonDrawable hexagon;
    Vector2 coords;
    Vector2 dim;
    String score="";
    public HexagonView(Context context,Vector2 coords, Vector2 dim) {
        super(context);
        this.coords = coords;
        this.dim = dim;
        SetupHexagonDrawable(dim,0);

    }

    public HexagonView(Context context,Vector2 coords, Vector2 dim, int color, String score) {
        super(context);
        this.coords = coords;
        this.score=score;
        this.dim = dim;
        SetupHexagonDrawable(dim, color);
    }

    private void SetupHexagonDrawable(Vector2 dim, int color) {
        hexagon = new HexagonDrawable(0xFF00FF84);
        if(color==0) {
            Random random = new Random();
            int r = random.nextInt(3);
            color = (r == 0) ? hexagon.blueColor : ((r == 1) ? hexagon.redColor : hexagon.transparent);
         }
        hexagon.centerColor = color;
        hexagon.dim = dim;
        hexagon.setBounds(dim.x / 2, dim.y / 2,dim.x,dim.y);
        //only no score hexagons has the skill to change color
        if(score.equals("")) {
            this.setOnClickListener(this);
        }
    }

    public void ConquerMe(){
        hexagon.centerColor = hexagon.centerColor == HexagonDrawable.blueColor ?
                HexagonDrawable.redColor : HexagonDrawable.blueColor;
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        hexagon.draw(canvas);

        //Pintamos el número de score en los hexágonos de puntuación.
        if(!score.equals("")) {
            Paint textPaint = new Paint();
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(54);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            //position is dim.x/2  and dim.y/16 because it is local canvas
            canvas.drawText(score, dim.x / 2, dim.y / 2 + 16, textPaint);
        }
    }


    @Override
    public void onClick(View v) {
        //just for be sure v has to be me but you'll never know XD
        if(v instanceof HexagonView){
            HexagonView hex = (HexagonView) v;
            hex.ConquerMe();
        }
    }
}
