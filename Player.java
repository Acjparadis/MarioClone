import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class Player extends DestructibleEntity {
    private int[] velocity = new int[]{0, 0};
    // private int[] impactDistance = new int[]{0, 0};
    private final int gravity;
    private final int jump;
    private final int walkSpeed;
    private final int bounceSpeed;
    private final int airHSpeed;
    private final int airVSpeed;
    private final int fallSpeed;
    private final int maxSpeed;
    private volatile int[] input = new int[]{0, 0};
    private boolean[] impact = new boolean[]{false, false};
    private boolean[] wouldImpact = new boolean[]{false, false, false, false};

    public Player(Image image, int[] position, Image death, int scale){
        super(image, position, death, 30);
        gravity = scale*3/4;
        jump = gravity*40/3;
        walkSpeed = gravity;
        bounceSpeed = jump/2;
        airHSpeed = gravity*3/4;
        airVSpeed = 1;
        fallSpeed = gravity*2;
        maxSpeed = jump*3/4;
    }

    public void StartLevelInput(){
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                switch (ke.getID()) {
                case KeyEvent.KEY_PRESSED:
                    if (ke.getKeyCode() == KeyEvent.VK_W){
                        input[1] = -1;
                    }
                    else if (ke.getKeyCode() == KeyEvent.VK_S){
                        input[1] = 1;
                    }
                    if (ke.getKeyCode() == KeyEvent.VK_A && getPosition()[0] != 0){
                        input[0] = -1;
                    }
                    else if (ke.getKeyCode() == KeyEvent.VK_D) {
                        input[0] = 1;
                    }
                    break;

                case KeyEvent.KEY_RELEASED:
                    if (ke.getKeyCode() == KeyEvent.VK_W){
                        input[1] = 0;
                    }
                    else if (ke.getKeyCode() == KeyEvent.VK_S){
                        input[1] = 0;
                    }
                    if (ke.getKeyCode() == KeyEvent.VK_A && getPosition()[0] != 0){
                        input[0] = 0;
                    }
                    else if (ke.getKeyCode() == KeyEvent.VK_D) {
                        input[0] = 0;
                    }
                    break;
                }
                return false;
            }
        });
    }

    public void gameloop(int scrolled){
        int[] position = getPosition();
        // System.out.println("\nPlayer Prints");
        // System.out.println("Impacts: " + impact[0] + ", " + impact[1]);
        // System.out.println("Would impact directions: " + wouldImpact[0] + ", " + wouldImpact[1] + ", " + wouldImpact[2] + ", " + wouldImpact[3]);
        // System.out.println("Input: " + input[0] + ", "+ input[1]);
        if ((impact[1] || wouldImpact[3]) && input[0] == 0){
            velocity[0] = 0;
        }
        if (!impact[1] && !wouldImpact[3]){
            velocity[1] += gravity; //because java canvases are upside down
            if (input[1] == 1){
                velocity[1] += fallSpeed;
            }
            else if (input[1] == -1){
                velocity[1] -= airVSpeed;
            }
        }
        if (input[1] == -1 && (impact[1] || wouldImpact[3]) && !wouldImpact[1]){
            velocity[1] = -jump;
        }
        if (!impact[0]){
            if (impact[1]){
                velocity[0] += (input[0] * walkSpeed);
            }
            else {
                velocity[0] += (input[0] * airHSpeed);
            }
        }
        if (position[0] < (Math.abs(velocity[0])+scrolled) && velocity[0] < 0){
           velocity[0] = 0;
           position[0] = scrolled;
        }
        for (int i = 0; i < position.length; i++){
            if (velocity[i] != velocity[1] || velocity[1] > 0){
                velocity[i] = velocity[i] < -maxSpeed ? -maxSpeed : velocity[i];
            }
            velocity[i] = velocity[i] > maxSpeed ? maxSpeed : velocity[i];
            // if (impactDistance[i] != 0){
            //     velocity[i] = Math.abs(velocity[i]) > Math.abs(impactDistance[i]) ? impactDistance[i] : velocity[i];
            // }

            // boolean isGoingToImpact = false;
            // if (velocity[i]!=0)
            //     isGoingToImpact = wouldImpact[i == 0 ? 1-velocity[i]/velocity[i] : 2-velocity[i]/velocity[i]];
            // System.out.println("Will impact " + i + ": " + isGoingToImpact);
            if (velocity[i] != 0 && (velocity[i] == velocity[1] || !wouldImpact[1-Math.abs(velocity[i])/velocity[i]])){
                position[i] += velocity[i];
            }
            else {
                velocity[i] = 0;
            }
            impact[i] = false;
            // impactDistance[i] = 0;
        }
        //reset wouldimpacts, because that works now
        for (int x = 0; x < wouldImpact.length; x++) {
            wouldImpact[x] = false;
        }
        // System.out.println("Velocity: " + velocity[0] + ", " + velocity[1]);
    }

    public void impact(boolean[] direction, int[] position){
        int[] location = getPosition();
        int size = getImage().getWidth(null);
        for (int i = 0; i < position.length;i++) {
            if (direction[i]){
                impact[i] = true;
                velocity[i] = 0;
                location[i] = location[i] > position[i] ? position[i] + size : position[i] - size;
                for (int x = 0; x < wouldImpact.length; x++) {
                    wouldImpact[x] = false;
                }
            }
        }
        for (int i = 0; i < wouldImpact.length; i++) {
            if (direction[i+2]){
                wouldImpact[i] = direction[i+2];
            }
        }
    }

    public int[] getVelocity() {
        return velocity;
    }

    @Override
    public void Die() {
        super.Die();
    }

    public void Bounce(){
        velocity[1] = -bounceSpeed;
    }
}