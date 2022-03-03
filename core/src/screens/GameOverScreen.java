package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOverScreen extends BaseScreen{
    private Stage stage;
    private Skin skin;
    private TextButton volver, jugar;
    private Label tiempo;
    private Image fondo;


    public GameOverScreen(final MainGame game) {
        super(game);
        fondo=new Image(game.getManager().get("podium.jpg", Texture.class));
        stage=new Stage(new FitViewport(640,360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        volver = new TextButton("Volver al menu", skin);
        jugar = new TextButton("Volver a jugar", skin);
//        gameOver=new Label("GAME OVER",skin);
        tiempo=new Label("Tu tiempo es: ",skin);

        volver.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });

        jugar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        fondo.setSize(600,450);
//        gameOver.setPosition(255,270);
        tiempo.setPosition(250,240);
        jugar.setSize(150, 50);
        jugar.setPosition(45, 70);
        volver.setSize(150, 50);
        volver.setPosition(420, 70);


        stage.addActor(fondo);
        stage.addActor(tiempo);
//        stage.addActor(gameOver);
        stage.addActor(volver);
        stage.addActor(jugar);
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
