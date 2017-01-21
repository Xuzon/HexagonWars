package psic07.uvigo.teleco.hexagonwars.ai;

import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.Random;

import psic07.uvigo.teleco.hexagonwars.GameActivity;
import psic07.uvigo.teleco.hexagonwars.HexagonDrawable;
import psic07.uvigo.teleco.hexagonwars.HexagonView;

/**
 * Created by danie on 30/11/2016.
 */

public class AiPlayer {
    GameActivity game;
    public float NearbyAlliesWeight = 0.10f;
    public float NearbyEnemiesWeight = 0.10f;
    public float WillConquerWeight = 0.80f;

    public AiPlayer(GameActivity game){
        this.game = game;
    }

    public AiPlayer(GameActivity game, float nAw, float nEw, float wCw){
        this.game = game;
        this.NearbyAlliesWeight = nAw;
        this.NearbyEnemiesWeight = nEw;
        this.WillConquerWeight = wCw;
    }

    public void Turn(){
        float higherValue = -1;
        ArrayList<HexagonView> candidates = new ArrayList<>();
        for(int i = 0; i < GameActivity.grid.size(); i++) {
            HexagonView hex = GameActivity.grid.get(i);

            //if I don't have the superToken and is not transparent I could not conquer it
            if(!game.superTokenOn && (hex.hexagon.centerColor != HexagonDrawable.transparent)){
                continue;
            }
            float value = hex.intelligence.Utility(this,game.superTokenOn);
            if(value > higherValue){
                candidates.clear();
                higherValue = value;
            }
            if(value >= higherValue){
                candidates.add(hex);
            }
        }
        if(candidates.size() > 0) {
            Random random = new Random();
            HexagonView hexToConquer = candidates.get(random.nextInt(candidates.size()));
            if (hexToConquer != null) {
                hexToConquer.ConquerMe();
                System.out.println("Conquisto el hexagono de Fila "+hexToConquer.getPosY()+" y Columna "+hexToConquer.getPosX());
            }
        }else{
            System.out.println("YOLO");
        }
    }
}
