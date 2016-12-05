package psic07.uvigo.teleco.hexagonwars;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.MediaPlayer;
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

import psic07.uvigo.teleco.hexagonwars.ai.AiPlayer;

public class GameActivity extends AppCompatActivity {
    public final int gameWindowSound = R.raw.epic;                //Path de sonido de la pestaña de juego.
    public final int hexagonSelectionSound = R.raw.click;                //Path de sonido de hexagonos.
    RelativeLayout layout;
    public static int SIZE = 150;
    public final static int HEXAGONS_PER_ROW = 7;
    public final static int ROWS = 9;
    public static HexagonView topPlayerScore;
    public static HexagonView bottomPlayerScore;
    public static int gridYOffset = 400;
    public static int gridXOffset = 0;
    public static ArrayList<HexagonView> grid; //ArrayList de los hexagonos (grid)
    public int turnColor; //Color del que tiene el turno.
    public int noturnColor; //Color del que no tiene el turno.
    public boolean flag_fin = false;  //Flag de fin de juego.
    public boolean superTokenOn = false; //flag de token (Para el que tiene el turno)
    public ImageView imgTokenTopL, imgTokenBottomL, imgTokenTopR, imgTokenBottomR; //Imagen de los tokens.
    MediaPlayer gamesound;               //Creamos mediaplayer para el sonido inicial.
    MediaPlayer hexagonsound;              //Creamos mediaplayer para el sonido de los hexagonos
    OptionsActivity optionsActivity = new OptionsActivity();

    public AiPlayer aiPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Calculo del centro del grid.
        gridYOffset = (int) ((InitActivity.screenHeight-((3f/4f)*SIZE*ROWS))/2);
        gridXOffset = (int) ((InitActivity.screenWidth-(SIZE*(HEXAGONS_PER_ROW-2)+SIZE*2*HexagonDrawable.FILL_PERCENTAGE))/2);

        //Ocultar barra notificaciones Android
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        layout = new RelativeLayout(this);
        SetBackground();

        //Turnos al inicio del juego.
        turnColor = InitActivity.bottomPlayerColor;
        noturnColor = InitActivity.topPlayerColor;

        grid = CreateGrid();
        ShowGrid();
        addScore();
        setContentView(layout);
        flag_fin = false;

        aiPlayer = new AiPlayer(this);
    }

    /**
     * Configuración del background del juego
     */
    private void SetBackground() {
        ImageView background = new ImageView(this);
        background.setBackgroundResource(R.drawable.gamebackground);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(InitActivity.screenWidth, InitActivity.screenHeight);
        params.leftMargin = 0;
        params.topMargin = 0;
        layout.addView(background,params);
    }

    /**
     * Creación del Array de Hexágonos del juego. Cada hexagono incluye su posición, color, etc.
     * @return ArrayList de los hexágonos.
     */
    private ArrayList<HexagonView> CreateGrid() {
        ArrayList<HexagonView> toRet = new ArrayList<HexagonView>();
        Vector2 hexagonDimension = new Vector2(SIZE,SIZE);
        int halfSizeEdge =(int) (SIZE * (1f - HexagonDrawable.FILL_PERCENTAGE ) / 2f);
        float ySizeMultiplier = 3f / 4f;
        int xPos = (int) (SIZE);
        int yPos = (int) (SIZE * ySizeMultiplier);
            for(int i = 0; i < ROWS; i++) {
                for(int j = 0; j < HEXAGONS_PER_ROW ; j++) {
                    boolean notOdd = (i % 2 == 0) ? true : false;
                int oddOffset = (notOdd) ? 0 : ((InitActivity.screenWidth-(SIZE*(HEXAGONS_PER_ROW-1)))/2);
                if(!notOdd && j == (HEXAGONS_PER_ROW - 1)) {
                    continue;
                }
                int xCoord = gridXOffset + oddOffset + j * xPos - halfSizeEdge * j;
                int yCoord = gridYOffset + i * yPos - halfSizeEdge * i;
                Vector2 coords = new Vector2(xCoord,yCoord);
                HexagonView hexagon = new HexagonView(this, this, coords,hexagonDimension, j, i, toRet.size());
                toRet.add(hexagon);
            }
        }
        return toRet;
    }

    /**
     * Añade los hexagonos con la puntación
     */
    private void addScore() {
        int offsetx = (InitActivity.screenWidth-SIZE)/2;
        int offsetyH1 = 100;
        int offsetyH2 = InitActivity.screenHeight-SIZE-100;
        Vector2 hexagonDimension = new Vector2(SIZE,SIZE);
        Vector2 coords = new Vector2(offsetx, offsetyH1);
        topPlayerScore = new HexagonView(this, this, coords,hexagonDimension, InitActivity.topPlayerColor);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(SIZE,SIZE);
        params.leftMargin = offsetx;
        params.topMargin = offsetyH1;
        layout.addView(topPlayerScore,params);

        hexagonDimension = new Vector2(SIZE,SIZE);
        coords = new Vector2(offsetx, offsetyH2);
        bottomPlayerScore = new HexagonView(this, this, coords,hexagonDimension, InitActivity.bottomPlayerColor);
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

    /**
     * Al cargar la ventana del juego, cargamos las imagenes de los tokens en los laterales de los hexágonos de Score.
     * Estos se ocultan por defecto. Se mostrarán al que le toque el token.
     * @param hasFocus
     */
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

    /**
     * Calcula la probabilidad de que al que tiene el turno le toque el token. Muestra la imagen para saber que tiene el token.
     * @return Si se otorga el token o no.
     */
    public boolean calculateSuperToken() {
        int dif;
        int scoreTurn = (turnColor == InitActivity.topPlayerColor) ? topPlayerScore.score : bottomPlayerScore.score;
        int scoreNoTurn = (noturnColor == InitActivity.topPlayerColor) ? topPlayerScore.score : bottomPlayerScore.score;
        dif = scoreNoTurn-scoreTurn;

        superTokenOn=false;

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
            if(InitActivity.topPlayerColor == turnColor) {
                imgTokenTopL.setVisibility(View.VISIBLE);
                imgTokenTopR.setVisibility(View.VISIBLE);

            } else {
                imgTokenBottomL.setVisibility(View.VISIBLE);
                imgTokenBottomR.setVisibility(View.VISIBLE);
            }
        }

        return this.superTokenOn;
    }

    /**
     * Cambia el turno
     */
    public void changeTurn() {
        noturnColor = turnColor;

        if (turnColor == InitActivity.bottomPlayerColor) {
            turnColor = InitActivity.topPlayerColor;

            //Instrucciones temporales, son para saber visualmente a quien pertenece el turno
            topPlayerScore.hexagon.setBorderColor(Color.WHITE);
            bottomPlayerScore.hexagon.setBorderColor(HexagonDrawable.defaultBorderColor);
            //Fin instrucciones temporales
            aiPlayer.Turn();

        } else {
            turnColor = InitActivity.bottomPlayerColor;

            //Instrucciones temporales, son para saber visualmente a quien pertenece el turno
            bottomPlayerScore.hexagon.setBorderColor(Color.WHITE);
            topPlayerScore.hexagon.setBorderColor(HexagonDrawable.defaultBorderColor);
            //Fin instrucciones temporales
        }

        calculateSuperToken();

        topPlayerScore.invalidate();
        bottomPlayerScore.invalidate();
    }

    /**
     * Comprueba si se ha rellenado todo el grid.
     * En ese caso muestra un alert con el ganador.
     */
    public void testFin() {
        //Comprobamos si se han cubierto todos los hexágonos y en ese caso quién sería el ganador.
        if((bottomPlayerScore.score + topPlayerScore.score) == grid.size()) {

            //Comprobamos si el oponete debe conquistar algo antes de terminar.
            HexagonView.testConquer(false);

            if(bottomPlayerScore.score > topPlayerScore.score) {
                alert("Finish", "The BOTTOM player WIN the game. Congratulations!!!!!");
            }
            if(bottomPlayerScore.score < topPlayerScore.score) {
                alert("Finish", "The TOP player WIN the game. Congratulations!!!!!");
            }
            flag_fin=true;
        }
    }

    /**
     * Actualizamos la puntuación. Y si es necesario cambiamos el turno.
     * @param conquer Si es conquiestado se resta la puntuación al oponente
     */
    public void scoreUpdate(boolean conquer) {
        if (turnColor == InitActivity.bottomPlayerColor) {
            bottomPlayerScore.score++;
            if (conquer) topPlayerScore.score--;
        } else {
            topPlayerScore.score++;
            if (conquer) bottomPlayerScore.score--;
        }
    }

    /**
     * Muestra un alert en la pantalla con un botón de ok (Temporal, es posible mejorarlo.)
     * @param title Título del alert
     * @param msg Mensaje del alert
     */
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




    //Funcion que se encarga de reproducir el sonido de la area de juego.
    private void playGameWindowSound() {
        if (optionsActivity.allow_music_sound) {
            gamesound = MediaPlayer.create(this, gameWindowSound);
            gamesound.setLooping(true);
            gamesound.start();
        }
    }

    //Funcion que se encarga de reproducir el sonido de los botones.
    private void playHexagonSelectionSound() {
        hexagonsound = MediaPlayer.create(this,hexagonSelectionSound);
        hexagonsound.start();
    }

    @Override
    protected void onStop() {
        if (gamesound != null && gamesound.isPlaying()) {
            gamesound.stop();
        }
        super.onPause();
    }

    //Funcion que vuelve a definir el media player en caso de volver a la area de juego.
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(gamesound == null) {
            playGameWindowSound();
        }
    }
    //Funcion  que vuelve a reproducir sonido en caso de volver a la ventana anterior
    @Override
    protected void onResume() {
        super.onResume();
        playGameWindowSound();
    }



}
