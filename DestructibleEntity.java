import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class DestructibleEntity extends Entity {
    private Image death;
    private boolean isAlive = true;
    private int deathTime = 0;
    private final int deathTimeMax;

    public DestructibleEntity (Image image, int[] position, Image death, int deathTimeMax) {
        super(image, position);
        this.death = death;
        this.deathTimeMax = deathTimeMax;
    }

	public void Die() {
        isAlive = false;
    }

    @Override
    public void Draw(Graphics g, ImageObserver observer) {
        int[] position = getPosition();
        if (isAlive){
            super.Draw(g, observer);
        }
        else {
            g.drawImage(death, position[0], position[1], observer);
            deathTime++;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getDeathTime(){
        return deathTime;
    }

    public int getDeathTimeMax() {
        return deathTimeMax;
    }

    public boolean shouldRemove(){
        if (!isAlive && deathTime >= deathTimeMax){
            return true;
        }
        else {
            return false;
        }
    }
}