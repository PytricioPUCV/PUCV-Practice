package io.github.some_example_name;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia implements Destruible {
    private Array<ObjetoLluvia> objetosLluvia;
    private long lastDropTime;
    private Sound dropSound;
    private Music rainMusic;

    private long intervaloCreacion = 80000000; // Intervalo por defecto en nanosegundos
    private float velocidadCaida = 300; // Velocidad inicial de caída

    // Probabilidades iniciales
    private int probabilidadMoneda = 60;  // 60%
    private int probabilidadTiburon = 20; // 20%
    //private int probabilidadEspada = 20;  // 20%


    private Texture texturaMoneda;
    private Texture texturaTiburon;
    private Texture texturaEspada;

    public Lluvia(Texture moneda, Texture tiburon, Texture espada, Sound ss, Music mm) {
        this.texturaMoneda = moneda;
        this.texturaTiburon = tiburon;
        this.texturaEspada = espada;
        this.dropSound = ss;
        this.rainMusic = mm;
    }

    public void crear() {
        objetosLluvia = new Array<>();
        crearObjetoLluvia();

        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void ajustarDificultad(Tarro tarro) {
        int puntos = tarro.getPuntos();

        if (puntos >= 1200) {
            // Cambiar las probabilidades para mayor dificultad
            probabilidadMoneda = 40;  // Monedas menos frecuentes (40%)
            probabilidadTiburon = 60; // Tiburones más frecuentes (40%)
            //probabilidadEspada = 20;  // Espadas permanecen igual (20%)

            // Aumentar la velocidad de caída
            velocidadCaida = 700;

            // Reducir el tiempo entre apariciones de objetos
            intervaloCreacion = 40000000; // más rápido
        }

        if (puntos >= 1000) {
            // Cambiar las probabilidades para mayor dificultad
            probabilidadMoneda = 50;  // Monedas menos frecuentes (40%)
            probabilidadTiburon = 50; // Tiburones más frecuentes (40%)
            //probabilidadEspada = 20;  // Espadas permanecen igual (20%)

            // Aumentar la velocidad de caída
            velocidadCaida = 600;

            // Reducir el tiempo entre apariciones de objetos
            intervaloCreacion = 50000000; // Se mantiene
        }

        if (puntos >= 7000) {
            // Cambiar las probabilidades para mayor dificultad
            probabilidadMoneda = 60;  // Monedas menos frecuentes (40%)
            probabilidadTiburon = 40; // Tiburones más frecuentes (40%)
            //probabilidadEspada = 20;  // Espadas permanecen igual (20%)

            // Aumentar la velocidad de caída
            velocidadCaida = 500;

            // Reducir el tiempo entre apariciones de objetos
            intervaloCreacion = 50000000; // más rápido
        } else if (puntos >= 500) {
            // Ajustes intermedios (500 puntos)
            probabilidadMoneda = 70;
            probabilidadTiburon = 30;
            //probabilidadEspada = 20;

            velocidadCaida = 400;
            intervaloCreacion = 70000000; // 0.07 segundos
        } else {
            // Valores por defecto (menos de 500 puntos)
            probabilidadMoneda = 80;
            probabilidadTiburon = 20;
            //probabilidadEspada = 20;

            velocidadCaida = 300;
            intervaloCreacion = 80000000; // 0.08 segundos
        }
    }


    private void crearObjetoLluvia() {
        ObjetoLluvia objeto;

        // Generar un número aleatorio entre 1 y 100 para decidir el objeto a crear
        int probabilidad = MathUtils.random(1, 100);

        // Usar las probabilidades dinámicas
        if (probabilidad <= probabilidadMoneda) {
            objeto = new Moneda(texturaMoneda);
        } else if (probabilidad <= probabilidadMoneda + probabilidadTiburon) {
            objeto = new Tiburón(texturaTiburon);
        } else {
            objeto = new Espada(texturaEspada);
        }

        // Asignar una posición aleatoria al objeto
        objeto.getArea().x = MathUtils.random(0, 800 - 64);
        objeto.getArea().y = 480;

        objetosLluvia.add(objeto);
        lastDropTime = TimeUtils.nanoTime();
    }


    public void actualizarMovimiento(Tarro tarro, boolean juegoPausado) {
        if (juegoPausado) return;

        // Ajustar la dificultad en función de los puntos del tarro
        ajustarDificultad(tarro);

        // Crear un nuevo objeto si ha pasado el intervalo definido
        if (TimeUtils.nanoTime() - lastDropTime > intervaloCreacion) {
            crearObjetoLluvia();
        }

        // Actualizar la posición de los objetos
        for (int i = 0; i < objetosLluvia.size; i++) {
            ObjetoLluvia objeto = objetosLluvia.get(i);
            objeto.actualizarMovimiento(velocidadCaida); // Usar velocidad ajustada

            // Verificar colisión con el tarro
            if (objeto.getArea().overlaps(tarro.getArea())) {
                objeto.alColisionar(tarro);
                objetosLluvia.removeIndex(i);
                dropSound.play();
            }

            // Eliminar objetos fuera de la pantalla
            if (objeto.getArea().y + 64 < 0) {
                objetosLluvia.removeIndex(i);
            }
        }
    }


    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (ObjetoLluvia objeto : objetosLluvia) {
            objeto.dibujar(batch);
        }
    }

    @Override
    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }

    public void reiniciar() {
        objetosLluvia.clear();
        crearObjetoLluvia();
        lastDropTime = TimeUtils.nanoTime();
        rainMusic.play();
    }
}
