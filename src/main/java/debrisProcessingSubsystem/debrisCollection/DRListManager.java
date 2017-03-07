package debrisProcessingSubsystem.debrisCollection;

/**
 * Created by jdt on 3/7/17.
 */
public class DRListManager implements DebrisCollection {

    private DebrisList newDebris, oldDebris;

    public DRListManager(){
        newDebris = new DRList();
        oldDebris = null;
    }
    /**
     * Start a new image.
     * @return success or failure
     */
    public boolean newImage(){
        //swap lists
        //clear new lists
        //ok to add images
        //track debris
        return true;
    }

    /**
     * Get a DebrisRecord from the old list for sending.
     * @return Next DebrisRecord in the old debris list.
     */
    public DebrisRecord getDebris(){
        return oldDebris.getDebrisElement();
    }

    /**
     * Make newDebris the old list and create a new list for the next image.
     */
    private void swapLists(){
        oldDebris = newDebris;
        newDebris = new DRList();
    }
}
