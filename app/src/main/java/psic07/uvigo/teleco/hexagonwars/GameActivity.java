package psic07.uvigo.teleco.hexagonwars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.webkit.ServiceWorkerClient;
import android.widget.FrameLayout;
import android.view.Window;
import android.view.WindowManager;

import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    public final static int SIZE = 150;
    public final static int HEXAGONS_PER_ROW = 7;
    public final static int ROWS = 9;
    public final static int yOffset = 400;
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    public ArrayList<HexagonView> grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Obtenemos las medidas en píxeles de la pantalla
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        //Ocultar barra notificaciones Android
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        frameLayout = new FrameLayout(this);
        grid = CreateGrid();
        SetBackground();
        ShowGrid();
        addScore();
        setContentView(frameLayout);
    }

    private void SetBackground() {
        ImageView background = new ImageView(this);
        background.setBackgroundResource(R.drawable.gamebackground);
        frameLayout.addView(background);
    }

    private ArrayList<HexagonView> CreateGrid() {
        ArrayList<HexagonView> toRet = new ArrayList<HexagonView>();
        Vector2 hexagonDimension = new Vector2(SIZE,SIZE);
        for(int i = 0; i < HEXAGONS_PER_ROW ; i++) {
            for(int j = 0; j < ROWS; j++) {
                boolean notOdd = (j % 2 == 0) ? true : false;
                int offset = (notOdd) ? 0 : 75;
                if(!notOdd && i == (HEXAGONS_PER_ROW - 1)){
                    continue;
                }
                Vector2 coords = new Vector2(i * SIZE + offset,yOffset + j*(SIZE / 4) * 3);
                HexagonView hexagon = new HexagonView(this,coords,hexagonDimension, 0);
                toRet.add(hexagon);
            }
        }
        return toRet;
    }

    private void addScore() {
        int offsetx = (screenWidth-SIZE)/2;
        int offsetyH1 = 100;
        int offsetyH2 = screenHeight-SIZE-100;
        Vector2 hexagonDimension = new Vector2(SIZE,SIZE);
        Vector2 coords = new Vector2(offsetx, offsetyH1);
        HexagonView hexagon = new HexagonView(this,coords,hexagonDimension, HexagonDrawable.blueColor);
        frameLayout.addView(hexagon);

        hexagonDimension = new Vector2(SIZE,SIZE);
        coords = new Vector2(offsetx, offsetyH2);
        hexagon = new HexagonView(this,coords,hexagonDimension, HexagonDrawable.redColor);
        frameLayout.addView(hexagon);

    }

    private void ShowGrid(){
        for(HexagonView hexagon : grid){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100,100);
            params.leftMargin = hexagon.coords.x;
            params.topMargin = hexagon.coords.y;
            frameLayout.addView(hexagon);
        }
    }
}
