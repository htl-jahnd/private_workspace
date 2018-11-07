package JMF;
import java.io.File;
import java.net.URL;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;

public class Mp3PlayerDemo extends Thread {
   
   private String filename;
   Player player;
   
   public Mp3PlayerDemo(String mp3Filename) {
      this.filename = mp3Filename;
   }
   
   public void run() {
      try {
    	  File f = new File (filename);
    	  URL mediaURL= f.toURI().toURL();
         MediaLocator locator = new MediaLocator(mediaURL);
         player = Manager.createPlayer(locator);
         player.addControllerListener(new ControllerListener() {
            public void controllerUpdate(ControllerEvent event) {
               if (event instanceof EndOfMediaEvent) {
                  player.stop();
                  player.close();
               }
            }
         });
         player.realize();
         player.start();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public static void main(String[] args) {
      new Mp3PlayerDemo("./ressources/audio/alarm_clock.wav").start();
   }
}