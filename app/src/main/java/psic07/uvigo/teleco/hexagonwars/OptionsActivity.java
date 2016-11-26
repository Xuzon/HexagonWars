package psic07.uvigo.teleco.hexagonwars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout layout;
    HexagonView playerHexagon;
    HexagonView pcHexagon;
    ImageButton c_b_left, c_b_right, p_b_left, p_b_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new RelativeLayout(this);

        //Ocultar barra notificaciones Android
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View v = LayoutInflater.from(this).inflate(R.layout.activity_options, layout, true);

        addSelectColorHexagon();

        setContentView(layout);
        c_b_left=(ImageButton)findViewById(R.id.c_b_left);
        c_b_right=(ImageButton)findViewById(R.id.c_b_right);
        p_b_left=(ImageButton)findViewById(R.id.p_b_left);
        p_b_right=(ImageButton)findViewById(R.id.p_b_right);
        c_b_left.setOnClickListener(this);
        c_b_right.setOnClickListener(this);
        p_b_left.setOnClickListener(this);
        p_b_right.setOnClickListener(this);

    }
    public void onWindowFocusChanged (boolean hasFocus) {
        playerHexagon.setX(p_b_left.getX()+((p_b_right.getX()-p_b_left.getX())/2));
        playerHexagon.setY(p_b_left.getY());
        pcHexagon.setX(c_b_left.getX()+((c_b_right.getX()-c_b_left.getX())/2));
        pcHexagon.setY(c_b_left.getY());
    }


    private void addSelectColorHexagon() {

        int marginLeft = 0;
        int marginTop = 0;
        Vector2 hexagonDimension = new Vector2(GameActivity.SIZE,GameActivity.SIZE);
        Vector2 coords = new Vector2(0, 0);
        playerHexagon = new HexagonView(this,coords,hexagonDimension, InitActivity.bottomPlayerColor);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(GameActivity.SIZE,GameActivity.SIZE);
        params.leftMargin = marginLeft;
        params.topMargin = marginTop;
        layout.addView(playerHexagon,params);

        hexagonDimension = new Vector2(GameActivity.SIZE,GameActivity.SIZE);
        coords = new Vector2(0, 0);
        pcHexagon = new HexagonView(this,coords,hexagonDimension, InitActivity.topPlayerColor);
        params = new RelativeLayout.LayoutParams(GameActivity.SIZE,GameActivity.SIZE);
        params.leftMargin = marginLeft;
        params.topMargin = marginTop+200;
        layout.addView(pcHexagon,params);
    }


    @Override
    public void onClick(View view) {
        int index;
        switch (view.getId())
        {
            //Change color bottons.
            case(R.id.c_b_left):
                index = InitActivity.colors.indexOf(pcHexagon.hexagon.centerColor);
                if(index-1<0) index=InitActivity.colors.size();
                if(playerHexagon.hexagon.centerColor == InitActivity.colors.get(index-1)) index=index-1;
                if(index-1<0) index=InitActivity.colors.size();
                InitActivity.topPlayerColor = InitActivity.colors.get(index-1);
                pcHexagon.hexagon.centerColor = InitActivity.colors.get(index-1);
                pcHexagon.invalidate();
                break;
            case(R.id.c_b_right):
                index = InitActivity.colors.indexOf(pcHexagon.hexagon.centerColor);
                if(index+1>=InitActivity.colors.size()) index=-1;
                if(playerHexagon.hexagon.centerColor == InitActivity.colors.get(index+1)) index=index+1;
                if(index+1>=InitActivity.colors.size()) index=-1;
                InitActivity.topPlayerColor = InitActivity.colors.get(index+1);
                pcHexagon.hexagon.centerColor = InitActivity.colors.get(index+1);
                pcHexagon.invalidate();
                break;
            case(R.id.p_b_left):
                index = InitActivity.colors.indexOf(playerHexagon.hexagon.centerColor);
                if(index-1<0) index=InitActivity.colors.size();
                if(pcHexagon.hexagon.centerColor == InitActivity.colors.get(index-1)) index=index-1;
                if(index-1<0) index=InitActivity.colors.size();
                InitActivity.bottomPlayerColor = InitActivity.colors.get(index-1);
                playerHexagon.hexagon.centerColor = InitActivity.colors.get(index-1);
                playerHexagon.invalidate();
                break;
            case(R.id.p_b_right):
                index = InitActivity.colors.indexOf(playerHexagon.hexagon.centerColor);
                if(index+1>=InitActivity.colors.size()) index=-1;
                if(pcHexagon.hexagon.centerColor == InitActivity.colors.get(index+1)) index=index+1;
                if(index+1>=InitActivity.colors.size()) index=-1;
                InitActivity.bottomPlayerColor = InitActivity.colors.get(index+1);
                playerHexagon.hexagon.centerColor = InitActivity.colors.get(index+1);
                playerHexagon.invalidate();
                break;
        }
    }
}
