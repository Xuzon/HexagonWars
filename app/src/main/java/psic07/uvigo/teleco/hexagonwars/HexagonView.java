package psic07.uvigo.teleco.hexagonwars;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.graphics.Typeface;
import android.widget.HeterogeneousExpandableList;

import java.lang.reflect.Array;
import java.net.HttpRetryException;
import java.util.ArrayList;

/**
 * Created by danie on 15/11/2016.
 */

public class HexagonView extends View implements View.OnClickListener{
    public HexagonDrawable hexagon;
    public HexagonIntelligence intelligence;
    Vector2 coords;
    Vector2 dim;
    int posX;
    int posY;
    int posArray;
    boolean hexScore = false; //Indica si el Hexagono es de partida o de Score
    int score = 0;
    GameActivity game;
    boolean conquerable = false;
    /**
     * Constructor de hexagono genérico (Transparente)
     * @param context
     * @param game      Juego
     * @param coords
     * @param dim
     * @param posX
     * @param posY
     * @param posArray
     */
    public HexagonView(Context context, GameActivity game, Vector2 coords, Vector2 dim, int posX, int posY, int posArray) {
        super(context);
        this.game = game;
        this.coords = coords;
        this.dim = dim;
        this.posX = posX;
        this.posY = posY;
        this.posArray = posArray;
        this.intelligence = new HexagonIntelligence(this);
        SetupHexagonDrawable(dim,hexagon.transparent);
    }

    /**
     * Constructor de hexagono de selección de color (Solo en Options)
     * @param context
     * @param coords
     * @param dim
     * @param color
     */
    public HexagonView(Context context,Vector2 coords, Vector2 dim, int color) {
        super(context);
        this.coords = coords;
        this.dim = dim;
        SetupHexagonDrawable(dim, color);
    }

    /**
     * Constructor de Hexagono de Score.
     * @param context
     * @param game      Juego
     * @param coords
     * @param dim
     * @param color     Color del hexágono
     */
    public HexagonView(Context context, GameActivity game, Vector2 coords, Vector2 dim, int color) {
        super(context);
        this.game = game;
        this.hexScore = true;
        this.coords = coords;
        this.dim = dim;
        SetupHexagonDrawable(dim, color);
    }

    private void SetupHexagonDrawable(Vector2 dim, int color) {
        hexagon = new HexagonDrawable(HexagonDrawable.defaultBorderColor);
        hexagon.centerColor = color;
        hexagon.dim = dim;
        hexagon.setBounds(dim.x / 2, dim.y / 2,dim.x,dim.y);
        //only no score hexagons has the skill to change color
        if(!hexScore) {
            this.setOnClickListener(this);
        }
    }

    /**
     * Conquista un hexagono en concreto.
     */
    public void ConquerMe(){

        if(game.flag_fin) return;

        boolean pintable = false;

        if(((GameActivity)this.getContext()).superTokenOn) {
            InitActivity.sounds.SoundSelection(Sounds.soundBoom);
            if(hexagon.centerColor==HexagonDrawable.transparent) {
                hexagon.centerColor = game.turnColor;    //Cambiamos el color del hexágono actual
                superToken(false,null);
                testConquer(false);  //Comprobamos si tenemos que conquistar algún hexágono
                game.scoreUpdate(false); //Actualizamos la puntuación, pero no hay que restar la del oponente
            }
            else if(hexagon.centerColor==game.noturnColor) {
                hexagon.centerColor = game.turnColor;    //Cambiamos el color del hexágono actual
                superToken(false,null);
                testConquer(false);  //Comprobamos si tenemos que conquistar algún hexágono
                game.scoreUpdate(true);
            }
            else {
                superToken(false,null);
                testConquer(false);
            }
            pintable = true;
        } else if(hexagon.centerColor==HexagonDrawable.transparent) {
            if(game.turnColor==InitActivity.bottomPlayerColor) {
                InitActivity.sounds.SoundSelection(Sounds.soundConquer);
            }
            hexagon.centerColor = game.turnColor;    //Cambiamos el color del hexágono actual
            testConquer(false);  //Comprobamos si tenemos que conquistar algún hexágono
            game.scoreUpdate(false); //Actualizamos la puntuación, pero no hay que restar la del oponente
            pintable = true;
        }

        //Si tenemos el superToken o el hexagono es tranparente debemos cambiar el turno y repintar el hexagono
        //así como los scores.
        if(pintable) {
            this.invalidate();
            GameActivity.topPlayerScore.invalidate();
            GameActivity.bottomPlayerScore.invalidate();
            if(game.testFin()) return;
            game.changeTurn();

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
    /**
     * Listener de cada Hexágono
     */
    public void onClick(View v) {
        //just for be sure v has to be me but you'll never know XD
        if(v instanceof HexagonView){
            HexagonView hex = (HexagonView) v;
            hex.ConquerMe();
        }
    }

    /**
     * Pinta todos los hexagono alrededor de este.
     */
    public void superToken(boolean test,ArrayList<HexagonView> grid) {

        int hpr = GameActivity.HEXAGONS_PER_ROW;
        int gridSize = GameActivity.grid.size();
        int offsetArray=0;
        HexagonView tempHex;

        //Offset, para la corrección de las filas pares, ya que en estas están desplazadas con respecto a las impares
        offsetArray = (posY%2 == 0)? 1 : 0;

        //Superior izquierdo
        if((tempHex = UpperLeftHexagon(hpr,offsetArray,grid)) != null) {
            tempHex.ChangeColor(test);
        }

        //Superior derecho
        if((tempHex = UpperRightHexagon(hpr, offsetArray,grid)) != null) {
            tempHex.ChangeColor(test);
        }

        //Anterior
        if((tempHex = LeftHexagon(grid)) != null) {
            tempHex.ChangeColor(test);
        }

        //Siguiente
        if((tempHex = RightHexagon(gridSize,grid)) != null) {
            tempHex.ChangeColor(test);
        }

        //Inferior izquierdo
        if((tempHex = LowerLeftHexagon(hpr, gridSize, offsetArray,grid)) != null) {
            tempHex.ChangeColor(test);
        }

        //Inferior derecho
        if((tempHex = LowerRightHexagon(hpr, gridSize, offsetArray,grid)) != null) {
            tempHex.ChangeColor(test);
        }
    }



    /**
     * Comprueba si el hexágono es conquistable, recorre todos los hexagonos vecinos y si hay mas del color contrario
     * que del propio, este es conquistado. Debe estar rodeado por mas de un hexágono del oponente.
     */
    public void testConquerAround(boolean test, ArrayList<HexagonView> grid) {
        //allies of the player who is playing this turn
        int numAllies = 0;
        //enemies of the player who is playing this turn
        int numEnemies = 0;

        //Si el hexagono no es conquistable salimos
        if(this.hexagon.centerColor!=game.noturnColor) {
            return;
        }

        numAllies = countAllies(grid);
        numEnemies = countEnemies(grid);

        //Debe estar rodeado por más de un hexágono del oponente para poder ser conquistado.
        if(numEnemies==0 && numAllies==1)
            return;

        //Si hay mas hexagonos del color del turno que del que no tiene el turno, conquistamos.
        if(numAllies > numEnemies) {
            this.hexagon.centerColor = game.turnColor;   //Se conquista el hexágono cambiandole el color.
            if(!test) {
                this.invalidate();
                game.scoreUpdate(true);  //Actualizamos la puntucación
                this.testConquer(test); //Volvemos a comprobar si conquistamos cualquier hexágono (Reiterativamente)
            }
        }
    }//testConquerAround

    /**
     * Función para contar los Alliados que colindan con el hexagono
     * @return Número de aliados.
     */
    public int countAllies(ArrayList<HexagonView> grid) {

        int hpr = GameActivity.HEXAGONS_PER_ROW;
        int gridSize = GameActivity.grid.size();
        int numAllies = 0;
        int offsetArray=0;
        //Offset, para la corrección de las filas pares, ya que en estas están desplazadas con respecto a las impares
        offsetArray = (posY%2 == 0)? 1 : 0;
        HexagonView tempHex;

        if((tempHex = UpperLeftHexagon(hpr, offsetArray,grid)) != null) {
            numAllies += (tempHex.hexagon.centerColor == game.turnColor) ? 1 : 0;
        }

        if((tempHex = UpperRightHexagon(hpr, offsetArray,grid)) != null) {
            numAllies += (tempHex.hexagon.centerColor == game.turnColor) ? 1 : 0;
        }

        if((tempHex = LeftHexagon(grid)) != null) {
            numAllies += (tempHex.hexagon.centerColor == game.turnColor) ? 1 : 0;
        }

        if((tempHex = RightHexagon(gridSize,grid)) != null) {
            numAllies += (tempHex.hexagon.centerColor == game.turnColor) ? 1 : 0;
        }

        if((tempHex = LowerLeftHexagon(hpr, gridSize, offsetArray,grid)) != null) {
            numAllies += (tempHex.hexagon.centerColor == game.turnColor) ? 1 : 0;
        }

        if((tempHex = LowerRightHexagon(hpr, gridSize, offsetArray,grid)) != null) {
            numAllies += (tempHex.hexagon.centerColor == game.turnColor) ? 1 : 0;
        }

        return numAllies;
    }

    /**
     * Función para contar el número de enemigos al rededor de un hexágono
     * @return Número de enemigos
     */
    public int countEnemies(ArrayList<HexagonView> grid) {
        int hpr = GameActivity.HEXAGONS_PER_ROW;
        int gridSize = GameActivity.grid.size();
        int numEnemies = 0;
        int offsetArray=0;
        //Offset, para la corrección de las filas pares, ya que en estas están desplazadas con respecto a las impares
        offsetArray = (posY%2 == 0)? 1 : 0;
        HexagonView tempHex;

        if((tempHex = UpperLeftHexagon(hpr, offsetArray,grid)) != null) {
            numEnemies += (tempHex.hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        if((tempHex = UpperRightHexagon(hpr, offsetArray,grid)) != null) {
            numEnemies += (tempHex.hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        if((tempHex = LeftHexagon(grid)) != null) {
            numEnemies += (tempHex.hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        if((tempHex = RightHexagon(gridSize,grid)) != null) {
            numEnemies += (tempHex.hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        if((tempHex = LowerLeftHexagon(hpr, gridSize, offsetArray,grid)) != null) {
            numEnemies += (tempHex.hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        if((tempHex = LowerRightHexagon(hpr, gridSize, offsetArray,grid)) != null) {
            numEnemies += (tempHex.hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        return numEnemies;
    }

    /**
     * La función calcula cuantos hexagonos serian conquistados en total en caso de marcar el propio.
     * @return Número de hexagonos conquistados.
     */
/*
    public int countHexConquer() {

        int ret = 0;

        //TODO: Contar número de hexágonos posibles a conquistar. (Debe contar también en caso de superToken)

        return  ret;

    }
*/



    /**
     * Comprobamos si conquistamos algún hexagono
     */
    public static void testConquer(boolean test) {
        //Comprobamos todos los hexágonos del array.
        for (int i = 0; i < GameActivity.grid.size(); i++) {
             GameActivity.grid.get(i).testConquerAround(test,GameActivity.grid);
         }
    }

    public void ChangeColor(boolean test) {
        if(this.hexagon.centerColor != game.turnColor) {
            if(!test) {
                if (this.hexagon.centerColor == game.noturnColor) {
                    game.scoreUpdate(true);
                } else {
                    game.scoreUpdate(false);
                }
            }
            this.hexagon.centerColor = game.turnColor;
            this.invalidate();
        }
    }

    public HexagonView LowerRightHexagon(int hpr, int gridSize, int offsetArray, ArrayList<HexagonView> grid) {
        HexagonView toRet = null;
        ArrayList<HexagonView> gridToResearch = (grid == null) ? GameActivity.grid : grid;
        if(posArray+hpr<gridSize && gridToResearch.get(posArray+hpr).posX==posX+1-offsetArray){
            toRet = gridToResearch.get(posArray+hpr);
        }
        return toRet;
    }

    public HexagonView LowerLeftHexagon(int hpr, int gridSize, int offsetArray,ArrayList<HexagonView> grid) {
        HexagonView toRet = null;
        ArrayList<HexagonView> gridToResearch = (grid == null) ? GameActivity.grid : grid;
        if(posArray+hpr-1<gridSize && gridToResearch.get(posArray+hpr-1).posX==posX-offsetArray){
            toRet = gridToResearch.get(posArray+hpr - 1);
        }
        return toRet;
    }

    public HexagonView RightHexagon(int gridSize, ArrayList<HexagonView> grid) {
        HexagonView toRet = null;
        ArrayList<HexagonView> gridToResearch = (grid == null) ? GameActivity.grid : grid;
        if( posArray+1<gridSize && gridToResearch.get(posArray+1).posY==posY) {
            toRet = gridToResearch.get(posArray + 1);
        }
        return toRet;
    }

    public HexagonView LeftHexagon(ArrayList<HexagonView> grid) {
        HexagonView toRet = null;
        ArrayList<HexagonView> gridToResearch = (grid == null) ? GameActivity.grid : grid;
        if(posArray-1>=0 && gridToResearch.get(posArray-1).posY==posY) {
            toRet = gridToResearch.get(posArray - 1);
        }
        return toRet;
    }

    public HexagonView UpperRightHexagon(int hpr, int offsetArray,ArrayList<HexagonView> grid) {
        HexagonView toRet = null;
        ArrayList<HexagonView> gridToResearch = (grid == null) ? GameActivity.grid : grid;
        if(posArray-hpr+1>=0 && gridToResearch.get(posArray-hpr+1).posX==posX+1-offsetArray) {
            toRet = gridToResearch.get(posArray - hpr + 1);
        }
        return toRet;
    }

    public HexagonView UpperLeftHexagon(int hpr, int offsetArray, ArrayList<HexagonView> grid) {
        HexagonView toRet = null;
        ArrayList<HexagonView> gridToResearch = (grid == null) ? GameActivity.grid : grid;
        if(posArray-hpr>=0 && gridToResearch.get(posArray-hpr).posX==posX-offsetArray){
            toRet = gridToResearch.get(posArray-hpr);
        }
        return toRet;
    }

    public HexagonView Clone(){
        HexagonView toRet = new HexagonView(game, game, coords,dim, posX, posY, posArray);
        toRet.hexagon.centerColor = this.hexagon.centerColor;
        return   toRet;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

}
