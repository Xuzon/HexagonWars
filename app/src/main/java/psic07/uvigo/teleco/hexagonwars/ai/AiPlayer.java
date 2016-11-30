package psic07.uvigo.teleco.hexagonwars.ai;

import psic07.uvigo.teleco.hexagonwars.GameActivity;
import psic07.uvigo.teleco.hexagonwars.HexagonDrawable;
import psic07.uvigo.teleco.hexagonwars.HexagonView;

/**
 * Created by danie on 30/11/2016.
 */

public class AiPlayer {
    GameActivity game;
    public float NearbyAlliesWeight = 0.33f;
    public float NearbyEnemiesWeight = 0.33f;
    public float WillConquerWeight = 0.33f;

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
        int gridHigherPos = -1;
        for(int i = 0; i < GameActivity.grid.size(); i++) {
            HexagonView hex = GameActivity.grid.get(i);
            //if I don't have the superToken and is not transparent I could not conquer it
            if(!game.superTokenOn && (hex.hexagon.centerColor != HexagonDrawable.transparent)){
                continue;
            }
            float value = hex.intelligence.Utility(this);
            if(value > higherValue){
                gridHigherPos = i;
                higherValue = value;
            }
        }
        if(gridHigherPos < GameActivity.grid.size() && gridHigherPos > 0) {
            HexagonView hex = GameActivity.grid.get(gridHigherPos);
            hex.ConquerMe();
        }
    }
}
