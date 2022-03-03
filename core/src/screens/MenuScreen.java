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

public class MenuScreen extends BaseScreen{
    private Stage stage;
    private Skin skin;
    private Image logo, fondo;
    private TextButton play,creditos,ranking,exit;

    public MenuScreen(final MainGame game) {
        super(game);
        stage=new Stage(new FitViewport(640,360));

        skin= new Skin(Gdx.files.internal("skin/uiskin.json"));
        logo=new Image(game.getManager().get("logo.png", Texture.class));
        fondo=new Image(game.getManager().get("fondo.png",Texture.class));
        play=new TextButton("Play", skin);
        creditos=new TextButton("Creditos", skin);
        ranking=new TextButton("Ranking", skin);
        exit=new TextButton("Exit", skin);


        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.seleccionScreen);
            }
        });
        creditos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.creditsScreen);
            }
        });
        ranking.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(game.creditsScreen);
                System.out.println("RANKING");
            }
        });
        exit.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        fondo.setSize(800,600);
        logo.setSize(300,300);
        logo.setPosition(((stage.getWidth()-logo.getWidth())/2)/2-100,(stage.getHeight()-logo.getHeight())/2);
        play.setSize(200,50);
        play.setPosition(((stage.getWidth()-play.getWidth())/2)*2-100,(stage.getHeight()/2)+100);
        ranking.setSize(200,50);
        ranking.setPosition(((stage.getWidth()-play.getWidth())/2)*2-100,(stage.getHeight()/2)+30);
        creditos.setSize(200,50);
        creditos.setPosition(((stage.getWidth()-play.getWidth())/2)*2-100,stage.getHeight()/2-45);
        exit.setSize(200,50);
        exit.setPosition(((stage.getWidth()-play.getWidth())/2)*2-100,(stage.getHeight()/2)-120);
        stage.addActor(fondo);
        stage.addActor(logo);
        stage.addActor(play);
        stage.addActor(creditos);
        stage.addActor(ranking);
        stage.addActor(exit);
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
    }

    @Override
    public void render(float delta) {
        //gl es lo que esta por debajo de la tarjeta grafica y le decimos que se vacie todos los
        // colores que tiene en pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Si preferimos limpiar la pantalla dejando otro color de fondo usamos la siguiente linea
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        stage.act();
        stage.draw();
    }
}

