import java.awt.Image;

public class Goomba extends Enemy {
    private final int maxSpeed;

    public Goomba(Image image, int[] position, Image death, int scale){
        super(image, position, death, scale, 30);
        maxSpeed = scale*3/4*10;
    }
    public Goomba(Image image, int positionx, int positiony, Image death, int scale){
        super(image, new int[]{positionx, positiony}, death, scale, 30);
        maxSpeed = scale*3/4*10;
    }

    @Override
    public void gameloop(int[] playerPosition) {
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
    }
}