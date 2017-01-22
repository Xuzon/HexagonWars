package psic07.uvigo.teleco.hexagonwars.ai;

import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.Collections;
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
    public float weightLearn = 0.10f;
    public ArrayList<UtilityValue> lastCandidates;
    public int lastPoints;
    public int expectedPoints;
    private boolean firstTurn = true;

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
        ArrayList<UtilityValue> candidates = new ArrayList<>();
        for(int i = 0; i < GameActivity.grid.size(); i++) {
            HexagonView hex = GameActivity.grid.get(i);

            //if I don't have the superToken and is not transparent I could not conquer it
            if(!game.superTokenOn && (hex.hexagon.centerColor != HexagonDrawable.transparent)){
                continue;
            }
            UtilityValue value = hex.intelligence.Utility(this,game.superTokenOn);
            candidates.add(value);
        }
        if(candidates.size() > 0) {
            Collections.sort(candidates,new UtilityValue.ValueSort());
            if(lastPoints != 0 && GameActivity.topPlayerScore.score  <= lastPoints){
                //I lost points so it is time to learn
                ReinforcementLearning(lastCandidates,candidates);
            }
            UtilityValue ut = candidates.get(0);
            lastPoints = GameActivity.topPlayerScore.score;
            expectedPoints = (int) (lastPoints + (float)ut.willConquer / WillConquerWeight);
            HexagonView hexToConquer = GameActivity.grid.get(ut.posArray);
            if(firstTurn){
                hexToConquer = GameActivity.grid.get(new Random().nextInt(GameActivity.grid.size()));
                firstTurn = false;
            }
            if (hexToConquer != null) {
                lastCandidates = candidates;
                hexToConquer.ConquerMe();
                System.out.println("Conquisto el hexagono de Fila "+hexToConquer.getPosY()+" y Columna "+hexToConquer.getPosX());
            }
        }else{
            System.out.println("YOLO");
        }
    }

    private void ReinforcementLearning(ArrayList<UtilityValue> lastCandidates, ArrayList<UtilityValue> candidates){
        weight lastWeight = weight.conquer;
        UtilityValue lC = lastCandidates.get(0);

        //Get real values
        lC.willConquer /= WillConquerWeight;
        lC.enemiesAround /= NearbyEnemiesWeight;
        lC.alliesAround /= NearbyAlliesWeight;

        //Normalize to get comparable values
        lC.willConquer /= 5f;
        lC.willConquer = Math.min(1, lC.willConquer);
        lC.alliesAround /= 6f;
        lC.enemiesAround /= 6f;

        //by default the decisive conquer will be conquer

        if(lC.alliesAround > lC.willConquer && lC.alliesAround > lC.enemiesAround){
            //nearby allies value was decisive to the last candidate
            lastWeight = weight.allies;
        }

        if(lC.enemiesAround > lC.willConquer && lC.enemiesAround > lC.alliesAround){
            //nearby enemies value was decisive to the last candidate
            lastWeight = weight.enemies;
        }
        switch (lastWeight){
            case conquer:
                WillConquerWeight -= weightLearn;
                NearbyEnemiesWeight += weightLearn / 2f;
                NearbyAlliesWeight += weightLearn / 2f;
                break;
            case allies:
                WillConquerWeight += weightLearn / 2f;
                NearbyEnemiesWeight += weightLearn / 2f;
                NearbyAlliesWeight -= weightLearn;
                break;
            case enemies:
                WillConquerWeight += weightLearn / 2f;
                NearbyEnemiesWeight -= weightLearn;
                NearbyAlliesWeight += weightLearn / 2f;
                break;
        }
        //be sure all weights are non negative
        WillConquerWeight = Math.max(WillConquerWeight,0);
        NearbyAlliesWeight = Math.max(NearbyAlliesWeight,0);
        NearbyEnemiesWeight = Math.max(NearbyEnemiesWeight,0);
    }

    private enum weight {conquer, allies, enemies}
}
