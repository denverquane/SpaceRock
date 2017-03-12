package debrisProcessingSubsystem;

/**
 * An abstract update class that holds the update type but whose functionality will be
 * defined specifically by the component type.
 * @author Nicholas Spurlok
 *
 */
public class ComponentUpdate
{
  private final UpdateType updateType;
  
  public ComponentUpdate(final UpdateType updateType)
  {
    this.updateType = updateType;
  }
  
  public UpdateType getUpdateType()
  {
    return this.updateType;
  }
}
