package debrisProcessingSubsystem;

/**
 * Interface that should be implemented by Camera, DebrisCollection, and
 * Operator.
 * Created by jdt on 3/10/17.
 */
public interface Updatable {
    public Update updateComponent(Update theUpdate);
    public Update pollComponent();
}
