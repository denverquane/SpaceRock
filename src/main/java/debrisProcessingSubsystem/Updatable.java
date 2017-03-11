package debrisProcessingSubsystem;

/**
 * Interface that should be implemented by Camera, DebrisCollection, and
 * Operator.
 * The scheduler uses this to manipulate the components.
 * Created by jdt on 3/10/17.
 */
public interface Updatable {
    /**
     * The scheduler will call this method to send an update to the implementing
     * component
     * @param theUpdate An Update object that the implementing object should
     *                  know what to do with.
     * @return A response to the action in the form of an Update.
     */
    public Update updateComponent(Update theUpdate);

    /**
     * The scheduler will call this method to see if updates are available from
     * the component
     * @return A response to the poll in the form of an Update.
     */
    public Update pollComponent();
}
