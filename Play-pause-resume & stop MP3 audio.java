//main class
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javazoom.jl.decoder.JavaLayerException;
public class Main extends JFrame implements ActionListener{
FileInputStream  fileInputStream;
JButton play=new JButton("Play");
JButton pause=new JButton("pause");
JButton resume=new JButton("resume");
JButton stop=new JButton("stop");
    Main(){  
        JFrame frame=new JFrame();  
        play.setBounds(0,50,70, 50);  
        pause.setBounds(70,50,70, 50);      
        resume.setBounds(140,50,70, 50); 
        stop.setBounds(210,50,70, 50); 
        frame.add(play);  
        frame.add(pause); 
        frame.add(resume); 
        frame.add(stop); 
        frame.setSize(300,200);  
        frame.setLayout(null);  
        frame.setVisible(true);           
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        play.addActionListener(this);
        pause.addActionListener(this);
        resume.addActionListener(this);
        stop.addActionListener(this);
    }  
              
     public static void main(String[] args) {  
            new JButtonExample();  
     }  

    
    public void actionPerformed(ActionEvent e) {
   
        try {
             fileInputStream = new FileInputStream("C:\\Users\\Nakos\\Desktop\\They3.mp3");
             PausablePlayer player = new PausablePlayer(fileInputStream);
            
             Object source = e.getSource();
             if(source==play)
             {System.out.println("play button");
             player.play();}
             if(source==pause)
             {System.out.println("pause button");
             player.pause();    }
             if(source==resume)
             {System.out.println("resume button");
             player.resume();}
              if(source==stop)
             {System.out.println("stop button");
             player.stop();}
        } catch (FileNotFoundException | JavaLayerException ex) {
            Logger.getLogger(JButtonExample.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(JButtonExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
//Player Class
import java.io.FileInputStream;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

public class PausablePlayer {

    private final static int NOTSTARTED = 0;
    private final static int PLAYING = 1;
    private final static int PAUSED = 2;
    private final static int FINISHED = 3;

    // the player actually doing all the work
    private final Player player;

    // locking object used to communicate with player thread
    private final Object playerLock = new Object();

    // status variable what player thread is doing/supposed to do
    private int playerStatus = NOTSTARTED;

    public PausablePlayer(final InputStream inputStream) throws JavaLayerException {
        this.player = new Player(inputStream);
    }

    public PausablePlayer(final InputStream inputStream, final AudioDevice audioDevice) throws JavaLayerException {
        this.player = new Player(inputStream, audioDevice);
    }

    /**
     * Starts playback (resumes if paused)
     */
    public void play() throws JavaLayerException {
        synchronized (playerLock) {
            switch (playerStatus) {
                case NOTSTARTED:
                    final Runnable r = new Runnable() {
                        public void run() {
                            playInternal();
                        }
                    };
                    final Thread t = new Thread(r);
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    playerStatus = PLAYING;
                    t.start();
                    break;
                case PAUSED:
                    resume();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Pauses playback. Returns true if new state is PAUSED.
     */
    public boolean pause() {
        synchronized (playerLock) {
            if (playerStatus == PLAYING) {
                playerStatus = PAUSED;
            }
            return playerStatus == PAUSED;
        }
    }

    /**
     * Resumes playback. Returns true if the new state is PLAYING.
     */
    public boolean resume() {
        synchronized (playerLock) {
            if (playerStatus == PAUSED) {
                playerStatus = PLAYING;
                playerLock.notifyAll();
            }
            return playerStatus == PLAYING;
        }
    }

    /**
     * Stops playback. If not playing, does nothing
     */
    public void stop() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
            playerLock.notifyAll();
        }
    }

    private void playInternal() {
        while (playerStatus != FINISHED) {
            try {
                if (!player.play(1)) {
                    break;
                }
            } catch (final JavaLayerException e) {
                break;
            }
            // check if paused or terminated
            synchronized (playerLock) {
                while (playerStatus == PAUSED) {
                    try {
                        playerLock.wait();
                    } catch (final InterruptedException e) {
                        // terminate player
                        break;
                    }
                }
            }
        }
        close();
    }

    /**
     * Closes the player, regardless of current state.
     */
    public void close() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
        }
        try {
            player.close();
        } catch (final Exception e) {
            // ignore, we are terminating anyway
        }
    }

    // demo how to use
    public static void main(String[] argv) {
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\Nakos\\Desktop\\They3.mp3"); 
            PausablePlayer player = new PausablePlayer(input);

            // start playing
            player.play();
            
            // after 5 secs, pause
            
            player.pause();     

            // after 5 secs, resume
            Thread.sleep(500);
            player.resume();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }}}
<pre>
