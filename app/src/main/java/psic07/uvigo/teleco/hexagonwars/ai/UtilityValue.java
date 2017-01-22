package psic07.uvigo.teleco.hexagonwars.ai;

import java.util.Comparator;

/**
 * Created by danie on 22/01/2017.
 */

public class UtilityValue {
    public int posArray;
    public float value;
    public int willConquer;
    public int enemiesAround;
    public int alliesAround;

    public UtilityValue(){}

    public UtilityValue(int posArray,float value, int willConquer, int enemiesAround, int alliesAround){
        this.posArray = posArray;
        this.value = value;
        this.willConquer = willConquer;
        this.enemiesAround = enemiesAround;
        this.alliesAround = alliesAround;
    }

    public static class ValueSort implements Comparator<UtilityValue> {
        public int compare(UtilityValue u1 , UtilityValue u2){
            float toRet = u2.value - u1.value;
            return (int) toRet;
        }
    }

    public static class EnemiesSort implements Comparator<UtilityValue> {
        public int compare(UtilityValue u1 , UtilityValue u2){
            int toRet = u2.enemiesAround - u1.enemiesAround;
            return toRet;
        }
    }

    public static class AlliesSort implements Comparator<UtilityValue> {
        public int compare(UtilityValue u1 , UtilityValue u2){
            int toRet = u2.alliesAround - u1.alliesAround;
            return toRet;
        }
    }
}
