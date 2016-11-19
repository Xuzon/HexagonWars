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
    int posX;
    int posY;
    boolean hexScore = false; //Indica si el Hexagono es de partida o de Score
    int score = 0;
    static int turnColor; //Color del que tiene el turno.
    static int noturnColor; //Color del que no tiene el turno.


    public HexagonView(Context context,Vector2 coords, Vector2 dim, int posX, int posY) {
        super(context);
        turnColor = GameActivity.bottomPlayerColor;
        this.coords = coords;
        this.dim = dim;
        this.posX = posX;
        this.posY = posY;
        GameActivity.gridList[posX][posY] = this;
        SetupHexagonDrawable(dim,hexagon.transparent);

    }

    public HexagonView(Context context,Vector2 coords, Vector2 dim, int color, boolean hexScore) {
        super(context);
        turnColor = GameActivity.bottomPlayerColor;
        this.hexScore = hexScore;
        this.coords = coords;
        this.dim = dim;
        SetupHexagonDrawable(dim, color);
    }

    private void SetupHexagonDrawable(Vector2 dim, int color) {
        hexagon = new HexagonDrawable(0xFF00FF84);
        /*if(color==0) {
            Random random = new Random();
            int r = random.nextInt(3);
            color = (r == 0) ? hexagon.blueColor : ((r == 1) ? hexagon.redColor : hexagon.transparent);
         }*/
        hexagon.centerColor = color;
        hexagon.dim = dim;
        hexagon.setBounds(dim.x / 2, dim.y / 2,dim.x,dim.y);
        //only no score hexagons has the skill to change color
        if(!hexScore) {
            this.setOnClickListener(this);
        }
    }

    public void ConquerMe(){
        if(hexagon.centerColor==HexagonDrawable.transparent) {
            hexagon.centerColor = turnColor;
            //testConquer();
            if(turnColor == HexagonDrawable.blueColor) {
                turnColor = HexagonDrawable.redColor;
                GameActivity.topPlayerScore.score++;
                GameActivity.topPlayerScore.invalidate();
            }
            else {
                turnColor = HexagonDrawable.blueColor;
                GameActivity.bottomPlayerScore.score++;
                GameActivity.bottomPlayerScore.invalidate();
            }
            this.invalidate();
        }
    }

    protected void onDraw(Canvas canvas) {
        hexagon.draw(canvas);

        //Pintamos el número de score en los hexágonos de puntuación.
        if(hexScore) {
            Paint textPaint = new Paint();
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(54);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            //position is dim.x/2  and dim.y/16 because it is local canvas
            canvas.drawText(score+"", dim.x / 2, dim.y / 2 + 16, textPaint);
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

    public void testConquerArround() {
        int numArround = 0;
        int cols = GameActivity.HEXAGONS_PER_ROW;
        if(posX>1 && GameActivity.gridList[posX-2][posY].hexagon.centerColor == turnColor)
            numArround++;
        if(posX<cols-1 && GameActivity.gridList[posX+2][posY].hexagon.centerColor == turnColor)
            numArround++;
        if(GameActivity.gridList[posX-1][posY-1].hexagon.centerColor == turnColor)
            numArround++;
        if(GameActivity.gridList[posX+1][posY-1].hexagon.centerColor == turnColor)
            numArround++;
        if(GameActivity.gridList[posX-1][posY+1].hexagon.centerColor == turnColor)
            numArround++;
        if(GameActivity.gridList[posX+1][posY+1].hexagon.centerColor == turnColor)
            numArround++;

        if(numArround>3) {
            this.hexagon.centerColor = turnColor;
            this.invalidate();
        }
    }

    public void testConquer() {
        if(posX>1 && GameActivity.gridList[posX-2][posY].hexagon.centerColor == noturnColor)
            GameActivity.gridList[posX-2][posY].testConquerArround();
    }
}
