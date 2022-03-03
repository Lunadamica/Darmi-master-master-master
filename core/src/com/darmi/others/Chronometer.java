package com.darmi.others;

public class Chronometer {
    //Indica el inicio del cronometro y el tiempo actual para saber cuanto tiempo lleva corriendo
    private long delta, lastTime;
    //Tiempo que dura el cronometro
    private long time;
    //Nos indica si el cronometro esta en marcha
    private boolean running;

    public Chronometer() {
        this.delta = 0;
        this.lastTime = System.currentTimeMillis();
        this.time = 0;
        this.running = false;
    }

    //Inicia el cronometro. Necesitamos la variable tiempo para saber la duracion
    public void run(long time){
        running = true;
        this.time = time;

    }

    //Actualiza el cronometro
    public void update(){
        //Comprobamos si el cronometro esta activo. Si esta activo acumulamos el tiempo que ha pasado desde
        //la ultima vez que entramos en este metodo para saber cuando se cumple el tiempo de cronometro
        if(running){
            delta += System.currentTimeMillis() - lastTime;
        }
        //Si se cumple el tiempo de cronometro, lo paramos y reseteamos el valor delta (tiempo) a 0
        if(delta >= time){
            running = false;
            delta = 0;
        }

        //Guardamos la hora actual
        lastTime = System.currentTimeMillis();

    }

    //Para saber si el cronometro esta en marcha
    public boolean isRunning(){
        return running;
    }
}
