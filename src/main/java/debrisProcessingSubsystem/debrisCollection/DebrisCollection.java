package debrisProcessingSubsystem.debrisCollection;

/**
 * This will be the interface implemented by the Debris Collection object shown
 * in the SADD.
 * This is a preliminary placeholder and very subject to change.
 * Created by jdt on 3/4/17.
 */
public interface DebrisCollection {
    /**
     * This method will be called when it is time to add a new image to the
     * debris list.
     * @return True on success.
     */
    public boolean newImage();

    /**
     * This method is to be called when. SADD team appears to envision this
     * implemented with an iterator.
     * possibly within the debris list itself?
     */
    public DebrisRecord getDebris();

    //methods swapList and clearList should be internal per prof roman.
}
