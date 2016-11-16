package psic07.uvigo.teleco.hexagonwars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    RelativeLayout frameLayout;
    public final static int SIZE = 150;
    public final static int HEXAGONS_PER_ROW = 7;
    public final static int ROWS = 9;
    public final static int yOffset = 200;
    public ArrayList<HexagonView> grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout = new RelativeLayout(this);
        SetBackground();
        grid = CreateGrid();
        ShowGrid();
        setContentView(frameLayout);
    }

    private void SetBackground() {
        ImageView background = new ImageView(this);
        background.setBackgroundResource(R.drawable.gameactivitybackground);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(1080, 1920);
        params.leftMargin = 0;
        params.topMargin = 0;
        frameLayout.addView(background,params);
    }

    private ArrayList<HexagonView> CreateGrid() {
        ArrayList<HexagonView> toRet = new ArrayList<HexagonView>();
        Vector2 hexagonDimension = new Vector2(SIZE,SIZE);
        //float xSizeMultiplier = 1f - (1f - HexagonDrawable.FILL_PERCENTAGE );
        float xSizeMultiplier = 1;
        float ySizeMultiplier = 3f / 4f;
        int xPos = (int) (SIZE * xSizeMultiplier);
        int yPos = (int) (SIZE * ySizeMultiplier);
        for(int i = 0; i < HEXAGONS_PER_ROW ; i++) {
            for(int j = 0; j < ROWS; j++) {
                boolean notOdd = (j % 2 == 0) ? true : false;
                int oddOffset = (notOdd) ? 0 : 75;
                if(!notOdd && i == (HEXAGONS_PER_ROW - 1)){
                    continue;
                }
                Vector2 coords = new Vector2(oddOffset + i * xPos,yOffset + j * yPos);
                HexagonView hexagon = new HexagonView(this,coords,hexagonDimension);
                toRet.add(hexagon);
            }
        }
        return toRet;
    }

    private void ShowGrid(){
        for(HexagonView hexagon : grid){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(hexagon.dim.x, hexagon.dim.y);
            params.leftMargin = hexagon.coords.x;
            params.topMargin = hexagon.coords.y;
            frameLayout.addView(hexagon,params);
        }
    }
}
