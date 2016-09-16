/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrente;

/**
 *
 * @author german
 */
public class ThreadTesting {

    public static void main(String[] args) {
        Runnable threadTareas = new MiEjecucion();
        Thread miHilo= new Thread(threadTareas);
        miHilo.start();
        System.out.println("En el main");
    }

}

class MiEjecucion implements Runnable {

    public void run() {
        ir();
    }

    public void ir() {
        hacerMas();
    }

    public void hacerMas() {
        System.out.println("En la pila");
    }
}
