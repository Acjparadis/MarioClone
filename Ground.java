import java.awt.Image;

public class Ground extends Entity {

    public Ground (Image image, int[] position) {
        super(image, position);
    }
    public Ground(Image image, int positionx, int positiony){
        super(image, new int[]{positionx, positiony});
    }
}