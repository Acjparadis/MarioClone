import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Mario {
    JFrame frame = new JFrame("Mario");
    Level level;
    final int fps = 30;
    final int timePerFrame = 1000/fps;
    public static void main(String[] args) {
        new Mario();
    }

    public Mario(){
        frame.setSize(640, 480);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        frame.getContentPane().setCursor(blankCursor);
        frame.setVisible(true);
        System.out.println("Target Frame time: " + timePerFrame);
        level = new Level(frame);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() { 
            @Override
            public void run() {
                level.gameloop();
            }
        }, 0, timePerFrame);
    }
}