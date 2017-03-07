package debrisProcessingSubsystem.debrisCollection;

import java.util.ArrayList;

/**
 * A list of debris records.
 * Created by jdt on 3/7/17.
 */
public class DRList implements DebrisList {

    private ArrayList<DebrisRecord> debrisList;

    public DRList(){
        debrisList = new ArrayList<>();
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
     * Is this list empty?
     * @return true if empty.
     */
    public boolean isEmpty(){
        return debrisList.isEmpty();
    }
}
