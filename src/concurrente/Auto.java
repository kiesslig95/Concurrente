/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrente;

import java.util.Random;

/**
 *
 * @author german
 */
public class Auto {

    private final String patente;
    private final int modelo;
    private final String marca;
    private int kmFaltantesParaElService;
    private int nafta = 50;

    public Auto(String patente, int modelo, String marca) {
        this.patente = patente;
        this.modelo = modelo;
        this.marca = marca;
    }

    public Auto() {
        this("-"+(new Random()).nextInt(1000), 2016, "Fiat");
    }

    public void recorrerKm(int cantidad) {
        this.kmFaltantesParaElService -= cantidad;
        this.nafta -= cantidad * 3;
    }

    public int getGasolina() {
        return this.nafta;
    }

    public void setGasolina(int cantidad) {
        this.nafta = cantidad;
    }
}
class Main {

    public static void main(String[] args) {
        
        Thread[] vehiculo = new Thread[5];
        
        vehiculo[0] = new Thread(new Recorrido(), "Auto1");
        vehiculo[1] = new Thread(new Recorrido(), "Auto2");
        vehiculo[2] = new Thread(new Recorrido(), "Auto3");
        vehiculo[3] = new Thread(new Recorrido(), "Auto4");
        vehiculo[4] = new Thread(new Recorrido(), "Auto5");
        
        for (Thread auto: vehiculo) {
            auto.start();
        }
    }
}
class Surtidor {

    private int capacidad; // en litros

    public Surtidor() {
        this(250);
    }

    public Surtidor(int cantidad) {
        this.capacidad = cantidad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void surtir(int cantidad) {
        this.capacidad -= cantidad;
    }
}
class Recorrido implements Runnable {

    private static Surtidor s = new Surtidor();
    
    private Auto unAuto = new Auto();
    private final int DISTANCIA_TOTAL = 40; // en km

    @Override
    public void run() {
        int distanciaRecorrida = 0;
        int distancia = (new Random()).nextInt(6) + 1;
        while (distanciaRecorrida <= DISTANCIA_TOTAL) {
            if (unAuto.getGasolina() > 5) {
                System.out.println(Thread.currentThread().getName() + " comienza a moverse y recorre: " + distancia + " km. Gasolina restante: " + unAuto.getGasolina());
                unAuto.recorrerKm(distancia);
                distanciaRecorrida += distancia;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.err.println("Ocurrió una interrupción");
                }
            } else {
                try {
                    this.cargarNafta(50);
                    distancia += (new Random()).nextInt(4) + 1;
                } catch (InterruptedException ex) {
                    System.err.println("Ocurrió una interrupción");
                } catch (SurtidorVacio ex) {
                    System.err.println(ex.getMessage());
                    break;
                }
            }
        }
        System.out.println("El auto " + Thread.currentThread().getName() + " recorrió " + distanciaRecorrida + " km. " + 
            ((distanciaRecorrida >= DISTANCIA_TOTAL) ? "Llegó a destino. " : "No llegó a destino"));
    }

    private void cargarNafta(int cantidad) throws InterruptedException, SurtidorVacio {
        if (s.getCapacidad() > cantidad) {
            System.out.println(Thread.currentThread().getName() + " está realizando una carga de: " + cantidad + " l.");
            Thread.sleep(1000);
            s.surtir(cantidad);
            unAuto.setGasolina(cantidad);

            System.out.println(Thread.currentThread().getName() + ": Carga realizada.");
            System.out.println(Thread.currentThread().getName() + ": Al surtidor le quedan: " + s.getCapacidad() + " l.");
        } else {
            System.err.println("No hay suficiente capacidad en el surtidor.");
            throw new SurtidorVacio();
        }
    }
}

class SurtidorVacio extends Exception {

    public SurtidorVacio() {
        super("El surtidor se quedó sin combustible.");
    }
}
