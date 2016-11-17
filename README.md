# HexagonWars

- [Descripción](#Descripción)
- [Reglas](#Reglas)

## Descripción
Proyecto grupo C de PSI, Grupo 07

Consiste en un juego contra una inteligencia artificial, consiste en conquistar un campo de batalla trazado con hexágonos. El jugador tiene como objetivo obtener mas hexagonos en el mapa que el pc, pero tendrá que utilizar la extrategia mas adecuada, ya que el oponente podrá conquistar zonas que estubierán ya conquistadas.

#### Miembros de grupo:
- Carlos Daniel Garrido Suárez
- Guillermo Manuel Rodríguez Agrasar
- Waheed Muhammad Mughal
- Bruno Nogareda Da Cruz


## Reglas
1. Cada jugador tiene un turno y en el turno tiene que poner un token en el campo de batalla, este tiene que ser colocado en un hexágono en blanco

2. En el primer turno, el token lo jugador, el token tiene la posibilidad de convertirse en un Super Token, si el jugador está perdiendo tiene mayores posibilidades.

3. Cuando se coloca un Token mira a los hexágonos cercanos, si tiene un enemigo cercano que intenta conquistarlo, la conquista tendrá éxito si el hexágono enemigo tiene más enemigos que aliados que lo rodean.

4. Cuando se coloca un Súper Token conquista inmediatamente a todos los enemigos circundantes.

5. Cuando se conquiste un lugar, intentará conquistar de nuevo a todos los enemigos cercanos.
**Ejemplo:** si un lugar era rojo y se vuelve azul tratará de conquistar a todos los enemigos rojos cercanos

6. Cuando todo el campo de batalla se toma el jugador que tiene más hexágonos conquistados es el ganador


##Referencias
- [Android Developers](http://developer.android.com/index.html)
- [Android Developer NanoDegree](https://www.udacity.com/course/android-developer-nanodegree--nd801)
- [Programming Mobile Applications for Android Handheld Systems: Part 1](https://www.coursera.org/learn/android-programming)
- [Programming Mobile Applications for Android Handheld Systems: Part 2](https://www.coursera.org/learn/android-programming-2)
- [Android programming course: learn how to create your own applications](http://www.sgoliver.net/blog/curso-de-programacion-android/)