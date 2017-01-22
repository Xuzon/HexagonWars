package psic07.uvigo.teleco.hexagonwars;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Atilano on 21/11/2016.
 */

public class RulesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.rulesactivity);
    }

    @Override
    protected  void onResume() {
        InitActivity.activityChange=false;
        InitActivity.sounds.SoundSelection(Sounds.musicGameRestart);
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(!InitActivity.activityChange) {
            InitActivity.sounds.SoundSelection(Sounds.musicGamePause);
        }
        super.onRestart();
    }

}
