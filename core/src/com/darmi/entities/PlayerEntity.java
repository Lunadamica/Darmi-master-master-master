package com.darmi.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.darmi.constants.Constants;

public class PlayerEntity extends Actor {
    private Texture texture;
    private World world;
    private Body player;
    private Fixture playerFixture;
    private Vector2 position;

    //Variable para comprobar si nos hemos chocado y perdido o no
    private boolean pierde = false;
    //Posicion actual del jugador en la coordenada Y y velocidades de movimiento del jugador sobre el eje Y y X
    private float posicionYActualJugador, velocityPlayerX = 20, velocityPlayerY = 20;

    public PlayerEntity(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;
        this.position = position;
        //CREAMOS EL CUERPO DEL JUGADOR
        BodyDef playerBodyDef = createVehicleBodyDef(position);
        player = world.createBody(playerBodyDef);

        //Indicamos que el cuepo del jugador tendra una forma poligonal
        PolygonShape playerShape = new PolygonShape();
        //Trabaja en metros
        playerShape.setAsBox(7,10);
        playerFixture = player.createFixture(playerShape,1);
        playerFixture.setUserData("jugador");
        playerShape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Actualizamos la posición del cuerpo que queremos pintar
        setPosition((player.getPosition().x-0.5f),
                (player.getPosition().y-0.5f));
        //Hemos metido el tamaño de la imagen en el siguiente draw
        batch.draw(texture, getX(), getY(),15, 20);
    }

    @Override
    public void act(float delta) {
        //Si se esta pulsando la pantalla, comprobamos si la posición del coche es mayor o menor que la posicion -5 del mundo
        //Si es menor, ponemos una velocidad constante para que no se salga del mapa, por el contrario le damos la velocidad estandar de movimiento
        if (Gdx.input.isTouched()){
            if(player.getPosition().x == 0){
                player.setLinearVelocity(2,0);
            }else if(player.getPosition().x < 0){
                player.setLinearVelocity(30,0);
            }else if(player.getPosition().x > 50){
                player.setLinearVelocity(-10,0);
            }else if(!pierde){
                //Damos al vehiculo una velocidad de movimiento en función del sector de la pantalla que toque
                acelerar(Gdx.input.getX(), Gdx.input.getY());
            }
            //Realizamos la misma operacion para el frenado, si se suelta el tactil de la pantalla y el vehiculo llega a la posición -5 del mundo,
            //mantenemos una velocidad constante para que no se salga del mapa
        }else{
            if(player.getPosition().x < 0){
                player.setLinearVelocity(2,0);
            }else if(player.getPosition().x > 50){
                player.setLinearVelocity(-10,0);
            }else{
                //Frenamos el vehiculo
                frenar();
            }
        }
    }

    public void detach() {
        player.destroyFixture(playerFixture);
        world.destroyBody(player);
    }

    private BodyDef createVehicleBodyDef(Vector2 position) {

        BodyDef bodyDef= new BodyDef();
        //Indicamos la posicion del cuerpo en el mundo
        bodyDef.position.set(position);
        //Indicamos que el tipo de cuerpo va a ser un cuerpo con dinamicas
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        return bodyDef;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
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
