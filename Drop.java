import java.awt.Image;

public abstract class Drop extends DestructibleEntity {
    private boolean[] impact = new boolean[]{false, false};
    private boolean[] wouldImpact = new boolean[]{false, false, false, false};
    private int[] velocity = new int[]{0, 0};
    private final int speed;
    private final int gravity;
    private int direction = -1;

    public Drop (Image image, int[] position, int scale) {
        super(image, position, null, 0);
        speed = scale;
        gravity = scale*3/4;
        direction = Math.random() > 0.5 ? -1 : 1;
    }

    public abstract void use();

    public abstract void gameloop();
    
    public void impact(boolean[] direction, int[] position){
        int[] location = getPosition();
        int size = getImage().getWidth(null);
        for (int i = 0; i < position.length;i++) {
            if (direction[i]){
                impact[i] = true;
                location[i] = location[i] > position[i] ? position[i] + size : position[i] - size;
                velocity[i] = 0;
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
        if (direction[0]){
            if (position[0] > location[0]){
                this.direction = -1;
            }
            if (position[0] < location[0]){
                this.direction = 1;
            }
        }
    }

    public void impact (Entity entity, int[] position){
        impact(entity.isIntersecting(position[0], position[1]), entity.getPosition());
    }

    public boolean[] getImpact() {
        return impact;
    }

    public void setImpact(boolean[] impact) {
        this.impact = impact;
    }

    public boolean[] getWouldImpact() {
        return wouldImpact;
    }

    public void setWouldImpact(boolean[] wouldImpact) {
        this.wouldImpact = wouldImpact;
    }

    public int[] getVelocity() {
        return velocity;
    }

    public void setVelocity(int[] velocity) {
        this.velocity = velocity;
    }

    public int getSpeed() {
        return speed;
    }

    public int getGravity() {
        return gravity;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}