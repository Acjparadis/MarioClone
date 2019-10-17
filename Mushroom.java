import java.awt.Image;

public class Mushroom extends Drop {
    private final int maxSpeed;

    public Mushroom (Image image, int[] position, int scale) {
        super(image, position, scale);
        maxSpeed = scale*3/4*10;
    }

    public Mushroom (Image image, int positionx, int positiony, int scale) {
        super(image, new int[] {positionx, positiony}, scale);
        maxSpeed = scale*3/4*10;
    }

    @Override
    public void use(){

    }

    @Override
    public void gameloop(){
        if (isAlive()){
            // System.out.println("Enemy would impact directions: " + getWouldImpact()[0] + ", " + getWouldImpact()[1] + ", " + getWouldImpact()[2] + ", " + getWouldImpact()[3]);
            int[] position = getPosition();
            boolean[] impact = getImpact();
            boolean[] wouldImpact = getWouldImpact();
            int[] velocity = getVelocity();
            int speed = getSpeed();
            int gravity = getGravity();
            int direction = getDirection();
            if (!impact[1] && !wouldImpact[3]){
                velocity[1] += gravity; //because java canvases are upside down
            }
            position[0] += speed * direction;
            for (int i = 0; i < position.length; i++) {
                velocity[i] = velocity[i] < -maxSpeed ? -maxSpeed : velocity[i];
                velocity[i] = velocity[i] > maxSpeed ? maxSpeed : velocity[i];
                position[i] += velocity[i];
            }
        }
        setImpact(new boolean[]{false, false});
        setWouldImpact(new boolean[]{false, false, false, false});
        throw new Error("position: " + getPosition()[0] + ", " + getPosition()[1]);
    }
}