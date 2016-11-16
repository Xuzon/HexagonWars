package psic07.uvigo.teleco.hexagonwars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.FrameLayout;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    public final static int SIZE = 150;
    public final static int HEXAGONS_PER_ROW = 7;
    public final static int ROWS = 9;
    public final static int yOffset = 200;
    public ArrayList<HexagonView> grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Ocultar barra notificaciones Android
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        frameLayout = new FrameLayout(this);
        grid = CreateGrid();
        ShowGrid();
        setContentView(frameLayout);
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
                HexagonView hexagon = new HexagonView(this,coords,hexagonDimension);
                toRet.add(hexagon);
            }
        }
        return toRet;
    }

    private void ShowGrid(){
        for(HexagonView hexagon : grid){
            frameLayout.addView(hexagon);
        }
    }
}
