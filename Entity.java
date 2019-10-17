import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public abstract class Entity {
    private Image image;
    private int[] position;
    
    public Entity(Image image, int[] position){
        this.image = image;
        this.position = position;
    }

    public void Draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, position[0], position[1], observer);
    }

    public boolean[] isIntersecting (int u, int v){
        boolean[] returnValue = new boolean[]{false, false, false, false, false, false};
        int s = image.getWidth(null);
        if (position[0] - s < u && u < position[0] + s && position[1] - s < v && v < position[1] + s){
            if (Math.abs(u-position[0]) > Math.abs(v-position[1])){
                returnValue[0] = true;
            }
            else if (Math.abs(u-position[0]) < Math.abs(v-position[1])){
                returnValue[1] = true;
            }
        }
        if (position[0] - s <= u && u <= position[0] + s && position[1] - s <= v && v <= position[1] + s){
            if (Math.abs(u-position[0]) > Math.abs(v-position[1]) && position[1] - s < v && v < position[1] + s){
                System.out.println("Horizontal impact from " + this.getClass().getSimpleName());
                if (u < position[0]){
                    returnValue[2] = true;
                }
                else if (u > position[0]){
                    returnValue[4] = true;
                }
            }
            else if (Math.abs(u-position[0]) < Math.abs(v-position[1]) && position[0] - s < u && u < position[0] + s){
                if (v > position[1]){
                    returnValue[3] = true;
                }
                else if (v < position[1]){
                    returnValue[5] = true;
                }
            }
        }
        return returnValue;
    }

    protected Image getImage() {
        return image;
    }

    protected void setImage(Image image) {
        this.image = image;
    }

    protected int[] getPosition() {
        return position;
    }

    protected void setPosition(int[] position) {
        this.position = position;
    }
}