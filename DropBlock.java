import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class DropBlock extends Entity {
    private final Image usedImage;
    private boolean isUsed = false;
    private Drop drop;
    
    public DropBlock(Image image, int[] position, Image usedImage, Drop drop){
        super(image, position);
        this.usedImage = usedImage;
        this.drop = drop;
    }
    public DropBlock(Image image, int positionx, int positiony, Image usedImage, Drop drop){
        super(image, new int[]{positionx, positiony});
        this.usedImage = usedImage;
        this.drop = drop;
    }

    public boolean[] isIntersecting(int u, int v, boolean isPlayer){
        boolean[] returnValue = new boolean[]{false, false, false, false, false, false};
        int[] position = getPosition();
        int s = getImage().getWidth(null);
        if (position[0] - s < u && u < position[0] + s && position[1] - s < v && v < position[1] + s){
            if (Math.abs(u-position[0]) > Math.abs(v-position[1])){
                returnValue[0] = true;
            }
            else if (Math.abs(u-position[0]) < Math.abs(v-position[1])){
                returnValue[1] = true;
                if (v > position[1] && isPlayer){
                    returnValue[3] = true;
                    if (!isUsed){
                        isUsed = true;
                    }
                }
            }
        }
        if (position[0] - s <= u && u <= position[0] + s && position[1] - s <= v && v <= position[1] + s){
            if (Math.abs(u-position[0]) > Math.abs(v-position[1]) && position[1] - s < v && v < position[1] + s){
                if (u < position[0]){
                    returnValue[2] = true;
                }
                else if (u > position[0]){
                    returnValue[4] = true;
                }
            }
            else if (Math.abs(u-position[0]) < Math.abs(v-position[1]) && position[0] - s < u && u < position[0] + s){
                if (v < position[1]){
                    returnValue[5] = true;
                }
            }
        }
        return returnValue;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public Drop getDrop(){
        return drop;
    }

    @Override
    public void Draw(Graphics g, ImageObserver observer) {
        if (!isUsed){
            super.Draw(g, observer);
        }
        else {
            int[] position = getPosition();
            g.drawImage(usedImage, position[0], position[1], observer);
        }
    }
}