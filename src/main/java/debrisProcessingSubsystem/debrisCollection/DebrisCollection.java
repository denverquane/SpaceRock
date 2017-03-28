package debrisProcessingSubsystem.debrisCollection;

import debrisProcessingSubsystem.schedulerTester.TestableComponent;
import debrisProcessingSubsystem.updateSystem.DebrisCollectorUpdate;
import debrisProcessingSubsystem.updateSystem.Updatable;
import debrisProcessingSubsystem.updateSystem.Update;
import debrisProcessingSubsystem.updateSystem.*;
import fpga.objectdetection.Debris;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * DebrisCollection manages the debris list on the CPU side of the satellite.
 * It receives updates from the scheduler and performs the appropriate actions
 * and responses.
 * It manages two lists, returns debris for sending to earth, and attempts to
 * track debris over time.
 * Created by jdt on 3/13/17.
 */
public class DebrisCollection implements Updatable, TestableComponent
{

    private DebrisList newDebris, oldDebris;
    private LinkedList<Update> outgoingUpdates;
    private boolean DEBUG = true;

    /**
     * Default constructor. Initializes debris lists to null.
     */
    public DebrisCollection(){
        newDebris = null;
        oldDebris = null;
        outgoingUpdates = new LinkedList<>();
    }

    /**
     * Call this to send an update to this component.
     * Returns a response Update.
     * TODO write this.
     * @param theUpdate The update to be received by this component.
     * @return A response update.
     */
    public Update updateComponent(Update theUpdate){
        //Determine what kind of message we have.
      DebrisCollectorUpdate updateIn = null;
      if(theUpdate instanceof DebrisCollectorUpdate){
        updateIn = (DebrisCollectorUpdate)theUpdate;
        HashMap updateMap = updateIn.getParamMap();

        /* Send debris home update */
        if(updateMap.containsKey(DebrisCollectorUpdate.DebrisCollectorParameters.SEND_DEBRIS_HOME)){
          if (DEBUG) System.out.println("Received SEND_DEBRIS_HOME update with value " 
              + updateMap.get(DebrisCollectorUpdate.DebrisCollectorParameters.SEND_DEBRIS_HOME));
          //get debris to send home
          DebrisRecord returnRecord = getDebris();
          if(returnRecord != null) {
            OperatorUpdate updateOut = new OperatorUpdate(UpdateType.OPERATOR);
            HashMap paramMap = updateOut.getParamMap();
            paramMap.put(OperatorUpdate.OperatorUpdateParameters.DEBRIS, returnRecord);
            return updateOut;
          }
          else{
            OperatorUpdate updateOut = new OperatorUpdate(UpdateType.ALL_DEBRIS_RETURNED);
            return updateOut;
          }
        }

        /* Add debris to list update */
        if(updateMap.containsKey(DebrisCollectorUpdate.DebrisCollectorParameters.ADD_DEBRIS)){
          Boolean debrisIn = (Boolean)updateMap.get(DebrisCollectorUpdate.DebrisCollectorParameters.ADD_DEBRIS);
          if (DEBUG) System.out.println("Received ADD_DEBRIS update with value " + debrisIn);
          //DebrisRecord newRecord = DebrisRecord(debrisIn.centerXLocation);
          //TODO convert Debris to debris object.
          //addDebris(updateIn.debrisObject);
        }

        /* Raw Image Request */
        if(updateMap.containsKey(DebrisCollectorUpdate.DebrisCollectorParameters.RAW_IMAGE_REQUEST)){
          if (DEBUG) System.out.println("Received RAW_IMAGE_REQUEST update with value " + updateMap.get(DebrisCollectorUpdate.DebrisCollectorParameters.RAW_IMAGE_REQUEST));
          //get raw image for updateIn.imageName
        }

        /* All debris sent */
        if(updateMap.containsKey(DebrisCollectorUpdate.DebrisCollectorParameters.ALL_DEBRIS_SENT)){
          if (DEBUG) System.out.println("Received ALL_DEBRIS_SENT update.");
          //All debris sent, new list becomes old list.
          swapLists();

        }
      }
        //if nothing has returned already there is no update.
        return null;
    }

    /**
     * Return any available updates.
     * @return If an outgoing update is available, return it, else return null.
     */
    public Update pollComponent(){
        Update retUpdate;
        if(outgoingUpdates.isEmpty()){
            retUpdate = null;
        } else {
            retUpdate = outgoingUpdates.removeFirst();
        }
        return retUpdate;
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
    private void addDebris(DebrisRecord objectIn){
        try {
            //TODO this will create a new debris object to put into the list.
            //TODO convert objectIn to debris record.
            //check to see if it has a match in the old list.
            newDebris.addDebris(objectIn);
            //check to see if this is a new debris or not.
        }
        catch(NullPointerException e){
            System.err.println("New Debris list not initialized: " + e.getMessage());
        }
    }

    /**
     * Get a DebrisRecord from the old list for sending.
     * @return Next DebrisRecord in the old debris list.
     */
    private DebrisRecord getDebris(){
        return oldDebris.getDebrisElement();
    }

  /**
   * For a given debris object, search the old debris record for potential matches
   * and decide which if any will be marked as a match.
   * TODO This is a stub, please implement.
   * @param debris DebrisRecord object to look for a match in.
   */
  private void checkForDebrisMatch(DebrisRecord debris){

    //Search oldDebris record for possible matches.
    //update debris with information about match.

    DebrisRecord old;

    //uses iterator for now, should probably change
    while((old = oldDebris.getDebrisElement()) != null)
    {
      double distance = debris.distanceTo(old);
      double radiusDiff = debris.radiusDifference(old);
      double radiusRatio = debris.radiusRatio(old);
      if(distance <= 10)
      {
        //confirmed match if radius increased & distance is less than max threshold
        if(radiusDiff >= 0)
        {
          debris.setId(old.getId());
          break;
        }

        //possible match if radius is smaller, but close to the old.
        else if(radiusRatio <= 0.2)
        {
          debris.setPossibleId(old.getId());
        }
      }

      //no matches were found, so a new label is created
      debris.setId(DebrisRecord.getNextID());
    }

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
     * Add an update to the outgoingUpdates queue. This will be returned to the
     * scheduler when the DebrisCollection is polled.
     * @param u Update that is ready to be returned to the scheduler.
     */
    private void queueUpdate(Update u){
        outgoingUpdates.addLast(u);
    }

    /**
     * Testing main for DebrisCollection.
     * @param args ignored.
     */
    public static void main(String[] args){

    }

  @Override
  public void addUpdateForScheduler(Update update)
  {
    queueUpdate(update);

  }

  @Override
  public void addDebrisRecord(DebrisRecord update){
    //add a debris record to list?
  }
}
