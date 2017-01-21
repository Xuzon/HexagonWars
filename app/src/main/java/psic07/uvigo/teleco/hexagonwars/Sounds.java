package psic07.uvigo.teleco.hexagonwars;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

/**
 * Created by Waheed on 21/01/2017.
 */

public class Sounds extends AppCompatActivity {

    public final int gameSoundPath = R.raw.epic;                //Path de sonido de juego.
    public final int buttonsSoundPath = R.raw.click;                //Path de sonido de bottones.
    public final int hexagonconquerSoundPath = R.raw.sms;                //Path de sonido de la conquista de hexagono.
    public final int superTokenSoundPath = R.raw.bomb;                //Path de sonido de super token.
    public final int loserSoundPath = R.raw.loser;              //Path de sonido de fin perdedor.
    public final int winnerSoundPath = R.raw.winner;              //Path de sonido de fin ganador.


    public Context context;

    MediaPlayer gamesound;
    MediaPlayer bottonsound;
    MediaPlayer hexagonconquersound;
    MediaPlayer supertoken;
    MediaPlayer loser;
    MediaPlayer winner;

    public final static int musicGameRestart = -3;
    public final static int musicGamePause = -2;
    public final static int musicGameStop = -1;
    public final static int musicGame = 1;
    public final static int soundButtons = 2;
    public final static int soundConquer = 3;
    public final static int soundBoom = 4;
    public final static int soundLoser = 5;
    public final static int soundWinner = 6;

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
        gamesound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        gamesound.setLooping(true);
        gamesound.start();
    }

    /**
     * Funcion que se encarga de reproducir el sonido de botones.
     */
    private void playBottonsSound() {
        if(bottonsound==null) {
            bottonsound = MediaPlayer.create(context, buttonsSoundPath);            //Creamos mediaplayer para el sonido de los botones
            bottonsound.setAudioStreamType(AudioManager.STREAM_MUSIC);

        }
        if(!bottonsound.isPlaying()) {
            bottonsound.start();
        }

    }

    /**
     * Funcion que se encarga de reproducir el sonido de la conquista.
     */
    private void playHexagonConquerSound()
    {
        if(hexagonconquersound==null) {
            hexagonconquersound = MediaPlayer.create(context, hexagonconquerSoundPath);  //Creamos mediaplayer para el sonido de la conquista de los hexagonostones.
            hexagonconquersound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        if(!hexagonconquersound.isPlaying()) {
            hexagonconquersound.start();
        }
    }

    /**
     * Funcion que se encarga de reproducir el sonido de la conquista.
     */
    private void playSuperTokenSound() {
        if(supertoken==null) {
            supertoken = MediaPlayer.create(context, superTokenSoundPath);             //Media Player para sonido de  Opcines.
            supertoken.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        if(!supertoken.isPlaying()) {
            supertoken.start();
        }
    }

    private void playLoserSound() {
        if(loser==null) {
            loser = MediaPlayer.create(context, loserSoundPath);             //Media Player para sonido de  Opcines.
            loser.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        loser.start();
    }

    private void playWinnerSound() {
        if (winner == null) {
            winner = MediaPlayer.create(context, winnerSoundPath);             //Media Player para sonido de  Opcines.
            winner.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        winner.start();
    }

    /**
     * Funcion que se encarga de parar el sonido del juego.
     */
    private void stopGameSound() {
        if(gamesound != null)
            gamesound.release();
    }

    private void pauseGameSound() {
        if (gamesound != null) {
            if (gamesound.isPlaying()) {
                gamesound.pause();
            }
        }
    }

        private void restartGameSound() {
            if (gamesound != null) {
                if (!gamesound.isPlaying()) {
                    gamesound.start();
                }
            }
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

        if(isSoundActive) {
            switch (sound) {
                case soundButtons:
                    playBottonsSound();
                    break;
                case soundConquer:
                    playHexagonConquerSound();
                    break;
                case soundBoom:
                    playSuperTokenSound();
                    break;
                case soundWinner:
                    playWinnerSound();
                    break;
                case soundLoser:
                    playLoserSound();
                    break;
            }
        }

        if(isMusicActive) {
            switch (sound) {
                case musicGame:
                    playGameSound();
                    break;
                case musicGamePause:
                    pauseGameSound();
                    break;
                case musicGameRestart:
                    restartGameSound();
                    break;
                case musicGameStop:
                    stopGameSound();
                    break;
            }
        }
        else {
            switch (sound) {
                case musicGameStop:
                    stopGameSound();
                    break;
                case musicGamePause:
                    pauseGameSound();
                    break;
            }
        }
    }



}
