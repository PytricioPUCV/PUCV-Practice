package io.github.some_example_name;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<ObjetoLluvia> objetosLluvia;
    private long lastDropTime;
    private Sound dropSound;
    private Music rainMusic;

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

    private void crearObjetoLluvia() {
        ObjetoLluvia objeto;

        // Generar un número aleatorio entre 1 y 100 para ponderar las probabilidades
        int probabilidad = MathUtils.random(1, 100);

        // Asignar probabilidades: Moneda (60%), Tiburón (20%), Espada (20%)
        if (probabilidad <= 60) {
            objeto = new Moneda(texturaMoneda); // Moneda: 60% de probabilidad
        } else if (probabilidad <= 80) {
            objeto = new Tiburón(texturaTiburon); // Tiburón: 20% de probabilidad
        } else {
            objeto = new Espada(texturaEspada); // Espada: 20% de probabilidad
        }

        // Asignar una posición aleatoria al objeto
        objeto.getArea().x = MathUtils.random(0, 800 - 64);
        objeto.getArea().y = 480;

        objetosLluvia.add(objeto);
        lastDropTime = TimeUtils.nanoTime();
    }


    public void actualizarMovimiento(Tarro tarro, boolean juegoPausado) {
        if (juegoPausado) return;

        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearObjetoLluvia();
        }


        for (int i = 0; i < objetosLluvia.size; i++) {
            ObjetoLluvia objeto = objetosLluvia.get(i);
            objeto.actualizarMovimiento(300); // Velocidad fija por ahora

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
