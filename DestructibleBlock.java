import java.awt.Image;

public class DestructibleBlock extends DestructibleEntity {
    boolean playerHit = false;
    
    public DestructibleBlock(Image image, int[] position, Image death){
        super(image, position, death, 10);
    }
    public DestructibleBlock(Image image, int positionx, int positiony, Image death){
        super(image, new int[]{positionx, positiony}, death, 10);
    }

    public boolean[] isIntersecting(int u, int v, boolean isPlayer){
        boolean[] returnValue = new boolean[]{false, false, false, false, false, false};
        if (!isAlive()){
            return returnValue;
        }
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
                    Die();
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
}