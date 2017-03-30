package debrisProcessingSubsystem.debrisCollection;

import java.awt.image.BufferedImage;

/**
 * This is the DebrisCollection representation of a debris object. There should
 * be one of these for each object detected.
 * Created by jdt on 3/5/17.
 */
public class DebrisRecord {
    private String id;
    private static int idCounter = 0;
    private final int VECTOR2_SIZE = 2;
    private double[] estimatedVelocityXY;
    private DebrisRecord matchingRecord;
    private boolean ambiguousMatch;
    private BufferedImage frameImage;
    private DebrisData data;
    private int frameX, frameY;

    public DebrisRecord(){
        data = new DebrisData();
        frameX = 0;
        frameY = 0;
    }

    //Begin setters and getters------------------------------------------------
    /**
     * Full constructor initializes all values.
     * TODO determine if these coordinates are relative to frame or whole image.
     * @param xLoc X coordinate of Debris.
     * @param yLoc Y coordinate of Debris.
     * @param frameX X value of frame location.
     * @param frameY Y value of frame location.
     * @param radius Radius of the object.
     * @param size Size of object. TODO determine if this is area
     */
    public DebrisRecord(int xLoc, int yLoc, int frameX, int frameY,
                        double radius, double size){
        data = new DebrisData(xLoc, yLoc, radius, size);
        this.frameX = frameX;
        this.frameY = frameY;
    }

    /**
     * Initialize all debris members at once.
     * @param xLoc X coordinate of Debris.
     * @param yLoc Y coordinate of Debris.
     * @param frameX X value of frame location.
     * @param frameY Y value of frame location.
     * @param radius Radius of the object.
     * @param size Size of the object.
     */
    public void initializeDebris(int xLoc, int yLoc, int frameX, int frameY,
                                 double radius, double size){
        data.xLoc = xLoc;
        data.yLoc = yLoc;
        this.frameX = frameX;
        this.frameY = frameY;
        data.radius = radius;
        data.size = size;
    }

    /**
     * Get the ID for the DebrisRecord.
     * @return The id member of this object.
     */
    public String getId(){
        return id;
    }

    /**
     * Set the ID for this DebrisRecord.
     * @param id The id for this DebrisRecord.
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * Set the ID for this DebrisRecord, marking "X" at the end.
     * This tags the Record that it is a possible match.
     * @param id The id for this DebrisRecord.
     */
    public void setPossibleId(String id){
        if(id.contains("X"))
        {
            setId(id);
        }
        else
        {
            setId(id + "X");
        }
    }

    /**
     * Generates a new label number by incrementing the counter
     * @return the String representation of the label counter
     */
    public static String getNextID()
    {
        return Integer.toString(idCounter++);
    }

    /**
     * Get the x coordinate of the debris.
     * @return xLoc The x coordinate of the debris' location.
     */
    public int getX(){
        return data.xLoc;
    }

    /**
     * Set the xLocation of this debris.
     * @param xLoc The x location of this debris.
     */
    public void setX(int xLoc){
        data.xLoc = xLoc;
    }

    /**
     * Set the y location of this debris.
     * @return yLoc The y location of this debris.
     */
    public int getY(){
        return data.yLoc;
    }

    /**
     * Set the y location of this debris.
     * @param yLoc the y location of this debris.
     */
    public void setY(int yLoc){
        data.yLoc = yLoc;
    }

    /**
     * Get the debris'  frame's x value.
     * @return frameX, the x value of the frame that contains the debris.
     */
    public int getFrameX(){
        return frameX;
    }

    /**
     * Set the x value of the frame that contains this debris.
     * @param frameX The x value of the frame that contains this debris.
     */
    public void setFrameX(int frameX){
        this.frameX = frameX;
    }

    /**
     * Get the y value of the frame containing this debris.
     * @return frameY, The y value of the frame that contains this debris.
     */
    public int getFrameY(){
        return frameY;
    }

    /**
     * Set the y value of the frame containing this debris.
     * @param frameY The y value of the frame containing this debris.
     */
    public void setFrameY(int frameY){
        this.frameY = frameY;
    }

    /**
     * Get the radius of this debris.
     * @return radius The radius of this debris object.
     */
    public double getRadius(){
        return data.radius;
    }

    /**
     * Set the radius of this object
     * @param r The radius of this object.
     */
    public void setRadius(double r){
        data.radius = r;
    }

    /**
     * Get the size of this object.
     * @return size The size of this object.
     */
    public double getSize(){
        return data.size;
    }

    /**
     * Set the size of this object.
     * @param size size The size of this object.
     */
    public void setSize(double size){
        data.size = size;
    }

    /**
     * Set the image associated with the debris.
     * @param  frameImage the image to set.
     */
    public void setImage(BufferedImage frameImage){
        this.frameImage = frameImage;
    }

    /**
     * Get the image associated with the debris.
     * @return frameImage.
     */
    public BufferedImage getImage(){
        return frameImage;
    }

    /**
     * Create a string with some identifying information about the debris.
     * @return String with id, radius, and location.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Debris: ");
        stringBuilder.append(id);
        stringBuilder.append(" radius: ");
        stringBuilder.append(data.radius);
        stringBuilder.append(" at : ");
        stringBuilder.append(data.xLoc);
        stringBuilder.append(", ");
        stringBuilder.append(data.yLoc);
        return stringBuilder.toString();
    }
    //end setters and getters---------------------------------------------------

    /**
     * Compare the radius of this DebrisRecord to another DebrisRecord and
     * return the difference. this.radius - other.radius
     * @param other The DebrisRecord we are comparing to.
     * @return  double The difference in radius between this Debris Record and
     * the other DebrisRecord passed as a parameter.
     */
    public double radiusDifference(DebrisRecord other){
        return data.radius - other.getRadius();
    }

    /**
     * Compute the percent difference between  this DebrisRecord and another DebrisRecord.
     * @param other The DebrisRecord we are comparing to.
     * @return  double percent difference in radius between this Debris Record and
     * the other DebrisRecord passed as a parameter.
     */
    public double radiusRatio(DebrisRecord other){
        double average = (data.radius + other.getRadius()) / 2;
        double diff = Math.abs(radiusDifference(other));
        return diff / average;
    }


    /**
     * Return distance from to object.
     * @param other The object to get distance from
     * @return Real (double) distance from other debris.
     */
    public double distanceTo(DebrisRecord other){
        double distanceSquared = (double)sqDistanceTo(other);
        return Math.sqrt(distanceSquared);
    }

    /**
     * Return the squared distance between this DebrisRecord and another
     * DebrisRecord.
     * @param other The DebrisRecord to measure the distance to.
     * @return The squared distance between the two DebrisRecords. dX^2 + dY^2.
     */
    public int sqDistanceTo(DebrisRecord other){
        int xDistance = other.getX() - data.xLoc;
        int yDistance = other.getY() - data.yLoc;
        return xDistance * xDistance + yDistance * yDistance;
    }

    private class DebrisData{
        public int xLoc, yLoc;
        public double radius;
        public double size;

        public DebrisData(){
            xLoc = 0;
            yLoc = 0;
            radius = 0.0;
            size = 0.0;
        }

        public DebrisData(int xLoc, int yLoc, double radius, double size){
            this.xLoc = xLoc;
            this.yLoc = yLoc;
            this.radius = radius;
            this.size = size;
        }
    }
}
