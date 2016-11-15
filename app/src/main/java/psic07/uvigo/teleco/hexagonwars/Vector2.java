package psic07.uvigo.teleco.hexagonwars;

/**
 * Created by danie on 15/11/2016.
 */

public class Vector2 {
    public int x;
    public int y;
    public static final Vector2 zero = new Vector2();
    public Vector2(){
        x = y = 0;
    }

    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }
}
