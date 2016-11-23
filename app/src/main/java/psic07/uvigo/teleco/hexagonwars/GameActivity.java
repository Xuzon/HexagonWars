package psic07.uvigo.teleco.hexagonwars;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.WindowManager;
import android.util.DisplayMetrics;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    RelativeLayout layout;
    public static int SIZE = 150;
    public final static int HEXAGONS_PER_ROW = 7;
    public final static int ROWS = 9;
    public static HexagonView topPlayerScore;
    public static HexagonView bottomPlayerScore;
    public static int gridYOffset = 400;
    public static int gridXOffset = 0;
    public static ArrayList<HexagonView> grid;
    public boolean superTokenOn = false;
    public ImageView imgTokenTopL, imgTokenBottomL, imgTokenTopR, imgTokenBottomR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Calculo del centro del grid.
        gridYOffset = (int) ((InitActivity.screenHeight-((3f/4f)*SIZE*ROWS))/2);
        gridXOffset = (int) ((InitActivity.screenWidth-(SIZE*HEXAGONS_PER_ROW))/2);

        //Ocultar barra notificaciones Android
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        layout = new RelativeLayout(this);
        SetBackground();
        grid = CreateGrid();
        ShowGrid();
        addScore();
        setContentView(layout);
    }

    private void SetBackground() {
        ImageView background = new ImageView(this);
        background.setBackgroundResource(R.drawable.gamebackground);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(InitActivity.screenWidth, InitActivity.screenHeight);
        params.leftMargin = 0;
        params.topMargin = 0;
        layout.addView(background,params);
    }

    private ArrayList<HexagonView> CreateGrid() {
        ArrayList<HexagonView> toRet = new ArrayList<HexagonView>();
        Vector2 hexagonDimension = new Vector2(SIZE,SIZE);
        //float xSizeMultiplier = 1f - (1f - HexagonDrawable.FILL_PERCENTAGE );
        float xSizeMultiplier = 1;
        float ySizeMultiplier = 3f / 4f;
        int xPos = (int) (SIZE * xSizeMultiplier);
        int yPos = (int) (SIZE * ySizeMultiplier);
            for(int i = 0; i < ROWS; i++) {
                for(int j = 0; j < HEXAGONS_PER_ROW ; j++) {
                    boolean notOdd = (i % 2 == 0) ? true : false;
                int oddOffset = (notOdd) ? 0 : ((InitActivity.screenWidth-(SIZE*(HEXAGONS_PER_ROW-1)))/2);
                if(!notOdd && j == (HEXAGONS_PER_ROW - 1)){
                    continue;
                }
                Vector2 coords = new Vector2(gridXOffset + oddOffset + j * xPos,gridYOffset + i * yPos);
                HexagonView hexagon = new HexagonView(this,coords,hexagonDimension, j, i, toRet.size());
                toRet.add(hexagon);
            }
        }
        return toRet;
    }

    private void addScore() {
        int offsetx = (InitActivity.screenWidth-SIZE)/2;
        int offsetyH1 = 100;
        int offsetyH2 = InitActivity.screenHeight-SIZE-100;
        Vector2 hexagonDimension = new Vector2(SIZE,SIZE);
        Vector2 coords = new Vector2(offsetx, offsetyH1);
        topPlayerScore = new HexagonView(this,coords,hexagonDimension, InitActivity.topPlayerColor, true);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(SIZE,SIZE);
        params.leftMargin = offsetx;
        params.topMargin = offsetyH1;
        layout.addView(topPlayerScore,params);

        hexagonDimension = new Vector2(SIZE,SIZE);
        coords = new Vector2(offsetx, offsetyH2);
        bottomPlayerScore = new HexagonView(this,coords,hexagonDimension, InitActivity.bottomPlayerColor, true);
        params = new RelativeLayout.LayoutParams(SIZE,SIZE);
        params.leftMargin = offsetx;
        params.topMargin = offsetyH2;
        layout.addView(bottomPlayerScore,params);

    }

    private void ShowGrid(){
        for(HexagonView hexagon : grid){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(hexagon.dim.x, hexagon.dim.y);
            params.leftMargin = hexagon.coords.x;
            params.topMargin = hexagon.coords.y;
            layout.addView(hexagon,params);
        }
    }


    public void onWindowFocusChanged (boolean hasFocus) {
        imgTokenTopR = new ImageView((this));
        imgTokenTopR.setImageResource(R.drawable.missile);
        imgTokenTopL = new ImageView((this));
        imgTokenTopL.setImageResource(R.drawable.missile);
        imgTokenBottomR = new ImageView((this));
        imgTokenBottomR.setImageResource(R.drawable.missile);
        imgTokenBottomL = new ImageView((this));
        imgTokenBottomL.setImageResource(R.drawable.missile);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(SIZE-50, SIZE+50);

        imgTokenTopR.setX(topPlayerScore.getX() + SIZE);
        imgTokenTopR.setY(topPlayerScore.getY());

        imgTokenTopL.setX(topPlayerScore.getX() - SIZE + 50);
        imgTokenTopL.setY(topPlayerScore.getY());

        imgTokenBottomR.setX(bottomPlayerScore.getX() + SIZE);
        imgTokenBottomR.setY(bottomPlayerScore.getY());

        imgTokenBottomL.setX(bottomPlayerScore.getX() - SIZE + 50);
        imgTokenBottomL.setY(bottomPlayerScore.getY());

        addContentView(imgTokenTopL, params);
        addContentView(imgTokenTopR, params);
        addContentView(imgTokenBottomL, params);
        addContentView(imgTokenBottomR, params);

        imgTokenTopL.setVisibility(View.GONE);
        imgTokenTopR.setVisibility(View.GONE);
        imgTokenBottomL.setVisibility(View.GONE);
        imgTokenBottomR.setVisibility(View.GONE);
    }


    public boolean calculateSuperToken() {
        int dif;
        int scoreTurn = (HexagonView.turnColor == InitActivity.topPlayerColor) ? topPlayerScore.score : bottomPlayerScore.score;
        int scoreNoTurn = (HexagonView.noturnColor == InitActivity.topPlayerColor) ? topPlayerScore.score : bottomPlayerScore.score;
        dif = scoreNoTurn-scoreTurn;

        superTokenOn=false;

        //System.out.println(Math.exp((scoreNoTurn-scoreTurn)/59));

        if(dif>3) {
            //prob = (59*1)/(scoreNoTurn-scoreTurn);
            superTokenOn = (((int)(Math.random()*grid.size()))<=dif)? true : false;
            //superTokenOn = (((int)(Math.random()*10))==1)? true : false;
        }

        //Hide the image of the tokens.
        imgTokenBottomL.setVisibility(View.GONE);
        imgTokenBottomR.setVisibility(View.GONE);
        imgTokenTopL.setVisibility(View.GONE);
        imgTokenTopR.setVisibility(View.GONE);

        //show the image of the token.
        if(superTokenOn) {
            if(InitActivity.topPlayerColor == HexagonView.turnColor) {
                imgTokenTopL.setVisibility(View.VISIBLE);
                imgTokenTopR.setVisibility(View.VISIBLE);

            } else {
                imgTokenBottomL.setVisibility(View.VISIBLE);
                imgTokenBottomR.setVisibility(View.VISIBLE);
            }
        }

        return this.superTokenOn;
    }

    //Muestra un alert en la pantalla (Temporal, es posible mejorarlo.)
    public void alert(String title, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
