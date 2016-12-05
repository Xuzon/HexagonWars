package psic07.uvigo.teleco.hexagonwars;

import java.util.ArrayList;
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

    public float Utility(AiPlayer player,boolean superToken){
        float toRet = 0;
        ArrayList<HexagonView> myGrid = new ArrayList<>();
        for(HexagonView view : GameActivity.grid){
            myGrid.add(view.Clone());
        }
        NearbyAlliesWeight = player.NearbyAlliesWeight;
        NearbyEnemiesWeight = player.NearbyEnemiesWeight;
        WillConquerWeight = player.WillConquerWeight;
        toRet += WillConquerValue(myGrid,superToken);
        toRet += NearbyAlliesValue();
        toRet += NearbyEnemiesValue();
        return toRet;
    }


    public float NearbyAlliesValue(){
        float toRet = 0;
        toRet += hexagon.countAllies(null);
        toRet *= NearbyAlliesWeight;
        return toRet;
    }

    public float NearbyEnemiesValue(){
        float toRet = 0;
        toRet += (6-hexagon.countEnemies(null));
        toRet *= NearbyEnemiesWeight;
        return toRet;
    }

    public float WillConquerValue(ArrayList<HexagonView> myGrid, boolean superToken){
        float toRet = 0;
        toRet += SimulateMovement(myGrid,superToken);
        toRet *= WillConquerWeight;
        return toRet;
    }

    protected int SimulateMovement(ArrayList<HexagonView> myGrid,boolean superToken){
        int toRet = 0;

        PointsClass olderPoints = CountPoints(myGrid);
        HexagonView myCopy = myGrid.get(hexagon.posArray);
        ChangeAffectedColors(myCopy);
        myCopy.testConquerAround(true,myGrid);
        for (HexagonView hex : myGrid) {
            hex.testConquerAround(true,myGrid);
        }
        PointsClass nextPoints = CountPoints(myGrid);
        int differenceMyPoints = nextPoints.myPoints - olderPoints.myPoints;
        int differenceHisPoints = nextPoints.hisPoints - olderPoints.hisPoints;
        toRet = differenceMyPoints - differenceHisPoints;
        return  toRet;
    }

    private void ChangeAffectedColors(HexagonView myCopy) {
        myCopy.ChangeColor(true);
    }

    private PointsClass CountPoints(ArrayList<HexagonView> myGrid) {
        PointsClass pClass = new PointsClass();
        for(HexagonView hex : myGrid){
            if(hex.hexagon.centerColor == hexagon.game.turnColor){
                pClass.myPoints++;
            }else{
                if(hex.hexagon.centerColor == hexagon.game.noturnColor){
                    pClass.hisPoints++;
                }
            }
        }
        return pClass;
    }

    public boolean WillEndGameValue(){
        boolean toRet = false;
        return toRet;
    }

    private class PointsClass{
        public  int myPoints;
        public int hisPoints;
        public PointsClass(){
            myPoints = 0;
            hisPoints = 0;
        }
    }
}
