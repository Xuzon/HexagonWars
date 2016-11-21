package psic07.uvigo.teleco.hexagonwars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
