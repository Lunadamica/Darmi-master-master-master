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

public class CreditsScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private Label credits;
    private TextButton back;
    private Image coche;

    public CreditsScreen(final MainGame game) {
        super(game);
        coche=new Image(game.getManager().get("cocheDeco.png", Texture.class));
        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        back = new TextButton("Volver", skin);

        credits = new Label("\nJuego 2D de coches.\nAguanta el maximo tiempo posible en la carretera\nsin chocarte con otros vehiculos.\n\nTrabajo realizado por:\n Inmaculada Moran Rastrollo\nMiguel Angel Vaquero Mateos", skin);

        back.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });

        coche.setSize(250,250);
        coche.setPosition(((stage.getWidth()-coche.getWidth())/2)*2-100,10);
        credits.setPosition(20, 340 - credits.getHeight());
        back.setSize(200, 80);
        back.setPosition(40, 50);

        stage.addActor(coche);
        stage.addActor(back);
        stage.addActor(credits);
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
