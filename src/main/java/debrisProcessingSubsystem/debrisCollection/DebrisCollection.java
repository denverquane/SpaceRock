package debrisProcessingSubsystem.debrisCollection;

import debrisProcessingSubsystem.Updatable;
import debrisProcessingSubsystem.Update;

/**
 * Created by jdt on 3/13/17.
 */
public class DebrisCollection implements Updatable {

    private DebrisList newDebris, oldDebris;


    /**
     * Default constructor. Initializes debris lists to null.
     */
    public DebrisCollection(){
        newDebris = null;
        oldDebris = null;
    }

    /**
     * Call this to send an update to this component.
     * Returns a response Update
     * TODO write this.
     * @param theUpdate The update to be received by this component.
     * @return A response update.
     */
    public Update updateComponent(Update theUpdate){
        return null;
    }

    /**
     * Return any available updates.
     * TODO write this.
     * @return An update is ready.
     */
    public Update pollComponent(){
        return null;
    }

    /**
     * Start a new image.
     * @return success or failure
     */
    private boolean newImage(){
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
    private void addDebris(){
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
     * Testing method
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

    }
}
