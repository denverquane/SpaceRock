package debrisProcessingSubsystem.debrisCollection;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A list of debris records. Implements DebrisList.
 * Created by jdt on 3/7/17.
 */
public class DRList implements DebrisList {

    private ArrayList<DebrisRecord> debrisList;
    private boolean sentHome; //has this list been sent back to earth yet?
  //this is to be used when debris records need to be returned one at a time.
    private Iterator<DebrisRecord> drIterator;

    public DRList(){
        debrisList = new ArrayList<>();
        sentHome = false;
        drIterator = null;
    }

    /**
     * Get a debris element from the list. Sends elements in the order determined
     * by the drIterator member. If it has sent all of the debris, the iterator
     * is reset.
     * @return The next DebrisRecord as determined by the internal iterator.
     * Returns null if there is nothing left in the list.
     */
    public DebrisRecord getDebrisElement(){
      DebrisRecord returnRecord = null;
        if(drIterator == null){
          drIterator = debrisList.iterator();
        }
        if(drIterator.hasNext()){
          returnRecord = drIterator.next();
        }
        else{
          drIterator = null;
        }

        return returnRecord;
    }

    public ArrayList<DebrisRecord> getDebrisRecords()
    {
      return debrisList;
    }

    /**
     * Get size of the DebrisList
     * @return  int: debrisList's size.
     */
    public int size(){
        return debrisList.size();
    }

    public void addDebris(DebrisRecord newDebrisRecord) throws NullPointerException{
        debrisList.add(newDebrisRecord);
    }

    public void flagAsSent(){
        sentHome = true;
    }

    public void flagAsNotSent(){
        sentHome = false;
    }

    public boolean hasBeenSentHome(){
        return sentHome;
    }

    /**
     * Is this list empty?
     * @return true if empty.
     */
    public boolean isEmpty(){
        return debrisList == null || debrisList.isEmpty();
    }
}
