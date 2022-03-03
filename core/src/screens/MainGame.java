package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {
	private AssetManager manager;
	protected MenuScreen menuScreen;
	protected GameScreen gameScreen;
	protected CreditsScreen creditsScreen;
	protected SeleccionScreen seleccionScreen;
	protected LoadingScreen loadingScreen;
	protected GameOverScreen gameOverScreen;
	protected Texture texture;

	public AssetManager getManager() {
		return manager;
	}

	@Override
	public void create() {

		manager = new AssetManager();
		//cargo en el manager todos mis recursos
		manager.load("logo.png",Texture.class);
		manager.load("carreteras.png",Texture.class);
		manager.load("fondo.png",Texture.class);
		manager.load("bandera.jpg",Texture.class);
		manager.load("cocheDeco.png",Texture.class);
		manager.load("podium.jpg",Texture.class);
		manager.load("car_blue_1.png",Texture.class);
		manager.load("car_red_1.png",Texture.class);
		manager.load("car_green_1.png",Texture.class);
		manager.load("car_black_1.png",Texture.class);
		manager.load("car_yellow_1.png",Texture.class);
		manager.load("song.ogg", Music.class);
//		manager.finishLoading();
		texture=new Texture("car_blue_1.png");

		//Instancio mis ventanas
		loadingScreen=new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public void finishLoading(){
		menuScreen=new MenuScreen(this);
		gameScreen=new GameScreen(this);
		creditsScreen=new CreditsScreen(this);
		seleccionScreen=new SeleccionScreen(this);
		gameOverScreen=new GameOverScreen(this);
		setScreen(menuScreen);
	}
}
