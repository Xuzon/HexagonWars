package psic07.uvigo.teleco.hexagonwars;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Waheed on 21/01/2017.
 */

public class Sounds extends AppCompatActivity {

    public final int gameSoundPath = R.raw.epic;                //Path de sonido de juego.
    public final int buttonsSoundPath = R.raw.click;                //Path de sonido de bottones.
    public final int hexagonconquerSoundPath = R.raw.sms;                //Path de sonido de la conquista de hexagono.
    public final int superTokenSoundPath = R.raw.dance;                //Path de sonido de super token.


    public Context context;


    MediaPlayer gamesound;
    MediaPlayer bottonsound;
    MediaPlayer hexagonconquersound;
    MediaPlayer supertoken;

    public boolean isMusicActive = true;                        //Variable si esta habilitada lla Música
    public boolean isSoundActive = true;                        //Variable si esta habitados llos sonidos.


    public Sounds() {}

    public Sounds(Context context) {
        this.context = context;
    }

//    public void createSounds (){
//
//       if(gamesound != null) gamesound = MediaPlayer.create(this,gameSoundPath);               //Creamos mediaplayer para el sonido del juego.
//        if(bottonsound != null) bottonsound = MediaPlayer.create(this,buttonsSoundPath);            //Creamos mediaplayer para el sonido de los botones
//        if(hexagonconquersound != null) hexagonconquersound = MediaPlayer.create(this,hexagonconquerSoundPath);  //Creamos mediaplayer para el sonido de la conquista de los hexagonostones.
//        if(supertoken != null) supertoken = MediaPlayer.create(this,superTokenSoundPath);             //Media Player para sonido de  Opcines.
//    }


    /**
     * Funcion que se encarga de reproducir el sonido del juego.
     */
    private void playGameSound() {
        gamesound = MediaPlayer.create(context, gameSoundPath);               //Creamos mediaplayer para el sonido del juego.
        gamesound.setLooping(true);
        gamesound.start();
    }

    /**
     * Funcion que se encarga de reproducir el sonido de botones.
     */
    private void playBottonsSound() {
        bottonsound = MediaPlayer.create(context, buttonsSoundPath);            //Creamos mediaplayer para el sonido de los botones
        bottonsound.start();
    }

    /**
     * Funcion que se encarga de reproducir el sonido de la conquista.
     */
    private void playHexagonConquerSound() {
        hexagonconquersound = MediaPlayer.create(context, hexagonconquerSoundPath);  //Creamos mediaplayer para el sonido de la conquista de los hexagonostones.
        hexagonconquersound.start();
    }

    /**
     * Funcion que se encarga de reproducir el sonido de la conquista.
     */
    private void playSuperTokenSound() {
        supertoken = MediaPlayer.create(context, superTokenSoundPath);             //Media Player para sonido de  Opcines.
        supertoken.start();
    }

    /**
     * Funcion que se encarga de parar el sonido del juego.
     */
    private void stopGameSound() {
        if(gamesound != null)
            gamesound.release();
    }

    /**
     * Funcion que se encarga de reproducir el sonido segun el parametro de entrada. También desactiva los
     * sonidos y musica. Segun el parametro de entra :
     *
     *      1 = Reproducir la música del juego.
     *      2 = Reproducir el sonido del botones y hexágonos
     *      3 = Reproducir el sonido de conquista de hexágono.
     *      4 = Reproducir el sonido  del super token.
     *      -1 = Desactivar la Múisica la música del juego
     *
     *
     *
     * @param sound
     */
    public void SoundSelection(int sound){

        if(sound == 1 && isMusicActive == true){
            playGameSound();
        }
        else  if(sound == 2 && isSoundActive == true){
            playBottonsSound();
        }
        else  if(sound == 3 && isSoundActive == true){
            playHexagonConquerSound();
        }
        else  if(sound == 4 && isSoundActive == true){
            playSuperTokenSound();
        }

        if(sound == -1){
            stopGameSound();
        }

    }



}
