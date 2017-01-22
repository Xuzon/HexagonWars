package psic07.uvigo.teleco.hexagonwars.ai;

import java.util.Comparator;
import java.util.Random;

/**
 * Created by danie on 22/01/2017.
 */

public class UtilityValue {
    public int posArray;
    public float value;
    public float willConquer;
    public float enemiesAround;
    public float alliesAround;

    public UtilityValue(){}

    public UtilityValue(int posArray,float value, float willConquer, float enemiesAround, float alliesAround){
        this.posArray = posArray;
        this.value = value;
        this.willConquer = willConquer;
        this.enemiesAround = enemiesAround;
        this.alliesAround = alliesAround;
    }

    public static class ValueSort implements Comparator<UtilityValue> {
        public int compare(UtilityValue u1 , UtilityValue u2){
            float toRet = u2.value - u1.value;
            if(toRet < 0){
                return -1;
            }else if(toRet > 0){
                return  1;
            }else {
                return 0;
            }
        }
    }

    public static class EnemiesSort implements Comparator<UtilityValue> {
        public int compare(UtilityValue u1 , UtilityValue u2){
            float toRet = u2.enemiesAround - u1.enemiesAround;
            if(toRet < 0){
                return -1;
            }else if(toRet > 0){
                return  1;
            }else {
                return 0;
            }
        }
    }

    public static class AlliesSort implements Comparator<UtilityValue> {
        public int compare(UtilityValue u1 , UtilityValue u2){
            float toRet = u2.alliesAround - u1.alliesAround;
            if(toRet < 0){
                return -1;
            }else if(toRet > 0){
                return  1;
            }else {
                return 0;
            }
        }
    }
}
