package psic07.uvigo.teleco.hexagonwars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class OptionsActivity extends AppCompatActivity {
    RelativeLayout layout;
    HexagonView playerHexagon;
    HexagonView pcHexagon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new RelativeLayout(this);

        //Ocultar barra notificaciones Android
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        addSelectColorHexagon();
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500,500);
//        addView(R.layout.activity_options, param);
//        layout.addView(findViewById(R.layout.activity_options));
        setContentView(R.layout.activity_options);


    }


    private void addSelectColorHexagon() {

        /*int offsetx = (screenWidth-SIZE)/2;
        int offsetyH1 = 100;
        int offsetyH2 = screenHeight-SIZE-100;*/
        Vector2 hexagonDimension = new Vector2(GameActivity.SIZE,GameActivity.SIZE);
        Vector2 coords = new Vector2(100, 100);
        playerHexagon = new HexagonView(this,coords,hexagonDimension, GameActivity.bottomPlayerColor, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(GameActivity.SIZE,GameActivity.SIZE);
        params.leftMargin = 100;
        params.topMargin = 1000;
        layout.addView(playerHexagon,params);

//        hexagonDimension = new Vector2(GameActivity.SIZE,GameActivity.SIZE);
//        coords = new Vector2(100, 200);
//        pcHexagon = new HexagonView(this,coords,hexagonDimension, 0xffffffff, true);
//        params = new RelativeLayout.LayoutParams(GameActivity.SIZE,GameActivity.SIZE);
//        params.leftMargin = 0;
//        params.topMargin = 0;
//        layout.addView(pcHexagon,params);
    }

}
