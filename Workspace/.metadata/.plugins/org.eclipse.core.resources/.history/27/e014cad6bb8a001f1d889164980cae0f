package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameLluvia {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Tarro tarro;
    private Lluvia lluvia;

    private Texture background;
    private Texture dayBackground;
    private Texture nightBackground;
    private Texture buttonTexture;

    private BitmapFont font; // Fuente para puntos y vidas

    private Stage stage;
    private boolean juegoPausado = false;
    private ImageButton botonReiniciar;

    public GameLluvia() {
        // Inicializar fondos
        dayBackground = new Texture(Gdx.files.internal("DayBackground1.jpg"));
        nightBackground = new Texture(Gdx.files.internal("NightBackground.png"));
        background = dayBackground;

        // Inicializar cámara y batch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        // Inicializar fuente
        font = new BitmapFont();

        // Crear Tarro y Lluvia
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("01._damage_grunt_male.wav"));
        tarro = new Tarro(new Texture(Gdx.files.internal("pirataSpriteA1OpenG.png")), hurtSound);

        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("Coins4.mp3"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("pirate.mp3"));
        lluvia = new Lluvia(
        	    new Texture(Gdx.files.internal("moneda_pirata.png")),
        	    new Texture(Gdx.files.internal("Shark1.png")),
        	    new Texture(Gdx.files.internal("moneda_pirata.png")),
        	    dropSound,
        	    rainMusic
        	);


        tarro.crear();
        lluvia.crear();

        // Inicializar Stage
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Crear botón de reinicio
        buttonTexture = new Texture(Gdx.files.internal("button.png"));
        crearBotonReiniciar();
    }

    private void crearBotonReiniciar() {
        // Crear drawable para el botón
        TextureRegionDrawable drawable = new TextureRegionDrawable(buttonTexture);

        // Crear ImageButton con el drawable
        botonReiniciar = new ImageButton(drawable);
        botonReiniciar.setSize(125, 62);

        // Configurar posición inicial del botón
        actualizarPosicionBotonReiniciar();

        // Añadir listener para reiniciar el juego
        botonReiniciar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reiniciarJuego();
            }
        });

        // Añadir el botón al Stage
        stage.addActor(botonReiniciar);
        botonReiniciar.setVisible(false);
    }

    // Método para actualizar la posición del botón dinámicamente
    private void actualizarPosicionBotonReiniciar() {
        botonReiniciar.setPosition(
            (stage.getViewport().getWorldWidth() - botonReiniciar.getWidth()) / 2,
            (stage.getViewport().getWorldHeight() - botonReiniciar.getHeight()) / 2
        );
    }

    public void resize(int width, int height) {
        // Actualizar el viewport del stage
        stage.getViewport().update(width, height, true);

        // Reposicionar el botón de reinicio
        actualizarPosicionBotonReiniciar();
    }

    public void actualizarFondo() {
        if (tarro.getPuntos() >= 1000) {
            background = nightBackground;
        } else {
            background = dayBackground;
        }
    }

    public void render() {
        if (tarro.getVidas() <= 0 && !juegoPausado) {
            pausarJuego();
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, 800, 480);
        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 10, 470);
        font.draw(batch, "Vidas: " + tarro.getVidas(), 720, 470);
        batch.end();

        if (juegoPausado) {
            botonReiniciar.setVisible(true);
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

    public void actualizarMovimiento() {
        if (!juegoPausado) {
            tarro.actualizarMovimiento();
            lluvia.actualizarMovimiento(tarro, false);
        }
    }

    public void pausarJuego() {
        juegoPausado = true;
        botonReiniciar.setVisible(true);
    }

    public void reiniciarJuego() {
        tarro.reiniciar();
        lluvia.reiniciar();
        background = dayBackground;
        juegoPausado = false;
        botonReiniciar.setVisible(false);
    }

    public void dispose() {
        dayBackground.dispose();
        nightBackground.dispose();
        buttonTexture.dispose();
        font.dispose();
        tarro.destruir();
        lluvia.destruir();
        batch.dispose();
    }

    // Método para obtener el Stage
    public Stage getStage() {
        return stage;
    }
}
