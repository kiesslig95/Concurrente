/*
 * Ejercicio TP3.
 */
package concurrente;

import java.util.concurrent.Semaphore;

/**
 *
 * @author german
 */
public class semaforo {

    public static void main(String[] args) {
        Semaphore sem1 = new Semaphore(1);
        Semaphore sem2 = new Semaphore(0);
        Semaphore sem3 = new Semaphore(0);

        P1 ej1 = new P1(sem1, sem3);
        P2 ej2 = new P2(sem1, sem2);
        P3 ej3 = new P3(sem2, sem3);

        ej1.start();
        ej2.start();
        ej3.start();
    }

    public void run() {
        System.out.println(" soy el semaforo" + Thread.currentThread());
    }
}

class P1 extends Thread {

    private Semaphore sem1;
    private Semaphore sem3;

    public P1(Semaphore sem1, Semaphore sem3) {
        this.sem1 = sem1;
        this.sem3 = sem3;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sem1.acquire();
            } catch (InterruptedException ex) {
                System.out.println("ocurrior una excepcion");
            }
            System.out.println("soy sem 1");
            sem3.release();
        }
    }

}

class P2 extends Thread {

    private Semaphore sem2;
    private Semaphore sem1;

    public P2(Semaphore sem1, Semaphore sem2) {
        this.sem1 = sem1;
        this.sem2 = sem2;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sem2.acquire();
                System.out.println("soy sem 2");
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                System.out.println("ocurrior una excepcion");
            }
            sem1.release();
        }
    }

}

class P3 extends Thread {

    private Semaphore sem2;
    private Semaphore sem3;

    public P3(Semaphore sem2, Semaphore sem3) {
        this.sem2 = sem2;
        this.sem3 = sem3;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sem3.acquire();
            } catch (InterruptedException ex) {
                System.out.println("ocurrior una excepcion");
            }
            System.out.println("soy sem 3");
            sem2.release();
        }
    }

}
