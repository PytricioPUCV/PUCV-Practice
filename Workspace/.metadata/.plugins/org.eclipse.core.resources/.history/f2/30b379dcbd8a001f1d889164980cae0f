package io.github.some_example_name;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Tarro {
    private Rectangle bucket;
    private Texture bucketImage;
    private TextureRegion[][] pirateRegions; // Matriz para los sprites del pirata
    private Sound sonidoHerido;
    
    private int vidas = 3;
    private int puntos;
    private int velx = 280;
    private boolean herido = false;
    private final int tiempoHeridoMax = 50;
    private int tiempoHerido;
    private int direccion; // 0 = Frontal (cara), 1 = Lado, 2 = Espalda
    private int frameActual = 0; // Frame actual de la animación
    private boolean flipX = false; // Indica si se debe voltear el sprite horizontalmente

    public Tarro(Texture tex, Sound ss) {
        bucketImage = tex;
        sonidoHerido = ss;

        // Dividir la imagen en una matriz de regiones de 32x32
        pirateRegions = TextureRegion.split(bucketImage, 32, 32);
    }
    
    public void crear() {
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;

        // Ajustar tamaño del área del personaje (más grande si es necesario)
        bucket.width = 20;
        bucket.height = 20;
    }

    public void dibujar(SpriteBatch batch) {
        // Verificar que los índices no estén fuera del rango de la matriz
        if (frameActual >= pirateRegions.length ||
            direccion >= pirateRegions[frameActual].length) {
            // Establecer valores por defecto si los índices no son válidos
            frameActual = 0;
            direccion = 0;
        }

        // Seleccionar la región adecuada del sprite según el frame y la dirección
        TextureRegion region = new TextureRegion(pirateRegions[frameActual][direccion]);

        // Aplicar flip horizontal si es necesario
        if (flipX) {
            region.flip(true, false);
        }

        if (!herido) {
            // Dibujar con escala (64x64)
            batch.draw(region, bucket.x, bucket.y, 64, 64);
        } else {
            // Efecto de sacudida cuando está herido
            batch.draw(region, bucket.x, bucket.y + MathUtils.random(-5, 5), 64, 64);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }


    public void actualizarMovimiento() {
        if (herido) {
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
            return; // No actualizar movimiento si está herido
        }

        // Aumentar la velocidad si los puntos son 1000 o más
        if (puntos >= 1000) {
            velx = 400; // Aumentar velocidad a 400
        } else {
            velx = 280; // Velocidad inicial
        }

        boolean moving = false;

        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);

        // Movimiento y actualización de posición
        if (left && !right) {
            bucket.x -= velx * Gdx.graphics.getDeltaTime();
            moving = true;
            flipX = true; // Voltear sprite horizontalmente al moverse a la izquierda
        } else if (right && !left) {
            bucket.x += velx * Gdx.graphics.getDeltaTime();
            moving = true;
            flipX = false; // No voltear el sprite al moverse a la derecha
        }

        if (up && !down) {
            bucket.y += velx * Gdx.graphics.getDeltaTime();
            moving = true;
        } else if (down && !up) {
            bucket.y -= velx * Gdx.graphics.getDeltaTime();
            moving = true;
        }

        // Asegurar que el personaje no se salga de los límites de la pantalla
        bucket.x = MathUtils.clamp(bucket.x, 0, 800 - bucket.width);
        bucket.y = MathUtils.clamp(bucket.y, 0, 600 - bucket.height);

        // Determinar dirección
        if (moving) {
            if (left || right) {
                direccion = 1; // Lado
            } else if (down) {
                direccion = 0; // Frontal (cara)
            } else if (up) {
                direccion = 2; // Espalda
            }
            // Actualizar el frame para la animación
            frameActual = (frameActual + 1) % 4; // 4 frames de animación
        } else {
            // Si el personaje no se está moviendo
            direccion = 2; // Espalda al estar parado
            frameActual = 0; // Frame de reposo
        }
    }

    public void dañar() {
        if (vidas > 0) {
            vidas--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
        }
        if (vidas <= 0) {
            vidas = 0;
        }
    }

    public void destruir() {
        bucketImage.dispose();
    }

    public boolean estaHerido() {
        return herido;
    }

    public void reiniciar() {
        vidas = 3;
        puntos = 0;
        bucket.x = 800 / 2 - 64 / 2;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public Rectangle getArea() {
        return bucket;
    }

    public void sumarPuntos(int pp) {
        puntos += pp;
    }
}
