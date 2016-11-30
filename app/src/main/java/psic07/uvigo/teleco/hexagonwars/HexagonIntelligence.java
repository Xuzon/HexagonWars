package psic07.uvigo.teleco.hexagonwars;

import java.util.Random;

import psic07.uvigo.teleco.hexagonwars.ai.AiPlayer;

/**
 * Created by danie on 30/11/2016.
 */

public class HexagonIntelligence {
    HexagonView hexagon;
    float NearbyAlliesWeight = 0;
    float NearbyEnemiesWeight = 0;
    float WillConquerWeight = 0;
    //for testing purposes
    Random random;
    public HexagonIntelligence(HexagonView hexagon){
        this.hexagon = hexagon;
        random = new Random();
    }

    public float Utility(AiPlayer player){
        float toRet = 0;
        NearbyAlliesWeight = player.NearbyAlliesWeight;
        NearbyEnemiesWeight = player.NearbyEnemiesWeight;
        WillConquerWeight = player.WillConquerWeight;
        toRet += NearbyAlliesValue();
        toRet += NearbyEnemiesValue();
        toRet += WillConquerValue();
        return toRet;
    }

    public float NearbyAlliesValue(){
        float toRet = 0;
        //toRet += random.nextInt(7);
        toRet += hexagon.countAllies();
        toRet *= NearbyAlliesWeight;
        return toRet;
    }

    public float NearbyEnemiesValue(){
        float toRet = 0;
        //toRet += random.nextInt(7);
        toRet += (7-hexagon.countEnemies());
        toRet *= NearbyEnemiesWeight;
        return toRet;
    }

    public float WillConquerValue(){
        float toRet = 0;
        toRet += random.nextInt(GameActivity.grid.size());
        toRet *= WillConquerWeight;
        return toRet;
    }

    public boolean WillEndGameValue(){
        boolean toRet = false;
        return toRet;
    }
}
