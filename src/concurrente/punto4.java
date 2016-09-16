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
public class punto4 extends Thread {

    public void run() {
        Recurso.uso();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
}

class Recurso {
//garantiza la exclucion mutua , ejecucion por un unico hilo
    static synchronized void uso() {
        Thread t = Thread.currentThread();
        System.out.println("Soy " + t.getName());
    }

    public static void main(String[] args) {
        punto4 juan = new punto4();
        juan.setName("juan Lopez");
        punto4 ines = new punto4();
        ines.setName("Ines Garcia");
        juan.start();
        ines.start();
    }
}
