package psic07.uvigo.teleco.hexagonwars;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Size;
import android.graphics.Rect;
import android.view.View;
import android.graphics.Typeface;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by danie on 15/11/2016.
 */

public class HexagonView extends View implements View.OnClickListener{
    public HexagonDrawable hexagon;
    Vector2 coords;
    Vector2 dim;
    int posX;
    int posY;
    int posArray;
    boolean hexScore = false; //Indica si el Hexagono es de partida o de Score
    int score = 0;
    GameActivity game;


    public HexagonView(Context context,Vector2 coords, Vector2 dim, int posX, int posY, int posArray) {
        super(context);
        game = (GameActivity) context;
        game.turnColor = InitActivity.bottomPlayerColor;
        game.noturnColor = InitActivity.topPlayerColor;
        this.coords = coords;
        this.dim = dim;
        this.posX = posX;
        this.posY = posY;
        this.posArray = posArray;
        SetupHexagonDrawable(dim,hexagon.transparent);
    }

    public HexagonView(Context context,Vector2 coords, Vector2 dim, int color, boolean hexScore) {
        super(context);
        game = (GameActivity) context;
        this.hexScore = hexScore;
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

    public void ConquerMe(){

        if(game.flag_fin) return;

        if(((GameActivity)this.getContext()).superTokenOn) {
            if(hexagon.centerColor==HexagonDrawable.transparent) {
                hexagon.centerColor = game.turnColor;    //Cambiamos el color del hexágono actual
                superToken();
                testConquer();  //Comprobamos si tenemos que conquistar algún hexágono
                game.scoreUpdate(false); //Actualizamos la puntuación, pero no hay que restar la del oponente
            }
            else if(hexagon.centerColor==game.noturnColor) {
                hexagon.centerColor = game.turnColor;    //Cambiamos el color del hexágono actual
                superToken();
                testConquer();  //Comprobamos si tenemos que conquistar algún hexágono
                game.scoreUpdate(true);
            }
            else {
                superToken();
                testConquer();
            }
            game.changeTurn();
        } else if(hexagon.centerColor==HexagonDrawable.transparent) {

            hexagon.centerColor = game.turnColor;    //Cambiamos el color del hexágono actual
            testConquer();  //Comprobamos si tenemos que conquistar algún hexágono
            game.scoreUpdate(false); //Actualizamos la puntuación, pero no hay que restar la del oponente
            game.changeTurn();
        }

        this.invalidate();
        GameActivity.topPlayerScore.invalidate();
        GameActivity.bottomPlayerScore.invalidate();
        game.testFin();

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
    public void onClick(View v) {
        //just for be sure v has to be me but you'll never know XD
        if(v instanceof HexagonView){
            HexagonView hex = (HexagonView) v;
            hex.ConquerMe();
        }
    }

    public void superToken() {

        int hpr = GameActivity.HEXAGONS_PER_ROW;
        int gridSize = GameActivity.grid.size();
        int offsetArray=0;
        HexagonView tempHex;

        //Offset, para la corrección de las filas pares, ya que en estas están desplazadas con respecto a las impares
        offsetArray = (posY%2 == 0)? 1 : 0;

        //Superior izquierdo
        if(posArray-hpr>=0 && GameActivity.grid.get(posArray-hpr).posX==posX-offsetArray) {
            tempHex = GameActivity.grid.get(posArray-hpr);
            if(tempHex.hexagon.centerColor != game.turnColor) {
                if(tempHex.hexagon.centerColor == game.noturnColor) {
                    game.scoreUpdate(true);
                } else {
                    game.scoreUpdate(false);
                }
                tempHex.hexagon.centerColor = game.turnColor;
                tempHex.invalidate();
            }
        }

        //Superior derecho
        if(posArray-hpr+1>=0 && GameActivity.grid.get(posArray-hpr+1).posX==posX+1-offsetArray) {
            tempHex = GameActivity.grid.get(posArray-hpr+1);
            if(tempHex.hexagon.centerColor != game.turnColor) {
                if(tempHex.hexagon.centerColor == game.noturnColor) {
                    game.scoreUpdate(true);
                } else {
                    game.scoreUpdate(false);
                }
                tempHex.hexagon.centerColor = game.turnColor;
                tempHex.invalidate();
            }
        }

        //Anterior
        if(posArray-1>=0 && GameActivity.grid.get(posArray-1).posY==posY) {
            tempHex = GameActivity.grid.get(posArray-1);
            if(tempHex.hexagon.centerColor != game.turnColor) {
                if(tempHex.hexagon.centerColor == game.noturnColor) {
                    game.scoreUpdate(true);
                } else {
                    game.scoreUpdate(false);
                }
                tempHex.hexagon.centerColor = game.turnColor;
                tempHex.invalidate();
            }
        }

        //Siguiente
        if(posArray+1<gridSize && GameActivity.grid.get(posArray+1).posY==posY) {
            tempHex = GameActivity.grid.get(posArray+1);
            if(tempHex.hexagon.centerColor != game.turnColor) {
                if(tempHex.hexagon.centerColor == game.noturnColor) {
                    game.scoreUpdate(true);
                } else {
                    game.scoreUpdate(false);
                }
                tempHex.hexagon.centerColor = game.turnColor;
                tempHex.invalidate();
            }
        }

        //Inferior izquierdo
        if(posArray+hpr-1<gridSize && GameActivity.grid.get(posArray+hpr-1).posX==posX-offsetArray) {
            tempHex = GameActivity.grid.get(posArray+hpr-1);
            if(tempHex.hexagon.centerColor != game.turnColor) {
                if(tempHex.hexagon.centerColor == game.noturnColor) {
                    game.scoreUpdate(true);
                } else {
                    game.scoreUpdate(false);
                }
                tempHex.hexagon.centerColor = game.turnColor;
                tempHex.invalidate();
            }
        }

        //Inferior derecho
        if(posArray+hpr<gridSize && GameActivity.grid.get(posArray+hpr).posX==posX+1-offsetArray) {
            tempHex = GameActivity.grid.get(posArray+hpr);
            if(tempHex.hexagon.centerColor != game.turnColor) {
                if(tempHex.hexagon.centerColor == game.noturnColor) {
                    game.scoreUpdate(true);
                } else {
                    game.scoreUpdate(false);
                }
                tempHex.hexagon.centerColor = game.turnColor;
                tempHex.invalidate();
            }
        }
    }

    /**
     * Comprueba si el hexágono es conquistable, recorre todos los hexagonos vecinos y si hay mas del color contrario
     * que del propio, este es conquistado. Debe estar rodeado por mas de un hexágono del oponente.
     */
    public void testConquerArround() {
        int hpr = GameActivity.HEXAGONS_PER_ROW;
        int gridSize = GameActivity.grid.size();
        int numArroundTurn = 0;
        int numArroundNoTurn = 0;
        int offsetArray=0;

        //Si el hexagono no es conquistable salimos
        if(this.hexagon.centerColor!=game.noturnColor)
            return;

        //Offset, para la corrección de las filas pares, ya que en estas están desplazadas con respecto a las impares
        offsetArray = (posY%2 == 0)? 1 : 0;

        //Comenzamos a contar los colores que reodean al hexagono.

        //Superior izquierdo
        if(posArray-hpr>=0 && GameActivity.grid.get(posArray-hpr).posX==posX-offsetArray) {
            numArroundTurn += (GameActivity.grid.get(posArray - hpr).hexagon.centerColor == game.turnColor) ? 1 : 0;
            numArroundNoTurn += (GameActivity.grid.get(posArray - hpr).hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        //Superior derecho
        if(posArray-hpr+1>=0 && GameActivity.grid.get(posArray-hpr+1).posX==posX+1-offsetArray) {
            numArroundTurn += (GameActivity.grid.get(posArray - hpr + 1).hexagon.centerColor == game.turnColor) ? 1 : 0;
            numArroundNoTurn += (GameActivity.grid.get(posArray - hpr + 1).hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        //Anterior
        if(posArray-1>=0 && GameActivity.grid.get(posArray-1).posY==posY) {
            numArroundTurn += (GameActivity.grid.get(posArray - 1).hexagon.centerColor == game.turnColor) ? 1 : 0;
            numArroundNoTurn += (GameActivity.grid.get(posArray - 1).hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        //Siguiente
        if(posArray+1<gridSize && GameActivity.grid.get(posArray+1).posY==posY) {
            numArroundTurn += (GameActivity.grid.get(posArray + 1).hexagon.centerColor == game.turnColor) ? 1 : 0;
            numArroundNoTurn += (GameActivity.grid.get(posArray + 1).hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        //Inferior izquierdo
        if(posArray+hpr-1<gridSize && GameActivity.grid.get(posArray+hpr-1).posX==posX-offsetArray) {
            numArroundTurn += (GameActivity.grid.get(posArray + hpr - 1).hexagon.centerColor == game.turnColor) ? 1 : 0;
            numArroundNoTurn += (GameActivity.grid.get(posArray + hpr - 1).hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        //Inferior derecho
        if(posArray+hpr<gridSize && GameActivity.grid.get(posArray+hpr).posX==posX+1-offsetArray) {
            numArroundTurn += (GameActivity.grid.get(posArray + hpr).hexagon.centerColor == game.turnColor) ? 1 : 0;
            numArroundNoTurn += (GameActivity.grid.get(posArray + hpr).hexagon.centerColor == game.noturnColor) ? 1 : 0;
        }

        //Debe estar rodeado por más de un hexágono del oponente para poder ser conquistado.
        if(numArroundNoTurn==0 && numArroundTurn==1)
            return;

        //Si hay mas hexagonos del color del turno que del que no tiene el turno, conquistamos.
        if(numArroundTurn > numArroundNoTurn) {
            this.hexagon.centerColor = game.turnColor;   //Se conquista el hexágono cambiandole el color.
            this.testConquer(); //Volvemos a comprobar si conquistamos cualquier hexágono (Reiterativamente)
            this.invalidate();
            game.scoreUpdate(true);  //Actualizamos la puntucación
        }
    }//testConquerArround

    /**
     * Comprobamos si conquistamos algún hexagono
     */
    public static void testConquer() {
        //Comprobamos todos los hexágonos del array.
        for(HexagonView hex : GameActivity.grid)
         {
             hex.testConquerArround();
         }
    }
}
