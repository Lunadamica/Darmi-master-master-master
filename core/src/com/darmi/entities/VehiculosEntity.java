package com.darmi.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class VehiculosEntity extends Actor {
    private Texture texture;
    private World world;
    private Body vehicle;
    private Fixture vehicleFixture;
    private Vector2 position;
    private int velocity;

    public VehiculosEntity(World world, Texture texture, Vector2 position, int velocity) {
        this.world = world;
        this.texture = texture;
        this.position = position;
        this.velocity = velocity;
        //CREAMOS EL CUERPO DEL JUGADOR
        BodyDef playerBodyDef = createVehicleBodyDef(position);
        vehicle = world.createBody(playerBodyDef);

        //Indicamos que el cuepo del jugador tendra una forma poligonal
        PolygonShape playerShape = new PolygonShape();
        //Trabaja en metros
        playerShape.setAsBox(6,10);
        vehicleFixture = vehicle.createFixture(playerShape,1);
        vehicleFixture.setUserData("vehiculo");
        playerShape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
            //Actualizamos la posición del cuerpo que queremos pintar
            setPosition((vehicle.getPosition().x-0.5f),
                    (vehicle.getPosition().y-0.5f));
            batch.draw(texture, getX(), getY(),15, 20);
            System.out.println(getY() + " " + position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        vehicle.setLinearVelocity(velocity,0);
    }

    public void detach() {
        vehicle.destroyFixture(vehicleFixture);
        world.destroyBody(vehicle);
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
}

