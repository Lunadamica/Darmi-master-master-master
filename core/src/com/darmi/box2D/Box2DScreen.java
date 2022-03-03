package com.darmi.box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import screens.BaseScreen;
import screens.MainGame;
import com.darmi.entities.PlayerEntity;

import java.util.ArrayList;

import com.darmi.others.Chronometer;

public class Box2DScreen extends BaseScreen {
    private Stage stage;
    private World world;
    private Box2DDebugRenderer renderer;
    //Creamos una camara para ver el mundo
    private OrthographicCamera camera;

    //Creamos el cuerpo de los objetos que aparecerán en el juego
    private Body player, laterlarSuperiorBody,lateralInferiorBody, cocheBody;
    //Fisicas de los objetos del juego
    private Fixture playerFixture,laterlarSuperiorFixture, lateralInferiorFixture, cocheFixture;
    //Texturas
    private Texture playerTexture;
    //Tamaño de la camara, es decir lo que se va a ver en el mapa
    private int cameraX = 64, cameraY = 32;
    //Posicion actual del jugador en la coordenada Y y velocidades de movimiento del jugador sobre el eje Y y X
    private float posicionYActualJugador, velocityPlayerX = 12, velocityPlayerY = 12;
    //Cronometros para la reaparicion de vehiculos y el aumento de nivel
    private Chronometer aparicionVehiculos, aumentoNivel;
    //Array donde guardaremos los vehiculos que irán apareciendo de manera aleatoria
    private ArrayList<Body> vehiculos;
    private ArrayList<Fixture> vehiculosFixture;
    //Variable para comprobar si nos hemos chocado y perdido o no
    private boolean pierde = false;
    //Velocidad de los vehiculos a los que adelantamos y el nivel de la partida
    private int vehiculoVelocity = -40, lvl = 1;



    //BACKGROUND
    //Screen
    private Camera camara;
    private Viewport ventana;

    //graphics
    private SpriteBatch loteSprites;
    private Texture background;

    //timing
    private float backgroundOffset;

    //parametros world
    private final int WORLD_WIDTH=72;
    private final int WORLD_HEIGHT=128;

    private PlayerEntity jugador;



    public Box2DScreen(MainGame game) {
        super(game);
        stage=new Stage(ventana);
        world = new World(new Vector2(0,0),true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(cameraX,cameraY);
        camera.translate(20,15.5f);

        vehiculos = new ArrayList<Body>();
        vehiculosFixture = new ArrayList<Fixture>();
//        aparicionVehiculos = new Chronometer();
//        aumentoNivel = new Chronometer();
//        aumentoNivel.run(25000);
//        aparicionVehiculos.run(10000);

        //instanciamos la textura del fondo
        camara = new OrthographicCamera();
        ventana=new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT,camara);

        background=new Texture("carreteras.png");
        backgroundOffset=0;
        loteSprites=new SpriteBatch();

        playerTexture=game.getManager().get("car_blue_1.png");

        jugador=new PlayerEntity(world,playerTexture,new Vector2(0,15));
        stage.addActor(jugador);

    }

    @Override
    public void show() {
        super.show();


        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
//                Fixture fisxtureA = contact.getFixtureA();
//                Fixture fisxtureB = contact.getFixtureB();
//                Body bodyA = fisxtureA.getBody();
//                Body bodyB = fisxtureB.getBody();
//                //Comprobamos si el jugador colisiona con algun vehiculo, si colisiona la variable que nos indica si ha perdido se pone a true
//                for(int i = 0; i < vehiculos.size(); i++){
//                    if(fisxtureA == playerFixture && fisxtureB == vehiculosFixture.get(i)){
//
//                        pierde = true;
//
//                    }else if (fisxtureB == playerFixture && fisxtureA == vehiculosFixture.get(i)){
//
//                        pierde = true;
//
//                    }
//                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        //crearPlayer();
        crearLateralSup();
        crearLateralInf();
        //crearLateralIzq();
        //crearLateralDer();

        //crearVehiculos(75,5 + (int)(Math.random() * 25));

    }


    @Override
    public void dispose() {
        super.dispose();
//        player.destroyFixture(playerFixture);
//        playerTexture.dispose();
        laterlarSuperiorBody.destroyFixture(laterlarSuperiorFixture);
        lateralInferiorBody.destroyFixture(lateralInferiorFixture);


        //Destruimos el objeto
//        world.destroyBody(player);
        world.destroyBody(laterlarSuperiorBody);
        world.destroyBody(lateralInferiorBody);


        stage.dispose();
        world.dispose();
        renderer.dispose();

    }
    //Actualizamos 60frames por segundo
    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //BACKGROUND
        loteSprites.begin();
        //scrolling background
        backgroundOffset= (float) (backgroundOffset+0.5);
        if(backgroundOffset%WORLD_WIDTH==0){
            backgroundOffset=0;
        }

        loteSprites.draw(background,-backgroundOffset,0,WORLD_WIDTH,WORLD_HEIGHT);
        loteSprites.draw(background,-backgroundOffset+WORLD_WIDTH,0,WORLD_WIDTH,WORLD_HEIGHT);


        //FIN BACKGROUND

        //Si se esta pulsando la pantalla, comprobamos si la posición del coche es mayor o menor que la posicion -5 del mundo
        //Si es menor, ponemos una velocidad constante para que no se salga del mapa, por el contrario le damos la velocidad estandar de movimiento
//        if (Gdx.input.isTouched()){
//            if(player.getPosition().x < -5){
//                player.setLinearVelocity(2,0);
//            }else if(player.getPosition().x > 45){
//
//                player.setLinearVelocity(0,0);
//
//            }else if(!pierde){
//                //Damos al vehiculo una velocidad de movimiento en función del sector de la pantalla que toque
//                acelerar(Gdx.input.getX(), Gdx.input.getY());
//            }
//            //Realizamos la misma operacion para el frenado, si se suelta el tactil de la pantalla y el vehiculo llega a la posición -5 del mundo,
//            //mantenemos una velocidad constante para que no se salga del mapa
//        }else{
//            if(player.getPosition().x < -5){
//                player.setLinearVelocity(2,0);
//            }else{
//                //Frenamos el vehiculo
//                frenar();
//            }
//        }

        //Actualizamos la velocidad de los vehiculos
//        cocheUpdate();
//        //Actualizamos el cronometro
//        aparicionVehiculos.update();
//        //Si el cronometro esta corriendo, no aparecerá ningun vehículo
//        //En el caso de que no este corriendo, aparecerán X vehículos y se reiniciará para que puedan aparecer nuevos vehículos
//        if(!aparicionVehiculos.isRunning()){
//            //Si el jugador no ha perdido aun, creamos un nuevo vehiculo
//            if(!pierde){
//                if(lvl < 3){
//                    crearVehiculos(75,5 + ((int)(Math.random()*25)));
//                } else {
//                    crearVehiculos(65 + (int)(Math.random()*15), 5 + ((int) (Math.random() * 10)));
//                    crearVehiculos(70 + (int)(Math.random()*20), 25 + ((int) (Math.random() * 30)));
//                }
//            }
//            //Reiniciamos el cronometro
//            aparicionVehiculos.run((int)Math.random()*8000 + 5000);
//        }

        //Actualizamos fisicas
        world.step(1/60f,6,2);
        //Actualizamos la camara
        camera.update();
        //Pintamos el mundo a través de esa camara
        renderer.render(world, camera.combined);

        stage.act();
        stage.draw();
        loteSprites.end();

    }

    @Override
    public void hide() {
        super.hide();
        jugador.detach();
    }

    @Override
    public void resize(int width, int height) {
        ventana.update(width,height,true);
        loteSprites.setProjectionMatrix(camara.combined);
    }

    private void crearVehiculos(int posX, int posY) {
        //creamos el cuerpo del actor
        BodyDef vehiculoBodyDef = createVehicleBodyDef(posX,posY);
        //Creamos el cuerpo del vehiculo
        cocheBody = world.createBody(vehiculoBodyDef);
        //Indicamos que el cuepo del jugador tendra una forma poligonal
        PolygonShape cocheShape = new PolygonShape();
        //Trabaja en metros
        cocheShape.setAsBox(6,3);
        cocheFixture = cocheBody.createFixture(cocheShape,1);
        cocheShape.dispose();
        //Añadimos el Body a un array de Bodies para poder actualizar y borrar cada cuerpo más adelante
        vehiculos.add(cocheBody);
        //Necesitamos guardar la Fixture en un array, para poder eliminarla posteriormente del mundo
        vehiculosFixture.add(cocheFixture);
    }

    private BodyDef createVehicleBodyDef(int posX, int posY) {

        BodyDef bodyDef= new BodyDef();
        //Indicamos la posicion del cuerpo en el mundo
        bodyDef.position.set(posX,posY);
        //Indicamos que el tipo de cuerpo va a ser un cuerpo con dinamicas
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        return bodyDef;
    }

    private void crearLateralSup() {
        //creamos el cuerpo del actor
        BodyDef lateralSupDef = createLateralDef(0,-1);
        //Creamos el cuerpo del lateral superior
        laterlarSuperiorBody = world.createBody(lateralSupDef);
        //Indicamos que el cuepo del lateral tendra una forma poligonal
        PolygonShape lateralSupShape = new PolygonShape();
        //Trabaja en metros
        lateralSupShape.setAsBox(500,2f);
        laterlarSuperiorFixture = laterlarSuperiorBody.createFixture(lateralSupShape,1);
        lateralSupShape.dispose();
    }
    private void crearLateralInf() {
        //creamos el cuerpo del actor
        BodyDef lateralInfDef = createLateralDef(0,32);
        //Creamos el cuerpo del lateral inferior
        lateralInferiorBody = world.createBody(lateralInfDef);
        //Indicamos que el cuepo del lateral tendra una forma poligonal
        PolygonShape lateralInfShape = new PolygonShape();
        //Trabaja en metros
        lateralInfShape.setAsBox(500,2f);
        lateralInferiorFixture = lateralInferiorBody.createFixture(lateralInfShape,1);
        lateralInfShape.dispose();
    }

    private BodyDef createLateralDef(int x, int y) {
        BodyDef bodyDef= new BodyDef();
        //Indicamos la posicion del cuerpo en el mundo
        bodyDef.position.set(x,y);
        //Indicamos que el tipo de cuerpo va a ser un cuerpo con dinamicas
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return bodyDef;
    }

    private void acelerar(int x, int y){
        posicionYActualJugador = player.getLinearVelocity().y;
        //Dividimos la pantalla en 4 sectores para realizar el movimiento del vehiculo del jugador
        //Si se pulsa el sector superior derecha de la pantalla, el vehiculo se moverá en esa dirección a una velocidad constante
        if(y <= Gdx.graphics.getHeight()/2 && x >= Gdx.graphics.getWidth()/2){
            player.setLinearVelocity(velocityPlayerX,velocityPlayerY);
            //Si se pulsa el sector inferior derecha de la pantalla, el vehiculo se moverá en esa dirección a una velocidad constante
        }else if ( y > Gdx.graphics.getHeight()/2 && x >= Gdx.graphics.getWidth()/2){
            player.setLinearVelocity(velocityPlayerX,-velocityPlayerY);
        }
        //Si se pulsa el sector superior izquierda de la pantalla, el vehiculo se moverá en esa dirección a una velocidad constante
        if(y <= Gdx.graphics.getHeight()/2 && x <= Gdx.graphics.getWidth()/2){
            player.setLinearVelocity(-velocityPlayerX,velocityPlayerY);
            //Si se pulsa el sector inferior izquierda de la pantalla, el vehiculo se moverá en esa dirección a una velocidad constante
        }else if ( y > Gdx.graphics.getHeight()/2 && x <= Gdx.graphics.getWidth()/2){
            player.setLinearVelocity(-velocityPlayerX,-velocityPlayerY);
        }

    }

    private void cocheUpdate() {
        //Actualizamos el cronometro de aunmento de nivel
        aumentoNivel.update();
        //Si el cronometro no esta corriendo, subimos el nivel de dificultad
        if(!aumentoNivel.isRunning()){
            //Aumentamos la velocidad de los coches que aparecen por pantallas
            vehiculoVelocity += -10;
            //Reiniciamos el cronometro de aumento de nivel
            aumentoNivel.run(25000);
            //Aumentamos el nivel de la partida
            lvl++;
            //Para que el jugador pueda reaccionar y moverse acorde a la velocidad de los demás coches, aumentamos también su velocidad de movimiento
            velocityPlayerX+=3;
            velocityPlayerY+=3;
        }

        //Comprobamos si hay algun vehiculo creado, si hay algun vehículo, le aplicaremos una fuerza o movimiento en dirección inversa a la del jugador
        //Simulando de esta forma que el jugador va a adelantar a estos vehiculos
        if(vehiculos.size() > 0){
            for(Body vehiculo: vehiculos){
                //vehiculo.applyForceToCenter(vehiculoVelocity,0,true);
                vehiculo.setLinearVelocity(vehiculoVelocity,0);
            }
            //En el caso de no haber ningun vehiculo en el mundo/pantalla y si el jugador no ha perdido aun, crearemos un nuevo vehiculo
        }else{
            if(!pierde){
                crearVehiculos((75),5 + (int)(Math.random() * 25));
            }
        }

        //Para cada vehiculo que haya en pantalla comprobaremos si se ha salido del mundo/pantalla y lo eliminamos del mapa para que no siga consumiendo recursos
        for(int i = 0; i < vehiculos.size(); i++){
            if(vehiculos.get(i).getPosition().x < (-20)){
                vehiculosFixture.remove(i);
                world.destroyBody(vehiculos.get(i));
                vehiculos.remove(i);
            }
        }
    }

    private void frenar() {
        //Para frenar al jugador ejercemos una fuerza inversa a su dirección de movimiento en el caso de que no haya perdido
        //Si ha perdido detenemos el vehiculo
        if(!pierde){
            player.applyForceToCenter(-1000,posicionYActualJugador,true);
        }else{
            player.applyForceToCenter(0,0,true);
        }
    }
}
