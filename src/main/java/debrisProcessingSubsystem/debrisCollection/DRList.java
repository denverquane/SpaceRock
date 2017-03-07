package debrisProcessingSubsystem.debrisCollection;

import java.util.ArrayList;

/**
 * A list of debris records. Implements DebrisList.
 * Created by jdt on 3/7/17.
 */
public class DRList implements DebrisList {

    private ArrayList<DebrisRecord> debrisList;
    private boolean sentHome;

    public DRList(){
        debrisList = new ArrayList<>();
        sentHome = false;
    }
    /**
     * Get a debris element from the list.
     * @return The next DebrisRecord as determined by the internal iterator.
     * TODO make this.
     */
    public DebrisRecord getDebrisElement(){
        return null;
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
