package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SeleccionScreen extends BaseScreen{
    private Stage stage;
    private Skin skin;
    private TextButton blue, green, red, yellow, black, volver;
    private Image fondo,azul,amarillo,rojo,negro,verde;

    public SeleccionScreen(final MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        fondo=new Image(game.getManager().get("bandera.jpg", Texture.class));
        blue = new TextButton("Blue", skin);
        red = new TextButton("Red", skin);
        green = new TextButton("Green", skin);
        yellow = new TextButton("Yellow", skin);
        black = new TextButton("Black", skin);
        volver = new TextButton("Volver", skin);
        azul=new Image(game.getManager().get("car_blue_1.png",Texture.class));
        amarillo=new Image(game.getManager().get("car_yellow_1.png",Texture.class));
        rojo=new Image(game.getManager().get("car_red_1.png",Texture.class));
        negro=new Image(game.getManager().get("car_black_1.png",Texture.class));
        verde=new Image(game.getManager().get("car_green_1.png",Texture.class));

        blue.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.texture=game.getManager().get("car_blue_1.png");
                game.setScreen(game.gameScreen);
            }
        });

        red.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.texture=game.getManager().get("car_red_1.png");
                game.setScreen(game.gameScreen);
            }
        });

        green.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.texture=game.getManager().get("car_green_1.png");
                game.setScreen(game.gameScreen);
            }
        });

        yellow.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.texture=game.getManager().get("car_yellow_1.png");
                game.setScreen(game.gameScreen);
            }
        });

        black.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.texture=game.getManager().get("car_black_1.png");
                game.setScreen(game.gameScreen);
            }
        });
        volver.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });
        azul.setSize(100,50);
        azul.setPosition(80,260);
        blue.setSize(100, 50);
        blue.setPosition(80, 180);
        rojo.setSize(100,50);
        rojo.setPosition(275,260);
        red.setSize(100, 50);
        red.setPosition(275, 180);
        verde.setSize(100,50);
        verde.setPosition(470,260);
        green.setSize(100, 50);
        green.setPosition(470, 180);
        amarillo.setSize(100,50);
        amarillo.setPosition(175,100);
        yellow.setSize(100, 50);
        yellow.setPosition(175, 25);
        negro.setSize(100,50);
        negro.setPosition(370,100);
        black.setSize(100, 50);
        black.setPosition(370, 25);

        volver.setPosition(550, 25);

        stage.addActor(fondo);
        stage.addActor(blue);
        stage.addActor(red);
        stage.addActor(yellow);
        stage.addActor(green);
        stage.addActor(black);
        stage.addActor(volver);
        stage.addActor(azul);
        stage.addActor(rojo);
        stage.addActor(amarillo);
        stage.addActor(verde);
        stage.addActor(negro);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
