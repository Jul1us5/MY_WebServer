package webserver;

/**
 *
 * @author Julius
 */
public class Multi {

    public static void main(String[] args) {

        class Cr {

            volatile int a = 0;
            volatile int b = 0;

            public synchronized void increment() {
                a++;
                b++;
                if (a != b) {
                    throw new IllegalStateException();
                }
            }

        }
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                Cr cr = new Cr();
                Cr cr2 = new Cr();
                while (cr.a < 1000) {
                    cr.increment();
                    cr2.increment();
                }
                System.out.println("t1 Done");

            }
        });
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                Cr cr = new Cr();
                Cr cr2 = new Cr();
                while (cr.a < 1000) {
                    cr.increment();
                    cr2.increment();
                }
                System.out.println("t2 Done");

            }
        });
        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                Cr cr = new Cr();
                Cr cr2 = new Cr();
                while (cr.a < 1000) {
                    cr.increment();
                    cr2.increment();
                }
                System.out.println("t3 Done");

            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
