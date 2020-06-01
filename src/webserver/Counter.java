package webserver;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class Counter extends Thread {
    
    private String name;
    
    public Counter(String name) {
        this.name = name;
    }
    
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                System.out.println(name + ":" + i);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            } 
        }
    }
}
