package debrisProcessingSubsystem.debrisCollection;

/**
 * This is the DebrisCollection representation of a debris object.
 * Created by jdt on 3/5/17.
 */
public class DebrisRecord {
    //TODO identification scheme, string maybe?
    private int xLoc, yLoc;
    private int frameX, frameY;
    private double radius;
    private double size;

    public DebrisRecord(){
        xLoc = 0;
        yLoc = 0;
        frameX = 0;
        frameY = 0;
        radius = 0.0;
        size = 0.0;
    }

    /**
     * Full constructor initializes all values.
     * TODO determine if these coordinates are relative to frame or whole image.
     * @param xLoc x coordinate of Debris.
     * @param yLoc y coordinate of Debris.
     * @param frameX x value of frame location.
     * @param frameY y value of frame location.
     * @param radius radius of the object.
     * @param size size of object. TODO determine if this is area
     */
    public DebrisRecord(int xLoc, int yLoc, int frameX, int frameY,
                        double radius, double size){
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.frameX = frameX;
        this.frameY = frameY;
        this.radius = radius;
        this.size = size;
    }

    //TODO getters and setters.

    //TODO comparators for sorting collection?
}
