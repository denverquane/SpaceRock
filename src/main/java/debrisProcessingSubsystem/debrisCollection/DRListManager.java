package debrisProcessingSubsystem.debrisCollection;

/**
 * This class is an implementation of the DebrisCollection interface. Manages
 * the debris collection. The functionality beyond the outward facing methods
 * go here.
 * Created by jdt on 3/7/17.
 */
public class DRListManager implements DebrisCollection {

    private DebrisList newDebris, oldDebris;

    /**
     * Default constructor. Initializes debris lists to null.
     */
    public DRListManager(){
        newDebris = null;
        oldDebris = null;
    }
    /**
     * Start a new image.
     * @return success or failure
     */
    public boolean newImage(){
        //swap lists
        if(newDebris == null){
            newDebris = new DRList();
        }
        else {
            //clear new lists
            swapLists();
            //ok to add images?
            //track debris
        }
        return true;
    }

    /**
     * Add a debris record to the list.
     * TODO needs parameters.
     */
    public void addDebris(){
        try {
            newDebris.addDebris(new DebrisRecord());
        }
        catch(NullPointerException e){
            System.err.println("New Debris list not initialized: " + e.getMessage());
        }
    }

    /**
     * Get a DebrisRecord from the old list for sending.
     * @return Next DebrisRecord in the old debris list.
     */
    public DebrisRecord getDebris(){
        return oldDebris.getDebrisElement();
    }

    /**
     * Print some info about this collection.
     * Testing method.
     */
    public void printCollectionCharacteristics(){
        if(newDebris != null){
            System.out.println("newDebris: " + newDebris.size());
        }
        if(oldDebris != null){
            System.out.println("oldDebris: " + oldDebris.size());
        }
    }

    /**
     * Make newDebris the old list and create a new list for the next image.
     */
    private void swapLists(){
        oldDebris = newDebris;
        newDebris = new DRList();
    }

    /**
     * Testing main for DebrisCollection.
     * @param args ignored.
     */
    public static void main(String[] args){
        DebrisCollection debris = new DRListManager();
        debris.newImage();
        for(int i = 0; i < 4; ++i){
            debris.addDebris();
        }
        debris.newImage();
        for(int i = 0; i < 3; ++i){
            debris.addDebris();
        }
        debris.printCollectionCharacteristics();

    }
}
