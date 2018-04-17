/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlegame;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author filippopiggici
 */
public class Time {

    Timer timer;
    AnchorPane endgame;
    Label upordown;
    
    public Time(AnchorPane endgame){
        this.endgame=endgame;
    }
    
    public Time(Label upordown) {
        this.upordown=upordown;
        
    }

    public void Timer(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(endgame), seconds * 1000);
    }
    
    public void Timer2(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask2(upordown), seconds * 1000);
    }
    
    public void cancel() {      
        timer.cancel();
    }

    class RemindTask extends TimerTask {

        public RemindTask(AnchorPane endgame){
            super();
           
        }
        public void run() {
            System.out.format("Time's up!%n");
            timer.cancel(); //Terminate the timer thread}}
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Time.class.getName()).log(Level.SEVERE, null, ex);
            }
             endgame.setVisible(true);
        }
    }
    
    class RemindTask2 extends TimerTask {

        public RemindTask2(Label upordown){
            super();
           
        }
        public void run() {
            System.out.format("Time's up!%n");
            timer.cancel(); //Terminate the timer thread}}
            upordown.setVisible(false);
        }
    }
}
